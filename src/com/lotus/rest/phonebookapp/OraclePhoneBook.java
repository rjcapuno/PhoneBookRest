package com.lotus.rest.phonebookapp;

import java.util.List;

import com.lotus.rest.phonebookapp.contact.ContactDAO;
import com.lotus.rest.phonebookapp.contact.ContactInterface;
import com.lotus.rest.phonebookapp.exceptions.ContactNotFoundException;
import com.lotus.rest.phonebookapp.exceptions.InvalidInputException;

public class OraclePhoneBook implements Phonebook{
	private static final boolean SUCCESS = true;
	private static ContactDAO contactDAO = new ContactDAO();
	
	@Override
	public void add(ContactInterface contact) throws InvalidInputException {
		List<ContactInterface> contacts = contactDAO.showContact(contact.getName());
		
		if(!contacts.isEmpty()) {
			throw new InvalidInputException("Contact already exist");
		}
		
		if(contactDAO.addContact(contact) == SUCCESS) {
			System.out.println(contact.getName() + " added");
		}
	}
	
	@Override
	public void list() throws ContactNotFoundException {
		List<ContactInterface> contacts = contactDAO.listContacts();
		
		if(contacts.isEmpty()) {
			throw new ContactNotFoundException("No contacts yet");
		}
		
		printHeader();
		for(ContactInterface contact : contacts) {
			printContact(contact);
		}
	}
	
	@Override
	public void show(String name) throws ContactNotFoundException {
		List<ContactInterface> contacts = contactDAO.showContact(name);
		
		if(contacts.isEmpty()) {
			throw new ContactNotFoundException();
		}
		
		printHeader();
		for(ContactInterface contact : contacts) {
			printContact(contact);
		}
	}
	
	@Override
	public void search(String query) throws ContactNotFoundException {
		List<ContactInterface> contacts = contactDAO.searchQuery(query);
		
		if(contacts.isEmpty()) {
			throw new ContactNotFoundException();
		}
		
		printHeader();
		for(ContactInterface contact : contacts) {
			printContact(contact);
		}
	}
	
	@Override
	public void delete(String name) throws ContactNotFoundException {
		List<ContactInterface> contacts = contactDAO.showContact(name);
		
		if(contacts.isEmpty()) {
			throw new ContactNotFoundException();
		}
		
		if(contactDAO.deleteContact(name) == SUCCESS) {
			System.out.println(name + " deleted");
		}
		
	}
	
	@Override
	public void update(String name, String number) throws ContactNotFoundException {
		List<ContactInterface> contacts = contactDAO.showContact(name);
		
		if(contacts.isEmpty()) {
			throw new ContactNotFoundException();
		}
		
		if(contactDAO.updateContact(name, number) == SUCCESS) {
			System.out.println(name + " updated");
		}
	}
	
	private void printContact(ContactInterface contact) {
		System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s\n", 
				contact.getId(), contact.getName(), contact.getBirthday(),
				contact.isVip(), contact.getCompanyId(), contact.getNumber());
	}
	
	private void printHeader() {
		System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s\n", 
				"Id", "Name", "Birthday", "is VIP", "Company ID", "Contact number");
	}
}
