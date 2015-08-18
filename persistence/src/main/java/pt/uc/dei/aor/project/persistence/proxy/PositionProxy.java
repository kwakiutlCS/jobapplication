package pt.uc.dei.aor.project.persistence.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.entity.PublicationChannelEntity;
import pt.uc.dei.aor.project.persistence.entity.ScriptEntity;


public class PositionProxy implements IPosition, IProxyToEntity<PositionEntity> {

	private PositionEntity entity;

	public PositionProxy(PositionEntity entity) {
		this.entity = entity != null ? entity : new PositionEntity();
	}

	public PositionProxy(Date openingDate, String title, Localization localization, PositionState state, int vacancies, Date closingDate, String sla, 
			String contactPerson, String company ,String description) {

		Set<PublicationChannelEntity> publications = new TreeSet<>();
		ScriptEntity script = null;
		List<TechnicalArea> technicalAreas = new ArrayList<>();		

		entity = new PositionEntity(title, localization,
				state,  vacancies, openingDate,
				closingDate,  sla,  contactPerson, company,
				technicalAreas,  description,
				publications,  script);
	}

	@Override
	public PositionEntity getEntity() {
		return entity;
	}


}
