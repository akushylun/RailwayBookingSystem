package com.akushylun.model.entities;

import java.time.LocalDateTime;

public class Departure {

    private int id;
    private LocalDateTime dateTime;
    private Train train;

    private Departure(Builder builder) {
	this.id = builder.id;
	this.train = builder.train;
	this.dateTime = builder.dateTime;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public LocalDateTime getDateTime() {
	return dateTime;
    }

    public Train getTrain() {
	return train;
    }

    public static class Builder {
	private int id;
	private Train train;
	private LocalDateTime dateTime;

	public Builder withId(int id) {
	    this.id = id;
	    return this;
	}

	public Builder withDateTtime(LocalDateTime dateTime) {
	    this.dateTime = dateTime;
	    return this;
	}

	public Builder withTrain(Train train) {
	    this.train = train;
	    return this;
	}

	public Departure build() {
	    return new Departure(this);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
	result = prime * result + id;
	result = prime * result + ((train == null) ? 0 : train.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof Departure))
	    return false;
	Departure other = (Departure) obj;
	if (dateTime == null) {
	    if (other.dateTime != null)
		return false;
	} else if (!dateTime.equals(other.dateTime))
	    return false;
	if (id != other.id)
	    return false;
	if (train == null) {
	    if (other.train != null)
		return false;
	} else if (!train.equals(other.train))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Departure [id=" + id + ", dateTime=" + dateTime + ", train=" + train + "]";
    }

}
