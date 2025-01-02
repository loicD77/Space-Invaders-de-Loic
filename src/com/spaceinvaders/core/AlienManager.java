package com.spaceinvaders.core;

import com.spaceinvaders.entities.*;
import com.spaceinvaders.render.Renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlienManager {
    private List<Alien> aliens;
    private List<Bullet> alienBullets;
    private boolean movingRight = true;
    private float speed = 0.2f;
    private float descentAmount = 0.05f;
    private boolean reachedEdge = false;
    private Random random;

    private float alienShootCooldown = 2.0f;
    private float alienShootTimer = 0.0f;

    private MysteryShip mysteryShip;
    private float mysteryShipCooldown = 10.0f;
    private float mysteryShipTimer = 0.0f;

    public AlienManager() {
        aliens = new ArrayList<>();
        alienBullets = new ArrayList<>();
        random = new Random();
        initializeAliens();
        mysteryShip = new MysteryShip(-1.2f, 0.8f, 0.2f, 0.1f, 0.5f);
    }

    private void initializeAliens() {
        aliens.clear();
        float[][] colors = {
                {1.0f, 0.0f, 0.0f},
                {0.0f, 1.0f, 0.0f},
                {0.0f, 0.0f, 1.0f}
        };

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {
                aliens.add(new Alien(-0.8f + col * 0.3f, 0.6f - row * 0.2f, 0.1f, 0.1f, colors[row]));
            }
        }
    }

    public void update(float delta, List<Shield> shields) {
        reachedEdge = false;

        for (Alien alien : aliens) {
            if ((movingRight && alien.getX() + alien.getWidth() >= 1.0f) ||
                    (!movingRight && alien.getX() <= -1.0f)) {
                reachedEdge = true;
                break;
            }
        }

        for (Alien alien : aliens) {
            if (reachedEdge) {
                alien.descend(descentAmount);
            }
            alien.update(delta, movingRight);
        }

        if (reachedEdge) {
            movingRight = !movingRight;
        }

        alienShootTimer -= delta;
        if (alienShootTimer <= 0) {
            spawnAlienProjectiles();
            alienShootTimer = alienShootCooldown;
        }

        for (Bullet bullet : new ArrayList<>(alienBullets)) {
            bullet.update(delta);
            if (bullet.isOffScreen()) {
                alienBullets.remove(bullet);
            }
        }

        mysteryShipTimer -= delta;
        if (mysteryShipTimer <= 0 && !mysteryShip.isActive()) {
            float spawnPosition = Math.random() < 0.5 ? -1.2f : 1.2f;
            float directionSpeed = spawnPosition < 0 ? 0.5f : -0.5f;
            mysteryShip.spawn(spawnPosition, directionSpeed);
            mysteryShipTimer = mysteryShipCooldown;
        }

        mysteryShip.update(delta);
    }

    public void render(Renderer renderer) {
        for (Alien alien : aliens) {
            alien.render(renderer);
        }
        for (Bullet bullet : alienBullets) {
            bullet.render(renderer);
        }
        mysteryShip.render(renderer);
    }

    public void resetAliens() {
        initializeAliens();
        speed += 0.05f;
        alienShootCooldown = Math.max(0.5f, alienShootCooldown - 0.1f);
    }

    public List<Alien> getAliens() {
        return aliens;
    }

    public List<Bullet> getAlienBullets() {
        return alienBullets;
    }

    public MysteryShip getMysteryShip() {
        return mysteryShip;
    }

    public boolean areAliensAtPlayerLevel(float playerY) {
        for (Alien alien : aliens) {
            if (alien.getY() <= playerY + 0.1f) {
                return true;
            }
        }
        return false;
    }

    private void spawnAlienProjectiles() {
        if (!aliens.isEmpty()) {
            Alien shooter = aliens.get(random.nextInt(aliens.size()));
            alienBullets.add(new Bullet(shooter.getX(), shooter.getY() - shooter.getHeight() / 2, -0.8f, new float[]{1.0f, 1.0f, 1.0f}));
        }
    }
}
