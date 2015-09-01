package pt.uc.dei.aor.project.persistence.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pt.uc.dei.aor.project.business.model.IAnswerChoice;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.persistence.IAnswerChoicePersistenceService;
import pt.uc.dei.aor.project.business.persistence.IScriptEntryPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.AnswerChoiceEntity;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntryEntity;
import pt.uc.dei.aor.project.persistence.proxy.AnswerChoiceProxy;
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;
import pt.uc.dei.aor.project.persistence.proxy.ScriptEntryProxy;

@Stateless
public class AnswerChoicePersistenceService implements IAnswerChoicePersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;
	
	
	
	@Override
	public IAnswerChoice save(IAnswerChoice answer) {
		AnswerChoiceEntity entity = getEntity(answer);
		entity = em.merge(entity);
		
		return new AnswerChoiceProxy(entity);
	}
	
	@SuppressWarnings("unchecked")
    private AnswerChoiceEntity getEntity(IAnswerChoice answerChoiceProxy) {
        if (answerChoiceProxy instanceof IProxyToEntity<?>) {
            return ((IProxyToEntity<AnswerChoiceEntity>) answerChoiceProxy).getEntity();
        }

        throw new IllegalStateException();
    }

}
