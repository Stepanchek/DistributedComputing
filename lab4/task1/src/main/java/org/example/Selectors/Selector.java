package org.example.Selectors;

import org.example.Util.Constants;
import org.example.Database;

public abstract class Selector implements Runnable {
    protected final Database database;
    protected final String queryParameter;

    protected Selector(Database database, String queryParameter) {
        this.database = database;
        this.queryParameter = queryParameter;
    }

    protected abstract String select();

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(Constants.INTERVAL);

                String queryResult = select();

                if(queryResult != null) {
                    System.out.printf("%s completed his query successfully(parameter = %s), result is %s%n",
                            getClass().getSimpleName(),
                            queryParameter,
                            queryResult);

                } else {
                    System.out.printf("%s didn't find(parameter = %s) any record%n",
                            getClass().getSimpleName(),
                            queryParameter);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
