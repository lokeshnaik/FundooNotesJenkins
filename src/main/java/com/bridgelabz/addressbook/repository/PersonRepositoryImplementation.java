package com.bridgelabz.addressbook.repository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.addressbook.dto.PersonUpdatedto;
import com.bridgelabz.addressbook.entity.PersonEntity;
@Repository
public class PersonRepositoryImplementation implements PersonRepository
{

	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public PersonEntity save(PersonEntity info) 
	{
		 Session session = entityManager.unwrap(Session.class);
		((Session) session).saveOrUpdate(info);
		return info;
	}
	@Override
	public boolean deletePerson(String name)
	{
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("DELETE FROM PersonEntity where firstName=:name");
		query.setParameter("name", name);	
		int result = query.executeUpdate();
		if (result >= 1) 
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonEntity> getAllContacts(Long bookid,Long id) {
		Session session = entityManager.unwrap(Session.class);	
		System.out.println("Am in repository before implemeting the query");
		List<PersonEntity> list = session.createQuery("From  PersonEntity where bookid=:bookid and user_id=:id").setParameter("bookid", bookid).setParameter("id",id).getResultList();
		System.out.println("Am in repository after implemeting the query");
		System.out.println("List:"+list);
		return list;
	}
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Optional<PersonEntity> getPersonByName(String name) 
	{
	
		Session session=entityManager.unwrap(Session.class);	
		return session.createQuery("from  PersonEntity where firstName=:name").setParameter("name", name).uniqueResultOptional();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional 
	@Override
	public boolean updatedetails(PersonUpdatedto personupdatedto,Long personid,Long bookid,Long userid)
	{
		
		Session session = entityManager.unwrap(Session.class);
	      Query q = session.createQuery("update PersonEntity set phoneNumber=:phonenumber,address=:address,state=:state,zipCode=:zip_code,city=:city  where personId=:personid and bookid=:bookid and UserId=:userid");
			q.setParameter("phonenumber",personupdatedto.getPhoneNumber());
			q.setParameter("address",personupdatedto.getAddress() );
			q.setParameter("state",personupdatedto.getClass() );
			q.setParameter("zip_code",personupdatedto.getZipCode());
			q.setParameter("city",personupdatedto.getCity());
			q.setParameter("personid", personid);
			q.setParameter("bookid",bookid);
			q.setParameter("userid", userid);
			int status = q.executeUpdate();
			if (status > 0) {
				return true;

			} else {
				return false;
			}
		
	}
	@Transactional 
	@Override
	public List<PersonEntity> getAllPersonDetails(Long id)
	{
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery("From PersonEntity where UserId=:id").setParameter("id",id).getResultList();
	}
}
