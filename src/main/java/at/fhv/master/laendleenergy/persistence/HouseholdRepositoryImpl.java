package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.LinkedList;

@ApplicationScoped
public class HouseholdRepositoryImpl implements HouseholdRepository {

    @Inject
    EntityManager entityManager;

    @Override
    public Household createHouseholdIfNotExists(String householdId) {
        Household household = entityManager.find(Household.class, householdId);

        if (household == null) {
            Household newHousehold = new Household(householdId, new Incentive("Noch keine Belohnung festgelegt.", null), new EnergySavingTarget(), new LinkedList<>());
            addHousehold(newHousehold);
            return newHousehold;
        }
        return household;
    }

    @Override
    public String addHousehold(Household household) {
        entityManager.persist(household);
        return household.getId();
    }

    @Override
    public void deleteHousehold(String householdId) throws HouseholdNotFoundException  {
        Household toRemove = entityManager.find(Household.class, householdId);
        if (toRemove == null) throw new HouseholdNotFoundException();

        entityManager.remove(toRemove);
    }

    @Override
    public void updateHousehold(Household household) throws HouseholdNotFoundException {
        Household toUpdate = entityManager.find(Household.class, household.getId());
        if (toUpdate == null) throw new HouseholdNotFoundException();

        entityManager.merge(household);
    }

    @Override
    public Household getHouseholdById(String householdId) throws HouseholdNotFoundException {
        Household household = entityManager.find(Household.class, householdId);
        if(household == null) throw new HouseholdNotFoundException();

        return  household;
    }
}
