package com.example.domain;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

public class WHService {

	private static final String PERSISTENCE_UNIT_NAME = "com.example.whbackend";
	private static EntityManagerFactory EMF = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private EntityManager em;

	public WHService() {
		em = EMF.createEntityManager();
		/** Create some test data automatically if DB empty */
		if ((Long) em.createQuery("Select COUNT(c) FROM Customer c")
				.getSingleResult() == 0) {
			createTestData();
		}
	}

	public void saveOrPersist(WorkEntry we) {
		em.getTransaction().begin();
		if (we.getId() == null) {
			em.persist(we);
		} else {
			we = em.merge(we);
		}
		em.getTransaction().commit();
	}

	public List<WorkEntry> getWorkEntries(Date date) {
		TypedQuery<WorkEntry> query = em.createQuery(
				"SELECT we FROM WorkEntry we WHERE we.date = :date",
				WorkEntry.class);
		query.setParameter("date", date, TemporalType.DATE);
		return query.getResultList();
	}

	public List<BillingType> getBillingTypes() {
		return em.createQuery("SELECT bt FROM BillingType bt", BillingType.class)
				.getResultList();
	}

	public void createTestData() {
		String[] customers = new String[] { "Ford", "Opel", "Audi", "Toyota" };
		String[] types = new String[] { "Development", "Architecturing",
				"Testing", "24/7 Help" };
		Random random = new Random(0);
		em.getTransaction().begin();
		Customer customer;
		BillingType billingType = null;
		for (String c : customers) {
			customer = new Customer();
			customer.setName(c);
			em.persist(customer);
			for (String t : types) {
				billingType = new BillingType();
				billingType.setName(t);
				billingType.setHourPrice(30 + random.nextInt(300));
				billingType.setCustomer(customer);
				em.persist(billingType);
			}
		}
		
		WorkEntry workEntry = new WorkEntry();
		workEntry.setBillingType(billingType);
		workEntry.setDate(new Date());
		workEntry.setDuration(3f);
		workEntry.setDescription("Fixing hard stuff");
		em.persist(workEntry);
		workEntry = new WorkEntry();
		workEntry.setBillingType(billingType);
		workEntry.setDate(new Date());
		workEntry.setDuration(2f);
		workEntry.setDescription("Continuing with stuff");		
		em.persist(workEntry);
		em.getTransaction().commit();
	}

	public void delete(WorkEntry workEntry) {
		em.getTransaction().begin();
		em.remove(workEntry);
		em.getTransaction().commit();
	}

}
