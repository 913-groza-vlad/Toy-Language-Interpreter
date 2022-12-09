package Repository;

import Model.*;
import Model.Exceptions.MyException;

import java.io.IOException;
import java.util.List;

public interface IRepository {

    ProgramState getCrtPrg();

    void addPrg(ProgramState prg);

    List<ProgramState> getProgramList();

    void setProgramList(List<ProgramState> newList);

    void logPrgStateExec(ProgramState prgState) throws IOException;
}
