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
		PositionEntity entity = GenericPersistenceService.getEntity(position);

		entity = em.merge(entity);

		return new PositionProxy(entity);
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
		code = q.getSingleResult();
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return code;

	}


	@Override
	public List<IPosition> findPositionByTitle(String title) {
		TypedQuery<PositionEntity> query = em.createNamedQuery(
				"Position.findPositionByTitle", PositionEntity.class);
		query.setParameter("title", "%"+title+"%");
		
		List<PositionEntity> entities = query.getResultList();
		List<IPosition> proxies = new ArrayList<>();
		
		for (PositionEntity pe : entities) {
			proxies.add(new PositionProxy(pe));
		}
		
		return proxies;
	}
}
