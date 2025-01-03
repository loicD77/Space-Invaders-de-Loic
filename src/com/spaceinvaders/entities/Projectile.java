package com.spaceinvaders.entities;

import com.spaceinvaders.render.Renderer;

public class Projectile {
    private float x, y;
    private float width, height;
    private float speed;
    private float[] color;

    public Projectile(float x, float y, float speed, float[] color) {
        this.x = x;
        this.y = y;
        this.width = 0.02f;
        this.height = 0.05f;
        this.speed = speed;
        this.color = color;
    }

    public void update(float delta) {
        y += speed * delta;
    }

    public void render(Renderer renderer) {
        renderer.drawRect(x, y, width, height, color);
    }

    public boolean isOffScreen() {
        return y > 1.0f || y < -1.0f;
    }
}
