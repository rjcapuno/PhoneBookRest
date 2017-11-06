package com.lotus.rest.phonebookapp.exceptions;

public class ContactNotFoundException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1765790858859401299L;

	public ContactNotFoundException() {
		super("Contact not found");
	}
	
	public ContactNotFoundException(String message) {
		super(message);
	}
}
