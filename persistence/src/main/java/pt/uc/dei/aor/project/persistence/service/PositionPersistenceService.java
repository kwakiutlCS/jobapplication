package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;
import pt.uc.dei.aor.project.persistence.proxy.PositionProxy;

@Stateless
public class PositionPersistenceService implements IPositionPersistenceService {

	@PersistenceContext(unitName = "jobs")
	private EntityManager em;

	@Override
	public IPosition save(IPosition position) {
		PositionEntity entity = getEntity(position);

		entity = em.merge(entity);

		return new PositionProxy(entity);
	}


	@Override
	public void delete(IPosition position) {
		PositionEntity entity = getEntity(position);
		em.remove(em.merge(entity));
	}
	
	
	@SuppressWarnings("unchecked")
	private PositionEntity getEntity(IPosition positionProxy) {
		if (positionProxy instanceof IProxyToEntity<?>) {
			return ((IProxyToEntity<PositionEntity>) positionProxy).getEntity();
		}
		throw new IllegalStateException();
	}


	@Override
	public List<IPosition> findAllPositions() {

		TypedQuery<PositionEntity> q =  em.createNamedQuery("position.findAll", PositionEntity.class);

		List<PositionEntity> positions = q.getResultList();
		List<IPosition> ipositions = new ArrayList<IPosition>();
		for(PositionEntity pos : positions){
			ipositions.add(new PositionProxy(pos));
		}

		return ipositions;
	}


	@Override
	public long findBiggestCode() {
		long code = 0;
		TypedQuery<Long> q = em.createNamedQuery("position.findMaxCode", Long.class);

		try
		{
			List<Long> codes = q.getResultList();
			code = codes.get(0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return code;

	}
}
