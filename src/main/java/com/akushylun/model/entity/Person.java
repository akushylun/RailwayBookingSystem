package com.akushylun.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private int id;
    private String name;
    private String surname;
    private String email;
    private Login login;
    private List<Booking> bookings = new ArrayList<Booking>();
    private Role role;

    public enum Role {
	ADMIN, USER
    }

    private Person(Builder builder) {
	this.id = builder.id;
	this.login = builder.login;
	this.name = builder.name;
	this.surname = builder.surname;
	this.email = builder.email;
	this.bookings = builder.bookings;
	this.role = builder.role;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public Login getLogin() {
	return login;
    }

    public String getName() {
	return name;
    }

    public String getSurname() {
	return surname;
    }

    public String getEmail() {
	return email;
    }

    public List<Booking> getOrders() {
	return bookings;
    }

    public String getRole() {
	return role.name();
    }

    public static class Builder {
	private int id;
	private Login login;
	private String name;
	private String surname;
	private String email;
	private List<Booking> bookings = new ArrayList<Booking>();
	private Role role;

	public Builder withId(int id) {
	    this.id = id;
	    return this;
	}

	public Builder withPersonLogin(Login login) {
	    this.login = login;
	    return this;
	}

	public Builder withName(String name) {
	    this.name = name;
	    return this;
	}

	public Builder withSurname(String surname) {
	    this.surname = surname;
	    return this;
	}

	public Builder withEmail(String email) {
	    this.email = email;
	    return this;
	}

	public Builder withOrders(List<Booking> bookings) {
	    this.bookings.addAll(bookings);
	    return this;
	}

	public Builder withRole(String role) {
	    this.role = Role.valueOf(role);
	    return this;
	}

	public Person build() {
	    return new Person(this);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((bookings == null) ? 0 : bookings.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + id;
	result = prime * result + ((login == null) ? 0 : login.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((role == null) ? 0 : role.hashCode());
	result = prime * result + ((surname == null) ? 0 : surname.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof Person))
	    return false;
	Person other = (Person) obj;
	if (bookings == null) {
	    if (other.bookings != null)
		return false;
	} else if (!bookings.equals(other.bookings))
	    return false;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (id != other.id)
	    return false;
	if (login == null) {
	    if (other.login != null)
		return false;
	} else if (!login.equals(other.login))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (role != other.role)
	    return false;
	if (surname == null) {
	    if (other.surname != null)
		return false;
	} else if (!surname.equals(other.surname))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Person [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", login=" + login
		+ ", bookings=" + bookings + ", role=" + role + "]";
    }

}
