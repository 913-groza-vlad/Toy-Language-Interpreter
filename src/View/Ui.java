package View;

import Controller.Service;
import Model.ADTstructures.*;
import Model.Exceptions.MyException;
import Model.Expressions.ArithmeticExp;
import Model.Expressions.ValueExp;
import Model.Expressions.VarExp;
import Model.ProgramState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;
import Repository.IRepository;
import Repository.Repository;

import java.io.BufferedReader;
import java.util.Scanner;

public class Ui {
    private Service serv;
    private final Scanner reader = new Scanner(System.in);

    //public Ui(Service service) {
     //   this.serv = service;
   // }

    private void displayMenu() {
        System.out.println("\n\t\tToy Language interpreter");
        System.out.println("\t1 -> input a program (choose one of the programs below)");
        System.out.println("\t0 -> exit the application\n");
    }

    private void displayPrograms() {
        System.out.println("\n1 -> int v; v=2; Print(v)");
        System.out.println("2 -> int a; int b; a=2+3*5; b=a+1; Print(b)");
        System.out.println("3 -> bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)\n");
    }


    private void displayOptions() {
        System.out.println("\n1 -> execute program step by step");
        System.out.println("2 -> execute all steps of the program\n");
        System.out.print("\t>>> ");
    }

    public void start() {
        while (true) {
            displayMenu();
            System.out.print(">>> ");
            String command = reader.nextLine();
            if (command.equals("1")) {
                displayPrograms();
                System.out.print("\t>>> ");
                String option = reader.nextLine();
                MyIStack<IStmt> stack = new MyStack<>();
                MyIDictionary<String, Value> symTable = new MyDictionary<>();
                MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
                MyIList<Value> out = new MyList<>();
                ProgramState prg = null;
                if (option.equals("1")) {
                    IStmt ogProgram1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
                    prg = new ProgramState(stack, symTable, out, fileTable, ogProgram1);
                    displayOptions();
                    String choice = reader.nextLine();
                    if (choice.equals("1"))
                        runOneStep(prg);
                    else if (choice.equals("2"))
                        runAllStep(prg);
                    else
                        System.out.println("Invalid option");
                }
                else if (option.equals("2")) {
                    IStmt ogProgram2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                            new CompStmt(new AssignStmt("a", new ArithmeticExp(new ValueExp(new IntValue(2)),
                                    new ArithmeticExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), "*"), "+")),
                                    new CompStmt(new AssignStmt("b",new ArithmeticExp(new VarExp("a"),
                                            new ValueExp(new IntValue(1)), "+")), new PrintStmt(new VarExp("b"))))));
                    prg = new ProgramState(stack, symTable, out, fileTable, ogProgram2);
                    displayOptions();
                    String choice = reader.nextLine();
                    if (choice.equals("1"))
                        runOneStep(prg);
                    else if (choice.equals("2"))
                        runAllStep(prg);
                    else
                        System.out.println("Invalid option");
                }
                else if (option.equals("3")) {
                    IStmt ogProgram3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
                            new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                    new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(
                                            new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(
                                            new VarExp("v"))))));
                    prg= new ProgramState(stack, symTable, out, fileTable, ogProgram3);
                    displayOptions();
                    String choice = reader.nextLine();
                    if (choice.equals("1"))
                        runOneStep(prg);
                    else if (choice.equals("2"))
                        runAllStep(prg);
                    else
                        System.out.println("Invalid option");
                }
                else
                    System.out.println("Invalid option");
            }
            else if (command.equals("0")) {
                return;
            }
            else
                System.out.println("Invalid command");
        }
    }

    private void runAllStep(ProgramState prg) {
        IRepository repo = new Repository("file.txt");
        serv = new Service(repo);
        serv.addProgramState(prg);
        serv.allStep();
    }

    private void runOneStep(ProgramState prg) {
        MyIStack<IStmt> stack = prg.getStk();
        IRepository repo = new Repository("file.txt");
        serv = new Service(repo);
        serv.addProgramState(prg);
        boolean stop = false;
        while (!stack.isEmpty() && !stop) {
            System.out.println("\n\t1 -> Continue execution");
            System.out.println("\t2 -> End execution\n");
            System.out.print("\t>>> ");
            String choice = reader.nextLine();
            if (choice.equals("1")) {
                try {
                    serv.oneStep(prg);
                } catch (MyException e) {
                    System.out.println(e.getMessage());
                }
                serv.displayState(prg);
            }
            else if (choice.equals("2"))
                stop = true;
            else
                System.out.println("Invalid option");
        }
    }

}
