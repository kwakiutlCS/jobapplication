package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.model.INotification;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.model.IWorkerNotification;
import pt.uc.dei.aor.project.business.persistence.INotificationPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IReportPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.WorkerNotificationEntity;
import pt.uc.dei.aor.project.persistence.proxy.WorkerNotificationProxy;

@Stateless
public class ReportPersistenceService implements IReportPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	
	@Override
	public long generatePeriodicAppReport(Date startDate, Date finishDate) {
		TypedQuery<Long> query = em.createNamedQuery("Application.numberCandidatesByPeriod", Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("finishDate", finishDate);
		
		List<Long> result = query.getResultList();
		
		if (result.isEmpty()) return 0;
		return result.get(0);
	}


	@Override
	public long generateSpontaneousAppReport(Date startDate, Date finishDate) {
		TypedQuery<Long> query = em.createNamedQuery("Application.numberSpontaneousByPeriod", Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("finishDate", finishDate);
		
		List<Long> result = query.getResultList();
		
		if (result.isEmpty()) return 0;
		return result.get(0);
	}

	


}
