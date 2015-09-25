package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import pt.uc.dei.aor.project.business.util.DataModel;
import pt.uc.dei.aor.project.business.util.DataPoint;

@Named
@ViewScoped
public class ChartBean implements Serializable {
	
	private static final long serialVersionUID = -1535787061220132256L;
	private BarChartModel barModel;
   
   
    public BarChartModel getBarModel() {
    	return barModel;
    }
     
   
    private BarChartModel init(DataModel<String, Long> data, String label) {
        barModel = new BarChartModel();
 
        ChartSeries series = new ChartSeries();
        series.setLabel(label);
        
        for (DataPoint<String, Long> p : data.getPoints()) {
        	series.set(p.getX(), p.getY());
        }
       
        barModel.addSeries(series);
         
        return barModel;
    }
     
       
    
    public void createChart(DataModel<String, Long> data, String label, String title,
    		String axisX, String axisY) {
    	
        barModel = init(data, label);
         
        barModel.setTitle(title);
        barModel.setLegendPosition("ne");
         
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel(axisX);
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel(axisY);
        yAxis.setMin(0);
        
        long max = 0;
        for (DataPoint<String, Long> p : data.getPoints()) {
        	max = Math.max(max, p.getY());
        }
        yAxis.setMax(max);
    }
     
   
}
