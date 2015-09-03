package pt.uc.dei.aor.project.business.persistence;

import java.util.List;

import pt.uc.dei.aor.project.business.model.IPosition;


public interface IPositionPersistenceService {
	
	IPosition save(IPosition position);
	
	List<IPosition> findAllPositions();
	
	long findBiggestCode();
	

}