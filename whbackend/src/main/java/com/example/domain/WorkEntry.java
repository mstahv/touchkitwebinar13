package com.example.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: WorkEntry
 *
 */
@Entity
public class WorkEntry extends AbstractEntity {

	@Temporal(TemporalType.DATE)
	private Date date;
	private Float duration = 0f;
	private String description = "";
	private BillingType billingType;
	
	public WorkEntry() {
		super();
	}   
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public BillingType getBillingType() {
		return billingType;
	}
	public void setBillingType(BillingType billingType) {
		this.billingType = billingType;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return duration of work effort in hours
	 */
	public Float getDuration() {
		return duration;
	}
	public void setDuration(Float duration) {
		this.duration = duration;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(description);
		return sb.toString();
	}
   
}
