package pt.uc.dei.aor.project.persistence.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
		System.out.println("PERSISTENCE");
		ScriptEntryEntity entity = getEntity(entry);
		System.out.println("QUESTION: "+entity.getQuestion());
		System.out.println("ANSWERS: "+entity.getQuestion().getAnswers());
		entity = em.merge(entity);
		System.out.println("ANSWERS: "+entity.getQuestion().getAnswers());
		
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
