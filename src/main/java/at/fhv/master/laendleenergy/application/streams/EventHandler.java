package at.fhv.master.laendleenergy.application.streams;

import at.fhv.master.laendleenergy.domain.DeviceCategory;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.HouseholdMember;
import at.fhv.master.laendleenergy.domain.events.*;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.DeviceRepository;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EventHandler {
    @Inject
    HouseholdRepository householdRepository;

    @Inject
    DeviceRepository deviceRepository;

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
    public void handleTaggingCreatedEvent(TaggingCreatedEvent event) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(event.getHouseholdId());

        for (HouseholdMember member : household.getHouseholdMembers()) {
            if (member.getId().equals(event.getMemberId())) {
                member.increaseNumberOfCreatedTags();
                return;
            }
        }
    }

    @Transactional
    public void handleMemberRemovedEvent(MemberRemovedEvent event) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(event.getHouseholdId());
        System.out.println(household.getHouseholdMembers().size());

        household.removeMember(event.getMemberId());

        System.out.println(household.getHouseholdMembers().size());

        householdRepository.updateHousehold(household);
    }

    @Transactional
    public void handleMemberUpdatedEvent(MemberUpdatedEvent event) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(event.getHouseholdId());
        household.updateMember(event.getMemberId(), event.getName());

        householdRepository.updateHousehold(household);
    }


    @Transactional
    public void handleDeviceCategoryAddedEvent(DeviceCategoryAddedEvent deviceCategoryAddedEvent) {
        DeviceCategory deviceCategory = new DeviceCategory(deviceCategoryAddedEvent.getName());
        deviceRepository.addDeviceCategory(deviceCategory);
    }
}
