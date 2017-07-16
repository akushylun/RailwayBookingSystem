package com.akushylun.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Ticket {

    private int id;
    private String description;
    private List<Booking> bookings;
    private List<TrainStation> trainStationList;

    private Ticket(Builder builder) {
	this.id = builder.id;
	this.description = builder.description;
	this.bookings = builder.bookings;
	this.trainStationList = builder.trainStationList;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getDescription() {
	return description;
    }

    public List<Booking> getBookings() {
	return bookings;
    }

    public List<TrainStation> getTrainStationList() {
	return trainStationList;
    }

    public void setTrainStationList(List<TrainStation> trainStationList) {
	this.trainStationList = trainStationList;
    }

    public static class Builder {

	private int id;
	private String description;
	private List<Booking> bookings = new ArrayList<>();
	private List<TrainStation> trainStationList;

	public Builder withId(int id) {
	    this.id = id;
	    return this;
	}

	public Builder withDescription(String description) {
	    this.description = description;
	    return this;
	}

	public Builder withBooking(List<Booking> bookings) {
	    this.bookings.addAll(bookings);
	    return this;
	}

	public Builder withTrainStationList(List<TrainStation> trainStationList) {
	    this.trainStationList = trainStationList;
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
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + id;
	result = prime * result + ((trainStationList == null) ? 0 : trainStationList.hashCode());
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
	if (description == null) {
	    if (other.description != null)
		return false;
	} else if (!description.equals(other.description))
	    return false;
	if (id != other.id)
	    return false;
	if (trainStationList == null) {
	    if (other.trainStationList != null)
		return false;
	} else if (!trainStationList.equals(other.trainStationList))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Ticket [id=" + id + ", description=" + description + ", bookings=" + bookings + ", trainStationList="
		+ trainStationList + "]";
    }

}
