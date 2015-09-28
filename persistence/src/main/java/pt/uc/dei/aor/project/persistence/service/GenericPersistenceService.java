package pt.uc.dei.aor.project.persistence.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;


public class GenericPersistenceService {
	
	@SuppressWarnings("unchecked")
	public static <T,U> T getEntity(U proxy) {
        if (proxy instanceof IProxyToEntity<?>) {
            return ((IProxyToEntity<T>) proxy).getEntity();
        }

        throw new IllegalStateException();
    }
	
	
	public static <T> Predicate andOrPredicate(
			List<List<T>> filter, Expression<List<T>> collection, CriteriaBuilder cb) {

		Predicate or = null;

		for (List<T> set : filter) {
			Predicate and = null;

			for (T area : set) {
				if (and == null) and = cb.isMember(area, collection);
				else and = cb.and(and, cb.isMember(area, collection));
			}

			if (or == null) or = and;
			else or = cb.or(or, and);
		}

		return or;
	}
	
	
	public static <T, U> Predicate andOrEntityPredicate(
			List<List<T>> filter, Expression<List<U>> collection, CriteriaBuilder cb) {

		Predicate or = null;

		for (List<T> set : filter) {
			Predicate and = null;

			for (T area : set) {
				U u = getEntity(area);
				if (and == null) and = cb.isMember(u, collection);
				else and = cb.and(and, cb.isMember(u, collection));
			}

			if (or == null) or = and;
			else or = cb.or(or, and);
		}

		return or;
	}
	
	
	public static Predicate orStringPredicate(List<String> filter, 
			Expression<String> expression, CriteriaBuilder cb) {
		Predicate or = null;
		
		for (String s : filter) {
			if (or == null) or = cb.like(cb.lower(expression), "%"+s.trim().toLowerCase()+"%");
			else or = cb.or(or, cb.like(cb.lower(expression), "%"+s.trim().toLowerCase()+"%"));
		}
		
		return or;
	}
	
	public static Predicate orLongPredicate(List<String> filter, 
			Expression<Long> expression, CriteriaBuilder cb) {
		Predicate or = null;
		
		for (String s : filter) {
			try {
				Long n = Long.parseLong(s);
				if (or == null) or = cb.equal(expression, n);
				else or = cb.or(or, cb.equal(expression, n));
			}
			catch(Exception e) {
				
			}
		}
		
		return or;
	}
	
	
//	public static <T> Predicate orCollectionStringPredicate(List<String> filter, 
//			Expression<String> expression, CriteriaBuilder cb, List<T> collection) {
//		Predicate or = null;
//		
//		List<T> chosen = new ArrayList<>();
//		for (String s : filter) {
//			for (T v : collection)
//		}
//		for (String s : filter) {
//			if (or == null) or = cb.isMember(cb.lower(expression), "%"+s.trim().toLowerCase()+"%");
//			else or = cb.or(or, cb.like(cb.lower(expression), "%"+s.trim().toLowerCase()+"%"));
//		}
//		
//		return or;
//	}
}
