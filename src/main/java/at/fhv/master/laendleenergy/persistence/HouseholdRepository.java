package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.HouseholdMember;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;

import java.util.List;

public interface HouseholdRepository {
    Household createHouseholdIfNotExists(String householdId);
    void addHousehold(Household household);
    void deleteHousehold(String householdId) throws HouseholdNotFoundException;
    void updateHousehold(Household household) throws HouseholdNotFoundException;
    Household getHouseholdById(String householdId) throws HouseholdNotFoundException;
    List<HouseholdMember> getMembersOfHousehold(String householdId) throws HouseholdNotFoundException;
}
