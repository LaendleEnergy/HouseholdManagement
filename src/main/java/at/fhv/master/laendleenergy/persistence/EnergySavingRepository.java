package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Incentive;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;

public interface EnergySavingRepository {
    void updateSavingTarget(String householdId, EnergySavingTarget savingTarget) throws HouseholdNotFoundException;
    EnergySavingTarget getCurrentSavingTarget(String householdId) throws HouseholdNotFoundException;
    void updateIncentive(String householdId, Incentive incentive) throws HouseholdNotFoundException;
    Incentive getCurrentIncentive(String householdId) throws HouseholdNotFoundException;
}
