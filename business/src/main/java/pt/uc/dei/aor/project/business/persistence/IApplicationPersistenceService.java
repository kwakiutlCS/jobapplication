package pt.uc.dei.aor.project.business.persistence;

import java.util.List;

import pt.uc.dei.aor.project.business.model.IApplication;

public interface IApplicationPersistenceService {

	List<IApplication> dummyQuery();

	IApplication save(IApplication application);

	IApplication find(long id);
	
}
