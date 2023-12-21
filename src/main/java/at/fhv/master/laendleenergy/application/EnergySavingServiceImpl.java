package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Incentive;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.EnergySavingRepository;
import at.fhv.master.laendleenergy.view.DTO.EnergySavingTargetDTO;
import at.fhv.master.laendleenergy.view.DTO.IncentiveDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EnergySavingServiceImpl implements EnergySavingService {

    @Inject
    EnergySavingRepository energySavingRepository;

    @Override
    @Transactional
    public void addSavingTarget(int value, int timeframe) {

    }

    @Override
    @Transactional
    public EnergySavingTargetDTO getCurrentSavingTarget(String householdId) {
        return null;
    }

    @Override
    @Transactional
    public void updateIncentive(String householdId, IncentiveDTO incentiveDTO) {
        energySavingRepository.updateIncentive(householdId, IncentiveDTO.create(incentiveDTO));
    }

    @Override
    @Transactional
    public IncentiveDTO getCurrentIncentive(String householdId) {
        return IncentiveDTO.create(energySavingRepository.getCurrentIncentive(householdId));
    }
}
