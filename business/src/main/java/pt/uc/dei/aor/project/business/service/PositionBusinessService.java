package pt.uc.dei.aor.project.business.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.filter.PositionFilter;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.business.util.EmailUtil;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;


@Stateless
public class PositionBusinessService implements IPositionBusinessService {

	private static final Logger logger = LoggerFactory.getLogger(PositionBusinessService.class);
	
	@Inject
	private IModelFactory factory;

	@Inject
	private IPositionPersistenceService positionPersistence;
	
	@Inject
	private INotificationBusinessService notificationService;
	
	@Inject
	private EmailUtil emailUtil;

	private long code;
	private Date openingDate;

	@Override
	public IPosition createNewPosition(String title, Collection<Localization> localizations,
			PositionState state, int vacancies, Date closingDate, int sla,
			IUser contactPerson, String company,
			Collection<TechnicalArea> technicalAreas, String description,
			List<IScript> scripts, Collection<IPublicationChannel> channels) {
		
		openingDate = new Date();		
		
		code = codeDefiningMethod(); 


		IPosition position = factory.position(code, openingDate, title, localizations, state, vacancies, closingDate, sla, 
				contactPerson, company, technicalAreas, description, scripts, channels);
		
		String msgTitle = "Position opening";
		String message = "Position: "+title+" was created with you as manager";
		notificationService.notify(contactPerson, message, msgTitle);
		
		logger.debug("sending email service");
		logger.debug(contactPerson.getEmail());
		//emailUtil.send(contactPerson.getEmail(), msgTitle, message, null);
		
		String msgEmail = "<h1>"+company+"</h1>"+
				"<p>Position creation "+
				title+" was created and assigned you as manager.</p>";
		emailUtil.send(contactPerson.getEmail(), msgEmail, msgTitle, null);

		return positionPersistence.save(position);
	}


	@Override
	public List<IPosition> getIPositions() {
	
		return positionPersistence.findAllPositions();
	}


	@Override
	public long codeDefiningMethod() {

		code =positionPersistence.findBiggestCode()+1;
				
		return code;
	}


	@Override
	public List<IPosition> findPositionByTitle(String title) {
		return positionPersistence.findPositionByTitle(title);
	}


	@Override
	public IPosition updatePosition(IPosition position, List<IScript> scripts) {
		position.setScripts(scripts);
		return positionPersistence.save(position);
	}


	@Override
	public List<IPosition> findFilteredPositions(int offset, int limit, PositionFilter filter) {
		return positionPersistence.findFilteredPositions(offset, limit, filter, null);
	}


	@Override
	public List<IPosition> findFilteredPositionsByManager(int offset, int limit, PositionFilter filter, 
			IUser user) {
		return positionPersistence.findFilteredPositions(offset, limit, filter, user);
	}


	@Override
	public IPosition findPositionById(long id) {
		return positionPersistence.findPositionById(id);

	}


	@Override
	public List<IPosition> findOpenPosition() {
		return positionPersistence.findOpenPositions();
	}


	
}
