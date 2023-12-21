package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Incentive;

public interface EnergySavingRepository {
    void addSavingTarget(int value, int timeframe);
    void updateIncentive(String householdId, Incentive incentive);
    Incentive getCurrentIncentive(String householdId);
    EnergySavingTarget getCurrentSavingTarget(String householdId);
}
