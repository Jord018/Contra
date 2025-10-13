package org.game.contra.entities.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import org.game.contra.entities.Player;

public interface Items  {

        void applyEffect(Player player); // สิ่งที่เกิดขึ้นเมื่อเก็บ
        Texture getTexture();            // รูปภาพของไอเท็ม
        void spawn(World world, float x, float y); // สร้างไอเท็มบนโลก
        String getName();                // ชื่อไอเท็ม
        void markForDestruction();       // ทำเครื่องหมายให้ทำลาย
    }

