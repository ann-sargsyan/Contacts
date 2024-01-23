package org.contacts.entity;

import lombok.Builder;

@Builder(toBuilder = true)
public record Person(
        String name,
        String surName,
        String phoneNumber) {

    @Override
    public String toString() {
        return name + " " + surName + " " + phoneNumber;
    }
}
