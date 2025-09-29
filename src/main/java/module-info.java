module org.audioconvert.contra {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.game.contra to javafx.fxml;
    exports org.game.contra;
    exports org.game.contra.core;
    opens org.game.contra.core to javafx.fxml;
}