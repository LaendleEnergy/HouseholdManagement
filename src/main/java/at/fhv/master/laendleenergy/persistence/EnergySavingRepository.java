package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Incentive;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;

public interface EnergySavingRepository {
    void addSavingTarget(int value, int timeframe);
    void updateIncentive(String householdId, Incentive incentive) throws HouseholdNotFoundException;
    void receiveReport(boolean receive);
    Incentive getCurrentIncentive(String householdId) throws HouseholdNotFoundException;
    EnergySavingTarget getCurrentSavingTarget(String householdId);
}
