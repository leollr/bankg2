package com.g2.bank.model.Entities;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
@SequenceGenerator(name = "SQ_USER ", sequenceName = "SQ_USER ", allocationSize = 1)
public class UsersEntity {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "SSN")
    private String ssn;
    @Column(name = "REGISTRATION_ID")
    private String registrationId;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
