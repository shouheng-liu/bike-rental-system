package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * Date: Nov 28-2019
 * Represents the date range between two dates.
 * @author shouheng
 * @version 1.1
 * @since 1.0
 *
 */
public class DateRange {
    /**
     * LocalDate value representing the start of the date range.
     */
    private LocalDate start;
    /**
     * LocalDate value representing the end of the date range.
     */
    private LocalDate end;
    
    /**
     * Creates a date range with specified start date and end date and sets them.
     * @param start	The first date of the date range.
     * @param end	The last date of the date range.
     */
    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }
    
    /**
     * Gets the start date of the date range.
     * @return A LocalDate representing the start date of the date range.
     */
    public LocalDate getStart() {
        return this.start;
    }
    
    /**
     * Gets the end date of the date range.
     * @return A LocalDate representing the last date of the date range.
     */
    public LocalDate getEnd() {
        return this.end;
    }

    /**
     * Returns the number of years between start and end date.
     * @return A long value representing the number of years between start and end date.
     */
    public long toYears() {
        return ChronoUnit.YEARS.between(this.getStart(), this.getEnd());
    }

    /**
     * Returns the number of days between start and end date.
     * @return A long value representing the number of days between start and end date.
     */
    public long toDays() {
        return ChronoUnit.DAYS.between(this.getStart(), this.getEnd());
    }

    /**
     * Checks whether the start date is before or on the same date as the other end date
     * and the end date is after or on the same date as the other start date.
     * @param other	The date range that's being compare against.
     * @return A Boolean of whether the date range has any overlapping days with another date range, other.
     */
    public Boolean overlaps(DateRange other) {
        // TODO: implement date range intersection checking
        int compareValueA = (this.start).compareTo(other.getEnd());
        int compareValueB = (this.end).compareTo(other.getStart());
		/*
		 * if (compareValueA <= 0 && compareValueB >= 0) { // if (this.start ==
		 * other.getStart() && this.end == other.getEnd()) { return true; } else {
		 * return false; }
		 */
        return compareValueA <= 0 && compareValueB >= 0;
        //assert false;
        //return null;
    }

    @Override
    public int hashCode() {
        // hashCode method allowing use in collections
        return Objects.hash(end, start);
    }

    @Override
    public boolean equals(Object obj) {
        // equals method for testing equality in tests
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateRange other = (DateRange) obj;
        return Objects.equals(end, other.end) && Objects.equals(start, other.start);
    }
    
    // You can add your own methods here
}
