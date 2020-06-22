package com.bridgelabz.addressbook.controller;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.addressbook.dto.PersonUpdatedto;
import com.bridgelabz.addressbook.dto.Persondto;
import com.bridgelabz.addressbook.entity.PersonEntity;
import com.bridgelabz.addressbook.exception.CustomException;
import com.bridgelabz.addressbook.response.PersonResponse;
import com.bridgelabz.addressbook.serviceImplementation.PersonServiceImplementation;
import com.bridgelabz.addressbook.utility.JWTOperations;
@RestController
@RequestMapping("/person")
public class PersonController 
{
	@Autowired
	private PersonServiceImplementation service;
	@Autowired
	JWTOperations jwtop;
    @PostMapping("/addcontact/{bookid}/{token}")
    public ResponseEntity<PersonResponse> addNewContact(@Valid@RequestBody Persondto persondto,BindingResult result,@PathVariable Long bookid,@RequestHeader String token)throws CustomException
   
    {
    	if(result.hasErrors())
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new PersonResponse(result.getAllErrors().get(0).getDefaultMessage(), 200,"null"));
    	PersonEntity information=service.addNewContact(persondto,bookid,token);
    	return ResponseEntity.status(HttpStatus.CREATED).body(new PersonResponse("Person added Successful", 200,information ));
    }
    
    @DeleteMapping("/deletecontact/{name}/{bookid}/{token}")
    public ResponseEntity<PersonResponse> deleteContact(@PathVariable String name,@PathVariable Long bookid,@RequestHeader String token) throws CustomException
    {
    	boolean information=service.deleteContact(name,bookid,token);
    	return ResponseEntity.status(HttpStatus.CREATED).body(new PersonResponse("Person deleted Successful", 200,information ));
    }
    
    @PutMapping("/update/{personid}/{bookid}/{token}")
    public ResponseEntity<PersonResponse> updatedetails(@RequestBody PersonUpdatedto personupdatedto,@PathVariable Long personid,@PathVariable Long bookid,@RequestHeader String token) throws CustomException
    {
    	boolean information=service.updateinformation(personupdatedto,personid,bookid,token);
    	return ResponseEntity.status(HttpStatus.CREATED).body(new PersonResponse("Person detail updated Successful", 200,information ));
    }
    @GetMapping("/getAllContacts/{bookid}/{token}")
    public ResponseEntity<PersonResponse> getContacts(@PathVariable Long bookid,@RequestHeader String token) throws CustomException
    {
    	 Long userid=jwtop.parseJWT(token);
    	 System.out.println("AM in person controller");
    	List<PersonEntity> information=service.getAllContacts(bookid,userid);
    	return ResponseEntity.status(HttpStatus.CREATED).body(new PersonResponse("Getting all contacts successfully", 200,information ));
    } 
    
    @GetMapping("/getContactById/{id}/{bookid}/{token}")
    public ResponseEntity<PersonResponse> getContactById(@PathVariable Long id,@PathVariable Long bookid,@RequestHeader String token) throws CustomException
    {
    	System.out.println("Lokesh Loki");
    	PersonEntity information=service.getContactById(id,bookid,token);
    	return ResponseEntity.status(HttpStatus.CREATED).body(new PersonResponse("Got person detail updated Successful", 200,information ));
    } 
    @GetMapping("/getContactByName/{name}/{bookid}/{token}")
    public ResponseEntity<PersonResponse> getContactByName(@PathVariable String name,@PathVariable Long bookid,@RequestHeader String token) throws CustomException
    {
    	PersonEntity information=service.getContactByName(name,bookid,token);
    	return ResponseEntity.status(HttpStatus.CREATED).body(new PersonResponse("Person detail updated Successful", 200,information ));
    } 
    @GetMapping("/sortByName/{bookid}/{token}")
    public ResponseEntity<PersonResponse> sortByName(@PathVariable Long bookid,@RequestHeader String token) throws CustomException 
    {
    	List<PersonEntity> list=service.sortByName(bookid,token);
    	return ResponseEntity.status(HttpStatus.CREATED).body(new PersonResponse("Person detail updated Successful", 200,list ));
    }
    @GetMapping("/sortByZipcode/{bookid}/{token}")
    public ResponseEntity<PersonResponse> sortByZipcode(@PathVariable Long bookid,@RequestHeader String token) throws CustomException
    {
    	List<PersonEntity> list=service.sortByZipCode(bookid,token);
    	return ResponseEntity.status(HttpStatus.CREATED).body(new PersonResponse("Person detail updated Successful", 200,list ));
    }
    
    @GetMapping("/allPersonDetails/{token}")
    public ResponseEntity<PersonResponse> allThePersonDetais(@RequestHeader String token) throws CustomException
    {
    	List<PersonEntity> list=service.allThePersonDetails(token);
    	return ResponseEntity.status(HttpStatus.CREATED).body(new PersonResponse("Person detail are Successful", 200,list ));
    }
    
    
    
}
