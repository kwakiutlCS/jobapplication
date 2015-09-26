package pt.uc.dei.aor.project.persistence.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.IColor;
import pt.uc.dei.aor.project.business.persistence.IColorPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.ColorEntity;
import pt.uc.dei.aor.project.persistence.proxy.ColorProxy;

@Stateless
public class ColorPersistenceService implements IColorPersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	
	@Override
	public IColor save(IColor color) {
		ColorEntity entity = GenericPersistenceService.getEntity(color);
		entity = em.merge(entity);
		return new ColorProxy(entity);
	}

	@Override
	public IColor findByTitle(String title) {
		TypedQuery<ColorEntity> query = em.createNamedQuery("Color.findColorByTitle", ColorEntity.class);
		query.setParameter("title", title);
		
		List<ColorEntity> entities = query.getResultList();
		
		if (entities.isEmpty()) return null;
		
		return new ColorProxy(entities.get(0));
	}

	
	
}
