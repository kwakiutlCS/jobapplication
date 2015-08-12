package pt.uc.dei.aor.project.persistence.proxy;

import java.time.LocalDate;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;

public class PositionProxy implements IPosition, IProxyToEntity<PositionEntity> {

	private PositionEntity entity;

	public PositionProxy(PositionEntity entity) {
		this.entity = entity != null ? entity : new PositionEntity();
	}

	public PositionProxy(LocalDate openingDate, String title,int vacancies, LocalDate closingDate,
			String sla, String contactPerson, String company, String description) {
		
		entity = new PositionEntity();
	}

	@Override
	public PositionEntity getEntity() {
		return entity;
	}



}
