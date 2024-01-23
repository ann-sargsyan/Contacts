package org.contacts.service;

import lombok.RequiredArgsConstructor;
import org.contacts.entity.Person;
import org.contacts.repository.ContactsRepository;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class ContactsService {

    private final ContactsRepository contactsRepository;
    private static final String REGEX_FOR_PHONE_NUMBER = "^\\+?[A-Za-z0-9]{1,2}(?:[ -]?\\(?[A-Za-z0-9]{2,}\\)?)?(?:[ -]?[A-Za-z0-9]{2,})?$";
    private static final String NO_CONTACT_TO_EDIT_MESSAGE = "No records to edit!";
    private static final String SELECT_FIELD_MESSAGE = "Select a field (name, surname, number): ";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String NUMBER = "number";
    private static final String RECORD_UPDATED_MESSAGE = "The record updated!";
    private static final String EMPTY_CONTACT_BOOK_MESSAGE = "The Phone Book has 0 records.";
    private static final String NO_RECORDS_TO_REMOVE_MESSAGE = "No records to remove!";
    private static final String ENTER_NUMBER = "Enter the number: ";
    private static final String ENTER_SURNAME = "Enter the surname of the person: ";
    private static final String ENTER_NAME = "Enter the name of the person: ";
    private static final String ENTER_INDEX = "Enter index: ";
    private static final String WRONG_NUMBER_FORMAT_MESSAGE = "Wrong number format!";
    private static final String ARROW_SYMBOL = "> ";


    public void addContact(Person person) {
        contactsRepository.savePerson(person);
    }

    public void showContacts() {
        List<Person> personList = contactsRepository.getContacts();
        IntStream.range(0, personList.size())
                .forEach(i -> {
                    Person person = personList.get(i);
                    System.out.println(i + 1 + ". " + person.toString());
                });
    }

    public void editContact(Scanner scanner) {
        if (contactsRepository.size() == 0) {
            System.out.println(NO_CONTACT_TO_EDIT_MESSAGE);
            return;
        }
        showContacts();
        int index = getIndexFromInput(scanner);
        String detail = getDetailFromInput(scanner);

        switch (detail) {
            case NAME -> {
                System.out.print(ENTER_NAME);
                String name = scanner.next();
                editName(name, index);

            }
            case SURNAME -> {
                System.out.print(ENTER_SURNAME);
                String surname = scanner.next();
                editSurname(surname, index);
            }
            case NUMBER -> {
                System.out.print(ENTER_NUMBER);
                String number = scanner.next();
                editNumber(number, index);
            }
        }
        System.out.println(RECORD_UPDATED_MESSAGE);
        showContacts();
    }

    public void showCountContacts() {
        System.out.println(contactsRepository.size() == 0 ? EMPTY_CONTACT_BOOK_MESSAGE
                : contactsRepository.size());
    }

    public void removeSelectedPerson(Scanner scanner) {
        if (contactsRepository.size() == 0) {
            System.out.println(NO_RECORDS_TO_REMOVE_MESSAGE);
            return;
        }
        int index = scanner.nextInt();
        index--;
        contactsRepository.removePerson(index);
        showContacts();
    }

    public void createPersonBasedOnInput(Scanner scanner) {
        String name = getNameFromInput(scanner);
        String surname = getSurNameFromInput(scanner);
        String phoneNumber = getPhoneNumberFromInput(scanner);

        addContact(isValidPhoneNumber(phoneNumber) ? new Person(name, surname, phoneNumber)
                : new Person(name, surname, null));

        showContacts();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(REGEX_FOR_PHONE_NUMBER);
        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) {
            return true;
        }

        System.out.println(WRONG_NUMBER_FORMAT_MESSAGE);
        return false;
    }

    private String getDetailFromInput(Scanner scanner) {
        System.out.print(SELECT_FIELD_MESSAGE);
        return scanner.next();
    }

    private int getIndexFromInput(Scanner scanner) {
        System.out.print(ENTER_INDEX);
        return scanner.nextInt();
    }

    private void editName(String name, int index) {
        index--;
        Person person = contactsRepository.getPersonByIndex(index);
        person = person.toBuilder().name(name).build();
        contactsRepository.saveInSelectedIndex(index, person);
    }

    private void editSurname(String surname, int index) {
        index--;
        Person person = contactsRepository.getPersonByIndex(index);
        person.toBuilder().surName(surname);
    }

    private void editNumber(String number, int index) {
        index--;
        Person person = contactsRepository.getPersonByIndex(index);
        person.toBuilder().phoneNumber(number);
    }

    private String getPhoneNumberFromInput(Scanner scanner) {
        System.out.println(ENTER_NUMBER);
        System.out.print(ARROW_SYMBOL);
        return scanner.next();
    }

    private String getSurNameFromInput(Scanner scanner) {
        System.out.println(ENTER_SURNAME);
        System.out.print(ARROW_SYMBOL);
        return scanner.next();
    }

    private String getNameFromInput(Scanner scanner) {
        System.out.println(ENTER_NAME);
        System.out.print(ARROW_SYMBOL);
        return scanner.next();
    }
}
