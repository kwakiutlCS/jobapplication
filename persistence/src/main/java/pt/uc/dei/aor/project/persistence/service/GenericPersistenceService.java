package pt.uc.dei.aor.project.persistence.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import pt.uc.dei.aor.project.business.util.TechnicalArea;
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
	
}
