package pt.uc.dei.aor.project.business.persistence;

import java.util.Date;
import java.util.List;

import pt.uc.dei.aor.project.business.model.IProposition;

public interface IPropositionPersistenceService {

	List<IProposition> findPropositionsByDate(Date startDate);

	List<IProposition> findOrphanPropositions();

	void remove(IProposition proposition);

	List<IProposition> findAll();

	List<IProposition> findHiringsByDate(Date startDate, Date finishDate);

	IProposition save(IProposition proxy);

	
	

}
