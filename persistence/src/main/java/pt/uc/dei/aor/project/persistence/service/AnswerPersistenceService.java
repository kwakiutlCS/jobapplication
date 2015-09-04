package pt.uc.dei.aor.project.persistence.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.persistence.IAnswerPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.AnswerEntity;
import pt.uc.dei.aor.project.persistence.proxy.AnswerProxy;

@Stateless
public class AnswerPersistenceService implements IAnswerPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	
	public IAnswer save(IAnswer answer) {
		AnswerEntity entity = GenericPersistenceService.getEntity(answer);
		
		entity = em.merge(entity);
		
		return new AnswerProxy(entity);
	}
	
}
