package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.service.IReportBusinessService;
import pt.uc.dei.aor.project.business.util.DataModel;

@Named
@ViewScoped
public class ReportBean implements Serializable {

	private static final long serialVersionUID = -785077512441625997L;
	
	private static final Logger logger = LoggerFactory.getLogger(ReportBean.class);
	
	@Inject
	private IReportBusinessService reportService;
	
	@Inject
	private ChartBean chart;
	
	private int period;

	private DataModel<String, Long> periodicAppModel;
	private boolean periodicAppVisible;

	private DataModel<String, Long> positionAppModel;
	private boolean positionAppVisible;
	
	private DataModel<String, Long> spontaneousAppModel;
	private boolean spontaneousAppVisible;
	
	private DataModel<String, Long> rejectedCandidatesModel;
	private boolean rejectedCandidatesVisible;
	
	private DataModel<String, Long> interviewReportModel;
	private boolean interviewReportVisible;
	
	private DataModel<String, Long> interviewTimeModel;
	private boolean interviewTimeVisible;
	
	
	public void generatePeriodicApp() {
		periodicAppModel = reportService.generatePeriodicaAppReport(period);
		periodicAppVisible = true;
		chart.createChart(periodicAppModel, "Applications", "", "", "Applications");
	}
	
	public void generatePositionApp() {
		setPositionAppModel(reportService.generatePositionAppReport());
		setPositionAppVisible(true);
		chart.createChart(positionAppModel, "Applications", "", "", "Applications");
	}
	
	public void generateSpontaneousApp() {
		setSpontaneousAppModel(reportService.generateSpontaneousAppReport(period));
		setSpontaneousAppVisible(true);
		chart.createChart(spontaneousAppModel, "Applications", "", "", "Applications");
	}
	
	public void generateRejectedCandidates() {
		setRejectedCandidatesModel(reportService.generateRejectedCandidatesReport(period));
		setRejectedCandidatesVisible(true);
		chart.createChart(rejectedCandidatesModel, "Applications", "", "", "Applications");
	}
	
	
	public void generateInterviewReport() {
		setInterviewReportModel(reportService.generateInterviewReport(period));
		setInterviewReportVisible(true);
		chart.createChart(interviewReportModel, "Interviews", "", "", "Interviews");
	}
	
	
	public void generateInterviewTime() {
		setInterviewTimeModel(reportService.generateInterviewTimeReport(period));
		setInterviewTimeVisible(true);
		chart.createChart(spontaneousAppModel, "Applications", "", "", "Applications");
	}
	
	
	
	public int getPeriod() {
		return period;
	}
	
	public void setPeriod(int period) {
		this.period = period;
	}



	public boolean isPeriodicAppVisible() {
		return periodicAppVisible;
	}



	public void setPeriodicAppVisible(boolean periodicAppVisible) {
		this.periodicAppVisible = periodicAppVisible;
	}



	public DataModel<String, Long> getPeriodicAppModel() {
		return periodicAppModel;
	}

	public DataModel<String, Long> getPositionAppModel() {
		return positionAppModel;
	}

	public void setPositionAppModel(DataModel<String, Long> positionAppModel) {
		this.positionAppModel = positionAppModel;
	}

	public boolean isPositionAppVisible() {
		return positionAppVisible;
	}

	public void setPositionAppVisible(boolean positionAppVisible) {
		this.positionAppVisible = positionAppVisible;
	}

	public DataModel<String, Long> getSpontaneousAppModel() {
		return spontaneousAppModel;
	}

	public void setSpontaneousAppModel(DataModel<String, Long> spontaneousAppModel) {
		this.spontaneousAppModel = spontaneousAppModel;
	}

	public boolean isSpontaneousAppVisible() {
		return spontaneousAppVisible;
	}

	public void setSpontaneousAppVisible(boolean spontaneousAppVisible) {
		this.spontaneousAppVisible = spontaneousAppVisible;
	}

	public DataModel<String, Long> getInterviewTimeModel() {
		return interviewTimeModel;
	}

	public void setInterviewTimeModel(DataModel<String, Long> interviewTimeModel) {
		this.interviewTimeModel = interviewTimeModel;
	}

	public boolean isInterviewTimeVisible() {
		return interviewTimeVisible;
	}

	public void setInterviewTimeVisible(boolean interviewTimeVisible) {
		this.interviewTimeVisible = interviewTimeVisible;
	}

	public DataModel<String, Long> getRejectedCandidatesModel() {
		return rejectedCandidatesModel;
	}

	public void setRejectedCandidatesModel(DataModel<String, Long> rejectedCandidatesModel) {
		this.rejectedCandidatesModel = rejectedCandidatesModel;
	}

	public boolean isRejectedCandidatesVisible() {
		return rejectedCandidatesVisible;
	}

	public void setRejectedCandidatesVisible(boolean rejectedCandidatesVisible) {
		this.rejectedCandidatesVisible = rejectedCandidatesVisible;
	}

	public DataModel<String, Long> getInterviewReportModel() {
		return interviewReportModel;
	}

	public void setInterviewReportModel(DataModel<String, Long> interviewReportModel) {
		this.interviewReportModel = interviewReportModel;
	}

	public boolean isInterviewReportVisible() {
		return interviewReportVisible;
	}

	public void setInterviewReportVisible(boolean interviewReportVisible) {
		this.interviewReportVisible = interviewReportVisible;
	}

	
}
