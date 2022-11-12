package Controller;

import Model.ADTstructures.MyIStack;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Statements.IStmt;
import Repository.IRepository;

import java.io.IOException;
import java.util.List;

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

    public void allStep() throws MyException{
        ProgramState prg = repo.getCrtPrg();
        displayState(prg);
        try {
            repo.logPrgStateExec();
        }
        catch (IOException e) {
            throw new MyException("Error on saving the content in the text file");
        }
        while (!prg.getStk().isEmpty()) {
            try {
                prg = oneStep(prg);
                repo.logPrgStateExec();
            }
            catch (MyException | IOException e) {
                throw new MyException("Error on saving the content in the text file");
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

    public IRepository getPrograms() {
        return repo;
    }

}
