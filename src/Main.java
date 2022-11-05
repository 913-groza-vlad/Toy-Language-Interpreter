import Controller.Service;
import Model.ADTstructures.*;
import Model.Expressions.*;
import Model.ProgramState;
import Model.Statements.*;
import Model.Types.*;
import Model.Values.*;
import Repository.*;
import View.Ui;

public class Main {
    public static void main(String[] args) {

        /* String example1 = "int v; v=2; Print(v)";
        String example2 = "int a; int b; a=2+3*5; b=a+1; Print(b)";
        String example3 = "bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)"; */


        Ui ui = new Ui();
        ui.start();

    }
}