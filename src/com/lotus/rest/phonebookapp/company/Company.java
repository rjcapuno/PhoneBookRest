package com.lotus.rest.phonebookapp.company;

public class Company implements CompanyInterface{
	private long id;
	private String code;
	private String name;
	private String description;
	
	public Company(long id, String code, String name, String description) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
	
	
}
