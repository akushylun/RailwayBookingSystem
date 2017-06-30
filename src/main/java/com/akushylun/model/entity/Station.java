package com.akushylun.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Station {

    private int id;
    private String from;
    private String to;
    private List<Shedule> shedules = new ArrayList<Shedule>();

    private Station(Builder builder) {
	this.id = builder.id;
	this.from = builder.from;
	this.to = builder.to;
	this.shedules = builder.shedules;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getFrom() {
	return from;
    }

    public String getTo() {
	return to;
    }

    public List<Shedule> getShedules() {
	return shedules;
    }

    public static class Builder {
	private int id;
	private String from;
	private String to;
	private List<Shedule> shedules = new ArrayList<Shedule>();

	public Builder withId(int id) {
	    this.id = id;
	    return this;
	}

	public Builder from(String fromStation) {
	    this.from = fromStation;
	    return this;
	}

	public Builder to(String toStation) {
	    this.to = toStation;
	    return this;
	}

	public Builder withShedules(List<Shedule> shedules) {
	    this.shedules.addAll(shedules);
	    return this;
	}

	public Station build() {
	    return new Station(this);
	}
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((from == null) ? 0 : from.hashCode());
	result = prime * result + id;
	result = prime * result + ((shedules == null) ? 0 : shedules.hashCode());
	result = prime * result + ((to == null) ? 0 : to.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof Station))
	    return false;
	Station other = (Station) obj;
	if (from == null) {
	    if (other.from != null)
		return false;
	} else if (!from.equals(other.from))
	    return false;
	if (id != other.id)
	    return false;
	if (shedules == null) {
	    if (other.shedules != null)
		return false;
	} else if (!shedules.equals(other.shedules))
	    return false;
	if (to == null) {
	    if (other.to != null)
		return false;
	} else if (!to.equals(other.to))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Station [id=" + id + ", from=" + from + ", to=" + to + ", shedules=" + shedules + "]";
    }

}
