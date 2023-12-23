package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.view.DTO.SavingTargetDTO;
import at.fhv.master.laendleenergy.view.DTO.IncentiveDTO;

public interface EnergySavingService {
    void updateSavingTarget(String householdId, SavingTargetDTO savingTargetDTO);
    SavingTargetDTO getCurrentSavingTarget(String householdId);
    void updateIncentive(String householdId, IncentiveDTO incentiveDTO);
    IncentiveDTO getCurrentIncentive(String householdId);
}
