package com.spaceinvaders.entities;

import com.spaceinvaders.render.Renderer;

public class Alien {
    private float x, y;
    private float width, height;
    private float[] color;

    public Alien(float x, float y, float width, float height, float[] color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void update(float delta, boolean movingRight) {
        float horizontalSpeed = 0.2f; // Vitesse horizontale
        x += (movingRight ? 1 : -1) * horizontalSpeed * delta;
    }

    public void render(Renderer renderer) {
        renderer.drawRect(x, y, width, height, color);
    }

    public void descend(float amount) {
        y -= amount; // Descend d'une certaine distance
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
