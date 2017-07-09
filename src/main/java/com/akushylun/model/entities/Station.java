package com.akushylun.model.entities;

public class Station {

    private int id;
    private String name;

    private Station(Builder builder) {
	this.id = builder.id;
	this.name = builder.name;
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

    public static class Builder {
	private int id;
	private String name;

	public Builder withId(int id) {
	    this.id = id;
	    return this;
	}

	public Builder withName(String name) {
	    this.name = name;
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
	result = prime * result + id;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
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
	if (id != other.id)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Station [id=" + id + ", name=" + name + "]";
    }

}