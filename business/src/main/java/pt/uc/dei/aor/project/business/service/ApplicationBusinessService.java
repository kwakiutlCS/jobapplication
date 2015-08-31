package pt.uc.dei.aor.project.business.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IModelFactory;

@Stateless
public class ApplicationBusinessService implements IApplicationBusinessService {

	@Inject
	private IModelFactory factory;
		
	@Override
	public Collection<IApplication> findActiveApplications() {
		// TODO 
		// eventualmente precisarei desta função para implementar as interviews
		// deverá devolver a lista de applications ainda sem resolução para que o admin
		// possa marcar uma entrevista
		
		// neste momento devolve provisoriamente uma lista de applications vazias
		// este método factory.application tb é provisorio (provavelmente vai ter que ser alterado)
		IApplication app = factory.application();
		return new ArrayList<IApplication>(Arrays.asList(new IApplication[]{app, app, app}));
	}
	
}
