package com.example.domain;

import com.example.domain.AbstractEntity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Customer
 *
 */
@Entity
public class Customer extends AbstractEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String name;

	public Customer() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
   
}
