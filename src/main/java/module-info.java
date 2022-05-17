module ca.bcit.comp2522.termproject.defendtheanimals {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.sql;
    requires javafx.media;

    opens ca.bcit.comp2522.termproject.defendtheanimals to javafx.fxml;
    exports ca.bcit.comp2522.termproject.defendtheanimals;
    exports ca.bcit.comp2522.termproject.defendtheanimals.scenes;
    opens ca.bcit.comp2522.termproject.defendtheanimals.scenes to javafx.fxml;
    exports ca.bcit.comp2522.termproject.defendtheanimals.utilities;
    opens ca.bcit.comp2522.termproject.defendtheanimals.utilities to javafx.fxml;
    exports ca.bcit.comp2522.termproject.defendtheanimals.sprites;
    opens ca.bcit.comp2522.termproject.defendtheanimals.sprites to javafx.fxml;
}