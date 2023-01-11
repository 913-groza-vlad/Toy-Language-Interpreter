package View.GUI;

import Controller.Service;
import Model.ADTstructures.MyHeap;
import Model.ADTstructures.MyIHeap;
import Model.ADTstructures.MyIList;
import Model.ADTstructures.MyList;
import Model.ProgramState;
import Model.Statements.IStmt;
import Model.Values.Value;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProgramExecController {
    @FXML
    private final Text programText = new Text();
    private Service service;

    @FXML
    private TextField prgStatesNumber;
    @FXML
    private TableView<Pair<Integer, Value>> heapTable;
    @FXML
    private ListView<String> out;
    @FXML
    private ListView<String> fileTable;
    @FXML
    private ListView<Integer> prgStatesIds;
    @FXML
    private TableView<Pair<String, Value>> symTable;
    @FXML
    private ListView<String> exeStack;
    @FXML
    private Button oneStepExecution;
    @FXML
    private Button stopExecution;
    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> addressCol;
    @FXML
    private TableColumn<Pair<Integer, Value>, String> valueCol;
    @FXML
    private TableColumn<Pair<String, Value>, String> variableCol;
    @FXML
    private TableColumn<Pair<String, Value>, String> symValueCol;

    /* public void setProgramText(String prgName) {
        programText.setText(prgName);
    }

    public Text getProgramText() {
        return programText;
    } */

    @FXML
    public void handleStopButtonAction(ActionEvent event) {
        Stage stage = (Stage) stopExecution.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        oneStepExecution.setOnAction(actionEvent -> {

        });
    }

    public void setService(Service service) {
        this.service = service;
        this.populate();
    }

    private void populate() {
        this.populateProgramStateIds();
        this.populateExeStack();
        this.populateSymTable();
        this.populateOut();
        this.populateFileTable();
        this.populateHeap();
    }

    private ProgramState getCurrentPrgState() {
        if (service.getPrograms().getProgramList().size() == 0)
            return null;
        int prgStateId = prgStatesIds.getSelectionModel().getSelectedIndex();
        if (prgStateId < 0)
            return service.getPrograms().getProgramList().get(0);
        return service.getPrograms().getProgramList().get(prgStateId);
    }

    private void populateExeStack() {
        ProgramState prgState = getCurrentPrgState();
        List<String> stringExecutionStack = new ArrayList<>();
        if (prgState != null) {
            for (IStmt statement : prgState.getStk())
                stringExecutionStack.add(statement.toString());
        }
        exeStack.setItems(FXCollections.observableList(stringExecutionStack));
        exeStack.refresh();
    }

    private void populateSymTable() {
        ProgramState prgState = getCurrentPrgState();
        List<Pair<String, Value>> stringSymbolTable = new ArrayList<>();
        if (prgState != null)
            for (Map.Entry<String, Value> el : prgState.getSymTable().getContent().entrySet())
                stringSymbolTable.add(new Pair<>(el.getKey(), el.getValue()));
        symTable.setItems(FXCollections.observableList(stringSymbolTable));
        symTable.refresh();
    }

    private void populateOut() {
        MyIList<Value> outputValues;
        if (service.getPrograms().getProgramList().size() > 0)
            outputValues = service.getPrograms().getProgramList().get(0).getOut();
        else
            outputValues = new MyList<>();
        List<String> stringOut = new ArrayList<>();
        for (Value val : outputValues)
            stringOut.add(val.toString());
        out.setItems(FXCollections.observableList(stringOut));
        out.refresh();
    }

    private void populateFileTable() {
        List<String> files = new ArrayList<>();
        if (service.getPrograms().getProgramList().size() > 0)
            files.addAll(service.getPrograms().getProgramList().get(0).getFileTable().getContent().keySet());
        fileTable.setItems(FXCollections.observableList(files));
        fileTable.refresh();
    }

    private void populateHeap() {
        MyIHeap heap = new MyHeap();
        List<Pair<Integer, Value>> heapList = new ArrayList<>();
        if (service.getPrograms().getProgramList().size() > 0)
            heap = service.getPrograms().getProgramList().get(0).getHeap();
        for (Map.Entry<Integer, Value> el : heap.getContent().entrySet())
            heapList.add(new Pair<>(el.getKey(), el.getValue()));
        heapTable.setItems(FXCollections.observableList(heapList));
        heapTable.refresh();
    }

    private void populateProgramStateIds() {
        List<ProgramState> programStates = service.getPrograms().getProgramList();
        List<Integer> programIds = programStates.stream().map(ProgramState::getPrgStateId).toList();
        prgStatesIds.setItems(FXCollections.observableList(programIds));
        prgStatesNumber.setText(String.valueOf(programStates.size()));
    }

}
