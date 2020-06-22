package com.bridgelabz.addressbook.serviceImplementation;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.bridgelabz.addressbook.dto.PersonUpdatedto;
import com.bridgelabz.addressbook.dto.Persondto;
import com.bridgelabz.addressbook.entity.PersonEntity;
import com.bridgelabz.addressbook.exception.CustomException;
import com.bridgelabz.addressbook.repository.PersonRepositoryImplementation;
import com.bridgelabz.addressbook.response.PersonResponse;
import com.bridgelabz.addressbook.service.PersonInterface;
import com.bridgelabz.addressbook.utility.JWTOperations;
@Service
public class PersonServiceImplementation implements PersonInterface
{
	@Autowired
	private RestTemplate restTemplate;
     @Autowired 
    private PersonRepositoryImplementation repository;
     @Autowired
     private JWTOperations jwtop;
     public PersonResponse isUserExists(Long id)
  	{
    	 PersonResponse response=restTemplate.getForEntity("http://localhost:8880/user/getuserbyid/"+id,PersonResponse.class).getBody();
  		return response;
  	}
     public PersonResponse isUserAndBookExist(Long id,Long bookid) throws CustomException
   	{
     	PersonResponse response=restTemplate.getForEntity("http://localhost:8880/user/getuserbyIdBookId/"+id+"/"+bookid,PersonResponse.class).getBody();
     	return response;
   	}
	@Override
	@Transactional
	public PersonEntity addNewContact(Persondto persondto,Long bookid,String token)throws CustomException
	{
		Long userid=(long)0;
		PersonEntity person=new PersonEntity();
		userid =(long )jwtop.parseJWT(token);
		isUserAndBookExist(userid,bookid);
		if(repository.getPersonByName(persondto.getFirstName()).isPresent()==false)
		{
			BeanUtils.copyProperties(persondto,person);
			person.setCreatedTime(LocalDateTime.now());
			person.setUpdateTime(LocalDateTime.now());
			person.setUserId(userid);
			person.setBookid(bookid);;
			repository.save(person);
			return person;
		}
		else
		{
			throw new CustomException("User is already present with this name",HttpStatus.FOUND);
		}
	}

	@Transactional
	@Override
	public boolean deleteContact(String name,Long bookid,String token) throws CustomException
	{
		Long userid=(long)0;
		userid =(long )jwtop.parseJWT(token);
		isUserAndBookExist(userid,bookid);
		boolean value=repository.deletePerson(name);
		return value;
	}

	@Transactional
	@Override
	public boolean updateinformation(PersonUpdatedto persondto,Long personid,Long bookid,String token) throws CustomException
	{
		Long userid=(long)0;
		userid =(long )jwtop.parseJWT(token);
		isUserAndBookExist(userid,bookid);
		System.out.println("User Id="+userid+" , BookId="+bookid);
	    boolean value=repository.updatedetails(persondto,personid,bookid,userid);
		return value;
	}

	@Override
	public List<PersonEntity> sortByName(Long bookid,String token) throws CustomException 
	{
		Long userid=(long)0;
		userid =(long )jwtop.parseJWT(token);
		isUserAndBookExist(userid,bookid);
		return getAllContacts(bookid,userid).parallelStream().sorted(Comparator.comparing(PersonEntity::getFirstName)).collect(Collectors.toList());
	}

	@Override
	public List<PersonEntity> sortByZipCode(Long bookid,String token) throws CustomException 
	{
		Long userid=(long)0;
		userid =(long )jwtop.parseJWT(token);
		isUserAndBookExist(userid,bookid);
		return getAllContacts(bookid,userid).parallelStream().sorted(Comparator.comparing(PersonEntity::getZipCode)).collect(Collectors.toList());
	}

	@Override
	public List<PersonEntity> getAllContacts(Long bookid,Long userId) throws CustomException
	{
		System.out.println("Am in person implementation");
		List<PersonEntity> contacts=repository.getAllContacts(bookid,userId);
		if(contacts.size()!=0)
		{
			System.out.println("CONTACTS:"+contacts);
			return contacts;
		}
		 throw new CustomException("Title in not found",HttpStatus.NOT_FOUND);	
	}

	@Override
	public PersonEntity getContactById(Long id,Long bookid,String token) throws CustomException 
	{
		System.out.println("Lokesh Naik");
		Long userid=(long)0;
		userid =(long )jwtop.parseJWT(token);
		isUserAndBookExist(userid,bookid);
		return getAllContacts(bookid,userid).stream().filter(n->n.getPersonId()==id).findFirst().orElseThrow(() -> new CustomException("Person Id  is not found", HttpStatus.NOT_FOUND));
	}

	@Override
	public PersonEntity getContactByName(String name,Long bookid,String token) throws CustomException
	{
		Long userid=(long)0;
		userid =(long )jwtop.parseJWT(token);
		isUserAndBookExist(userid,bookid);
		return getAllContacts(bookid,userid).stream().filter(n->n.getFirstName().equals(name)).findFirst().orElseThrow(() -> new CustomException("Person name is not found", HttpStatus.NOT_FOUND));

	}
	@Override
	public List<PersonEntity> allThePersonDetails(String token) throws CustomException 
	{
		Long userid=(long)0;
		userid =(long )jwtop.parseJWT(token);
		isUserExists(userid);
		List<PersonEntity>list=repository.getAllPersonDetails(userid);
		return list;
	}

}
