package uk.ac.ed.bikerental;

import java.util.ArrayList;

public class Customer {
    private Location location;
    private ArrayList<Payment> orderInfos = new ArrayList<>();


    public Customer(Location location) {
        this.location = location;
    }

    public ArrayList<Payment> getOrderInfos() {
        return orderInfos;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((orderInfos == null) ? 0 : orderInfos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (orderInfos == null) {
			if (other.orderInfos != null)
				return false;
		} else if (!orderInfos.equals(other.orderInfos))
			return false;
		return true;
	}
    
}
