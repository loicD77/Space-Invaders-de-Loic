package com.spaceinvaders.core;

import com.spaceinvaders.entities.*;
import com.spaceinvaders.render.Renderer;
import com.spaceinvaders.sound.SoundPlayer;

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
    private boolean mysteryShipDirectionLeftToRight = true;

    public Game() {
        initializeGame();
    }

    private void initializeGame() {
        player = new Player(0.0f, -0.8f, 0.1f, 0.1f, new float[]{0.0f, 0.0f, 1.0f});
        alienManager = new AlienManager();
        playerBullets = new ArrayList<>();
        shields = initializeShields();
        mysteryShip = new MysteryShip(-1.2f, 0.8f, 0.2f, 0.1f, 0.5f);
    }

    private List<Shield> initializeShields() {
        List<Shield> shields = new ArrayList<>();
<<<<<<< HEAD
        shields.add(new Shield(-0.75f, -0.6f, 0.2f, 0.1f, 5, 10));
        shields.add(new Shield(-0.25f, -0.6f, 0.2f, 0.1f, 5, 10));
        shields.add(new Shield(0.25f, -0.6f, 0.2f, 0.1f, 5, 10));
        shields.add(new Shield(0.75f, -0.6f, 0.2f, 0.1f, 5, 10));
=======
        shields.add(new Shield(-0.75f, -0.6f, 0.2f, 0.1f, 3));
        shields.add(new Shield(-0.25f, -0.6f, 0.2f, 0.1f, 3));
        shields.add(new Shield(0.25f, -0.6f, 0.2f, 0.1f, 3));
        shields.add(new Shield(0.75f, -0.6f, 0.2f, 0.1f, 3));
>>>>>>> f6ecbdeb7f14403636951663fbdc6cba695fe0ed
        return shields;
    }

    public void update(float delta) {
        if (isGameOver) return;

        player.update(moveLeft, moveRight, delta);

        handlePlayerShooting();
        alienManager.update(delta, shields);
        handlePlayerBullets(delta);
        handleAlienBullets();

<<<<<<< HEAD
=======
        // Vérifie si tous les aliens sont détruits pour déclencher une nouvelle vague
>>>>>>> f6ecbdeb7f14403636951663fbdc6cba695fe0ed
        if (alienManager.getAliens().isEmpty()) {
            alienManager.resetAliens();
        }

<<<<<<< HEAD
=======
        // Vérifie si un alien atteint le niveau du joueur pour déclencher le Game Over
>>>>>>> f6ecbdeb7f14403636951663fbdc6cba695fe0ed
        if (alienManager.areAliensAtPlayerLevel(player.getY())) {
            isGameOver = true;
        }

<<<<<<< HEAD
        shields.removeIf(Shield::isFullyDestroyed);
=======
        shields.removeIf(Shield::isDestroyed);
>>>>>>> f6ecbdeb7f14403636951663fbdc6cba695fe0ed
        handleMysteryShip(delta);
    }

    private void handlePlayerShooting() {
        if (shooting) {
            float bulletX = player.getX();
            float bulletY = player.getY() + player.getHeight() / 2;
            playerBullets.add(new Bullet(bulletX, bulletY, 0.5f, new float[]{1.0f, 1.0f, 1.0f}));
            SoundPlayer.playSoundAsync("res/sounds/laser.wav");
            shooting = false;
        }
    }

    private void handlePlayerBullets(float delta) {
        for (Bullet bullet : new ArrayList<>(playerBullets)) {
            bullet.update(delta);

            if (bullet.isOffScreen()) {
                playerBullets.remove(bullet);
            } else {
                checkBulletCollisions(bullet);
            }
        }
    }

    private void checkBulletCollisions(Bullet bullet) {
        for (Shield shield : shields) {
            if (shield.checkCollision(bullet.getX(), bullet.getY())) {
<<<<<<< HEAD
=======
                shield.takeDamage();
>>>>>>> f6ecbdeb7f14403636951663fbdc6cba695fe0ed
                playerBullets.remove(bullet);
                SoundPlayer.playSoundAsync("res/sounds/impact.wav");
                return;
            }
        }

        for (Alien alien : new ArrayList<>(alienManager.getAliens())) {
            if (bullet.collidesWith(alien)) {
                playerBullets.remove(bullet);
                alienManager.getAliens().remove(alien);
                score += 10;
                SoundPlayer.playSoundAsync("res/sounds/explosion.wav");
                return;
            }
        }

        if (bullet.getY() <= -0.95f) {
            playerBullets.remove(bullet);
            SoundPlayer.playSoundAsync("res/sounds/impact.wav");
        } else if (mysteryShip.isActive() && mysteryShip.checkCollision(bullet.getX(), bullet.getY())) {
            score += 200;
            playerBullets.remove(bullet);
            mysteryShip.deactivate();
            mysteryShipTimer = mysteryShipCooldown;
            SoundPlayer.playSoundAsync("res/sounds/explosion.wav");
        }
    }

    private void handleAlienBullets() {
        for (Bullet alienBullet : new ArrayList<>(alienManager.getAlienBullets())) {
            if (alienBullet.getY() <= -0.91f) {
                alienManager.getAlienBullets().remove(alienBullet);
                SoundPlayer.playSoundAsync("res/sounds/impact.wav");
                continue;
            }

            if (alienBullet.collidesWith(player)) {
                alienManager.getAlienBullets().remove(alienBullet);
                lives--;
                if (lives <= 0) isGameOver = true;
                return;
            }

            for (Shield shield : shields) {
                if (shield.checkCollision(alienBullet.getX(), alienBullet.getY())) {
<<<<<<< HEAD
=======
                    shield.takeDamage();
>>>>>>> f6ecbdeb7f14403636951663fbdc6cba695fe0ed
                    alienManager.getAlienBullets().remove(alienBullet);
                    SoundPlayer.playSoundAsync("res/sounds/impact.wav");
                    return;
                }
            }
        }
    }

    private void handleMysteryShip(float delta) {
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
            renderGameOver(renderer);
        } else {
            renderGame(renderer);
        }
    }

    private void renderGameOver(Renderer renderer) {
        renderer.drawText("GAME OVER", -0.4f, 0.0f, 0.1f, new float[]{1.0f, 0.0f, 0.0f});
        renderer.drawText("SCORE: " + score, -0.3f, -0.2f, 0.05f, new float[]{1.0f, 1.0f, 1.0f});
    }

    private void renderGame(Renderer renderer) {
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
