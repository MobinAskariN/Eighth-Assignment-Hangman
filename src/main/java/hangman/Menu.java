package hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu {
    DatabaseManager database = null;
    Scene scene = null;
    Parent root = null;
    Stage stage = null;
    String message = "nothing", username = "";
    @FXML
    private Label l_message;

    public Menu(){
    }
    public Menu(Scene scene, Parent root, Stage stage, DatabaseManager database, String message){
        this.scene = scene;
        this.root = root;
        this.stage = stage;
        this.database = database;
        this.message = message;
    }

    public void new_game(ActionEvent event) throws IOException {
        Game game = new Game();
        game.switch_game(event, username, database);
    }

    public void quit(ActionEvent event) throws IOException {
        HangmanApp hangmanApp = new HangmanApp();
        hangmanApp.switch_hangman(event, database);
    }

    public void scoreboard(ActionEvent event){
        database.show_scoreboard();
    }

    public void game_records(ActionEvent event){
        database.show_game_records(username);
    }

    public void switch_menu(ActionEvent event, String username, DatabaseManager database) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        root = loader.load();
        Menu menuController = loader.getController();
        menuController.setUsername(username);
        menuController.setDatabase(database);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDatabase(DatabaseManager database) {
        this.database = database;
    }
}
