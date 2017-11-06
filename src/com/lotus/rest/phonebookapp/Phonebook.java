package com.lotus.rest.phonebookapp;

import com.lotus.rest.phonebookapp.contact.ContactInterface;
import com.lotus.rest.phonebookapp.exceptions.ContactNotFoundException;
import com.lotus.rest.phonebookapp.exceptions.InvalidInputException;

public interface Phonebook {
	void add(ContactInterface contact) throws InvalidInputException;
	
	void list() throws ContactNotFoundException;
	
	void show(String name) throws ContactNotFoundException;
	
	void search(String query) throws ContactNotFoundException;
	
	void delete(String name) throws ContactNotFoundException;
	
	void update(String name, String number) throws ContactNotFoundException;
	
}
