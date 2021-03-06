package pt.uc.dei.aor.project.business.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Days;

import pt.uc.dei.aor.project.business.exception.AllPhasesCompletedException;
import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IProposition;
import pt.uc.dei.aor.project.business.persistence.IAnswerPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IInterviewPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IPropositionPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IReportPersistenceService;
import pt.uc.dei.aor.project.business.util.DataModel;
import pt.uc.dei.aor.project.business.util.DataPoint;
import pt.uc.dei.aor.project.business.util.ProposalSituation;

@Stateless
public class ReportBusinessService implements IReportBusinessService {
	
	private static final String[] MONTHS = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun",
			"Jul", "Ago", "Sep", "Oct", "Nov", "Dec"};
	
		
	
	@Inject
	private IReportPersistenceService reportPersistence;
	
	@Inject
	private IPositionPersistenceService positionPersistence;
	
	@Inject
	private IApplicationPersistenceService applicationPersistence;
	
	@Inject 
	private IAnswerPersistenceService answerPersistence;
	
	@Inject
	private IInterviewPersistenceService interviewPersistence;

	@Inject
	private IPropositionPersistenceService propositionPersistence;
	
	
	@Override
	public DataModel<String, Long> generatePeriodicaAppReport(int period) {
		if (period == 12) {
			return generateYearlyAppReport(false);
		}
		if (period == 3) {
			return generateTrimonthAppReport(false);
		}
		else {
			return generateMonthlyAppReport(false);
		}
	}

	@Override
	public DataModel<String, Long> generateSpontaneousAppReport(int period) {
		if (period == 12) {
			return generateYearlyAppReport(true);
		}
		if (period == 3) {
			return generateTrimonthAppReport(true);
		}
		else {
			return generateMonthlyAppReport(true);
		}
	}
	
	private DataModel<String, Long> generateMonthlyAppReport(boolean spontaneous) {
		DataModel<String, Long> data = new DataModel<>();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.add(Calendar.MONTH, -11);
		
		for (int i = 0; i < 12; i++) {
			Calendar finish = Calendar.getInstance();
			finish.setTime(cal.getTime());
			finish.add(Calendar.MONTH, 1);
			Date startDate = cal.getTime();
			Date finishDate = finish.getTime();
			
			long y;
			
			if (spontaneous) y = reportPersistence.generateSpontaneousAppReport(startDate, finishDate);
			else y = reportPersistence.generatePeriodicAppReport(startDate, finishDate);
			data.addPoint(new DataPoint<>(cal.get(Calendar.YEAR)+"/"+MONTHS[cal.get(Calendar.MONTH)], y));
			cal.add(Calendar.MONTH, 1);
		}
		
		return data;
	}


	private DataModel<String, Long> generateYearlyAppReport(boolean spontaneous) {
		int year = (Calendar.getInstance().get(Calendar.YEAR));
		DataModel<String, Long> data = new DataModel<>();
		
		for (int i = 6; i >= 0; i--) {
			Calendar cal = Calendar.getInstance();
			cal.set(year-i, 0, 0, 0, 0, 0);
			Date startDate = cal.getTime();
			cal.add(Calendar.YEAR, 1);
			Date finishDate = cal.getTime();
			
			long y;
			
			if (spontaneous) y =reportPersistence.generateSpontaneousAppReport(startDate, finishDate);
			else y = reportPersistence.generatePeriodicAppReport(startDate, finishDate);
			data.addPoint(new DataPoint<>(cal.get(Calendar.YEAR)+"", y));
		}
		
		return data;
	}
	
	private DataModel<String, Long> generateTrimonthAppReport(boolean spontaneous) {
		DataModel<String, Long> data = new DataModel<>();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)/3*3);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.add(Calendar.MONTH, -27);
		
		for (int i = 0; i < 10; i++) {
			Calendar finish = Calendar.getInstance();
			finish.setTime(cal.getTime());
			finish.add(Calendar.MONTH, 3);
			Date startDate = cal.getTime();
			Date finishDate = finish.getTime();
			
			long y;
			
			if (spontaneous) y =reportPersistence.generateSpontaneousAppReport(startDate, finishDate);
			else y = reportPersistence.generatePeriodicAppReport(startDate, finishDate);
			data.addPoint(new DataPoint<>(cal.get(Calendar.YEAR)+"/"+MONTHS[cal.get(Calendar.MONTH)], y));
			cal.add(Calendar.MONTH, 3);
		}
		
		return data;
	}


	@Override
	public DataModel<String, Long> generatePositionAppReport() {
		List<IPosition> positions = positionPersistence.findOpenPositions();
		
		DataModel<String, Long> model = new DataModel<>();
		for (IPosition p : positions) {
			model.addPoint(new DataPoint<>(p.getTitle(), 
					applicationPersistence.findApplicationsByPosition(p)));
		}
		
		return model;
	}

	@Override
	public DataModel<String, Long> generateInterviewTimeReport() {
		DataModel<String, Long> data = new DataModel<>();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.add(Calendar.MONTH, -11);
		
		for (int i = 0; i < 12; i++) {
			Calendar finish = Calendar.getInstance();
			finish.setTime(cal.getTime());
			finish.add(Calendar.MONTH, 1);
			Date startDate = cal.getTime();
			Date finishDate = finish.getTime();
			
			List<IInterview> interviews = interviewPersistence.findInterviewsByDate(startDate, finishDate);
			
			int counter = 0;
			int total = 0;
			for (IInterview interview : interviews) {
				try {
					if (interview.getInterviewPhase() == 1) {
						DateTime iDate = new DateTime(interview.getDateObject());
						DateTime aDate = new DateTime(interview.getApplication().getDateObject());
						total += Days.daysBetween(aDate , iDate).getDays();
						counter++;
					}
				} catch (AllPhasesCompletedException e) {
					
				}
			}
			
			if (counter == 0)
				total = 0;
			else total /= counter;
			data.addPoint(new DataPoint<>(cal.get(Calendar.YEAR)+"/"+MONTHS[cal.get(Calendar.MONTH)], (long) total));
			cal.add(Calendar.MONTH, 1);
		}
		
		return data;
		
	}

	@Override
	public DataModel<String, Long> generateHiringTimeReport() {
		DataModel<String, Long> data = new DataModel<>();
		System.out.println("generating report");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.add(Calendar.MONTH, -11);
		
		for (int i = 0; i < 12; i++) {
			Calendar finish = Calendar.getInstance();
			finish.setTime(cal.getTime());
			finish.add(Calendar.MONTH, 1);
			Date startDate = cal.getTime();
			Date finishDate = finish.getTime();
			System.out.println(startDate);
			List<IApplication> applications = applicationPersistence.findApplicationsByDate(startDate, finishDate);
			System.out.println(applications.size());
			int counter = 0;
			int total = 0;
			for (IApplication app : applications) {
				System.out.println(app.isProposed());
				if (app.isProposed()) {
					DateTime iDate = new DateTime(app.getPropositionDate());
					DateTime aDate = new DateTime(app.getDateObject());
					total += Days.daysBetween(aDate , iDate).getDays();
					counter++;
				}
			}
			
			if (counter == 0)
				total = 0;
			else total /= counter;
			data.addPoint(new DataPoint<>(cal.get(Calendar.YEAR)+"/"+MONTHS[cal.get(Calendar.MONTH)], (long) total));
			cal.add(Calendar.MONTH, 1);
		}
		
		return data;
		
	}
	
	@Override
	public DataModel<String, Long> generateRejectedCandidatesReport(int period) {
		List<IApplication> applications;
		Date startDate;
		Calendar cal = Calendar.getInstance();
		
		if (period == 3) {
			cal.add(Calendar.MONTH, -3);
		}
		else if (period == 12) {
			cal.add(Calendar.YEAR, -1);
		}
		else {
			cal.add(Calendar.YEAR, -1000); // all applications
		}
		
		startDate = cal.getTime();
		
		applications = reportPersistence.findRejectedByDate(startDate);
		
		long rejected = 0;
		long interviewed = 0;
		long missedInterview = 0;
		
		for (IApplication app : applications) {
			if (app.getInterviews().isEmpty()) rejected++;
			else {
				boolean missed = false;
				for (IInterview i : app.getInterviews()) {
					List<IAnswer> answers = answerPersistence.findAnswersByInterview(i);
					if (answers.isEmpty()) {
						missedInterview++;
						missed = true;
						break;
					}
				}
				if (!missed) interviewed++;
			}
		}
		
		
		DataModel<String, Long> model = new DataModel<>();
		model.addPoint(new DataPoint<String, Long>("Curriculum analysis", rejected));
		model.addPoint(new DataPoint<String, Long>("Interview unattended", missedInterview));
		model.addPoint(new DataPoint<String, Long>("Interview analysis", interviewed));
		
		return model;
	}

	@Override
	public DataModel<String, Long> generateInterviewReport(int period) {
		Date startDate;
		Calendar cal = Calendar.getInstance();
		
		if (period == 3) {
			cal.add(Calendar.MONTH, -3);
		}
		else if (period == 12) {
			cal.add(Calendar.YEAR, -1);
		}
		else {
			cal.add(Calendar.YEAR, -1000); // all applications
		}
		
		startDate = cal.getTime();
		
		List<IInterview> interviews = interviewPersistence.
				findInterviewsByClosedPositionAndDate(startDate);
		
		long refused = 0;
		long nextPhase = 0;
		long accepted = 0;
		long missed = 0;
		
		for (IInterview i : interviews) {
			List<IAnswer> answers = answerPersistence.findAnswersByInterview(i);
			if (answers.size() <= 1) missed++;
			else {
				if (isFinalInterview(i)) {
					if (i.getApplication().isRefused()) refused++;
					else accepted++;
				}
				else nextPhase++;
			}
		}
		
		DataModel<String, Long> model = new DataModel<>();
		model.addPoint(new DataPoint<String, Long>("Application rejected", refused));
		model.addPoint(new DataPoint<String, Long>("Application accepted", accepted));
		model.addPoint(new DataPoint<String, Long>("Proceed next phase", nextPhase));
		model.addPoint(new DataPoint<String, Long>("Interview unattended", missed));
		
		return model;
	}
	
	
	private boolean isFinalInterview(IInterview interview) {
		IApplication app = interview.getApplication();
		
		List<IInterview> interviews = interviewPersistence.findInterviewsByApplication(app);
		
		if (interviews.size() == 1) return true;
		
		Calendar interviewCal = Calendar.getInstance();
		interviewCal.setTime(interview.getDateObject());
		Calendar otherCal = Calendar.getInstance();
		for (IInterview i : interviews) {
			otherCal.setTime(i.getDateObject());
			if (otherCal.after(interviewCal)) return true;
		}
		
		return false;
	}

	@Override
	public DataModel<String, Long> generateProposalReport(int period) {
		Date startDate;
		Calendar cal = Calendar.getInstance();
		
		if (period == 3) {
			cal.add(Calendar.MONTH, -3);
		}
		else if (period == 12) {
			cal.add(Calendar.YEAR, -1);
		}
		else {
			cal.add(Calendar.YEAR, -1000); // all applications
		}
		
		startDate = cal.getTime();
		
		List<IProposition> proposals = propositionPersistence.findPropositionsByDate(startDate);
		
		long unanswered = 0;
		long refused = 0;
		long accepted = 0;
		
		for (IProposition p : proposals) {
			if (p.getState() == ProposalSituation.ACCEPTED) accepted++;
			else if (p.getState() == ProposalSituation.REFUSED) refused++;
			else if (p.getState() == ProposalSituation.ONHOLD) unanswered++;
		}
		
		DataModel<String, Long> model = new DataModel<>();
		model.addPoint(new DataPoint<String, Long>("rejected", refused/2));
		model.addPoint(new DataPoint<String, Long>("accepted", accepted/2));
		model.addPoint(new DataPoint<String, Long>("unanswered", unanswered/2));
		
		return model;
	}
}
