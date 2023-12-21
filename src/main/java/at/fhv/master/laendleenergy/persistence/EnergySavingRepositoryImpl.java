package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Incentive;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.LinkedList;

@ApplicationScoped
public class EnergySavingRepositoryImpl implements EnergySavingRepository {
    @Inject
    EntityManager entityManager;
    @Inject
    HouseholdRepository householdRepository;

    @Override
    public void updateIncentive(String householdId, Incentive incentive) {
        Household household = entityManager.find(Household.class, householdId);

        if (household == null) {
            Household newHousehold = new Household(householdId, incentive, new EnergySavingTarget(), new LinkedList<>());
            householdRepository.addHousehold(newHousehold);
        } else {
            household.setIncentive(incentive);
            entityManager.merge(household);
        }
    }

    @Override
    public Incentive getCurrentIncentive(String householdId) {
        Household household = entityManager.find(Household.class, householdId);

        if (household == null) {
            Household newHousehold = new Household(householdId, new Incentive("Noch keine Belohnung festgelegt.", null), new EnergySavingTarget(), new LinkedList<>());
            householdRepository.addHousehold(newHousehold);
            return newHousehold.getIncentive();
        }

        return household.getIncentive();
    }

    @Override
    public EnergySavingTarget getCurrentSavingTarget(String householdId) {
        return null;
    }


    @Override
    public void addSavingTarget(int value, int timeframe) {

    }

}
