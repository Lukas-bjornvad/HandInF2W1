package facades;

import entities.Person;
import exceptions.PersonNotFoundException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade{

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private PersonFacade() {
    populate();
    }
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    

    @Override
    public Person addPerson(String fName, String lName, String phone) {
        EntityManager em = getEntityManager();
        try {
            Person per =new Person(fName,lName,phone, new Date());
            em.getTransaction().begin();
            em.persist(per);
            em.getTransaction().commit();
            return per;
        } finally {
            em.close();
        }
    }

    @Override
    public Person deletePerson(int id) throws PersonNotFoundException{
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person per = em.find(Person.class, id);
            if(per==null){
              throw new PersonNotFoundException("Could not delete, provided id does not exist");  
            }
            em.remove(per);
            em.getTransaction().commit();
            return per;
        } finally {
            em.close();
        }
    }

    @Override
    public Person getPerson(int id)throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            Person per = em.find(Person.class, id);
            if(per==null){
              throw new PersonNotFoundException("No person with provided id found");  
            }
            return per;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Person> getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query
                    = em.createQuery("Select p from Person p", Person.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Person editPerson(Person p) throws PersonNotFoundException{
         EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person per = em.find(Person.class, p.getId());
             if(per==null){
              throw new PersonNotFoundException("No person with provided id found");  
            }
            per.setFirstName(p.getFirstName());
            per.setLastName(p.getLastName());
            per.setPhone(p.getPhone());
            per.setLastEdited(new Date());
            em.merge(per);
            em.getTransaction().commit();
            return per;
        } finally {
            em.close();
        }
    }
    
    public void populate(){
        addPerson("Lars","Larsen","78459645");
        addPerson("Karl","Dellings","98887565");
        addPerson("Borris","Vladislav","65893258");
    }

}
