package com.akushylun.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Train {

    private int id;
    private String name;
    private List<Shedule> shedules = new ArrayList<Shedule>();

    private Train(Builder builder) {
	this.id = builder.id;
	this.name = builder.name;
	this.shedules = builder.shedules;
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

    public List<Shedule> getTickets() {
	return shedules;
    }

    public static class Builder {
	private int id;
	private String name;
	private List<Shedule> shedules = new ArrayList<Shedule>();

	public Builder withId(int id) {
	    this.id = id;
	    return this;
	}

	public Builder withName(String name) {
	    this.name = name;
	    return this;
	}

	public Builder withShedules(List<Shedule> shedules) {
	    this.shedules.addAll(shedules);
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
	result = prime * result + id;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((shedules == null) ? 0 : shedules.hashCode());
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
	if (id != other.id)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (shedules == null) {
	    if (other.shedules != null)
		return false;
	} else if (!shedules.equals(other.shedules))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Train [id=" + id + ", name=" + name + ", shedules=" + shedules + "]";
    }

}
