package pt.uc.dei.aor.project.persistence.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import javax.inject.Inject;

import org.hamcrest.core.IsInstanceOf;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.persistence.entity.User;
import pt.uc.dei.aor.project.persistence.proxy.ModelFactory;
import pt.uc.dei.aor.project.persistence.proxy.WorkerProxy;
import pt.uc.dei.aor.project.persistence.util.Role;

@RunWith(Arquillian.class)
public class WorkerPersistenceServiceTest {

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
            //.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private IWorkerPersistenceService workerEjb;
    
    @Inject
    private IModelFactory factory;
    

    @Test
    public void shouldReturnNullForNonExistentWorker() {
    	String username = "inexistentUser";
    	
    	assertThat(workerEjb.getWorkerByLogin(username), is(equalTo(null)));
    }
    
    @Test
    public void shouldCreateANonExistentWorker() {
    	String login = "inexistentUser";
    	String email = "email";
    	String password = "password";
    	String name = "name";
    	String surname = "surname";
    	
    	assertThat(workerEjb.getWorkerByLogin(login), is(equalTo(null)));
    	
    	IWorker worker = factory.worker(login, email, password, name, surname);
    	workerEjb.save(worker);
    	
    	worker = workerEjb.getWorkerByLogin(login);
    	assertThat(worker, is(IsInstanceOf.instanceOf(WorkerProxy.class)));
    	
    	workerEjb.delete(worker);
    	assertThat(workerEjb.getWorkerByLogin(login), is(equalTo(null)));
    }
}