package pt.uc.dei.aor.project.business.service;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.model.IImage;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.persistence.IImagePersistenceService;
import pt.uc.dei.aor.project.business.util.UploadUtil;


@Stateless
public class ImageBusinessService implements IImageBusinessService {

	private static Logger logger = LoggerFactory.getLogger(ImageBusinessService.class);

	@Inject
	private IModelFactory factory;
	
	@Inject
	private IImagePersistenceService imagePersistence;
	
	@Inject
	private UploadUtil upload;
	

	@Override
	public void upload(Part img) throws IOException {
		IImage image = factory.image(img.getSubmittedFileName());
		
		upload.uploadImg(img);
		
		imagePersistence.save(image);
	}

}

