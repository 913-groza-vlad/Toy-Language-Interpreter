package Repository;

import Model.Exceptions.MyException;
import Model.ProgramState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<ProgramState> prgStateList;
    private String logFilePath;

    public Repository(String filePath) {
        prgStateList = new ArrayList<>();
        this.logFilePath = filePath;
    }

    @Override
    public ProgramState getCrtPrg() {
        return prgStateList.get(0);
    }

    @Override
    public void addPrg(ProgramState prg) {
        prgStateList.add(prg);
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return prgStateList;
    }

    @Override
    public void logPrgStateExec() throws IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(getCrtPrg().toString());
        logFile.close();
    }
}
