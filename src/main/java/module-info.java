module org.game.contra {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens org.game.contra to javafx.fxml;
    exports org.game.contra;
    exports org.game.contra.core;
    opens org.game.contra.core to javafx.fxml;
    exports org.game.contra.systems;
    exports org.game.contra.entity;
}