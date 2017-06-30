package com.akushylun.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Booking {

    private int id;
    private BigDecimal price;
    private LocalDateTime dateTime;
    private List<Ticket> tickets = new ArrayList<Ticket>();
    private Person person;

    private Booking(Builder builder) {
	this.id = builder.id;
	this.price = builder.price;
	this.dateTime = builder.dateTime;
	this.tickets = builder.tickets;
	this.person = builder.person;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public BigDecimal getPrice() {
	return price;
    }

    public LocalDateTime getDate() {
	return dateTime;
    }

    public void setTickets(List<Ticket> tickets) {
	this.tickets = tickets;
    }

    public List<Ticket> getTickets() {
	return tickets;
    }

    public Person getUser() {
	return person;
    }

    public static class Builder {
	private int id;
	private BigDecimal price;
	private LocalDateTime dateTime;
	private Person person;
	private List<Ticket> tickets = new ArrayList<Ticket>();

	public Builder withId(int id) {
	    this.id = id;
	    return this;
	}

	public Builder withPrice(BigDecimal price) {
	    this.price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
	    return this;
	}

	public Builder withTickets(List<Ticket> tickets) {
	    this.tickets.addAll(tickets);
	    return this;
	}

	public Builder withDate(LocalDateTime dateTime) {
	    this.dateTime = dateTime;
	    return this;
	}

	public Builder withUser(Person person) {
	    this.person = person;
	    return this;
	}

	public Booking build() {
	    return new Booking(this);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
	result = prime * result + id;
	result = prime * result + ((price == null) ? 0 : price.hashCode());
	result = prime * result + ((tickets == null) ? 0 : tickets.hashCode());
	result = prime * result + ((person == null) ? 0 : person.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof Booking))
	    return false;
	Booking other = (Booking) obj;
	if (dateTime == null) {
	    if (other.dateTime != null)
		return false;
	} else if (!dateTime.equals(other.dateTime))
	    return false;
	if (id != other.id)
	    return false;
	if (price == null) {
	    if (other.price != null)
		return false;
	} else if (!price.equals(other.price))
	    return false;
	if (tickets == null) {
	    if (other.tickets != null)
		return false;
	} else if (!tickets.equals(other.tickets))
	    return false;
	if (person == null) {
	    if (other.person != null)
		return false;
	} else if (!person.equals(other.person))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Order [id=" + id + ", price=" + price + ", date=" + dateTime + ", tickets=" + tickets + ", user="
		+ person + "]";
    }

}
