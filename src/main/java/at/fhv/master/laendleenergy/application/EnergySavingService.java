package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.DTO.SavingTargetDTO;
import at.fhv.master.laendleenergy.view.DTO.IncentiveDTO;

public interface EnergySavingService {
    void updateSavingTarget(String householdId, SavingTargetDTO savingTargetDTO) throws HouseholdNotFoundException;
    SavingTargetDTO getCurrentSavingTarget(String householdId) throws HouseholdNotFoundException;
    void updateIncentive(String householdId, IncentiveDTO incentiveDTO) throws HouseholdNotFoundException;
    IncentiveDTO getCurrentIncentive(String householdId) throws HouseholdNotFoundException;
}
