package View.GUI;

import Controller.Service;
import Model.ADTstructures.*;
import Model.ProgramState;
import Model.Statements.IStmt;
import Model.Values.Value;
import Repository.IRepository;
import Repository.Repository;
import View.Ui;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.util.List;
import java.util.Objects;

public class ListViewController {

    @FXML
    private ListView<IStmt> statementsList;

    @FXML
    private Button selectButton;
    @FXML
    private Button exitButton;

    @FXML
    private Text title;

    private ProgramExecController programExecController;


    @FXML
    public void initialize() {
        List<IStmt> programs = ProgramExamples.createExamples();
        statementsList.setItems(FXCollections.observableArrayList(programs));
        selectButton.setOnAction(actionEvent -> {
            int index = statementsList.getSelectionModel().getSelectedIndex();
            if (index < 0)
                return;
            MyIStack<IStmt> exeStack = new MyStack<>();
            MyIDictionary<String, Value> symTable = new MyDictionary<>();
            MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
            MyIList<Value> out = new MyList<>();
            MyIHeap heap = new MyHeap();
            ProgramState programState = new ProgramState(exeStack, symTable, out, fileTable, heap, programs.get(index));
            String logFileNr = String.valueOf(index + 1);
            IRepository repository = new Repository("log" + logFileNr + ".txt");
            Service serv = new Service(repository);
            serv.addProgramState(programState);

            // programExecController.setProgramText(programState.getStk().toString());
            try {
                FXMLLoader programLoader = new FXMLLoader();
                programLoader.setLocation(getClass().getResource("ProgramExec.fxml"));
                GridPane programRoot = (GridPane) programLoader.load();
                ProgramExecController programExecController = programLoader.getController();
                programExecController.setService(serv);
                programRoot.setHgap(7);
                programRoot.setVgap(10);
                Stage secondaryStage = new Stage();
                // programRoot.add(programExecController.getProgramText(), 0, 1);
                secondaryStage.setTitle("Program Execution");
                Scene secondaryScene = new Scene(programRoot, 800, 600);
                secondaryScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("programexec.css")).toExternalForm());
                secondaryStage.setScene(secondaryScene);
                secondaryStage.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        });
        exitButton.setOnAction(actionEvent -> {System.exit(0);});
    }

}
