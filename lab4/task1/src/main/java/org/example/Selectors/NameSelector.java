package org.example.Selectors;

import org.example.Database;

public class NameSelector extends Selector {
    public NameSelector(Database database, String queryParameter) {
        super(database, queryParameter);
    }

    @Override
    protected String select() {
        try {
            return database.getNameByPhoneNumber(queryParameter);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
