package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Person;
import exceptions.GenericExceptionMapper;
import exceptions.PersonNotFoundException;
import exceptions.PersonNotFoundExceptionMapper;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.DROP_AND_CREATE);
    private static final PersonFacade FACADE =  PersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private PersonNotFoundExceptionMapper pmapper = new PersonNotFoundExceptionMapper();
    private static final GenericExceptionMapper gmapper= new GenericExceptionMapper();
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response demo() {
        try{
        return Response.ok("{\"msg\":\"Hello World\"}").build();
        }catch(Exception e){
            return gmapper.toResponse(e);
        }
    }
//    @Path("count")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public String getRenameMeCount() {
//        long count = FACADE.getRenameMeCount();
//        //System.out.println("--------------->"+count);
//        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
//    }

    
    @Path("/id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") int id) {
         Person per = null;
        
        try{
         per = FACADE.getPerson(id);
         }catch(PersonNotFoundException e){
       return pmapper.toResponse(e);
    }catch(Exception e){
       return gmapper.toResponse(e);
    }
       return Response.ok(per).build();
    }
     
     @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        String out = "";
        try{
        HashMap<String, List<Person>> l =  new HashMap();
        l.put("all", FACADE.getAllPersons());
         out = GSON.toJson(l);
        }catch(Exception e){
        return gmapper.toResponse(e);
    }
        return Response.ok(out).build();
    }
    

    @POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response addPerson(String pers) {
   Person per = null;
   try{
   per = GSON.fromJson(pers, Person.class); 
   Person pper = FACADE.addPerson(per.getFirstName(), per.getLastName(), per.getPhone());
   per = pper;
   }catch(Exception e){
        return gmapper.toResponse(e);
    }
   return Response.ok(per).build();
}

@PUT
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/{id}")
public Response editPerson(String pers) {
     Person pper = null;
    try{  
    Person per = GSON.fromJson(pers, Person.class);
      Person k = FACADE.getPerson(per.getId());
      k.setFirstName(per.getFirstName());
      k.setLastName(per.getLastName());
      k.setPhone(per.getPhone());
      k.setLastEdited(new Date());
    pper = FACADE.editPerson(k);
   
  
     }catch(PersonNotFoundException e){
       return pmapper.toResponse(e);
    }catch(Exception ex){
    return gmapper.toResponse(ex);
    }    return Response.ok(pper).build();
    
//   //Fetch the Car Entity using the provided ID, make the changes
//   //received in the provided DTO and merge it back
//   //Convert the edited Entity object back into a DTO
}

@DELETE
//No Consumes annotation
@Produces(MediaType.APPLICATION_JSON)
@Path("/{id}")
public Response deletePerson(@PathParam("id") int id) {
    try{
        FACADE.deletePerson(id);
    }catch(PersonNotFoundException e){
         return pmapper.toResponse(e);
    }catch(Exception ex){
        return gmapper.toResponse(ex);
    }
   // Fetch and Remove the car using the provided id 
   // Come up with a “sensible” response for the request  
   return Response.ok("{\"status\": \"removed\"}").build();
}


    
}
