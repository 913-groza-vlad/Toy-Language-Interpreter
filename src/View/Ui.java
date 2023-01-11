package View;

import Controller.Service;
import Model.ADTstructures.*;
import Model.Exceptions.MyException;
import Model.Expressions.*;
import Model.ProgramState;
import Model.Statements.*;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.IRepository;
import Repository.Repository;
import View.GUI.ProgramExamples;

import java.io.BufferedReader;
import java.util.List;
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
                MyIHeap heapTable = new MyHeap();
                ProgramState prg = null;
                if (option.equals("1")) {
                    IStmt ogProgram1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
                    prg = new ProgramState(stack, symTable, out, fileTable, heapTable, ogProgram1);
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
                    prg = new ProgramState(stack, symTable, out, fileTable, heapTable, ogProgram2);
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
                    prg = new ProgramState(stack, symTable, out, fileTable, heapTable, ogProgram3);
                    displayOptions();
                    String choice = reader.nextLine();
                    if (choice.equals("1"))
                        runOneStep(prg);
                    else if (choice.equals("2"))
                        runAllStep(prg);
                    else
                        System.out.println("Invalid option");
                }
                else if (option.equals("4")) {
                    IStmt ogProgram4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                            new CompStmt(new OpenRFile(new VarExp("varf")),
                                    new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                            new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                    new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));
                    prg = new ProgramState(stack, symTable, out, fileTable, heapTable, ogProgram4);
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
            System.out.println("\t0 -> End execution\n");
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
            else if (choice.equals("0"))
                stop = true;
            else
                System.out.println("Invalid option");
        }
    }

    private Service createRunExample(IStmt program, String logFile) {
        MyIStack<IStmt> stack = new MyStack<>();
        MyIDictionary<String, Value> symTable = new MyDictionary<>();
        MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
        MyIList<Value> out = new MyList<>();
        MyIHeap heap = new MyHeap();
        MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
        try {
            program.typeCheck(typeEnv);
        }
        catch (MyException me) {
            System.out.println(me.getMessage());
        }
        ProgramState prg = new ProgramState(stack, symTable, out, fileTable, heap, program);
        IRepository repo = new Repository(logFile);
        repo.addPrg(prg);
        return new Service(repo);
    }

    public void mainStart() {

        List<IStmt> programs = ProgramExamples.createExamples();
        Service ctr1 = createRunExample(programs.get(0), "log1.txt");
        Service ctr2 = createRunExample(programs.get(1), "log2.txt");
        Service ctr3 = createRunExample(programs.get(2), "log3.txt");
        Service ctr4 = createRunExample(programs.get(3), "log4.txt");
        Service ctr5 = createRunExample(programs.get(4), "log5.txt");
        Service ctr6 = createRunExample(programs.get(5), "log6.txt");
        Service ctr7 = createRunExample(programs.get(6), "log7.txt");
        Service ctr8 = createRunExample(programs.get(7), "log8.txt");
        Service ctr9 = createRunExample(programs.get(8), "log9.txt");
        Service ctr10 = createRunExample(programs.get(9), "log10.txt");
        Service ctr11 = createRunExample(programs.get(10), "log11.txt");

        TextMenu textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand("0", "exit"));
        textMenu.addCommand(new RunExample("1", programs.get(0).toString(), ctr1));
        textMenu.addCommand(new RunExample("2", programs.get(1).toString(), ctr2));
        textMenu.addCommand(new RunExample("3", programs.get(2).toString(), ctr3));
        textMenu.addCommand(new RunExample("4", programs.get(3).toString(), ctr4));
        textMenu.addCommand(new RunExample("5", programs.get(4).toString(), ctr5));
        textMenu.addCommand(new RunExample("6", programs.get(5).toString(), ctr6));
        textMenu.addCommand(new RunExample("7", programs.get(6).toString(), ctr7));
        textMenu.addCommand(new RunExample("8", programs.get(7).toString(), ctr8));
        textMenu.addCommand(new RunExample("9", programs.get(8).toString(), ctr9));
        textMenu.addCommand(new RunExample("10", programs.get(9).toString(), ctr10));
        textMenu.addCommand(new RunExample("11", programs.get(10).toString(), ctr11));
        textMenu.show();
    }
}
