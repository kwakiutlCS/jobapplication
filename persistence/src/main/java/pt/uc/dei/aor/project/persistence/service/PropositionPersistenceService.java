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
	public IProposition save(IProposition proxy) {
		JobProposalEntity entity = GenericPersistenceService.getEntity(proxy);
		entity = em.merge(entity);
		
		return new PropositionProxy(entity);
	}
	
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

	@Override
	public List<IProposition> findOrphanPropositions() {
		TypedQuery<JobProposalEntity> query = em.createNamedQuery("Proposition.findOrphans", 
				JobProposalEntity.class);
		
		List<JobProposalEntity> entities = query.getResultList();
		List<IProposition> proxies = new ArrayList<>();
		
		for (JobProposalEntity pe : entities) {
			proxies.add(new PropositionProxy(pe));
		}
		
		return proxies;
	}
	
	@Override
	public List<IProposition> findAll() {
		TypedQuery<JobProposalEntity> query = em.createNamedQuery("Proposition.findAll", 
				JobProposalEntity.class);
		
		List<JobProposalEntity> entities = query.getResultList();
		List<IProposition> proxies = new ArrayList<>();
		
		for (JobProposalEntity pe : entities) {
			proxies.add(new PropositionProxy(pe));
		}
		
		return proxies;
	}
	
	@Override
	public void remove(IProposition proposition) {
		em.remove(em.merge(GenericPersistenceService.getEntity(proposition)));
	}

	@Override
	public List<IProposition> findHiringsByDate(Date startDate, Date finishDate) {
		TypedQuery<JobProposalEntity> query = em.createNamedQuery("Proposition.findHiringsByDate", 
				JobProposalEntity.class);
		query.setParameter("startDate", startDate);
		query.setParameter("finishDate", finishDate);
		
		List<JobProposalEntity> entities = query.getResultList();
		List<IProposition> proxies = new ArrayList<>();
		
		for (JobProposalEntity pe : entities) {
			proxies.add(new PropositionProxy(pe));
		}
		
		return proxies;
	}
	
}
