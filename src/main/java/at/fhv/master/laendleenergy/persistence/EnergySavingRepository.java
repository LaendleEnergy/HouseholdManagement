package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Incentive;

public interface EnergySavingRepository {
    void updateSavingTarget(String householdId, EnergySavingTarget savingTarget);
    EnergySavingTarget getCurrentSavingTarget(String householdId);
    void updateIncentive(String householdId, Incentive incentive);
    Incentive getCurrentIncentive(String householdId);
}
