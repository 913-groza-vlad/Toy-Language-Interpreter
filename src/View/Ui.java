package View;

import Controller.Service;
import Model.ADTstructures.*;
import Model.Exceptions.MyException;
import Model.Expressions.ArithmeticExp;
import Model.Expressions.RelationalExp;
import Model.Expressions.ValueExp;
import Model.Expressions.VarExp;
import Model.ProgramState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
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
        try {
            serv.allStep();
        }
        catch (MyException e) {
            System.out.println(e.getMessage());
        }
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
        ProgramState prg1 = new ProgramState(stack, symTable, out, fileTable, heap, program);
        IRepository repo1 = new Repository(logFile);
        repo1.addPrg(prg1);
        return new Service(repo1);
    }

    public void mainStart() {

        // int v; v=2; Print(v)
        IStmt ogProgram1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        Service ctr1 = createRunExample(ogProgram1, "log1.txt");

        // int a; int b; a=2+3*5; b=a+1; Print(b)")
        IStmt ogProgram2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                new CompStmt(new AssignStmt("a", new ArithmeticExp(new ValueExp(new IntValue(2)),
                        new ArithmeticExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), "*"), "+")),
                        new CompStmt(new AssignStmt("b",new ArithmeticExp(new VarExp("a"),
                                new ValueExp(new IntValue(1)), "+")), new PrintStmt(new VarExp("b"))))));
        Service ctr2 = createRunExample(ogProgram2, "log2.txt");

        // bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        IStmt ogProgram3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                        new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(
                                new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(
                                new VarExp("v"))))));
        Service ctr3 = createRunExample(ogProgram3, "log3.txt");

        // string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc);print(varc);
        // readFile(varf,varc);print(varc); closeRFile(varf)
        IStmt ogProgram4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                new CompStmt(new OpenRFile(new VarExp("varf")),
                        new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                        new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));
        Service ctr4 = createRunExample(ogProgram4, "log4.txt");

        // int a; int b; string file; file="ex5.in"; openRFile(file); readFile(file, a)
        // readFile(file, b); (If a < b Then Print(a) Else Print(b); closeRFile(file)
        IStmt ogProgram5 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                new CompStmt(new VarDeclStmt("file", new StringType()), new CompStmt(new AssignStmt("file", new ValueExp(new StringValue("ex5.in"))),
                        new CompStmt(new OpenRFile(new VarExp("file")), new CompStmt(new ReadFile(new VarExp("file"), "a"),
                                new CompStmt(new ReadFile(new VarExp("file"), "b"), new CompStmt(new IfStmt(new RelationalExp(new VarExp("a"), new VarExp("b"), "<"),
                                        new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))), new CloseRFile(new VarExp("file"))))))))));
        Service ctr5 = createRunExample(ogProgram5, "log5.txt");

        // int v; v=4; (while (v>0) print(v); v=v-1); print(v)
        IStmt ogProgram6 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue()), ">"), new CompStmt(new PrintStmt(new VarExp("v")),
                        new AssignStmt("v", new ArithmeticExp(new VarExp("v"), new ValueExp(new IntValue(1)), "-")))), new PrintStmt(new VarExp("v")))));
        Service ctr6 = createRunExample(ogProgram6, "log6.txt");

        TextMenu textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand("0", "exit"));
        textMenu.addCommand(new RunExample("1", ogProgram1.toString(), ctr1));
        textMenu.addCommand(new RunExample("2", ogProgram2.toString(), ctr2));
        textMenu.addCommand(new RunExample("3", ogProgram3.toString(), ctr3));
        textMenu.addCommand(new RunExample("4", ogProgram4.toString(), ctr4));
        textMenu.addCommand(new RunExample("5", ogProgram5.toString(), ctr5));
        textMenu.addCommand(new RunExample("6", ogProgram6.toString(), ctr6));
        textMenu.show();
    }
}
