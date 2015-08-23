package pt.uc.dei.aor.project.persistence.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.persistence.IScriptEntryPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntryEntity;
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;
import pt.uc.dei.aor.project.persistence.proxy.ScriptEntryProxy;

@Stateless
public class ScriptEntryPersistenceService implements IScriptEntryPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;
	
	
	
	@Override
	public IScriptEntry save(IScriptEntry entry) {
		ScriptEntryEntity entity = getEntity(entry);
		entity = em.merge(entity);
		return new ScriptEntryProxy(entity);
	}
	
	@Override
	public void delete(IScriptEntry entry) {
		ScriptEntryEntity entity = getEntity(entry);
		em.remove(em.merge(entity));
	}

		
	@SuppressWarnings("unchecked")
    private ScriptEntryEntity getEntity(IScriptEntry scriptEntryProxy) {
        if (scriptEntryProxy instanceof IProxyToEntity<?>) {
            return ((IProxyToEntity<ScriptEntryEntity>) scriptEntryProxy).getEntity();
        }

        throw new IllegalStateException();
    }

	
	
}
