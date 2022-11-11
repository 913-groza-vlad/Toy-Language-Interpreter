package View;

import Controller.Service;
import Model.Exceptions.MyException;

public class RunExample extends Command {

    private Service serv;

    public RunExample(String key, String description, Service serv) {
        super(key, description);
        this.serv = serv;
    }

    @Override
    public void execute() {
        try {
            serv.allStep();
        }
        catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}
