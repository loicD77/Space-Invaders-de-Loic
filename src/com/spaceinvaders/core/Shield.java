package com.spaceinvaders.core;

import com.spaceinvaders.render.Renderer;

public class Shield {
    private float x, y, width, height;
    private int durability;

    public Shield(float x, float y, float width, float height, int durability) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.durability = durability;
    }

    // Méthode render avec le paramètre Renderer
    public void render(Renderer renderer) {
        if (durability > 0) {
            renderer.drawShield(x, y, width, height, durability);
        }
    }

    public void takeDamage() {
        if (durability > 0) {
            durability--;
        }
    }

    public boolean isDestroyed() {
        return durability <= 0;
    }

    public boolean checkCollision(float px, float py) {
        return px >= x - width / 2 && px <= x + width / 2 &&
                py >= y - height / 2 && py <= y + height / 2;
    }
}
