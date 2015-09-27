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
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import pt.uc.dei.aor.project.business.exception.IllegalRoleException;
import pt.uc.dei.aor.project.business.filter.InterviewFilter;
import pt.uc.dei.aor.project.business.model.IModelFactory;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.persistence.IUserPersistenceService;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.Role;
import pt.uc.dei.aor.project.persistence.entity.UserEntity;
import pt.uc.dei.aor.project.persistence.proxy.ModelFactory;
import pt.uc.dei.aor.project.persistence.proxy.UserProxy;

@RunWith(Arquillian.class)
public class WorkerPersistenceServiceTest2 {

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
            .addPackage(IUser.class.getPackage())
            .addPackage(IllegalRoleException.class.getPackage())
            .addPackage(UserPersistenceService.class.getPackage())
            .addPackage(IUserPersistenceService.class.getPackage());
            //.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private IUserPersistenceService userEjb;
    
    @Inject
    private IModelFactory factory;
    
    @Test
    public void shouldReturnNullForNonExistentWorker() {
    	String username = "inexistentUser";
    	
    	assertThat(userEjb.getUserByLogin(username), is(equalTo(null)));
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
    	
    	assertThat(userEjb.getUserByLogin(login), is(equalTo(null)));
    	
    	IUser worker = factory.user(login, email, password, name, surname, roles);
    	userEjb.save(worker);
    	
    	worker = userEjb.getUserByLogin(login);
    	assertThat(worker, is(IsInstanceOf.instanceOf(UserProxy.class)));
    	
    }
    
    
   
}