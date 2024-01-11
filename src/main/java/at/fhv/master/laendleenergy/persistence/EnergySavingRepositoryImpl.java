package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Incentive;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class EnergySavingRepositoryImpl implements EnergySavingRepository {
    @Inject
    EntityManager entityManager;
    @Inject
    HouseholdRepository householdRepository;

    @Override
    public void updateIncentive(String householdId, Incentive incentive) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);
        household.setIncentive(incentive);
        entityManager.merge(household);
    }

    @Override
    public Incentive getCurrentIncentive(String householdId) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);
        return household.getIncentive();
    }

    @Override
    public EnergySavingTarget getCurrentSavingTarget(String householdId) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);
        return household.getSavingTarget();
    }


    @Override
    public void updateSavingTarget(String householdId, EnergySavingTarget savingTarget) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);
        household.setSavingTarget(savingTarget);
        entityManager.merge(household);
    }

}
