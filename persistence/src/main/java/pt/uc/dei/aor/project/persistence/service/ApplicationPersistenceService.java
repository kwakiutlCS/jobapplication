package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.ICandidate;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.business.persistence.ICandidatePersistenceService;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.CandidateEntity;
import pt.uc.dei.aor.project.persistence.proxy.ApplicationProxy;
import pt.uc.dei.aor.project.persistence.proxy.CandidateProxy;
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;

@Stateless
public class ApplicationPersistenceService implements IApplicationPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	@Override
	public List<IApplication> dummyQuery() {
		TypedQuery<ApplicationEntity> q = em.createNamedQuery("Application.dummyQuery", 
				ApplicationEntity.class);
		
		List<ApplicationEntity> entities = q.getResultList();
		List<IApplication> proxies = new ArrayList<>();
		
		for (ApplicationEntity ae : entities) {
			proxies.add(new ApplicationProxy(ae));
		}
		
		return proxies;
	}
	
	
	
	

}
