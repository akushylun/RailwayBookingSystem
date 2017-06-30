package com.akushylun.model.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Ticket {

    private int id;
    private BigDecimal price;
    private List<Booking> bookings;
    private Shedule shedule;

    private Ticket(Builder builder) {
	this.id = builder.id;
	this.price = builder.price;
	this.bookings = builder.bookings;
	this.shedule = builder.shedule;
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

    public List<Booking> getOrders() {
	return bookings;
    }

    public Shedule getShedule() {
	return shedule;
    }

    public static class Builder {

	private int id;
	private BigDecimal price;
	private List<Booking> bookings = new ArrayList<>();
	private Shedule shedule;

	public Builder withId(int id) {
	    this.id = id;
	    return this;
	}

	public Builder withPrice(BigDecimal price) {
	    this.price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
	    return this;
	}

	public Builder withOrders(List<Booking> bookings) {
	    this.bookings.addAll(bookings);
	    return this;
	}

	public Builder withShedule(Shedule shedule) {
	    this.shedule = shedule;
	    return this;
	}

	public Ticket build() {
	    return new Ticket(this);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((bookings == null) ? 0 : bookings.hashCode());
	result = prime * result + id;
	result = prime * result + ((price == null) ? 0 : price.hashCode());
	result = prime * result + ((shedule == null) ? 0 : shedule.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof Ticket))
	    return false;
	Ticket other = (Ticket) obj;
	if (bookings == null) {
	    if (other.bookings != null)
		return false;
	} else if (!bookings.equals(other.bookings))
	    return false;
	if (id != other.id)
	    return false;
	if (price == null) {
	    if (other.price != null)
		return false;
	} else if (!price.equals(other.price))
	    return false;
	if (shedule == null) {
	    if (other.shedule != null)
		return false;
	} else if (!shedule.equals(other.shedule))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Ticket [id=" + id + ", price=" + price + ", bookings=" + bookings + ", shedule=" + shedule + "]";
    }

}
