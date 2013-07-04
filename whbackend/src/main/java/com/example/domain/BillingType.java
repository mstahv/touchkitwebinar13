package com.example.domain;

import com.example.domain.AbstractEntity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: BillingType
 *
 */
@Entity
public class BillingType extends AbstractEntity implements Serializable {

	@ManyToOne(fetch = FetchType.EAGER)
	private Customer customer;
	private String name;
	private float hourPrice;
	
	public BillingType() {
		super();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getHourPrice() {
		return hourPrice;
	}

	public void setHourPrice(float hourPrice) {
		this.hourPrice = hourPrice;
	}
	
	@Override
	public String toString() {
		return customer + ", " + name + ", " + hourPrice + "€/h";
	}
   
}
