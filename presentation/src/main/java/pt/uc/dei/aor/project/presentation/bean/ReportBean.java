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

	private DataModel<String, Long> periodicAppModel;
	private int period;
	private boolean periodicAppVisible;

	private DataModel<String, Long> positionAppModel;
	private boolean positionAppVisible;
	
	


	public void generatePeriodicApp() {
		periodicAppModel = reportService.generatePeriodicaAppReport(period);
		periodicAppVisible = true;
		chart.createChart(periodicAppModel, "", "", "", "Applications");
	}
	
	public void generatePositionApp() {
		setPositionAppModel(reportService.generatePositionAppReport());
		setPositionAppVisible(true);
		chart.createChart(positionAppModel, "", "", "", "Applications");
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



}
