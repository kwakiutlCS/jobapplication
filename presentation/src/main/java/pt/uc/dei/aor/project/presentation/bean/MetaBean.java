package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

@Named
@ViewScoped
public class MetaBean implements Serializable {
	
	private static final long serialVersionUID = -4041049634352023785L;
	
	private String page;
	private Part image;
	private String test;
	
	
	public void testFunction() {
		System.out.println(page);
		System.out.println(test);
	}
	
	
	public String getPage() {
		return page;
	}


	public void setPage(String page) {
		this.page = page;
	}


	public String getTest() {
		return test;
	}


	public void setTest(String test) {
		this.test = test;
	}


	@PostConstruct
	public void init() {
		page = "admin";
		test = page;
	}
	
}

