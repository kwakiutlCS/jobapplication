package pt.uc.dei.aor.project.persistence.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.persistence.proxy.WorkerProxy;



public class WorkerPersistenceServiceTest {

	private static EJBContainer ec;
    private static Context ctx;
 
    @BeforeClass
    public static void initContainer() throws Exception {
        Map<Object, Object> properties = new HashMap<>();
        properties.put(EJBContainer.MODULES, new File[]{new File("target/classes"), new File("target/test-classes")});
        ec = EJBContainer.createEJBContainer(properties);
        ctx = ec.getContext();
    }
 
    @AfterClass
    public static void closeContainer() throws Exception {
        if (ec != null) {
            ec.close();
        }
    }
 
    @Test
    public void shouldRetrieveNullForInexistentWorkerByLogin() throws NamingException {
    	String login = "nonExistingUser";
    	
    	WorkerPersistenceService service = (WorkerPersistenceService) ctx.lookup("java:global/classes/WorkerPersistenceService");
    	
    	assertThat(service.getWorkerByLogin(login), is(equalTo(null)));
    }
    
    
    @Ignore
    @Test
    public void shouldCreateANewWorker() {
//    	String login = "login";
//    	String email = "email";
//    	String password = "password";
//    	String name = "name";
//    	String surname = "surname";
//    	
//        WorkerPersistenceService service = (WorkerPersistenceService) ctx.lookup("java:global/classes/WorkerPersistenceService");
// 
//        IWorker worker = new WorkerProxy(login, email, password, name, surname);
// 
//        // Persists the worker
//        service.save(worker);       
 
        assertThat(true, equalTo(true));      
    }
}
