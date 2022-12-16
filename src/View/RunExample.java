package View;

import Controller.Service;
import Model.ADTstructures.MyIStack;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Statements.IStmt;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;

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
        /* if (choice.equals("1")) {
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
                        prg.getHeap().setContent(serv.garbageCollector(serv.getAddrFromSymTable(prg.getSymTable().getContent(), prg.getHeap().getContent().values()), prg.getHeap().getContent()));
                        serv.getPrograms().logPrgStateExec(prg);
                    } catch (MyException e) {
                        System.out.println(e.getMessage());
                    }
                     catch (IOException fe) {
                        System.out.println("Error on saving the content in the text file");
                    }
                    serv.displayState(prg);
                } else if (option.equals("0"))
                    stop = true;
                else
                    System.out.println("Invalid option");
            }
        } */
        if (choice.equals("1")) {
            serv.executor = Executors.newFixedThreadPool(2);
            List<ProgramState> prgList = serv.removeCompletedPrg(serv.getPrograms().getProgramList());
            boolean stop = false;
            while (prgList.size() > 0 && !stop) {
                System.out.println("\n\tnext -> Continue execution");
                System.out.println("\t0 -> End execution\n");
                System.out.print("\t>>> ");
                String option = reader.nextLine();
                if (option.equals("next")) {
                    serv.conservativeGarbageCollector(prgList);
                    serv.oneStepForAllPrograms(prgList);
                    prgList = serv.removeCompletedPrg(serv.getPrograms().getProgramList());
                }
                else if (option.equals("0"))
                    stop = true;
                else
                    System.out.println("Invalid option");

            }
            serv.executor.shutdownNow();
            serv.getPrograms().setProgramList(prgList);
        }
        else if (choice.equals("2")) {
            serv.allStep();
        }
        else
            System.out.println("Invalid option");
    }
}
