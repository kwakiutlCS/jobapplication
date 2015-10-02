package pt.uc.dei.aor.project.persistence.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pt.uc.dei.aor.project.business.persistence.IImagePersistenceService;

@Stateless
public class ImagePersistenceService implements IImagePersistenceService {
	
	@PersistenceContext(unitName = "jobs")
    private EntityManager em;

	
	
}
