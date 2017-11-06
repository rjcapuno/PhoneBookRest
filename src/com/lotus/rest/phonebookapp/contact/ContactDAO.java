package com.lotus.rest.phonebookapp.contact;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class ContactDAO {
	private final static String DB_USERNAME = "sample_rj";
	private final static String DB_PASSWORD = "1234";
	private final static String LIST_CONTACT_QUERY = "SELECT * FROM contact";
	private final static String SHOW_CONTACT_QUERY = "SELECT * FROM contact WHERE LOWER(name) = ?";
	private final static String SEARCH_QUERY = "SELECT * FROM contact WHERE LOWER(name) LIKE ?  OR contact_number LIKE ?";
	private final static String DELETE_CONTACT_QUERY = "DELETE from contact WHERE LOWER(name) = ?";
	private final static String UPDATE_CONTACT_QUERY = "UPDATE contact SET contact_number = ? WHERE LOWER(name) = ?";
	private final static String ADD_CONTACT_QUERY = "INSERT INTO contact(id, name, birthday, vipyn, companyid, contact_number)" + 
			"VALUES (contact_seq.NEXTVAL, ?, ?, ?, ?, ?)";
	private final static String UPDATE_ALL_CONTACT_INFO = "UPDATE contact SET contact_number = ?, birthday = ?, vipyn = ?, companyid = ? WHERE LOWER(name) = ?";
	
	private Connection setUpConnection() throws SQLException {
		Connection connection = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", DB_USERNAME, DB_PASSWORD);
			connection.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Database connection failed");
		} 
		
		return connection;
	}
	
	public List<ContactInterface> listContacts() {
		List<ContactInterface> contacts = new LinkedList<>();
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(LIST_CONTACT_QUERY);
			
			while (result.next()) {
				contacts.add(convertQueryToContact(result));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return contacts;
	}
	
	public List<ContactInterface> showContact(String name) {
		List<ContactInterface> contacts = new LinkedList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(SHOW_CONTACT_QUERY);
			statement.setString	(1, name.toLowerCase());
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				contacts.add(convertQueryToContact(result));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return contacts;
	}
	
	public List<ContactInterface> searchQuery(String query) {
		List<ContactInterface> contacts = new LinkedList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		query = query.toLowerCase();
		query = "%" + query + "%";
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(SEARCH_QUERY);
			statement.setString	(1, query);
			statement.setString	(2, query);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				contacts.add(convertQueryToContact(result));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return contacts;
	}
	
	public boolean deleteContact(String name) {
		Connection connection = null;
		PreparedStatement statement = null;
		boolean isSuccess = true;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(DELETE_CONTACT_QUERY);
			statement.setString	(1, name.toLowerCase());
			statement.executeUpdate();
			connection.commit();
			
		} catch(SQLException e) {
			e.printStackTrace();
			isSuccess = false;
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isSuccess;
	}
	
	public boolean updateContact(String name, String newContactNumber) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(UPDATE_CONTACT_QUERY);
			statement.setString	(1, newContactNumber.toLowerCase());
			statement.setString	(2, name.toLowerCase());
			statement.executeUpdate();
			connection.commit();
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean updateAllContactInfo(Contact contact) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(UPDATE_ALL_CONTACT_INFO);
			statement.setString	(1, contact.getNumber());
			statement.setDate(2, new java.sql.Date (contact.getBirthday().getTime()));
			statement.setBoolean(3, contact.isVip());
			statement.setString	(4, contact.getCompanyId());
			statement.setString	(5, contact.getName().toLowerCase());
			statement.executeUpdate();
			connection.commit();
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean addContact(ContactInterface contact) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = setUpConnection();
			statement = connection.prepareStatement(ADD_CONTACT_QUERY);
			statement.setString	(1, contact.getName());
			statement.setDate(2, new java.sql.Date (contact.getBirthday().getTime()));
			statement.setBoolean(3, contact.isVip());
			statement.setString	(4, contact.getCompanyId());
			statement.setString	(5, contact.getNumber());
			statement.executeUpdate();
			connection.commit();
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	
	private ContactInterface convertQueryToContact(ResultSet result) throws SQLException {
		long id = result.getLong("id");
		String name = result.getString("name");
		Date birthday = result.getDate("birthday");
		boolean isVip = result.getBoolean("vipYN");
		String companyID = result.getString("companyID");
		String number = result.getString("contact_number");
		ContactInterface contact = new Contact(id, name, birthday, isVip, companyID, number);
		return contact;
	}
	
}
