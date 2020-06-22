package com.bridgelabz.addressbook.response;
import lombok.Data;
@Data
public class PersonResponse 
{
public PersonResponse() 
{
		
}
	String message;
	int statusCode;
	Object data;
	public PersonResponse(String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	public PersonResponse(String message, int statusCode, Object data) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.data = data;
	}
}
