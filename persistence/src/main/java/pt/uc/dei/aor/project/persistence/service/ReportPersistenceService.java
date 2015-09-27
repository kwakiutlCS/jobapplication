package pt.uc.dei.aor.project.persistence.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.persistence.IReportPersistenceService;

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
