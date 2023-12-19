package at.fhv.master.laendleenergy.domain.exceptions;

public class HouseholdNotFoundException extends Exception {

    public HouseholdNotFoundException() {
        super("Household not found.");
    }
}
