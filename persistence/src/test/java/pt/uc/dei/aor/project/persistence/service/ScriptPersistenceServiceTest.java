package pt.uc.dei.aor.project.persistence.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.hamcrest.core.IsInstanceOf;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IScriptPersistenceService;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.persistence.entity.User;
import pt.uc.dei.aor.project.persistence.proxy.ModelFactory;
import pt.uc.dei.aor.project.persistence.proxy.WorkerProxy;

@RunWith(Arquillian.class)
public class ScriptPersistenceServiceTest {

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
    private IScriptPersistenceService ejb;
    
    @Inject
    private IModelFactory factory;
    

    @Test
    public void shouldReturnListOfScripts() {
    	String title1 = "nonExistingScriptNumber1";
    	String title2 = "nonExistingScriptNumber1";
    	
    	int size = ejb.findAllScripts().size();
    	IScript script1 = ejb.save(factory.script(title1));
    	IScript script2 = ejb.save(factory.script(title2));
    	
    	assertThat(ejb.findAllScripts().size(), is(equalTo(size+2)));
    	
    	ejb.delete(script1);
    	ejb.delete(script2);
    	
    	assertThat(ejb.findAllScripts().size(), is(equalTo(size)));
    }
    
   
}