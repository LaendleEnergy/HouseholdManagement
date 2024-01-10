package at.fhv.master.laendleenergy.domain.events;

import io.lettuce.core.StreamMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class TaggingCreatedEvent {
    private String eventId;
    private LocalDateTime taggingTime;
    private String userId;
    private String deviceId;
    private String householdId;

    public TaggingCreatedEvent() {}

    public TaggingCreatedEvent(String eventId, LocalDateTime taggingTime, String userId, String deviceId, String householdId) {
        this.eventId = eventId;
        this.taggingTime = taggingTime;
        this.userId = userId;
        this.deviceId = deviceId;
        this.householdId = householdId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getTaggingTime() {
        return taggingTime;
    }

    public void setTaggingTime(LocalDateTime taggingTime) {
        this.taggingTime = taggingTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public static TaggingCreatedEvent fromStreamMessage(StreamMessage<String, String> message) {
        Map<String, String> body = message.getBody();

        String eventId = body.get("eventId");
        String userId = body.get("userId");
        String deviceId = body.get("deviceId");
        String householdId = body.get("householdId");

        String taggingTimeString = body.get("taggingTime");
        LocalDateTime taggingTime = LocalDateTime.parse(taggingTimeString, DateTimeFormatter.ISO_DATE_TIME);

        return new TaggingCreatedEvent(eventId, taggingTime, userId, deviceId, householdId);
    }
}