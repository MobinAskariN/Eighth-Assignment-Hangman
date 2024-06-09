package hangman;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.Instant;

public class Game {
    DatabaseManager database = null;
    Scene scene = null;
    Parent root = null;
    Stage stage = null;
    private String word = "";
    public String username, wrong_guesses = "";
    private int part = 1, start_point = 0, n_word = 0;
    private Instant startTime;
    private boolean isFirstClick = true;
    private static int game_id = 0;

    public Game() {
        set_word();
        game_id++;
    }


    @FXML
    private Button A;
    @FXML
    private Button B;
    @FXML
    private Button C;
    @FXML
    private Button D;
    @FXML
    private Button E;
    @FXML
    private Button F;
    @FXML
    private Button G;
    @FXML
    private Button H;
    @FXML
    private Button I;
    @FXML
    private Button J;
    @FXML
    private Button K;
    @FXML
    private Button L;
    @FXML
    private Button M;
    @FXML
    private Button N;
    @FXML
    private Button O;
    @FXML
    private Button P;
    @FXML
    private Button Q;
    @FXML
    private Button R;
    @FXML
    private Button S;
    @FXML
    private Button T;
    @FXML
    private Button U;
    @FXML
    private Button V;
    @FXML
    private Button W;
    @FXML
    private Button X;
    @FXML
    private Button Y;
    @FXML
    private Button Z;
    @FXML
    private Label l1;
    @FXML
    private Label l2;
    @FXML
    private Label l3;
    @FXML
    private Label l4;
    @FXML
    private Label l5;
    @FXML
    private Label l6;
    @FXML
    private Label l7;
    @FXML
    private Label l8;
    @FXML
    private Line p1;
    @FXML
    private Line p2;
    @FXML
    private Line p3;
    @FXML
    private Line body;
    @FXML
    private Line hand1;
    @FXML
    private Line hand2;
    @FXML
    private Line foot1;
    @FXML
    private Line foot2;
    @FXML
    private Circle head;
    @FXML
    private Line soog;
    @FXML
    private Label l_username;
    @FXML
    private Label n_wins;
    @FXML
    private Label place;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label l_message;

    public void letter_handler(String letter, ActionEvent event) throws IOException {

        if (isFirstClick) {
            startTime = Instant.now();
            isFirstClick = false;
        }

        for (int i = 0; i < word.length(); i++) {
            if (i + start_point == 0) l1.setVisible(true);
            else if (i + start_point == 1) l2.setVisible(true);
            else if (i + start_point == 2) l3.setVisible(true);
            else if (i + start_point == 3) l4.setVisible(true);
            else if (i + start_point == 4) l5.setVisible(true);
            else if (i + start_point == 5) l6.setVisible(true);
            else if (i + start_point == 6) l7.setVisible(true);
            else if (i + start_point == 7) l8.setVisible(true);
        }

        if (word.contains(letter)) {
            for (int i = 0; i < word.length(); i++) {
                if (word.substring(i, i + 1).equals(letter)) {
                    n_word++;
                    if (i + start_point == 0) l1.setText(letter);
                    else if (i + start_point == 1) l2.setText(letter);
                    else if (i + start_point == 2) l3.setText(letter);
                    else if (i + start_point == 3) l4.setText(letter);
                    else if (i + start_point == 4) l5.setText(letter);
                    else if (i + start_point == 5) l6.setText(letter);
                    else if (i + start_point == 6) l7.setText(letter);
                    else if (i + start_point == 7) l8.setText(letter);
                }
            }
            if (n_word == word.length()) {
                anchorPane.setVisible(true);
                l_message.setText("you won!");
                Instant endTime = Instant.now();
                int elapsedTime = (int) java.time.Duration.between(startTime, endTime).toSeconds();
                database.record_game(username, game_id, word, wrong_guesses, elapsedTime, 1);
                database.add_win(username);
                System.out.println(username);



                PauseTransition pause = new PauseTransition(Duration.seconds(10));
                pause.setOnFinished(e -> {
                    try {
                        Menu menu = new Menu(scene, root, stage, database, "you won!");
                        menu.switch_menu(event, username, database);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
                pause.play();
            }
        } else {
            wrong_guesses += letter;
            if (part == 1)
                p1.setVisible(true);
            else if (part == 2)
                p2.setVisible(true);
            else if (part == 3)
                p3.setVisible(true);
            else if (part == 4)
                head.setVisible(true);
            else if (part == 5)
                body.setVisible(true);
            else if (part == 6) {
                hand1.setVisible(true);
                hand2.setVisible(true);
            } else if (part == 7) {
                foot1.setVisible(true);
                foot2.setVisible(true);
                soog.setVisible(true);
            } else {
                anchorPane.setVisible(true);
                l_message.setText("you lost!");
                Instant endTime = Instant.now();
                int elapsedTime = (int) java.time.Duration.between(startTime, endTime).toSeconds();
                database.record_game(username, game_id, word, wrong_guesses, elapsedTime, 0);


                PauseTransition pause = new PauseTransition(Duration.seconds(10));
                pause.setOnFinished(e -> {
                    try {
                        Menu menu = new Menu(scene, root, stage, database, "you lost!");
                        menu.switch_menu(event, username, database);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
                pause.play();
            }
            part++;
        }
    }

    public void set_word() {
        word = "animal";

        if (word.length() % 2 == 0)
            start_point = 4 - word.length() / 2;
        else start_point = 3 - word.length() / 2;
    }
    public void setUsername(String username) {
        this.username = username;
        l_username.setText(username);
    }

    public void setDatabase(DatabaseManager database) {
        this.database = database;
    }


    public void A_click(ActionEvent event) throws IOException {
        A.setVisible(false);
        letter_handler("a", event);
    }

    public void B_click(ActionEvent event) throws IOException {
        B.setVisible(false);
        letter_handler("b", event);
    }

    public void C_click(ActionEvent event) throws IOException {
        C.setVisible(false);
        letter_handler("c", event);
    }

    public void D_click(ActionEvent event) throws IOException {
        D.setVisible(false);
        letter_handler("d", event);
    }

    public void E_click(ActionEvent event) throws IOException {
        E.setVisible(false);
        letter_handler("e", event);
    }

    public void F_click(ActionEvent event) throws IOException {
        F.setVisible(false);
        letter_handler("f", event);
    }

    public void G_click(ActionEvent event) throws IOException {
        G.setVisible(false);
        letter_handler("g", event);
    }

    public void H_click(ActionEvent event) throws IOException {
        H.setVisible(false);
        letter_handler("h", event);
    }

    public void I_click(ActionEvent event) throws IOException {
        I.setVisible(false);
        letter_handler("i", event);
    }

    public void J_click(ActionEvent event) throws IOException {
        J.setVisible(false);
        letter_handler("j", event);
    }

    public void K_click(ActionEvent event) throws IOException {
        K.setVisible(false);
        letter_handler("k", event);
    }

    public void L_click(ActionEvent event) throws IOException {
        L.setVisible(false);
        letter_handler("l", event);
    }

    public void M_click(ActionEvent event) throws IOException {
        M.setVisible(false);
        letter_handler("m", event);
    }

    public void N_click(ActionEvent event) throws IOException {
        N.setVisible(false);
        letter_handler("n", event);
    }

    public void O_click(ActionEvent event) throws IOException {
        O.setVisible(false);
        letter_handler("o", event);
    }

    public void P_click(ActionEvent event) throws IOException {
        P.setVisible(false);
        letter_handler("p", event);
    }

    public void Q_click(ActionEvent event) throws IOException {
        Q.setVisible(false);
        letter_handler("q", event);
    }

    public void R_click(ActionEvent event) throws IOException {
        R.setVisible(false);
        letter_handler("r", event);
    }

    public void S_click(ActionEvent event) throws IOException {
        S.setVisible(false);
        letter_handler("s", event);
    }

    public void T_click(ActionEvent event) throws IOException {
        T.setVisible(false);
        letter_handler("t", event);
    }

    public void U_click(ActionEvent event) throws IOException {
        U.setVisible(false);
        letter_handler("u", event);
    }

    public void V_click(ActionEvent event) throws IOException {
        V.setVisible(false);
        letter_handler("v", event);
    }

    public void W_click(ActionEvent event) throws IOException {
        W.setVisible(false);
        letter_handler("w", event);
    }

    public void X_click(ActionEvent event) throws IOException {
        X.setVisible(false);
        letter_handler("x", event);
    }

    public void Y_click(ActionEvent event) throws IOException {
        Y.setVisible(false);
        letter_handler("y", event);
    }

    public void Z_click(ActionEvent event) throws IOException {
        Z.setVisible(false);
        letter_handler("z", event);
    }

    public void switch_game(ActionEvent event, String username, DatabaseManager database) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game page.fxml"));
        root = loader.load();
        Game game = loader.getController();
        game.setUsername(username);
        game.setDatabase(database);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
