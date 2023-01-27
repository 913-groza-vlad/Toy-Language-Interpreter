package View.GUI;

import Controller.Service;
import Model.Expressions.*;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;

import java.util.ArrayList;
import java.util.List;

public class ProgramExamples {
    public static List<IStmt> programs = new ArrayList<>();

    public static List<IStmt> createExamples() {
        // int v; v=2; Print(v)
        IStmt ogProgram1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        programs.add(ogProgram1);

        // int a; int b; a=2+3*5; b=a+1; Print(b)")
        IStmt ogProgram2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                new CompStmt(new AssignStmt("a", new ArithmeticExp(new ValueExp(new IntValue(2)),
                        new ArithmeticExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), "*"), "+")),
                        new CompStmt(new AssignStmt("b",new ArithmeticExp(new VarExp("a"),
                                new ValueExp(new IntValue(1)), "+")), new PrintStmt(new VarExp("b"))))));
        programs.add(ogProgram2);

        // bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        IStmt ogProgram3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                        new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(
                                new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(
                                new VarExp("v"))))));
        programs.add(ogProgram3);

        // string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc);print(varc);
        // readFile(varf,varc);print(varc); closeRFile(varf)
        IStmt ogProgram4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                new CompStmt(new OpenRFile(new VarExp("varf")),
                        new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                        new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));
        programs.add(ogProgram4);

        // int a; int b; string file; file="ex5.in"; openRFile(file); readFile(file, a)
        // readFile(file, b); (If a < b Then Print(a) Else Print(b); closeRFile(file)
        IStmt ogProgram5 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                new CompStmt(new VarDeclStmt("file", new StringType()), new CompStmt(new AssignStmt("file", new ValueExp(new StringValue("ex5.in"))),
                        new CompStmt(new OpenRFile(new VarExp("file")), new CompStmt(new ReadFile(new VarExp("file"), "a"),
                                new CompStmt(new ReadFile(new VarExp("file"), "b"), new CompStmt(new IfStmt(new RelationalExp(new VarExp("a"), new VarExp("b"), "<"),
                                        new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))), new CloseRFile(new VarExp("file"))))))))));
        programs.add(ogProgram5);

        // int v; v=4; (while (v>0) print(v); v=v-1); print(v)
        IStmt ogProgram6 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue()), ">"), new CompStmt(new PrintStmt(new VarExp("v")),
                        new AssignStmt("v", new ArithmeticExp(new VarExp("v"), new ValueExp(new IntValue(1)), "-")))), new PrintStmt(new VarExp("v")))));
        programs.add(ogProgram6);

        // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a))+5)
        IStmt ogProgram7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v", new ValueExp(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new New("a", new VarExp("v")),
                        new CompStmt(new PrintStmt(new ReadH(new VarExp("v"))), new PrintStmt(new ArithmeticExp(new ReadH(new ReadH(new VarExp("a"))),
                                new ValueExp(new IntValue(5)), "+")))))));
        programs.add(ogProgram7);

        // Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
        IStmt ogProgram8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v", new ValueExp(new IntValue(20))),
                new CompStmt(new PrintStmt(new ReadH(new VarExp("v"))), new CompStmt(new WriteH("v", new ValueExp(new IntValue(30))),
                        new PrintStmt(new ArithmeticExp(new ReadH(new VarExp("v")), new ValueExp(new IntValue(5)), "+"))))));
        programs.add(ogProgram8);

        // Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))
        IStmt wrongEx = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v", new ValueExp(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new New("a", new VarExp("v")),
                        new CompStmt(new New("v", new ValueExp(new IntValue(30))), new PrintStmt(new ReadH(new ReadH(new VarExp("a")))))))));
        programs.add(wrongEx);

        // int v; Ref int a; v=10; new(a,22);
        // fork(wH(a,30); v=32; print(v); print(rH(a)));
        // print(v); print(rH(a))
        IStmt ogProgram9 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new New("a", new ValueExp(new IntValue(22))),
                        new CompStmt(new ForkStmt(new CompStmt(new WriteH("a", new ValueExp(new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadH(new VarExp("a"))))))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadH(new VarExp("a")))))))));
        programs.add(ogProgram9);

        // int x; x = 13; Ref(int) y; new(y, 25); Fork(x = 50; print(x));
        // Fork(WriteHeap(y -> 43); print(ReadHeap(y)));
        // print(x); print(ReadHeap(y))
        IStmt ogProgram10 = new CompStmt(new VarDeclStmt("x", new IntType()), new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(13))),
                new CompStmt(new VarDeclStmt("y", new RefType(new IntType())), new CompStmt(new New("y", new ValueExp(new IntValue(25))),
                        new CompStmt(new ForkStmt(new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(50))), new PrintStmt(new VarExp("x")))),
                                new CompStmt(new ForkStmt(new CompStmt(new WriteH("y", new ValueExp(new IntValue(43))), new PrintStmt(new ReadH(new VarExp("y"))))),
                                        new CompStmt(new PrintStmt(new VarExp("x")), new PrintStmt(new ReadH(new VarExp("y"))))))))));
        programs.add(ogProgram10);

        // Ref int a; new(a,20);
        // (for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a)));
        // print(rh(a))
        IStmt ogProgram11 = new CompStmt(new VarDeclStmt("a", new RefType(new IntType())), new CompStmt (new VarDeclStmt("v", new IntType()), new CompStmt(new New("a", new ValueExp(new IntValue(20))),
                                new CompStmt(new ForStmt("v", new ValueExp(new IntValue()), new ValueExp(new IntValue(3)),
                                        new ArithmeticExp(new VarExp("v"), new ValueExp(new IntValue(1)), "+"),
                                            new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                                new AssignStmt("v", new ArithmeticExp(new VarExp("v"), new ReadH(new VarExp("a")), "*"))))),
                                                    new PrintStmt((new ReadH(new VarExp("a"))))))));
        programs.add(ogProgram11);

        // Ref int v1; Ref int v2; int x;  int q;
        // new(v1,20);new(v2,30);newLock(x);
        // fork(   fork(     lock(x);wh(v1,rh(v1)-1);unlock(x)   );
        // lock(x);wh(v1,rh(v1)*10);unlock(x));newLock(q);
        // fork(   fork(lock(q);wh(v2,rh(v2)+5);unlock(q));  lock(q);wh(v2,rh(v2)*10);unlock(q));
        // nop;nop;nop;nop;lock(x);
        // print(rh(v1)); unlock(x);
        // lock(q); print(rh(v2)); unlock(q);
        IStmt ogProgram12 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())), new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("x", new IntType()), new CompStmt(new VarDeclStmt("q", new IntType()), new CompStmt(new New("v1", new ValueExp(new IntValue(20))),
                        new CompStmt(new New("v2", new ValueExp(new IntValue(30))), new CompStmt(new NewLockStmt("x"), new CompStmt(new ForkStmt(new CompStmt(new ForkStmt(
                                new CompStmt(new LockStmt("x"), new CompStmt(new WriteH("v1", new ArithmeticExp(new ReadH(new VarExp("v1")), new ValueExp(new IntValue(1)), "-")),
                                        new UnlockStmt("x")))), new CompStmt(new LockStmt("x"),
                                new CompStmt(new WriteH("v1", new ArithmeticExp(new ReadH(new VarExp("v1")), new ValueExp(new IntValue(10)), "*")), new UnlockStmt("x"))))),
                                new CompStmt(new NewLockStmt("q"), new CompStmt(new ForkStmt(new CompStmt(new ForkStmt(
                                        new CompStmt(new LockStmt("q"), new CompStmt(new WriteH("v2", new ArithmeticExp(new ReadH(new VarExp("v2")), new ValueExp(new IntValue(5)), "+")),
                                                new UnlockStmt("q")))), new CompStmt(new LockStmt("q"),
                                        new CompStmt(new WriteH("v2", new ArithmeticExp(new ReadH(new VarExp("v2")), new ValueExp(new IntValue(10)), "*")), new UnlockStmt("q"))))),
                                        new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(),
                                                new CompStmt(new LockStmt("x"), new CompStmt(new PrintStmt(new ReadH(new VarExp("v1"))),
                                                        new CompStmt(new UnlockStmt("x"), new CompStmt(new LockStmt("q"),
                                                                new CompStmt(new PrintStmt(new ReadH(new VarExp("v2"))), new UnlockStmt("q"))))))))))))))))))));
        programs.add(ogProgram12);

        return programs;
    }

}
