package pt.uc.dei.aor.project.business.persistence;

import java.util.List;

import pt.uc.dei.aor.project.business.model.IImage;

public interface IImagePersistenceService {

	IImage save(IImage image);
	
	void delete(IImage image);
	
	List<IImage> findAllImages();
	
}
