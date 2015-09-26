package pt.uc.dei.aor.project.presentation.bean;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
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
	
	private DataModel<String, Long> interviewTimeModel;
	private boolean interviewTimeVisible;
	
	private String base64Str;


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

	public String getBase64Str() {
		return base64Str;
	}
	
	public void setBase64Str(String x) {
		base64Str = x;
	}
	
	public void submittedBase64Str(ActionEvent event){
	    // You probably want to have a more comprehensive check here. 
	    // In this example I only use a simple check
		System.out.println("here");
		System.out.println(base64Str);
		System.out.println(Arrays.toString(base64Str.split(",")));
	    if(base64Str.split(",").length > 1){
	        String encoded = base64Str.split(",")[1];
	        byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(encoded);
	        // Write to a .png file
	        try {
	            RenderedImage renderedImage = ImageIO.read(new ByteArrayInputStream(decoded));
	            System.out.println(renderedImage);
	            ImageIO.write(renderedImage, "png", new File("/home/ricardo/a.png")); // use a proper path & file name here.
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
