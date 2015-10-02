package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.uc.dei.aor.project.business.model.IImage;
import pt.uc.dei.aor.project.business.persistence.IImagePersistenceService;
import pt.uc.dei.aor.project.persistence.entity.ImageEntity;
import pt.uc.dei.aor.project.persistence.proxy.ImageProxy;

@Stateless
public class ImagePersistenceService implements IImagePersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	@Override
	public IImage save(IImage image) {
		ImageEntity entity = GenericPersistenceService.getEntity(image);
		entity = em.merge(entity);
		
		return new ImageProxy(entity);
	}

	@Override
	public void delete(IImage image) {
		em.remove(em.merge(GenericPersistenceService.getEntity(image)));
	}

	@Override
	public List<IImage> findAllImages() {
		TypedQuery<ImageEntity> query = em.createNamedQuery("Image.findAllImages", ImageEntity.class);
		
		List<ImageEntity> entities = query.getResultList();
		List<IImage> proxies = new ArrayList<>();
		
		for (ImageEntity ie : entities) {
			proxies.add(new ImageProxy(ie));
		}
		
		return proxies;
	}

	
	
}
