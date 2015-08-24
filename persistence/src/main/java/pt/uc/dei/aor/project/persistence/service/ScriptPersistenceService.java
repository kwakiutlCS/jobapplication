package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.persistence.IScriptPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntity;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntryEntity;
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;
import pt.uc.dei.aor.project.persistence.proxy.ScriptProxy;

@Stateless
public class ScriptPersistenceService implements IScriptPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;
	
	
	
	@Override
	public IScript save(IScript script) {
		ScriptEntity entity = getEntity(script);
		entity = em.merge(entity);
		return new ScriptProxy(entity);
	}
	
	@Override
	public void delete(IScript script) {
		ScriptEntity entity = getEntity(script);
		em.remove(em.merge(entity));
	}

		
	@Override
	public List<IScript> findAllScripts() {
		TypedQuery<ScriptEntity> query = em.createNamedQuery("Script.findAllScripts", ScriptEntity.class);
		
		List<ScriptEntity> entities = query.getResultList();
		
		List<IScript> proxies = new ArrayList<>();
		for (ScriptEntity se : entities) {
			proxies.add(new ScriptProxy(se));
		}
		
		return proxies;
	}
	
		
	@SuppressWarnings("unchecked")
    private ScriptEntity getEntity(IScript scriptProxy) {
        if (scriptProxy instanceof IProxyToEntity<?>) {
            return ((IProxyToEntity<ScriptEntity>) scriptProxy).getEntity();
        }

        throw new IllegalStateException();
    }

	
}
