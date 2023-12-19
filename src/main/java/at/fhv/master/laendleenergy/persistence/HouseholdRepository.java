package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;

public interface HouseholdRepository {
    String addHousehold(Household household);
    void deleteHousehold(String householdId) throws HouseholdNotFoundException;
    void updateHousehold(Household household) throws HouseholdNotFoundException;
    Household getHouseholdById(String householdId) throws HouseholdNotFoundException;
}
