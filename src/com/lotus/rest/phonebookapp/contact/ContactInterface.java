package com.lotus.rest.phonebookapp.contact;

import java.util.Date;

public interface ContactInterface {
	String getName();

	void setName(String name);

	String getNumber();

	void setNumber(String number);
	
	long getId();
	
	Date getBirthday();
	
	boolean isVip();
	
	String getCompanyId();
}
