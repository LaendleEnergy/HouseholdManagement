package at.fhv.master.laendleenergy.application.streams;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.HouseholdMember;
import at.fhv.master.laendleenergy.domain.events.HouseholdCreatedEvent;
import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import at.fhv.master.laendleenergy.domain.events.MemberRemovedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EventHandler {
    @Inject
    HouseholdRepository householdRepository;

    @Transactional
    public void handleHouseholdCreatedEvent(HouseholdCreatedEvent event) {
        Household household = Household.create(event.getHouseholdId());
        HouseholdMember member = HouseholdMember.create(event.getMemberId(), event.getName(), household);
        household.addMemberToHousehold(member);

        householdRepository.addHousehold(household);
    }

    @Transactional
    public void handleMemberAddedEvent(MemberAddedEvent event) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(event.getHouseholdId());
        HouseholdMember member = HouseholdMember.create(event.getMemberId(), event.getName(), household);
        household.addMemberToHousehold(member);

        householdRepository.updateHousehold(household);
    }

    @Transactional
    public void handleTaggingCreatedEvent(String householdId, String memberId) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);

        for (HouseholdMember member : household.getHouseholdMembers()) {
            if (member.getId().equals(memberId)) {
                member.increaseNumberOfCreatedTags();
                return;
            }
        }
    }

    @Transactional
    public void handleMemberRemovedEvent(MemberRemovedEvent event) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(event.getHouseholdId());
        household.removeMember(event.getMemberId());

        householdRepository.updateHousehold(household);
    }
}
