package pt.uc.dei.aor.project.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;
import pt.uc.dei.aor.project.persistence.proxy.PositionProxy;


public class PositionPersistenceService implements IPositionPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;
	
	@Override
	public IPosition save(IPosition position) {
		PositionEntity entity = getEntity(position);
		
		entity = em.merge(entity);
		
		return new PositionProxy(entity);
	}


	@SuppressWarnings("unchecked")
	private PositionEntity getEntity(IPosition positionProxy) {
        if (positionProxy instanceof IProxyToEntity<?>) {
            return ((IProxyToEntity<PositionEntity>) positionProxy).getEntity();
        }

        throw new IllegalStateException();
    }

	
	
	
	
	

}
