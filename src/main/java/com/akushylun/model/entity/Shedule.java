package com.akushylun.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Shedule {

    private int id;
    private LocalDateTime start;
    private LocalDateTime end;
    private List<Ticket> tickets = new ArrayList<Ticket>();
    private Station station;
    private Train train;

    private Shedule(Builder builder) {
	this.id = builder.id;
	this.start = builder.start;
	this.end = builder.end;
	this.tickets = builder.tickets;
	this.station = builder.station;
	this.train = builder.train;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public LocalDateTime getStart() {
	return start;
    }

    public LocalDateTime getEnd() {
	return end;
    }

    public List<Ticket> getTickets() {
	return tickets;
    }

    public Station getStation() {
	return station;
    }

    public Train getTrain() {
	return train;
    }

    public static class Builder {
	private int id;
	private LocalDateTime start;
	private LocalDateTime end;
	private List<Ticket> tickets = new ArrayList<Ticket>();
	private Station station;
	private Train train;

	public Builder withId(int id) {
	    this.id = id;
	    return this;
	}

	public Builder start(LocalDateTime startDateTime) {
	    this.start = startDateTime;
	    return this;
	}

	public Builder end(LocalDateTime endDateTime) {
	    this.end = endDateTime;
	    return this;
	}

	public Builder withTickets(List<Ticket> tickets) {
	    this.tickets = tickets;
	    return this;
	}

	public Builder withStation(Station station) {
	    this.station = station;
	    return this;
	}

	public Builder withTrain(Train train) {
	    this.train = train;
	    return this;

	}

	public Shedule build() {
	    return new Shedule(this);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((end == null) ? 0 : end.hashCode());
	result = prime * result + id;
	result = prime * result + ((start == null) ? 0 : start.hashCode());
	result = prime * result + ((station == null) ? 0 : station.hashCode());
	result = prime * result + ((tickets == null) ? 0 : tickets.hashCode());
	result = prime * result + ((train == null) ? 0 : train.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof Shedule))
	    return false;
	Shedule other = (Shedule) obj;
	if (end == null) {
	    if (other.end != null)
		return false;
	} else if (!end.equals(other.end))
	    return false;
	if (id != other.id)
	    return false;
	if (start == null) {
	    if (other.start != null)
		return false;
	} else if (!start.equals(other.start))
	    return false;
	if (station == null) {
	    if (other.station != null)
		return false;
	} else if (!station.equals(other.station))
	    return false;
	if (tickets == null) {
	    if (other.tickets != null)
		return false;
	} else if (!tickets.equals(other.tickets))
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
	return "Shedule [id=" + id + ", start=" + start + ", end=" + end + ", tickets=" + tickets + ", station="
		+ station + ", train=" + train + "]";
    }

}
