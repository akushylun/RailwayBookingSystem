package com.akushylun.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Train {

    private int id;
    private String name;
    private List<Station> stationList = new ArrayList<Station>();
    private List<Departure> departureList = new ArrayList<Departure>();

    private Train(Builder builder) {
	this.id = builder.id;
	this.name = builder.name;
	this.stationList = builder.stationList;
	this.departureList = builder.departureList;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public List<Station> getStationList() {
	return stationList;
    }

    public List<Departure> getDepartureList() {
	return departureList;
    }

    public static class Builder {
	private int id;
	private String name;
	private List<Station> stationList = new ArrayList<Station>();
	private List<Departure> departureList = new ArrayList<Departure>();

	public Builder withId(int id) {
	    this.id = id;
	    return this;
	}

	public Builder withName(String name) {
	    this.name = name;
	    return this;
	}

	public Builder withStationList(List<Station> stationList) {
	    this.stationList = stationList;
	    return this;
	}

	public Builder withDepartureList(List<Departure> departureList) {
	    this.departureList = departureList;
	    return this;
	}

	public Train build() {
	    return new Train(this);
	}
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((departureList == null) ? 0 : departureList.hashCode());
	result = prime * result + id;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((stationList == null) ? 0 : stationList.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof Train))
	    return false;
	Train other = (Train) obj;
	if (departureList == null) {
	    if (other.departureList != null)
		return false;
	} else if (!departureList.equals(other.departureList))
	    return false;
	if (id != other.id)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (stationList == null) {
	    if (other.stationList != null)
		return false;
	} else if (!stationList.equals(other.stationList))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Train [id=" + id + ", name=" + name + ", stationList=" + stationList + ", departureList="
		+ departureList + "]";
    }

}
