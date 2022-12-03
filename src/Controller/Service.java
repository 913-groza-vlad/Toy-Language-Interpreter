package Controller;

import Model.ADTstructures.MyIStack;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Statements.IStmt;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                prg.getHeap().setContent(garbageCollector(getAddrFromSymTable(prg.getSymTable().getContent().values(), prg.getHeap().getContent().values()), prg.getHeap().getContent()));
                repo.logPrgStateExec();
            }
            catch (IOException e) {
                throw new MyException("Error on saving the content in the text file");
            }
            catch (MyException me) {
                throw new MyException(me.getMessage());
            }
            displayState(prg);
        }
    }

    public Map<Integer, Value> garbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap){
        return heap.entrySet().stream().
                filter(e->symTableAddr.contains(e.getKey())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /*
    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream().
                filter(v -> v instanceof RefValue).
                map(v -> {RefValue val = (RefValue) v; return val.getAddress();}).
                collect(Collectors.toList());
    }
    */

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Collection<Value> heap) {
        Stream<Integer> heapStream = heap.stream().
                filter(v -> v instanceof RefValue).
                map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                });
        Stream<Integer> symTableStream = symTableValues.stream().
                        filter(v -> v instanceof RefValue).
                        map(v -> {
                            RefValue v1 = (RefValue) v;
                            return v1.getAddress();
                        });
        return Stream.concat(heapStream, symTableStream).collect(Collectors.toList());
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
