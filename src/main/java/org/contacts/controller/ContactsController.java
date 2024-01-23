package org.contacts.controller;

import org.contacts.repository.ContactsRepository;
import org.contacts.service.ContactsService;

import java.util.Scanner;

public class ContactsController {
    private static final String ENTER_ACTION = "Enter action (add, remove, edit, count, list, exit): > ";
    private static final String ADD = "add";
    private static final String REMOVE = "remove";
    private static final String EDIT = "edit";
    private static final String COUNT = "count";
    private static final String LIST = "list";
    private static final String EXIT = "exit";
    private static final String BYE = "Bye";

    Scanner scanner;

    ContactsService contactsService;


    public ContactsController() {
        scanner = new Scanner(System.in);
        contactsService = new ContactsService(new ContactsRepository());
    }

    public void start() {
        makeChoose(scanner);
    }

    public void showMenu() {
        System.out.print(ENTER_ACTION);
    }

    private void makeChoose(Scanner scanner) {
        boolean isExit = true;
        while (isExit) {
            showMenu();
            String userInput = scanner.next().toLowerCase();
            switch (userInput) {
                case ADD:
                    contactsService.createPersonBasedOnInput(scanner);
                    break;
                case REMOVE:
                    contactsService.showContacts();
                    contactsService.removeSelectedPerson(scanner);
                    break;
                case EDIT:
                    contactsService.editContact(scanner);
                    break;
                case COUNT:
                    contactsService.showCountContacts();
                    break;
                case LIST:
                    contactsService.showContacts();
                    break;
                case EXIT:
                    System.out.println(BYE);
                    isExit = false;
                    break;
            }
        }
    }
}
