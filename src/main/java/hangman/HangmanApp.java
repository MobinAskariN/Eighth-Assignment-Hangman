package hangman;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class HangmanApp extends Application {

    DatabaseManager database = new DatabaseManager();
    Scene scene = null;
    Parent root = null;
    Stage stage = null;
    Game game = null;
    Menu menu = null;

    public HangmanApp(){}
    public HangmanApp(Scene scene, Parent root, Stage stage, DatabaseManager database){
        this.scene = scene;
        this.root = root;
        this.stage = stage;
        this.database = database;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HangmanApp.class.getResource("hangman-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 660, 600);
        stage.setTitle("Hangman");
        stage.setScene(scene);
        stage.show();
    }

    public void switch_hangman(ActionEvent event, DatabaseManager database) throws IOException {
        this.database = database;
        root = FXMLLoader.load(getClass().getResource("hangman-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private TextField l_username;
    @FXML
    private TextField l_password;
    @FXML
    private Label l_label;

    public void login(ActionEvent e) throws IOException {
        String username = l_username.getText();
        String password = l_password.getText();
        if(username.isEmpty() || password.isEmpty())
            l_label.setText("some information missing");
        else{
            String name = database.login(username, password);
            if(name == null){
                l_label.setText("!!!!");
            } else {
                menu = new Menu();
                menu.switch_menu(e, username, database);
            }
        }
    }


    @FXML
    private TextField r_username;
    @FXML
    private TextField r_password;
    @FXML
    private TextField r_name;
    @FXML
    private Label r_label;
    public void register(ActionEvent e) throws IOException {
        String username = r_username.getText();
        String password = r_password.getText();
        String name = r_name.getText();
        if(username.isEmpty() || password.isEmpty() || name.isEmpty())
            r_label.setText("some information missing");
        else{
            database.register(username, password, name);
            r_label.setText("registered successfully");
        }
    }



    public static void main(String[] args) {
        launch();
    }
}