package pt.uc.dei.aor.project.business.persistence;

import java.util.Date;

import pt.uc.dei.aor.project.business.model.IAnswerChoice;

public interface IReportPersistenceService {

	long generatePeriodicAppReport(Date startDate, Date finishDate);

	long generateSpontaneousAppReport(Date startDate, Date finishDate);

}
