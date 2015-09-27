package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pt.uc.dei.aor.project.business.filter.WorkerFilter;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.IUserPersistenceService;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.UserEntity;
import pt.uc.dei.aor.project.persistence.proxy.UserProxy;

@Stateless
public class UserPersistenceService implements IUserPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;
	
	@Override
	public IUser save(IUser worker) {
		UserEntity entity = GenericPersistenceService.getEntity(worker);
		
		entity = em.merge(entity);
		
		return new UserProxy(entity);
	}
	
	@Override
	public void delete(IUser worker) {
		UserEntity entity = GenericPersistenceService.getEntity(worker); 
		
		em.remove(em.merge(entity));
	}

	
	@Override
	public IUser getUserByLogin(String login) {
		TypedQuery<UserEntity> query = em.createNamedQuery("User.findUserByLogin", UserEntity.class);
		query.setParameter("login", login);
		
		List<UserEntity> entities = query.getResultList();
		
		if (!entities.isEmpty()) {
			return new UserProxy(entities.get(0));
		}
		
		return null;
	}
	

	@Override
	public IUser getUserByEmail(String email) {
		TypedQuery<UserEntity> query = em.createNamedQuery("User.findUserByEmail", UserEntity.class);
		query.setParameter("email", email);
		
		List<UserEntity> entities = query.getResultList();
		
		if (!entities.isEmpty()) {
			return new UserProxy(entities.get(0));
		}
		
		return null;
	}
	
	@Override
	public List<IUser> findAllUsers() {
		TypedQuery<UserEntity> query = em.createNamedQuery("User.findAllWorkers", UserEntity.class);
		
		List<UserEntity> entities = query.getResultList();
		List<IUser> proxies = new ArrayList<>();
		
		for (UserEntity we : entities) {
			if (!we.getEmail().equals("SU"))
				proxies.add(new UserProxy(we));
		}
		
		return proxies;
	}

	@Override
	public Collection<IUser> findAllAdmins() {
		TypedQuery<UserEntity> query = em.createNamedQuery("User.findAllAdmins", UserEntity.class);
		
		List<UserEntity> entities = query.getResultList();
		List<IUser> proxies = new ArrayList<>();
		
		for (UserEntity we : entities) {
			proxies.add(new UserProxy(we));
		}
		
		return proxies;
	}

	@Override
	public Collection<IUser> findAllManagers() {
		TypedQuery<UserEntity> query = em.createNamedQuery("User.findAllManagers", UserEntity.class);
		
		List<UserEntity> entities = query.getResultList();
		List<IUser> proxies = new ArrayList<>();
		
		for (UserEntity we : entities) {
			proxies.add(new UserProxy(we));
		}
		
		return proxies;
	}

	@Override
	public Collection<IUser> findAllInterviewers() {
		TypedQuery<UserEntity> query = em.createNamedQuery("User.findAllInterviewers", UserEntity.class);
		
		List<UserEntity> entities = query.getResultList();
		List<IUser> proxies = new ArrayList<>();
		
		for (UserEntity we : entities) {
			proxies.add(new UserProxy(we));
		}
		
		return proxies;
	}

	
	@Override
	public void insertInterview(long worker_id, IInterview interview) {
		UserEntity entity = em.find(UserEntity.class, worker_id);
		entity.addInterview(GenericPersistenceService.getEntity(interview));
		em.merge(entity);
	}
	
	@Override
	public boolean findUserByEmailOrLogin(String email, String login) {
		TypedQuery<UserEntity> q = em.createNamedQuery("User.findUserByEmailOrLogin", UserEntity.class);
		q.setParameter("email", email);
		q.setParameter("login", login);
		
		return q.getResultList().size() > 0;
	}
			
	

	@Override
	public void removeInterview(long worker_id, long interview_id) {
		UserEntity UserEntity = em.find(UserEntity.class, worker_id);
		InterviewEntity interviewEntity = em.find(InterviewEntity.class, interview_id);
		
		UserEntity.removeInterview(interviewEntity);
		
		em.merge(UserEntity);
	}

	@Override
	public IUser findWorkerByEmail(String email) {
		TypedQuery<UserEntity> query = em.createNamedQuery("User.findUserByEmail", UserEntity.class);
		query.setParameter("email", email);
		
		List<UserEntity> entities = query.getResultList();
		if (entities.isEmpty()) return null;
		
		return new UserProxy(entities.get(0));
	}

	@Override
	public IUser verifyUser(long id, String password) {
		TypedQuery<UserEntity> query = em.createNamedQuery("User.verifyUser", UserEntity.class);
		query.setParameter("id", id);
		query.setParameter("password", password);
		
		List<UserEntity> entities = query.getResultList();
		if (entities.isEmpty()) return null;
		
		return new UserProxy(entities.get(0));
	}

	@Override
	public List<IUser> findUsersWithFilter(WorkerFilter filter, int offset, int limit) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<UserEntity> q = cb.createQuery(UserEntity.class);
		Root<UserEntity> worker = q.from(UserEntity.class);
		q.select(worker);
		
		List<Predicate> criteriaPredicates = new ArrayList<>();
			
		// start where
		if (filter != null) {
			
			// workers
			String workerFilter = filter.getKeyword();
			if (workerFilter != null) {
				workerFilter = workerFilter.toLowerCase();
				
				Expression<String> name = worker.get("completeName");
				Predicate or = cb.like(cb.lower(name), "%"+workerFilter+"%");
				
				Expression<String> email = worker.get("email");
				or = cb.or(or, cb.like(cb.lower(email), "%"+workerFilter+"%"));
				
				Expression<List<Role>> roles = worker.get("roles");
				if (workerFilter.equals("admin") || workerFilter.equals("administrator"))
					or = cb.or(or, cb.isMember(Role.ADMIN, roles));
				else if (workerFilter.equals("manager"))
					or = cb.or(or, cb.isMember(Role.MANAGER, roles));
				else if (workerFilter.equals("interviewer"))
					or = cb.or(or, cb.isMember(Role.INTERVIEWER, roles));
					
				criteriaPredicates.add(or);
			}
			
			// roles
			List<List<Role>> roleFilter = filter.getRoleSets();
			if (roleFilter != null && !roleFilter.isEmpty()) {
				Expression<List<Role>> roles = worker.get("roles");
				criteriaPredicates.add(GenericPersistenceService.andOrPredicate(roleFilter, roles, cb));
			}
		}
		
		q.where(cb.and(criteriaPredicates.toArray(new Predicate[0])));
		// finish where
		
		TypedQuery<UserEntity> query = em.createQuery(q);
		
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		List<UserEntity> entities = query.getResultList();
		List<IUser> proxies = new ArrayList<>();
		
		for (UserEntity ie : entities) {
			proxies.add(new UserProxy(ie));
		}
		
		return proxies;
	}

	@Override
	public long countAdmins() {
		TypedQuery<Long> query = em.createNamedQuery("User.countAdmins", Long.class);
		
		List<Long> result = query.getResultList();
		
		if (result.isEmpty()) return 0;
		
		return result.get(0);
	}

}
