package com.lotus.rest.phonebookapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.lotus.rest.phonebookapp.contact.Contact;
import com.lotus.rest.phonebookapp.contact.ContactInterface;
import com.lotus.rest.phonebookapp.exceptions.ContactNotFoundException;
import com.lotus.rest.phonebookapp.exceptions.InvalidInputException;

public class PhoneBookApp {
	private static final String DATE_FORMAT = "MM/dd/yyyy";
	private static Scanner scanner = new Scanner(System.in);
	private static boolean willExit = false;
	private static Phonebook opb = new OraclePhoneBook();
	
	
	public static void main(String[] args) {
		
		String line;
		do {
			System.out.print("Enter commands: ");
			line = scanner.nextLine();
			String[] inputs = line.split(" ");
			try {
				executeCommands(inputs);
			} catch (ContactNotFoundException | InvalidInputException e) {
				System.out.println(e.getMessage());
			}
			
		} while(!willExit);
	}
	
	private static void executeCommands(String[] inputs) throws ContactNotFoundException, InvalidInputException {
		if(inputs[0].toLowerCase().equals("list") && inputs.length == 1) {
			list();
		} else if(inputs[0].toLowerCase().equals("show") && inputs.length == 2) {
			show(inputs[1]);
		} else if(inputs[0].toLowerCase().equals("search") && inputs.length == 2) {
			search(inputs[1]);
		} else if(inputs[0].toLowerCase().equals("delete") && inputs.length == 2) {
			delete(inputs[1]);
		} else if(inputs[0].toLowerCase().equals("create") && inputs.length == 1) {
			add();
		} else if(inputs[0].toLowerCase().equals("update") && inputs.length == 3) {
			update(inputs[1], inputs[2]);
		} else if(inputs[0].toLowerCase().equals("exit") && inputs.length == 1) {
			willExit = true;
			scanner.close();
		} else {
			System.out.println("Invalid command");
		}
	}
	
	private static void add() throws InvalidInputException {
		long id = 0;
		String name = null;
		Date birthday = null;
		Boolean isVip = null;
		String companyId = null;
		String number = null;
		String input = null;
		
		System.out.println("Name: ");
		input = scanner.nextLine();
		if(isValidName(input)) {
			name = input;
		}
		
		System.out.println("Birthday (dd/mm/yyyy): ");
		input = scanner.nextLine();
		if(!input.equals(null)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			try {
				birthday = dateFormat.parse(input);
			} catch (ParseException e) {
				throw new InvalidInputException("Invalid date format");
			}
		}
		
		System.out.println("Vip? (true/false) ");
		input = scanner.nextLine();
		if(!input.equals(null)) {
			isVip = Boolean.parseBoolean(input);
		}
		
		
		System.out.println("Company ID: ");
		companyId = scanner.nextLine();
		
		System.out.println("Contact number (11 digits): ");
		number = scanner.nextLine();
		if(number.equals(null)) {
			throw new InvalidInputException("Number cannot be null");
		}
		
		if(number.length() != 11) {
			throw new InvalidInputException("Number must be exactly 11 digits");
		}
		
		ContactInterface contact = new Contact(id, name, birthday, isVip, companyId, number);
		
		opb.add(contact);
	}
	
	private static boolean isValidName(String name) throws InvalidInputException {
		if(name.equals(null)) {
			throw new InvalidInputException("Name cannot be null");
		}
		
		if(name.contains(" ")) {
			throw new InvalidInputException("Name cannot contain spaces");
		}
		
		return true;
	}
	
	private static void list() throws ContactNotFoundException {
		opb.list();
	}
	
	private static void show(String name) throws ContactNotFoundException {
		opb.show(name);
	}
	
	private static void search(String query) throws ContactNotFoundException {
		opb.search(query);
	}
	
	private static void delete(String name) throws ContactNotFoundException {
		opb.delete(name);
	}
	
	private static void update(String name, String number) throws ContactNotFoundException {
		opb.update(name, number);
	}
	
	

}
