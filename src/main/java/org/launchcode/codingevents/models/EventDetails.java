package org.launchcode.codingevents.models;

import javax.persistence.Entity;
import javax.validation.constraints.*;

@Entity
public class EventDetails extends AbstractEntity {
    @Size(max = 500, message = "Description too long!")
    @NotBlank(message = "Description is required")
    @NotNull(message = "Description is required")
    private String description;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email. Try again.")
    private String contactEmail;

    @NotBlank(message = "Location is required")
    @NotNull(message = "Location is required")
    private String location;

    @AssertTrue(message = "This event must have attendees register")
    private boolean shouldRegister = true;

    @NotNull(message = "At least 1 attendee is required")
    @Min(value = 1, message = "At least 1 attendee is required")
    private Integer numOfAttendees;

    public EventDetails(String description, String contactEmail, String location, boolean shouldRegister, Integer numOfAttendees) {
        this.description = description;
        this.contactEmail = contactEmail;
        this.location = location;
        this.shouldRegister = shouldRegister;
        this.numOfAttendees = numOfAttendees;
    }

    public EventDetails() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isShouldRegister() {
        return shouldRegister;
    }

    public void setShouldRegister(boolean shouldRegister) {
        this.shouldRegister = shouldRegister;
    }

    public Integer getNumOfAttendees() {
        return numOfAttendees;
    }

    public void setNumOfAttendees(Integer numOfAttendees) {
        this.numOfAttendees = numOfAttendees;
    }
}
