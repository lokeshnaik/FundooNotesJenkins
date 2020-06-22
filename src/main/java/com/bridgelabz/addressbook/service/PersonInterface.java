package com.bridgelabz.addressbook.service;
import java.util.List;
import com.bridgelabz.addressbook.dto.PersonUpdatedto;
import com.bridgelabz.addressbook.dto.Persondto;
import com.bridgelabz.addressbook.entity.PersonEntity;
import com.bridgelabz.addressbook.exception.CustomException;
public interface PersonInterface
{
  public PersonEntity addNewContact(Persondto persondto,Long bookid,String token)throws CustomException;
  public boolean deleteContact(String name,Long bookid,String token) throws CustomException;
  public boolean updateinformation(PersonUpdatedto personupdatedto,Long personid,Long bookid,String token)throws CustomException;
  public List<PersonEntity> sortByName(Long bookid,String token)throws CustomException;
  public List<PersonEntity> sortByZipCode(Long bookid,String token)throws CustomException;
  public List<PersonEntity> getAllContacts(Long bookid,Long id)throws CustomException;
  public PersonEntity getContactById(Long id,Long bookid,String token) throws CustomException;
  public PersonEntity getContactByName(String name,Long bookid,String token)throws CustomException;
  public List<PersonEntity> allThePersonDetails(String token)throws CustomException;
  
}
