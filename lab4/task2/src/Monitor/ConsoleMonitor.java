package Monitor;

import util.Garden;

public class ConsoleMonitor extends Monitor {
    public ConsoleMonitor(Garden garden) {
        super(garden, "Console monitor");
    }

    @Override
    protected void monitor() {
        garden.outputField();
    }
}