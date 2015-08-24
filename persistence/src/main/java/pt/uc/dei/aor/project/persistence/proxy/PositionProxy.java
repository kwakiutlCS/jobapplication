package pt.uc.dei.aor.project.persistence.proxy;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IPublicationChannel;
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
	public PositionProxy(String code, Date openingDate, String title,
			Collection<Localization> localizations, PositionState state,
			int vacancies, Date closingDate, String sla, String contactPerson,
			String company, Collection<TechnicalArea> technicalAreas,
			String description, IScript script,
			Collection<IPublicationChannel> channels){


		Set<PublicationChannelEntity> publicationChannelEntities = new TreeSet<>();
		if (channels != null) {
			for(IPublicationChannel pc: channels){
				publicationChannelEntities.add(((IProxyToEntity<PublicationChannelEntity>) pc).getEntity());
			}
		}


		ScriptEntity scriptEntity = null;
		if (script != null) {
			scriptEntity = ((IProxyToEntity<ScriptEntity>) script).getEntity();
		}

		entity = new PositionEntity(title, localizations,
				state,  vacancies, openingDate,
				closingDate,  sla,  contactPerson, company,
				technicalAreas,  description, publicationChannelEntities, scriptEntity);
	}

	@Override
	public PositionEntity getEntity() {
		return entity;
	}


}
