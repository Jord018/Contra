package org.game.contra.core;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class input {
    private final Set<KeyCode> activeKeys = new HashSet<>();
    
    public void handleKeyPress(KeyEvent event) {
        activeKeys.add(event.getCode());
    }
    
    public void handleKeyRelease(KeyEvent event) {
        activeKeys.remove(event.getCode());
    }
    
    public boolean isKeyPressed(KeyCode key) {
        return activeKeys.contains(key);
    }
    
    // Helper methods for common controls
    public boolean isMoveLeft() {
        return isKeyPressed(KeyCode.LEFT) || isKeyPressed(KeyCode.A);
    }
    
    public boolean isMoveRight() {
        return isKeyPressed(KeyCode.RIGHT) || isKeyPressed(KeyCode.D);
    }
    
    public boolean isJump() {
        return isKeyPressed(KeyCode.UP) || isKeyPressed(KeyCode.W) || isKeyPressed(KeyCode.SPACE);
    }
    
    public boolean isShoot() {
        return isKeyPressed(KeyCode.CONTROL) || isKeyPressed(KeyCode.ENTER);
    }
    
    public void clear() {
        activeKeys.clear();
    }
}
