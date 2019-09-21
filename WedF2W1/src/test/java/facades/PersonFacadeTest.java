package facades;

import utils.EMF_Creator;
import entities.Person;
import exceptions.PersonNotFoundException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    public PersonFacadeTest() {
    }

    //@BeforeAll
//    public static void setUpClass() {
//        emf = EMF_Creator.createEntityManagerFactory(
//                "pu",
//                "jdbc:mysql://localhost:3307/startcode_test",
//                "dev",
//                "ax2",
//                EMF_Creator.Strategy.CREATE);
//        facade = PersonFacade.getFacadeExample(emf);
//    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = PersonFacade.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    private final Person p1 = new Person("Jack", "Jensen", "45789865", new Date());
    private final Person p2 = new Person("Perron", "Laddo", "32145428", new Date());
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }
    
      @Test
    public void testGetAll() {
        List<Person> result = facade.getAllPersons();
        assertTrue(result != null);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getFirstName(), "Jack");
        assertEquals(result.get(1).getFirstName(), "Perron");
    }
    @Test
    public void testGetById() {
        try{
        Person expected = new Person("Jack", "Jensen", "45789865", new Date());
        assertEquals(expected.getFirstName(), facade.getPerson(p1.getId()).getFirstName());
        }catch( PersonNotFoundException e){
            e.printStackTrace();
        }
    }
     @Test
    public void testEdit() {
        try{
        Person per = new Person("Jack", "Jensen", "45789865", new Date());
        per.setId(p2.getId());
        facade.editPerson(per);
        assertEquals(per.getFirstName(), facade.getPerson(p2.getId()).getFirstName());
        }catch( PersonNotFoundException e){
            e.printStackTrace();
        }
    }
         @Test
    public void testDelete() {
        try{
        facade.deletePerson(p2.getId());
        assertTrue(facade.getPerson(p2.getId()) == null);
        }catch( PersonNotFoundException e){
            e.printStackTrace();
        }
    }
     @Test
    public void testAdd() {
        facade.addPerson("Florence", "Karlson", "98712356");
        assertEquals(facade.getAllPersons().size(), 3);
        
    }
}
