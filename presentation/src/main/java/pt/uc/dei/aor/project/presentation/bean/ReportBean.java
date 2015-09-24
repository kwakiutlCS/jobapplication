package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.service.IReportBusinessService;
import pt.uc.dei.aor.project.business.util.DataModel;
import pt.uc.dei.aor.project.business.util.DataPoint;

@Named
@RequestScoped
public class ReportBean implements Serializable {

	private static final long serialVersionUID = -785077512441625997L;
	
	private static final Logger logger = LoggerFactory.getLogger(ReportBean.class);
	
	@Inject
	private IReportBusinessService reportService;

	private int period;
	
	private LineChartModel periodicAppModel;
	private boolean periodicAppVisible;

	
	
	
	public LineChartModel getPeriodicAppModel() {
		return periodicAppModel;
	}



	public void generatePeriodicApp() {
		System.out.println(reportService.generatePeriodicaAppReport(period));
		addDataToModel(periodicAppModel, reportService.generatePeriodicaAppReport(period));
		periodicAppVisible = true;
	}
	
	
	
	private void addDataToModel(LineChartModel model, DataModel<String, Long> data) {
		model = new LineChartModel();
		 System.out.println(model);
        ChartSeries series = new ChartSeries();
        series.setLabel("Applications");
        System.out.println(series);
        int c = 1;
        for (DataPoint<String, Long> p : data.getPoints()) {
        	series.set(c++, p.getY());
        	System.out.println(p);
        }
        
        model.addSeries(series);
        
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
        
        model.setTitle("Category Chart");
        model.setLegendPosition("e");
        model.setShowPointLabels(true);
        model.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(200);
        System.out.println("done");
     }
	
	
	
	
	public void setPeriodicAppModel(LineChartModel periodicAppModel) {
		this.periodicAppModel = periodicAppModel;
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

	
}
