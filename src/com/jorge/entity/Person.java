package com.jorge.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jorge.component.Address;

/**
 * An object of entity has its own database entity (primary key value) 
 * I.e.: Person table has primary key
 * 
 * You can't relate an entity with a value type of another entity
 * 
 * Entity: Person
 * Value Type (Component): Address
 * They just need a table in DB (2 classes, 1 table)
 *
 */
@Entity // It is a entity class whose value type (component) class is Address class
@Table(name="person")//The table name in DB, CASE SENSITIVE
public class Person {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Embedded // It is value type (component) of Person entity class
	@AttributeOverrides({
		@AttributeOverride(name="street", column=@Column(name="home_street")), // 'name="street"' is the name attribute in Address class.
																			   // 'column=@Column(name="home_street"' is the field name in DB
																			   // You must delete @Column annotations from Address class
		@AttributeOverride(name="city", column=@Column(name="home_city")),
		@AttributeOverride(name="zipcode", column=@Column(name="home_zipcode")),
	})
	private Address homeAddress; // Its component
	
	@Embedded // It is value type (component) of Person entity class
	@AttributeOverrides({
		@AttributeOverride(name="street", column=@Column(name="billing_street")),
		@AttributeOverride(name="city", column=@Column(name="billing_city")),
		@AttributeOverride(name="zipcode", column=@Column(name="billing_zipcode")),
	})
	private Address billingAddress; // Its component
	
	public Person() {}
	
	public Person(String name, Address homeAddress, Address billingAddress){
		this.name = name;
		this.homeAddress = homeAddress;
		this.billingAddress = billingAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", homeAddress=" + homeAddress + ", billingAddress="
				+ billingAddress + "]";
	}
	
}
