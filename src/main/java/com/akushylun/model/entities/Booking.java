package com.akushylun.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Booking {

    private int id;
    private BigDecimal price;
    private LocalDateTime dateTime;
    private Ticket ticket;
    private Person person;

    private Booking(Builder builder) {
	this.id = builder.id;
	this.price = builder.price;
	this.dateTime = builder.dateTime;
	this.ticket = builder.ticket;
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

    public void setPrice(BigDecimal price) {
	this.price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setDateTime(LocalDateTime dateTime) {
	this.dateTime = dateTime;
    }

    public void setPerson(Person person) {
	this.person = person;
    }

    public LocalDateTime getDate() {
	return dateTime;
    }

    public Ticket getTicket() {
	return ticket;
    }

    public void setTicket(Ticket ticket) {
	this.ticket = ticket;
    }

    public Person getUser() {
	return person;
    }

    public static class Builder {
	private int id;
	private BigDecimal price;
	private LocalDateTime dateTime;
	private Person person;
	private Ticket ticket;

	public Builder withId(int id) {
	    this.id = id;
	    return this;
	}

	public Builder withPrice(BigDecimal price) {
	    this.price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
	    return this;
	}

	public Builder withTicket(Ticket ticket) {
	    this.ticket = ticket;
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
	result = prime * result + ((person == null) ? 0 : person.hashCode());
	result = prime * result + ((price == null) ? 0 : price.hashCode());
	result = prime * result + ((ticket == null) ? 0 : ticket.hashCode());
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
	if (person == null) {
	    if (other.person != null)
		return false;
	} else if (!person.equals(other.person))
	    return false;
	if (price == null) {
	    if (other.price != null)
		return false;
	} else if (!price.equals(other.price))
	    return false;
	if (ticket == null) {
	    if (other.ticket != null)
		return false;
	} else if (!ticket.equals(other.ticket))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Booking [id=" + id + ", price=" + price + ", dateTime=" + dateTime + ", ticket=" + ticket + ", person="
		+ person + "]";
    }

}
