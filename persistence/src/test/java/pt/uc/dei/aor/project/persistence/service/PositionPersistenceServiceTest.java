package pt.uc.dei.aor.project.persistence.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IPublicationChannel;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.business.util.TechnicalArea;
import pt.uc.dei.aor.project.persistence.entity.User;
import pt.uc.dei.aor.project.persistence.proxy.ModelFactory;

@RunWith(Arquillian.class)
public class PositionPersistenceServiceTest {


	    @Deployment
	    public static JavaArchive createDeployment() {
	        return ShrinkWrap.create(JavaArchive.class)
	        	.addAsResource("META-INF/persistence.xml")
	       		.addPackage(User.class.getPackage())
	       		.addPackage(Role.class.getPackage())
	       		.addPackage(Localization.class.getPackage())
	       		.addPackage(IModelFactory.class.getPackage())
	       		.addPackage(ModelFactory.class.getPackage())
	            .addClass(IWorker.class)
	            .addPackage(WorkerPersistenceService.class.getPackage())
	            .addPackage(IWorkerPersistenceService.class.getPackage());
	    }

	    @Inject
	    private IPositionPersistenceService ejb;
	    
	    @Inject
	    private IModelFactory factory;
	    

	    @Test
	    public void shouldReturnListOfPositions() {
	    	long code=0; 
	    	Date openingDate = new Date(); 
	    	String title= "a"; 
	    	List<Localization> localizations = new ArrayList<Localization>();
	    	localizations.add(Localization.COIMBRA);
	    	PositionState  state = PositionState.OPEN;
	    	int vacancies=1; 
	    	Date closingDate = new Date(); 
	    	int sla = 1; 
	    	String contactPerson = "João"; 
	    	String company="qualquer"; 
	    	List<TechnicalArea> technicalAreas = new ArrayList<TechnicalArea>();
	    	technicalAreas.add(TechnicalArea.SSPA);
	    	String description= ""; 
	    	IScript script=null; 
	    	List<IPublicationChannel> channels = new ArrayList<IPublicationChannel>();
	    	
	 
	    	
	    	int size = ejb.findAllPositions().size();
	    	IPosition position1 = ejb.save(factory.position(code, openingDate, title, localizations, state, vacancies, closingDate, sla, contactPerson, company, technicalAreas, description, script, channels));
	    	
	    	
	    	assertThat(ejb.findAllPositions().size(), is(equalTo(size+1)));
	    	
	    	
	    	
//	    	assertThat(ejb.findAllPositions().size(), is(equalTo(size)));
	    }
	    
	 	
}
