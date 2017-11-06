package com.lotus.rest.phonebookapp.contact;

import java.util.Date;

public class Contact implements ContactInterface, Comparable<Contact>{
	private Long id;
	private String name;
	private Date birthday;
	private boolean isVip;
	private String companyId;
	private String number;
	
	public Contact(long id, String name, Date birthday, boolean isVip, String companyId, String number) {
		
		this.id = id;
		this.name = name;
		this.birthday = birthday;
		this.isVip = isVip;
		this.companyId = companyId;
		this.number = number;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public Date getBirthday() {
		return birthday;
	}

	@Override
	public boolean isVip() {
		return isVip;
	}
	
	@Override
	public String getCompanyId() {
		return companyId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getNumber() {
		return number;
	}

	@Override
	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return this.id + " " +
				this.name + " " + 
				this.birthday + " " +
				this.isVip + " " +
				this.companyId + " " +
				this.number;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null) {
			Contact c = (Contact) obj;
			return this.getName().toLowerCase().compareTo(c.getName().toLowerCase()) == 0 || this.getNumber().compareTo(c.getNumber()) == 0;
		}
		return false;
	}

	@Override
	public int compareTo(Contact o) {
		if(this.equals(o)) {
			return 0;
		} else {
			return this.getName().toLowerCase().compareTo(o.getName().toLowerCase());
		}
	}
	
}
