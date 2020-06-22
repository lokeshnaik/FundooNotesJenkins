package com.bridgelabz.addressbook.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class Persondto
{
 @NotBlank(message = "FirstName is mandatory")	
  private String firstName;
 @NotBlank(message = "LastName is mandatory")
  private String lastName;
  @Pattern(regexp = "(\\5|6|7|8|9)[0-9]{9}")
  private String phoneNumber;
  @NotBlank(message = "Address is mandatory")
  private String address;
  @NotBlank(message = "City is mandatory")
  private String city;
  @NotBlank(message = "State is mandatory")
  private String state;
  private Long zipCode;  
}
