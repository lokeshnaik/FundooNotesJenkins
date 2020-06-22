package com.bridgelabz.addressbook.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.micrometer.core.annotation.Counted;
import lombok.Data;

@Data
@Entity
@Table(name="Contacts")
public class PersonEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long personId;
   	@NotBlank(message = "FirstName is mandatory")
	 private String firstName;
   	@NotBlank(message = "LastName is mandatory")
	  private String lastName;
   	@Column(length=10)
	  private String phoneNumber;
   	@NotBlank(message = "Address is mandatory")
	  private String address;
   	@NotBlank(message = "City is mandatory")
	  private String city;
   	@NotBlank(message = "State is mandatory")
	  private String state;
	  private Long zipCode;
	  private LocalDateTime createdTime;
	  private LocalDateTime updateTime;
	  private long UserId;
	  private long bookid;
}
