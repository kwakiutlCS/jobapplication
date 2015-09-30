package pt.uc.dei.aor.project.business.persistence;

import java.util.Date;
import java.util.List;

import pt.uc.dei.aor.project.business.model.IApplication;

public interface IReportPersistenceService {

	long generatePeriodicAppReport(Date startDate, Date finishDate);

	long generateSpontaneousAppReport(Date startDate, Date finishDate);

	List<IApplication> findAllCloseApplicationsByDate(Date startDate);

	List<IApplication> findRejectedByDate(Date startDate);

}
