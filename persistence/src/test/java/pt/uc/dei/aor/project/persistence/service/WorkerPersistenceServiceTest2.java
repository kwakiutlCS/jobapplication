package pt.uc.dei.aor.project.persistence.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.hamcrest.core.IsInstanceOf;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import pt.uc.dei.aor.project.business.exception.IllegalRoleException;
import pt.uc.dei.aor.project.business.filter.InterviewFilter;
import pt.uc.dei.aor.project.business.filter.WorkerFilter;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IWorkerPersistenceService;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.persistence.entity.User;
import pt.uc.dei.aor.project.persistence.entity.WorkerEntity;
import pt.uc.dei.aor.project.persistence.proxy.ModelFactory;
import pt.uc.dei.aor.project.persistence.proxy.WorkerProxy;

@RunWith(Arquillian.class)
public class WorkerPersistenceServiceTest2 {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
        	.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
       		.addPackage(User.class.getPackage())
       		.addPackage(Role.class.getPackage())
       		.addPackage(Localization.class.getPackage())
       		.addPackage(IModelFactory.class.getPackage())
       		.addPackage(ModelFactory.class.getPackage())
       		.addPackage(InterviewFilter.class.getPackage())
            .addPackage(IWorker.class.getPackage())
            .addPackage(IllegalRoleException.class.getPackage())
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
    @Transactional(TransactionMode.ROLLBACK)
    public void shouldCreateANonExistentWorker() {
    	String login = "inexistentUser";
    	String email = "email";
    	String password = "password";
    	String name = "name";
    	String surname = "surname";
    	List<Role> roles = new ArrayList<>();
    	roles.add(Role.ADMIN);
    	
    	assertThat(workerEjb.getWorkerByLogin(login), is(equalTo(null)));
    	
    	IWorker worker = factory.worker(login, email, password, name, surname, roles);
    	workerEjb.save(worker);
    	
    	worker = workerEjb.getWorkerByLogin(login);
    	assertThat(worker, is(IsInstanceOf.instanceOf(WorkerProxy.class)));
    	
    }
    
    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void findAllUsersShouldntReturnSuperUser() {
    	workerEjb.createSuperUser();
    	String login = "inexistentUser";
    	String email = "email";
    	String password = "password";
    	String name = "name";
    	String surname = "surname";
    	List<Role> roles = new ArrayList<>();
    	roles.add(Role.ADMIN);
    	
    	assertThat(workerEjb.getWorkerByLogin(login), is(equalTo(null)));
    	assertThat(workerEjb.getWorkerByLogin("SU"), is(not(equalTo(null))));
    	IWorker worker = factory.worker(login, email, password, name, surname, roles);
    	
    	workerEjb.save(worker);
    	
    	List<IWorker> users = workerEjb.findAllUsers();
    	
    	for (IWorker w : users) {
    		assertThat(w.getName(), is(not(equalTo("SU"))));
    	}
    }
    
    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void findAllUsersWithFilterShouldntReturnSuperUser() {
    	System.out.println("Super usering");
    	workerEjb.createSuperUser();
    	String login = "inexistentUser";
    	String email = "email";
    	String password = "password";
    	String name = "name";
    	String surname = "surname";
    	List<Role> roles = new ArrayList<>();
    	roles.add(Role.ADMIN);
    	
    	assertThat(workerEjb.getWorkerByLogin(login), is(equalTo(null)));
    	assertThat(workerEjb.getWorkerByLogin("SU"), is(not(equalTo(null))));
    	IWorker worker = factory.worker(login, email, password, name, surname, roles);
    	
    	workerEjb.save(worker);
    	
    	List<IWorker> users = workerEjb.findUsersWithFilter(new WorkerFilter(), 0, 20);
    	
    	for (IWorker w : users) {
    		System.out.println(w.getName());
    		assertThat(w.getName(), is(not(equalTo("SU"))));
    	}
    }
}