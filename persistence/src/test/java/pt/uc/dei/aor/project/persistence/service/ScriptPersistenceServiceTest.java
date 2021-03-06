package pt.uc.dei.aor.project.persistence.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import pt.uc.dei.aor.project.business.filter.InterviewFilter;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.IScriptPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IUserPersistenceService;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.QuestionType;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.persistence.entity.UserEntity;
import pt.uc.dei.aor.project.persistence.proxy.ModelFactory;

@RunWith(Arquillian.class)
public class ScriptPersistenceServiceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
        	.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
       		.addPackage(UserEntity.class.getPackage())
       		.addPackage(Role.class.getPackage())
       		.addPackage(Localization.class.getPackage())
       		.addPackage(IModelFactory.class.getPackage())
       		.addPackage(ModelFactory.class.getPackage())
       		.addPackage(InterviewFilter.class.getPackage())
            .addClass(IUser.class)
            .addPackage(UserPersistenceService.class.getPackage())
            .addPackage(IUserPersistenceService.class.getPackage());
    }

    @Inject
    private IScriptPersistenceService ejb;
    
    @Inject
    private IModelFactory factory;
    
    @Ignore
    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void shouldReturnListOfScripts() {
    	String title1 = "nonExistingScriptNumber1";
    	String title2 = "nonExistingScriptNumber2";
    	
    	int size = ejb.findAllScripts().size();
    	
    	IScript s1 = factory.script(title1);
    	IScript s2 = factory.script(title2);
    	
    	s1 = ejb.save(s1);
    	s2 = ejb.save(s2);
    	
    	assertThat(ejb.findAllScripts().size(), is(equalTo(size)));
    	
    	s1.addQuestion("test", QuestionType.LONG_ANSWER);
    	
    	ejb.save(s1);
    	assertThat(ejb.findAllScripts().size(), is(equalTo(size+1)));
    	
    	
    	s2.addQuestion("test", QuestionType.LONG_ANSWER);
    	
    	ejb.save(s2);
    	assertThat(ejb.findAllScripts().size(), is(equalTo(size+2)));
    }
    
   
}