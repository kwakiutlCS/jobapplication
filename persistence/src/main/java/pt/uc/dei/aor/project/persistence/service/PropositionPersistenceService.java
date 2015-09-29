package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.IProposition;
import pt.uc.dei.aor.project.business.persistence.IPropositionPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.JobProposalEntity;
import pt.uc.dei.aor.project.persistence.proxy.PropositionProxy;

@Stateless
public class PropositionPersistenceService implements IPropositionPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	
	@Override
	public List<IProposition> findPropositionsByDate(Date startDate) {
		TypedQuery<JobProposalEntity> query = em.createNamedQuery("Proposition.findByDate", 
				JobProposalEntity.class);
		query.setParameter("startDate", startDate);
		
		List<JobProposalEntity> entities = query.getResultList();
		List<IProposition> proxies = new ArrayList<>();
		
		for (JobProposalEntity pe : entities) {
			proxies.add(new PropositionProxy(pe));
		}
		
		return proxies;
	}

	
	
}
