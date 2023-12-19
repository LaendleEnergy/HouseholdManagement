package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Incentive;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EnergySavingRepositoryImpl implements EnergySavingRepository {
    @Inject
    EntityManager entityManager;

    @Override
    public void addSavingTarget(int value, int timeframe) {

    }

    @Override
    @Transactional
    public void updateIncentive(String householdId, Incentive incentive) throws HouseholdNotFoundException {
        Household household = entityManager.find(Household.class, householdId);
        if (household == null) throw new HouseholdNotFoundException();

        household.setIncentive(incentive);
        entityManager.merge(household);
    }

    @Override
    public void receiveReport(boolean receive) {

    }

    @Override
    public Incentive getCurrentIncentive(String householdId) throws HouseholdNotFoundException {
        Household household = entityManager.find(Household.class, householdId);
        if (household == null) throw new HouseholdNotFoundException();

        return household.getIncentive();
    }

    @Override
    public EnergySavingTarget getCurrentSavingTarget(String householdId) {
        return null;
    }
}
