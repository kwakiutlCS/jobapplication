package pt.uc.dei.aor.project.business.startup;


import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.business.service.INotificationBusinessService;
import pt.uc.dei.aor.project.business.util.EmailUtil;

@Singleton
public class ScheduledEjb {

	private static final Logger logger = LoggerFactory.getLogger(ScheduledEjb.class);
	
	@Inject
	private IPositionPersistenceService positionPersistence;
	
	@Inject
	private INotificationBusinessService notificationService;
	
	@Inject
	private EmailUtil emailUtil;
	
	
    @PostConstruct
    @Schedule(hour="8", minute="0", second="0", persistent=false)
    public void run() {
       for (IPosition p : positionPersistence.findOpenPositions()) {
    	   if (isOverdue(p))
    		   sendSLANotification(p);
       }
    }

    
    
    private void sendSLANotification(IPosition p) {
    	logger.debug(p.toString()+" deadline");
    	String msg = "SLA for position "+p.getCode()+" - "+p.getTitle()+" expected position to close soon.";
    	notificationService.notify(p.getContactPerson(), msg, 
    			"SLA deadline");
    	
    	emailUtil.send(p.getContactPerson().getEmail(), "SLA deadline", msg);
    }
    
    private boolean isOverdue(IPosition position) {
    	Calendar limit = Calendar.getInstance();
    	limit.setTime(position.getOpeningDate());
    	
    	limit.add(Calendar.DAY_OF_YEAR, Integer.parseInt(position.getSla()));
    	
    	Calendar today = Calendar.getInstance();
    	
    	if (limit.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) 
    			&& limit.get(Calendar.YEAR) == today.get(Calendar.YEAR))
    		return true;
    	
    	return false;
    }
}