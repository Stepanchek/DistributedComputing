package org.example.Selectors;

import org.example.Database;

public class PhoneSelector extends Selector {
    public PhoneSelector(Database database, String queryParameter) {
        super(database, queryParameter);
    }

    @Override
    protected String select() {
        try {
            return database.getPhoneNumberByName(queryParameter);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}