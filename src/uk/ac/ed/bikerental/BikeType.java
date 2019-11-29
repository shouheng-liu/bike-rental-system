package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Objects;

public class BikeType {

    private BikeTypes bikeType;
    private BigDecimal replacementValue = BigDecimal.ZERO;

    public BikeType(String bikeType, BigDecimal replacementValue) {
        BikeTypes type = BikeTypes.from(bikeType);
        this.bikeType = type;
        this.replacementValue = replacementValue;
        Controller.addBikeType(this);
    }



    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bikeType == null) ? 0 : bikeType.hashCode());
		result = prime * result + ((replacementValue == null) ? 0 : replacementValue.hashCode());
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
		BikeType other = (BikeType) obj;
		if (bikeType != other.bikeType)
			return false;
		if (replacementValue == null) {
			if (other.replacementValue != null)
				return false;
		} else if (!replacementValue.equals(other.replacementValue))
			return false;
		return true;
	}



	public BigDecimal getReplacementValue() {
        return this.replacementValue;
    }

    public BikeTypes getBikeType() {
        return this.bikeType;
    }


}