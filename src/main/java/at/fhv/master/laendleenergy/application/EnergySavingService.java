package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.DTO.IncentiveDTO;

public interface EnergySavingService {
    void addSavingTarget(int value, int timeframe);
    String getCurrentSavingTarget(String householdId);
    void updateIncentive(String householdId, IncentiveDTO incentiveDTO) throws HouseholdNotFoundException;
    void receiveReport(boolean receive);
    String getCurrentIncentive(String householdId) throws HouseholdNotFoundException;
}
