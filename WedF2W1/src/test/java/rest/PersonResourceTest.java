package rest;

import entities.Person;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test

public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    //Read this line from a settings-file  since used several places
   // private static final String TEST_DB = "jdbc:mysql://localhost:3307/startcode_test";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);

        //NOT Required if you use the version of EMF_Creator.createEntityManagerFactory used above        
        //System.setProperty("IS_TEST", TEST_DB);
        //We are using the database on the virtual Vagrant image, so username password are the same for all dev-databases
        
        httpServer = startServer();
        
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
   
        RestAssured.defaultParser = Parser.JSON;
    }
    
    @AfterAll
    public static void closeTestServer(){
        //System.in.read();
         httpServer.shutdownNow();
    }
    
    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
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
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }
   
    //This test assumes the database contains two rows
    @Test
    public void testAll() throws Exception {
       given()
        .contentType("application/json")
        .get("/person/all").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("all.firstName", hasItem("Jack"))
        .body("all.firstName", hasItem("Perron"))
        .body("all.phone", hasItem("45789865"))
        .body("all.phone", hasItem("32145428"));
    }
    
    @Test
    public void testById() throws Exception {
        given()
        .contentType("application/json")
        .get("/person/id/"+p1.getId()).then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("firstName", equalTo("Jack"));   
    }
    @Test
    public void testAdd() throws Exception {
        
        Map<String,String> per = new HashMap<>();
		per.put("firstName", "Kukul");
		per.put("lastName", "Kan");
		per.put("phone", "02458798");
        given()
        .contentType("application/json")
        .body(per)
        .post("/person").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("firstName", equalTo("Kukul"));   
    }
    
    @Test
    public void testEdit() throws Exception {
        Map<String,String> per = new HashMap<>();
        String out = ""+p2.getId();
               per.put("id",out);
		per.put("firstName", "Chro");
		per.put("lastName", "Nos");
		per.put("phone", "01522574");
        given()
        .contentType(ContentType.JSON)
        .body(per)
        .put("/person/"+p2.getId()).then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("firstName", equalTo("Chro"));   
    }
    
     @Test
    public void testDelete() throws Exception {
        given()
        .contentType("application/json")
        .delete("/person/"+p1.getId()).then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode());  
    }
}
