package pt.uc.dei.aor.project.persistence.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.persistence.entity.User;
import pt.uc.dei.aor.project.persistence.util.Role;

@RunWith(Arquillian.class)
public class WorkerPersistenceServiceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
        	.addAsResource("META-INF/persistence.xml")
       		.addPackage(User.class.getPackage())
       		.addPackage(Role.class.getPackage())
            .addClass(IWorker.class)
            .addPackage(WorkerPersistenceService.class.getPackage())
            .addPackage(IWorkerPersistenceService.class.getPackage());
            //.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    IWorkerPersistenceService workerEjb;

    @Test
    public void shouldReturnNullForNonExistentWorker() {
    	String username = "inexistentUser";
    	
    	assertThat(workerEjb.getWorkerByLogin(username), is(equalTo(null)));
    }
    
    
}