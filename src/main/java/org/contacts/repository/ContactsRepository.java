package org.contacts.repository;

import org.contacts.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class ContactsRepository {
    private static List<Person> contacts = new ArrayList<>();

    public void savePerson(Person person) {
        contacts.add(person);
    }

    public int size() {
        return contacts.size();
    }

    public void saveInSelectedIndex(int index, Person person) {
        contacts.remove(person);
        contacts.set(index, person);
    }

    public void removePerson(int index) {
        Person person = contacts.get(index);
        contacts.remove(person);
    }

    public Person getPersonByIndex(int index) {
        return contacts.get(index);
    }

    public List<Person> getContacts() {
        return contacts;
    }
}
