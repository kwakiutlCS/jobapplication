package pt.uc.dei.aor.project.business.service;

import pt.uc.dei.aor.project.business.util.DataModel;


public interface IReportBusinessService {

	DataModel<String, Long> generatePeriodicaAppReport(int period);

	DataModel<String, Long> generatePositionAppReport();

	DataModel<String, Long> generateSpontaneousAppReport(int period);

	DataModel<String, Long> generateInterviewTimeReport(int period);

	
	
}
