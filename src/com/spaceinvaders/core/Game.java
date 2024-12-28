package com.spaceinvaders.core;

import com.spaceinvaders.entities.*;
import com.spaceinvaders.render.Renderer;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Player player;
    private AlienManager alienManager;
    private List<Bullet> playerBullets;
    private List<Shield> shields;
    private MysteryShip mysteryShip;
    private boolean moveLeft, moveRight, shooting;
    private int score = 0;
    private int lives = 3;
    private boolean isGameOver = false;

    private float mysteryShipCooldown = 20.0f;
    private float mysteryShipTimer = mysteryShipCooldown;
    private boolean mysteryShipDirectionLeftToRight = true; // Alterner les directions

    public Game() {
        player = new Player(0.0f, -0.8f, 0.1f, 0.1f, new float[]{0.0f, 0.0f, 1.0f});
        alienManager = new AlienManager();
        playerBullets = new ArrayList<>();
        shields = new ArrayList<>();

        shields.add(new Shield(-0.6f, -0.6f, 0.2f, 0.1f, 3));
        shields.add(new Shield(0.0f, -0.6f, 0.2f, 0.1f, 3));
        shields.add(new Shield(0.6f, -0.6f, 0.2f, 0.1f, 3));

        mysteryShip = new MysteryShip(-1.2f, 0.8f, 0.2f, 0.1f, 0.5f);
    }

    public void update(float delta) {
        if (isGameOver) return;

        player.update(moveLeft, moveRight, delta);

        if (shooting) {
            float bulletX = player.getX();
            float bulletY = player.getY() + player.getHeight() / 2;
            playerBullets.add(new Bullet(bulletX, bulletY, 0.5f, new float[]{1.0f, 1.0f, 1.0f}));
            shooting = false;
        }

        alienManager.update(delta);

        if (alienManager.getAliens().isEmpty()) {
            alienManager.resetAliens();
        }

        for (Bullet bullet : new ArrayList<>(playerBullets)) {
            bullet.update(delta);

            if (bullet.isOffScreen()) {
                playerBullets.remove(bullet);
            } else {
                for (Shield shield : shields) {
                    if (shield.checkCollision(bullet.getX(), bullet.getY())) {
                        shield.takeDamage();
                        playerBullets.remove(bullet);
                        break;
                    }
                }

                for (Alien alien : new ArrayList<>(alienManager.getAliens())) {
                    if (bullet.collidesWith(alien)) {
                        playerBullets.remove(bullet);
                        alienManager.getAliens().remove(alien);
                        score += 10;
                        break;
                    }
                }

                if (mysteryShip.isActive() && mysteryShip.checkCollision(bullet.getX(), bullet.getY())) {
                    score += 200;
                    playerBullets.remove(bullet);
                    mysteryShip.deactivate();
                    mysteryShipTimer = mysteryShipCooldown;
                    break;
                }
            }
        }

        for (Bullet alienBullet : new ArrayList<>(alienManager.getAlienBullets())) {
            if (alienBullet.collidesWith(player)) {
                alienManager.getAlienBullets().remove(alienBullet);
                lives--;
                if (lives <= 0) {
                    isGameOver = true;
                }
                break;
            } else {
                for (Shield shield : shields) {
                    if (shield.checkCollision(alienBullet.getX(), alienBullet.getY())) {
                        shield.takeDamage();
                        alienManager.getAlienBullets().remove(alienBullet);
                        break;
                    }
                }
            }
        }

        shields.removeIf(Shield::isDestroyed);

        if (!mysteryShip.isActive()) {
            mysteryShipTimer -= delta;
            if (mysteryShipTimer <= 0) {
                float spawnPosition = mysteryShipDirectionLeftToRight ? -1.2f : 1.2f;
                float directionSpeed = mysteryShipDirectionLeftToRight ? 0.5f : -0.5f;
                mysteryShip.spawn(spawnPosition, directionSpeed);

                mysteryShipTimer = mysteryShipCooldown;
                mysteryShipDirectionLeftToRight = !mysteryShipDirectionLeftToRight;
            }
        }

        mysteryShip.update(delta);
    }

    public void render(Renderer renderer) {
        if (isGameOver) {
            renderer.drawText("GAME OVER", -0.4f, 0.0f, 0.1f, new float[]{1.0f, 0.0f, 0.0f});
            renderer.drawText("SCORE: " + score, -0.3f, -0.2f, 0.05f, new float[]{1.0f, 1.0f, 1.0f});
            return;
        }

        renderer.drawGround(-0.95f, new float[]{0.0f, 1.0f, 0.0f});
        player.render(renderer);
        alienManager.render(renderer);

        for (Bullet bullet : playerBullets) {
            bullet.render(renderer);
        }

        for (Shield shield : shields) {
            shield.render(renderer);
        }

        mysteryShip.render(renderer);

        renderer.drawText("SCORE: " + score, -0.9f, 0.9f, 0.05f, new float[]{1.0f, 1.0f, 1.0f});
        renderer.drawText("LIVES: " + lives, 0.5f, 0.9f, 0.05f, new float[]{1.0f, 1.0f, 1.0f});
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }
}
