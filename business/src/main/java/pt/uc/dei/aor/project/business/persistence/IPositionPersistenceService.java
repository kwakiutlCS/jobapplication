package pt.uc.dei.aor.project.business.persistence;

import java.util.List;

import pt.uc.dei.aor.project.business.filter.PositionFilter;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IWorker;


public interface IPositionPersistenceService {
	
	IPosition save(IPosition position);
	
	List<IPosition> findAllPositions();
	
	long findBiggestCode();

	List<IPosition> findPositionByTitle(String title);

	List<IPosition> findFilteredPositions(int offset, int limit, PositionFilter filter, IWorker user);

	List<IPosition> findOpenPositions();

	IPosition findPositionById(long id);
	
}
