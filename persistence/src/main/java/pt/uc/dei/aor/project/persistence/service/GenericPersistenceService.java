package pt.uc.dei.aor.project.persistence.service;

import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;

public class GenericPersistenceService {
	
	@SuppressWarnings("unchecked")
	public static <T,U> T getEntity(U proxy) {
        if (proxy instanceof IProxyToEntity<?>) {
            return ((IProxyToEntity<T>) proxy).getEntity();
        }

        throw new IllegalStateException();
    }
}
