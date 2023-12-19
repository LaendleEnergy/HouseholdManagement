package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.EnergySavingRepository;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.view.DTO.IncentiveDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EnergySavingServiceImpl implements EnergySavingService {

    @Inject
    HouseholdRepository householdRepository;
    @Inject
    EnergySavingRepository energySavingRepository;

    @Override
    public void addSavingTarget(int value, int timeframe) {

    }

    @Override
    public String getCurrentSavingTarget(String householdId) {
        return null;
    }

    @Override
    public void updateIncentive(String householdId, IncentiveDTO incentiveDTO) throws HouseholdNotFoundException {
        energySavingRepository.updateIncentive(householdId, IncentiveDTO.create(incentiveDTO));
    }

    @Override
    public void receiveReport(boolean receive) {

    }

    @Override
    public String getCurrentIncentive(String householdId) throws HouseholdNotFoundException {
        return energySavingRepository.getCurrentIncentive(householdId).getDescription();
    }
}
