package at.fhv.master.laendleenergy.application;

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
    public void updateSavingTarget(String householdId, SavingTargetDTO savingTargetDTO) {
        energySavingRepository.updateSavingTarget(householdId, SavingTargetDTO.create(savingTargetDTO));
    }

    @Override
    @Transactional
    public SavingTargetDTO getCurrentSavingTarget(String householdId) {
        return SavingTargetDTO.create(energySavingRepository.getCurrentSavingTarget(householdId));
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
