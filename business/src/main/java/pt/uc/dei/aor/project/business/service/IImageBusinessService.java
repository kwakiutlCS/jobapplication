package pt.uc.dei.aor.project.business.service;

import java.io.IOException;

import javax.servlet.http.Part;

public interface IImageBusinessService {

	void upload(Part img) throws IOException;
	
	

}
