package org.contacts;

import org.contacts.controller.ContactsController;

public class Main {
    public static void main(String[] args) {
        ContactsController contactsController = new ContactsController();
        contactsController.start();
    }
}