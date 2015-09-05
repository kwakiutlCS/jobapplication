package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.persistence.IAnswerPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.AnswerEntity;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
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
	
	@Override
	public IAnswer findAnswerByInterviewAndQuestion(IInterview interview, String question) {
		TypedQuery<AnswerEntity> q = em.createNamedQuery("Answer.findAnswerByInterviewAndQuestion", 
				AnswerEntity.class);
		q.setParameter("question", question);
		InterviewEntity interviewEntity = GenericPersistenceService.getEntity(interview);
		q.setParameter("interview", interviewEntity);
		
		List<AnswerEntity> entities = q.getResultList();
		
		if (entities.isEmpty()) return null;
		return new AnswerProxy(entities.get(0));
		
	}

	@Override
	public List<IAnswer> findAnswersByInterview(IInterview interview) {
		TypedQuery<AnswerEntity> q = em.createNamedQuery("Answer.findAnswersByInterview", 
				AnswerEntity.class);
		InterviewEntity interviewEntity = GenericPersistenceService.getEntity(interview);
		q.setParameter("interview", interviewEntity);
		
		List<AnswerEntity> entities = q.getResultList();
		
		List<IAnswer> proxies = new ArrayList<>();
		
		for (AnswerEntity ae : entities) {
			proxies.add(new AnswerProxy(ae));
		}
		
		return proxies;
	}
}
