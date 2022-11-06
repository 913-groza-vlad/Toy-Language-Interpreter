package View;

import Controller.Service;

public class RunExample extends Command {

    private Service serv;

    public RunExample(String key, String description, Service serv) {
        super(key, description);
        this.serv = serv;
    }

    @Override
    public void execute() {
        serv.allStep();
    }
}
