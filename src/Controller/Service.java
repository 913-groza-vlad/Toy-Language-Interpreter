package Controller;

import Model.ADTstructures.MyIStack;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Statements.IStmt;
import Repository.IRepository;

import java.io.IOException;

public class Service {
    private final IRepository repo;

    public Service(IRepository repo) {
        this.repo = repo;
    }

    public ProgramState oneStep(ProgramState state) throws MyException {
        MyIStack<IStmt> stack = state.getStk();
        if (stack.isEmpty())
            throw new MyException("ProgramState stack is empty");
        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }

    public void allStep() {
        ProgramState prg = repo.getCrtPrg();
        displayState(prg);
        try {
            repo.logPrgStateExec();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        while (!prg.getStk().isEmpty()) {
            try {
                prg = oneStep(prg);
                repo.logPrgStateExec();
            }
            catch (MyException | IOException e) {
                System.out.println(e.getMessage());
            }
            displayState(prg);
        }
    }

    public void displayState(ProgramState prg) {
        System.out.println(prg.toString());
    }

    public void addProgramState(ProgramState program) {
        this.repo.addPrg(program);
    }

}
