package pt.uc.dei.aor.project.persistence.proxy;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IPublicationChanhel;
import pt.uc.dei.aor.project.business.model.IScript;
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

	@SuppressWarnings("unchecked")
	public PositionProxy(Date openingDate, String title, List<Localization> localizations, PositionState state, int vacancies, Date closingDate, String sla, 
			String contactPerson, String company ,List<TechnicalArea> technicalAreas, String description, IScript script) {

		Set<PublicationChannelEntity> publications = new TreeSet<>();
			
		ScriptEntity scriptEntity = null;
		if (script != null) {
			scriptEntity = ((IProxyToEntity<ScriptEntity>) script).getEntity();
		}
		
		entity = new PositionEntity(title, localizations,
				state,  vacancies, openingDate,
				closingDate,  sla,  contactPerson, company,
				technicalAreas,  description,
				publications,  scriptEntity);
	}



	@Override
	public PositionEntity getEntity() {
		return entity;
	}


}
