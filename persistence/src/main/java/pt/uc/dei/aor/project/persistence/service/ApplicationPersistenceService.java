package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.persistence.IApplicationPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.proxy.ApplicationProxy;
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;

@Stateless
public class ApplicationPersistenceService implements IApplicationPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	// query provisoria - para ser apagada
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

	@Override
	public IApplication save(IApplication application) {
		ApplicationEntity entity = GenericPersistenceService.getEntity(application);
		
		entity = em.merge(entity);
		
		return new ApplicationProxy(entity);
	}
	
	
	@Override
	public IApplication find(long id) {
		ApplicationEntity entity = em.find(ApplicationEntity.class, id);
		return new ApplicationProxy(entity);
	}
	
	
}
