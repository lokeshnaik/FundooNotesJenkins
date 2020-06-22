package com.bridgelabz.addressbook.repository;
import java.util.List;
import java.util.Optional;
import com.bridgelabz.addressbook.dto.PersonUpdatedto;
import com.bridgelabz.addressbook.entity.PersonEntity;
public interface PersonRepository 
{
 PersonEntity save(PersonEntity info);
 boolean deletePerson(String  name);
 List<PersonEntity> getAllContacts(Long bookid,Long id);
 Optional<PersonEntity>getPersonByName(String name);
 boolean updatedetails(PersonUpdatedto personupdatedto,Long personid,Long bookid,Long userid); 
 List<PersonEntity> getAllPersonDetails(Long id);
}
