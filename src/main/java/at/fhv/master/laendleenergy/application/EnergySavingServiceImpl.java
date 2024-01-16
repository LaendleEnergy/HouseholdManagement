package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.EnergySavingRepository;
import at.fhv.master.laendleenergy.view.DTO.SavingTargetDTO;
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
    public void updateSavingTarget(String householdId, SavingTargetDTO savingTargetDTO) throws HouseholdNotFoundException {
        energySavingRepository.updateSavingTarget(householdId, savingTargetDTO.toEnergySavingTarget());
    }

    @Override
    @Transactional
    public SavingTargetDTO getCurrentSavingTarget(String householdId) throws HouseholdNotFoundException {
        return SavingTargetDTO.create(energySavingRepository.getCurrentSavingTarget(householdId));
    }

    @Override
    @Transactional
    public void updateIncentive(String householdId, IncentiveDTO incentiveDTO) throws HouseholdNotFoundException {
        energySavingRepository.updateIncentive(householdId, incentiveDTO.toIncentive());
    }

    @Override
    @Transactional
    public IncentiveDTO getCurrentIncentive(String householdId) throws HouseholdNotFoundException {
        return IncentiveDTO.create(energySavingRepository.getCurrentIncentive(householdId));
    }
}
