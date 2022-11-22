package View;

import Controller.Service;
import Model.ADTstructures.MyIStack;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Statements.IStmt;

import java.io.IOException;
import java.util.Scanner;

public class RunExample extends Command {

    private Service serv;

    public RunExample(String key, String description, Service serv) {
        super(key, description);
        this.serv = serv;
    }

    @Override
    public void execute() {
        System.out.println("\n1 -> execute program step by step");
        System.out.println("2 -> execute all steps of the program\n");
        System.out.print("\t>>> ");
        Scanner reader = new Scanner(System.in);
        String choice = reader.nextLine();
        if (choice.equals("1")) {
            ProgramState prg = serv.getPrograms().getCrtPrg();
            MyIStack<IStmt> stack = prg.getStk();
            boolean stop = false;
            while (!stack.isEmpty() && !stop) {
                System.out.println("\n\tnext -> Continue execution");
                System.out.println("\t0 -> End execution\n");
                System.out.print("\t>>> ");
                String option = reader.nextLine();
                if (option.equals("next")) {
                    try {
                        serv.oneStep(prg);
                    } catch (MyException e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        serv.getPrograms().logPrgStateExec();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    serv.displayState(prg);
                } else if (option.equals("0"))
                    stop = true;
                else
                    System.out.println("Invalid option");
            }
        }
        else if (choice.equals("2")) {
                try {
                    serv.allStep();
                } catch (MyException e) {
                    System.out.println(e.getMessage());
                }
            }
        else
            System.out.println("Invalid option");
    }
}
