package com.spaceinvaders.entities;

import com.spaceinvaders.render.Renderer;

public class Shield {
    private float x, y;
    private float width, height;
    private int health;

    public Shield(float x, float y, float width, float height, int health) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
    }

    public void takeDamage() {
        if (health > 0) {
            health--;
        }
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public boolean checkCollision(float bulletX, float bulletY) {
        return bulletX > x && bulletX < x + width && bulletY > y && bulletY < y + height;
    }

    public void render(Renderer renderer) {
        if (health > 0) {
            float[] color = getColorBasedOnHealth();
            renderer.drawRect(x, y, width, height, color);
        }
    }

    private float[] getColorBasedOnHealth() {
        if (health == 3) {
            return new float[]{0.0f, 1.0f, 0.0f}; // Vert
        } else if (health == 2) {
            return new float[]{1.0f, 1.0f, 0.0f}; // Jaune
        } else if (health == 1) {
            return new float[]{1.0f, 0.0f, 0.0f}; // Rouge
        }
        return new float[]{0.0f, 0.0f, 0.0f}; // Invisible
    }

    // Ajout des méthodes nécessaires
    public float getY() {
        return y;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getWidth() {
        return width;
    }
}
