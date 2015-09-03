package pt.uc.dei.aor.project.persistence.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


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
import pt.uc.dei.aor.project.persistence.proxy.PublicationChannelProxy;


@RunWith(Arquillian.class)
public class PositionPersistenceServiceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
        	.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
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
    IPosition pos = null;
    
    @Inject
    private IModelFactory factory;
    
    
    
    @Before
    public void init() {
    	long code = 3;
    	Date openingDate = new Date();
    	String title = "title";
    	List<Localization> localizations = Arrays.asList(new Localization[]{Localization.COIMBRA});
    	PositionState state = PositionState.OPEN;
    	int vacancies = 4;
    	Date closingDate = new Date();
    	int sla = 2;
    	String description = "description";
    	String person = "person";
    	String company = "company";
    	List<TechnicalArea> tech = Arrays.asList(new TechnicalArea[]{TechnicalArea.JAVA_DEVELOPMENT});
    	List<IPublicationChannel> pub = Arrays.asList(new PublicationChannelProxy[]{
    			new PublicationChannelProxy("jornal")});
    	IScript script = null;
    	
    	pos = factory.position(code, openingDate, title, localizations, state, vacancies, closingDate, 
    			sla, person, company, tech, description, script, pub);
    }
   
    @Test
    public void shouldStartCodesWithZero() {
    	assertThat(ejb.findBiggestCode(), is(equalTo(0L)));
    }
    
    

	@Test
    @Transactional(TransactionMode.ROLLBACK)
    public void shouldReturnHighestCode() {
    	long code = 7;
    	Date openingDate = new Date();
    	String title = "title2";
    	List<Localization> localizations = Arrays.asList(new Localization[]{Localization.LISBOA});
    	PositionState state = PositionState.OPEN;
    	int vacancies = 1;
    	Date closingDate = new Date();
    	int sla = 3;
    	String description = "description2";
    	String person = "person2";
    	String company = "company2";
    	List<TechnicalArea> tech = Arrays.asList(new TechnicalArea[]{TechnicalArea.DOT_NET_DEVELOPMENT});
    	List<IPublicationChannel> pub = Arrays.asList(new PublicationChannelProxy[]{
    			new PublicationChannelProxy("jornal")});
    	IScript script = null;
    	
    	IPosition otherPos = factory.position(code, openingDate, title, localizations, state, vacancies, closingDate, 
    			sla, person, company, tech, description, script, pub);
    	
    	ejb.save(pos);
    	ejb.save(otherPos);
    	Long expected = Math.max(pos.getCode(), otherPos.getCode());
    	
    	assertThat(ejb.findBiggestCode(), is(equalTo(expected)));
    }

	
    
}
