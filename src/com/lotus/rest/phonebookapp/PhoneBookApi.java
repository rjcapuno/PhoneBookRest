package com.lotus.rest.phonebookapp;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.lotus.rest.phonebookapp.contact.Contact;
import com.lotus.rest.phonebookapp.contact.ContactDAO;
import com.lotus.rest.phonebookapp.contact.ContactInterface;
import com.lotus.rest.phonebookapp.exceptions.InvalidInputException;

@Path("phonebook")
public class PhoneBookApi {
	private static final String DATE_FORMAT = "MM/dd/yyyy";
	private static final String EMPTY = "{}";
	private static final String OK = "status: OK";
	private static ContactDAO contactDAO = new ContactDAO();
	
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response save(@FormParam("name") String name, @FormParam("number") String number, @FormParam("isVip") boolean isVip,
			@FormParam("companyId") String companyId, @FormParam("birthday") String birthday) {
		final String NAME_NULL = "Name cannot be null";
		final String NUMBER_NULL = "Number cannot be null";
		final String NUMBER_INVALID = "Number must be 11 characters long";
		final String BIRTHDAY_INVALID = "Birthday invalid format";
		final String UPDATE_FAILED = "Update failed";
		final String CREATE_FAILED = "Create failed";
		
		if(name.equals(null)) {
			return Response.status(400).entity(NAME_NULL).build();
		} 
		
		if(number.equals(null)) {
			return Response.status(400).entity(NUMBER_NULL).build();
		}
		
		if(number.length() != 11) {
			return Response.status(400).entity(NUMBER_INVALID).build();
		}
		
		Date birthDate = null;
		if(birthday.equals(null)) {
			if(contactDAO.updateContact(name, number)) {
				return Response.status(200).entity(OK).build();
			}
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			try {
				birthDate = dateFormat.parse(birthday);
			} catch (ParseException e) {
				return Response.status(400).entity(BIRTHDAY_INVALID).build();
			}
		}
		
		List<ContactInterface> contacts = contactDAO.showContact(name);
		Contact newContact = new Contact(0, name, birthDate, isVip, companyId, number);
		
		if(contacts.isEmpty()) {
			if(contactDAO.addContact(newContact)) {
				return Response.status(200).entity(OK).build();
			} else {
				return Response.status(500).entity(CREATE_FAILED).build();
			}
		}
			
		if(contactDAO.updateAllContactInfo(newContact)) {
			return Response.status(200).entity(OK).build();
		} else {
			return Response.status(500).entity(UPDATE_FAILED).build();
		}
	}

	
		
	@Path("delete/{name}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("name") String name) throws JsonGenerationException, JsonMappingException, IOException {
		final String FAIL = "error: User not found";
		
		List<ContactInterface> contacts = contactDAO.showContact(name);
		
		if(contacts.isEmpty()) {
			return Response.status(200).entity(FAIL).build();
		}
		boolean isDeleteSuccess = contactDAO.deleteContact(name);
		if(isDeleteSuccess) {
			return Response.status(200).entity(OK).build();
		}
		return Response.status(200).entity(FAIL).build();
		
	}
	
	@Path("search/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@PathParam("name") String query) throws JsonGenerationException, JsonMappingException, IOException {
		
		List<ContactInterface> contacts = contactDAO.searchQuery(query);
		
		if(contacts.isEmpty()) {
			return Response.status(200).entity(EMPTY).build();
		}
		
		return Response.status(200).entity(contacts).build();
	}

	@Path("{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response show(@PathParam("name") String name) throws JsonGenerationException, JsonMappingException, IOException {
		final int FIRST_INDEX = 0;
		List<ContactInterface> contacts = contactDAO.showContact(name);
		
		if(contacts.isEmpty()) {
			return Response.status(200).entity(EMPTY).build();
		}
		
		return Response.status(200).entity(contacts.get(FIRST_INDEX)).build();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		
		List<ContactInterface> contacts = contactDAO.listContacts();
		
		if(contacts.isEmpty()) {
			return Response.status(200).entity(EMPTY).build();
		}
		
		return Response.status(200).entity(contacts).build();
	}
	
}
