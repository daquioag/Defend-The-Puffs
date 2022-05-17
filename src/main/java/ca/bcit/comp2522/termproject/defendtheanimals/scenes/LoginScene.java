package ca.bcit.comp2522.termproject.defendtheanimals.scenes;

import ca.bcit.comp2522.termproject.defendtheanimals.GameController;
import ca.bcit.comp2522.termproject.defendtheanimals.sprites.StationarySprite;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Represents a login scene.
 * @author Al & Bosco
 * @version 2022
 */
public class LoginScene extends GeneralScene {

    private final GameController gameController;
    private final Connection sqlConnection;
    private final StationarySprite background;

    /**
     * Constructs a login scene.
     * @param gameController the game controller.
     * @throws SQLException if the application cannot connect to the mysql instance.
     * @throws ClassNotFoundException if the jdbc driver is not found.
     */
    public LoginScene(final GameController gameController) throws SQLException, ClassNotFoundException {
        super();
        sqlConnection = connectToSQL();
        this.gameController = gameController;   // tried to put it in GeneralScene but it would crash
        GridPane grid = makeLoginForm();
        StackPane sp = new StackPane();
        sp.setAlignment(Pos.CENTER);

        sp.getChildren().add(grid);
        root.getChildren().add(sp);

        AnchorPane.setTopAnchor(sp, 0.0);
        AnchorPane.setBottomAnchor(sp, 0.0);
        AnchorPane.setLeftAnchor(sp, 0.0);
        AnchorPane.setRightAnchor(sp, 0.0);

        background = new StationarySprite("images/loginPic", GAME_WIDTH, GAME_HEIGHT);
        background.setPosition((double) GAME_WIDTH/2, (double) GAME_HEIGHT/2);
    }

    /**
     * Draws the scene.
     */
    @Override
    public void draw() {
        backgroundMusic();
        activeKeys.clear();
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(final long now) {
                background.render(gc);
            if (activeKeys.contains(KeyCode.ESCAPE)) {
                stopMusic();
                this.stop();
                gameController.exit();
            }
            }
        };

        timer.start();
    }



    private GridPane makeLoginForm() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(300, 25, 25, 25));


        Label userName = new Label("User Name:");
        userName.setFont(Font.font("Monaco", 15));
        userName.setPrefSize(100, 40);
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        userTextField.setPrefSize(500, 40);
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        pw.setFont(Font.font("Monaco", 15));
        pw.setPrefSize(100, 40);
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        pwBox.setPrefSize(500, 40);
        grid.add(pwBox, 1, 2);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        Button btn = new Button("Sign in");
        btn.setPrefSize(100, 40);
        btn.setDefaultButton(true);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                String userNameInput = userTextField.getText().toString();
                String passwordInput = pwBox.getText().toString();
                try {
                    if (loginUser(userNameInput, passwordInput)) {
                        stopMusic();
                        playSoundEffect("images/soundEffects/select.mp3");
                        gameController.changeToWelcomeScene();
                    } else {
                        actiontarget.setText("Incorrect credentials");
                    }
                } catch (ClassNotFoundException | SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(btn, 1, 4);

        return grid;
    }

    private boolean loginUser(final String username, final String password) throws ClassNotFoundException, SQLException {

        Statement statement = sqlConnection.createStatement();
        ResultSet rs = statement.executeQuery("Select * FROM users WHERE user_id = '"
                + username + "' AND password = '" + password + "'");

        if (rs.next()) {
            String usernameInDatabase = rs.getString("user_id");
            String passwordInDatabase = rs.getString("password");

            if (passwordInDatabase.equals(password) && usernameInDatabase.equals(username)) {
                System.out.println("Logging in " + usernameInDatabase + " " + passwordInDatabase);
                return true;
            }
        }

        return false;
    }

    private Connection connectToSQL() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        final String URL = "jdbc:mysql://localhost:3306/comp2522";

        final Properties connectionProperties = new Properties();
        connectionProperties.put("user", "<UserName>");
        connectionProperties.put("password", "<Password>");

        Connection connection = DriverManager.getConnection(URL, connectionProperties);
        if (connection != null) {
            System.out.println("Successfully connected to MySQL database");
            return connection;
        }
        return null;
    }

}
