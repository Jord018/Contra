module org.game.contra {
    // JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // LibGDX modules - using automatic module names
    requires transitive gdx;
    requires transitive gdx.backend.lwjgl3;
    requires transitive gdx.box2d;
    
    // Export your packages
    exports org.game.contra;
    exports org.game.contra.core;
    exports org.game.contra.controller;
    exports org.game.contra.entities;
    exports org.game.contra.screens;
    exports org.game.contra.utils;
    
    // Open all packages to required modules
    opens org.game.contra to javafx.fxml, gdx, gdx.backend.lwjgl3, gdx.box2d;
    opens org.game.contra.core to gdx, gdx.backend.lwjgl3, gdx.box2d;
    opens org.game.contra.entities to gdx, gdx.box2d;
    opens org.game.contra.screens to gdx, gdx.box2d;
    opens org.game.contra.utils to gdx, gdx.box2d;
    opens org.game.contra.controller to gdx, gdx.box2d;
}