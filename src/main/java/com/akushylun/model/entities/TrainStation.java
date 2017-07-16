package com.akushylun.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TrainStation {

    private int id;
    private Station station;
    private BigDecimal cost_price;
    private LocalDateTime datetime;
    private Train train;

    private TrainStation(Builder builder) {
	this.id = builder.id;
	this.station = builder.station;
	this.cost_price = builder.cost_price;
	this.datetime = builder.datetime;
	this.train = builder.train;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public Station getStation() {
	return station;
    }

    public BigDecimal getCost_price() {
	return cost_price;
    }

    public LocalDateTime getDatetime() {
	return datetime;
    }

    public Train getTrain() {
	return train;
    }

    public static class Builder {

	private int id;
	private Station station;
	private BigDecimal cost_price;
	private LocalDateTime datetime;
	private Train train;

	public Builder withStation(Station station) {
	    this.station = station;
	    return this;
	}

	public Builder withPrice(BigDecimal cost_price) {
	    this.cost_price = cost_price;
	    return this;
	}

	public Builder withDateTime(LocalDateTime datetime) {
	    this.datetime = datetime;
	    return this;
	}

	public Builder withId(int id) {
	    this.id = id;
	    return this;
	}

	public Builder withTrain(Train train) {
	    this.train = train;
	    return this;
	}

	public TrainStation build() {
	    return new TrainStation(this);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((cost_price == null) ? 0 : cost_price.hashCode());
	result = prime * result + ((datetime == null) ? 0 : datetime.hashCode());
	result = prime * result + id;
	result = prime * result + ((station == null) ? 0 : station.hashCode());
	result = prime * result + ((train == null) ? 0 : train.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof TrainStation))
	    return false;
	TrainStation other = (TrainStation) obj;
	if (cost_price == null) {
	    if (other.cost_price != null)
		return false;
	} else if (!cost_price.equals(other.cost_price))
	    return false;
	if (datetime == null) {
	    if (other.datetime != null)
		return false;
	} else if (!datetime.equals(other.datetime))
	    return false;
	if (id != other.id)
	    return false;
	if (station == null) {
	    if (other.station != null)
		return false;
	} else if (!station.equals(other.station))
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
	return "TrainStation [id=" + id + ", station=" + station + ", cost_price=" + cost_price + ", datetime="
		+ datetime + ", train=" + train + "]";
    }

}
