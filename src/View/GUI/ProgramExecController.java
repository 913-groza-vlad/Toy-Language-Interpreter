package View.GUI;

import Controller.Service;
import Model.ADTstructures.*;
import Model.ProgramState;
import Model.Statements.CompStmt;
import Model.Statements.IStmt;
import Model.Values.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;

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

    private final MyIList<Value> outCopy = new MyList<>();
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
        addressCol.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().x).asObject());
        valueCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().y.toString()));
        variableCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().x));
        symValueCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().y.toString()));

        oneStepExecution.setOnAction(actionEvent -> {
            try {
                boolean stackEmpty = Objects.requireNonNull(getCurrentPrgState()).getStk().isEmpty();

                if(stackEmpty){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Execution of the program has ended!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Execution of the program has ended!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            service.oneStepExec();
            if (service.getPrograms().getProgramList().size() == 0) {
                List<String> stringOut = new ArrayList<>();
                for (Value val : service.outCopy)
                    stringOut.add(val.toString());
                out.setItems(FXCollections.observableList(stringOut));
            }

            this.populate();
        });
        prgStatesIds.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int programId = prgStatesIds.getSelectionModel().getSelectedIndex();
                if (programId == -1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You selected an in-existing program state!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
                MyIStack<IStmt> currentExeStack = service.getPrograms().getProgramList().get(programId).getStk().copyStack();
                List<String> stringExeStack = new ArrayList<>();

                if (currentExeStack.size() == 2) {
                    IStmt statement = currentExeStack.pop();
                    stringExeStack.add(statement.toString());
                }
                IStmt stmt = currentExeStack.pop();
                while (stmt instanceof CompStmt compStmt) {
                    stringExeStack.add(compStmt.getFirst().toString());
                    stmt = compStmt.getSecond();
                }
                stringExeStack.add(stmt.toString());
                exeStack.setItems(FXCollections.observableList(stringExeStack));
            }
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
            //for (IStmt statement : prgState.getStk())
            //    stringExecutionStack.add(statement.toString());
            MyIStack<IStmt> executionStack = prgState.getStk().copyStack();

            if (executionStack.size() == 2) {
                IStmt statement = executionStack.pop();
                stringExecutionStack.add(statement.toString());
            }
            IStmt stmt = executionStack.pop();
            while (stmt instanceof CompStmt compStmt) {
                stringExecutionStack.add(compStmt.getFirst().toString());
                stmt = compStmt.getSecond();
            }
            stringExecutionStack.add(stmt.toString());

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
        else
            return;
        symTable.setItems(FXCollections.observableList(stringSymbolTable));
        symTable.refresh();
    }

    private void populateOut() {
        MyIList<Value> outputValues;
        if (service.getPrograms().getProgramList().size() > 0) {
            outputValues = service.getPrograms().getProgramList().get(0).getOut();
            for (Value x : outputValues)
                outCopy.add(x);
        }
        else
            return;
        List<String> stringOut = new ArrayList<>();
        for (Value val : outputValues)
            stringOut.add(val.toString());
        out.setItems(FXCollections.observableList(stringOut));
        out.refresh();
    }

    private void populateFileTable() {
        List<String> files;
        if (service.getPrograms().getProgramList().size() > 0)
            files = new ArrayList<>(service.getPrograms().getProgramList().get(0).getFileTable().getContent().keySet());
        else
            return;
        fileTable.setItems(FXCollections.observableList(files));
        fileTable.refresh();
    }

    private void populateHeap() {
        MyIHeap heap = new MyHeap();
        List<Pair<Integer, Value>> heapList = new ArrayList<>();
        if (service.getPrograms().getProgramList().size() > 0)
            heap = service.getPrograms().getProgramList().get(0).getHeap();
        else
            return;
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

class Pair<T1, T2> {
    T1 x;
    T2 y;

    public Pair(T1 x, T2 y) {
        this.x = x;
        this.y = y;
    }
}
