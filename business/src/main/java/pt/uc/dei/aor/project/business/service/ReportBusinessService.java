package pt.uc.dei.aor.project.business.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.INotification;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.model.IWorkerNotification;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.persistence.ICandidatePersistenceService;
import pt.uc.dei.aor.project.business.persistence.INotificationPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IReportPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.business.util.DataModel;
import pt.uc.dei.aor.project.business.util.DataPoint;
import pt.uc.dei.aor.project.business.util.EmailUtil;

@Stateless
public class ReportBusinessService implements IReportBusinessService {

	private static final String[] MONTHS = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun",
			"Jul", "Ago", "Sep", "Oct", "Nov", "Dec"};
	

	@Inject
	private EmailUtil emailUtil;
	
	
	@Inject
	private IReportPersistenceService reportPersistence;
	
	@Inject
	private IPositionPersistenceService positionPersistence;
	
	@Inject
	private IApplicationPersistenceService applicationPersistence;

	
	@Override
	public DataModel<String, Long> generatePeriodicaAppReport(int period) {
		if (period == 12) {
			return generateYearlyAppReport();
		}
		if (period == 3) {
			return generateTrimonthAppReport();
		}
		else {
			return generateMonthlyAppReport();
		}
	}

	
	private DataModel<String, Long> generateMonthlyAppReport() {
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
			
			long y = reportPersistence.generatePeriodicAppReport(startDate, finishDate);
			data.addPoint(new DataPoint<>(cal.get(Calendar.YEAR)+"/"+MONTHS[cal.get(Calendar.MONTH)], y));
			cal.add(Calendar.MONTH, 1);
		}
		
		return data;
	}


	private DataModel<String, Long> generateYearlyAppReport() {
		int year = (Calendar.getInstance().get(Calendar.YEAR));
		DataModel<String, Long> data = new DataModel<>();
		
		for (int i = 6; i >= 0; i--) {
			Calendar cal = Calendar.getInstance();
			cal.set(year-i, 0, 0, 0, 0, 0);
			Date startDate = cal.getTime();
			cal.add(Calendar.YEAR, 1);
			Date finishDate = cal.getTime();
			
			long y = reportPersistence.generatePeriodicAppReport(startDate, finishDate);
			data.addPoint(new DataPoint<>(cal.get(Calendar.YEAR)+"", y));
		}
		
		return data;
	}
	
	private DataModel<String, Long> generateTrimonthAppReport() {
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
			
			long y = reportPersistence.generatePeriodicAppReport(startDate, finishDate);
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
}
