package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Incentive;
import at.fhv.master.laendleenergy.view.DTO.EnergySavingTargetDTO;
import at.fhv.master.laendleenergy.view.DTO.IncentiveDTO;

public interface EnergySavingService {
    void addSavingTarget(int value, int timeframe);
    EnergySavingTargetDTO getCurrentSavingTarget(String householdId);
    void updateIncentive(String householdId, IncentiveDTO incentiveDTO);
    IncentiveDTO getCurrentIncentive(String householdId);
}
