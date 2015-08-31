package pt.uc.dei.aor.project.business.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IModelFactory;

public class ApplicationBusinessService implements IApplicationBusinessService {

		
	@Override
	public Collection<IApplication> findActiveApplications() {
		// TODO 
		// eventualmente precisarei desta função para implementar as interviews
		// deverá devolver a lista de applications ainda sem resolução para que o admin
		// possa marcar uma entrevista
		
		// neste momento devolve provisoriamente uma lista de nulls
		return new ArrayList<IApplication>(Arrays.asList(new IApplication[]{null, null, null}));
	}
	
}
