package com.spaceinvaders.core;

import com.spaceinvaders.entities.Alien;
import com.spaceinvaders.entities.Bullet;
import com.spaceinvaders.entities.Player;
import com.spaceinvaders.render.Renderer;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Player player;
    private AlienManager alienManager;
    private List<Bullet> playerBullets;
    private List<Shield> shields; // Liste des boucliers
    private boolean moveLeft, moveRight, shooting;
    private int score = 0;
    private int lives = 3;
    private boolean isGameOver = false;

    public Game() {
        player = new Player(0.0f, -0.8f, 0.1f, 0.1f, new float[]{0.0f, 0.0f, 1.0f});
        alienManager = new AlienManager();
        playerBullets = new ArrayList<>();
        shields = new ArrayList<>();

        // Initialisation des boucliers
        shields.add(new Shield(-0.6f, -0.6f, 0.2f, 0.1f, 3));
        shields.add(new Shield(0.0f, -0.6f, 0.2f, 0.1f, 3));
        shields.add(new Shield(0.6f, -0.6f, 0.2f, 0.1f, 3));
    }

    public void update(float delta) {
        if (isGameOver) return;

        // Mise à jour du joueur
        player.update(moveLeft, moveRight, delta);

        // Gestion des tirs du joueur
        if (shooting) {
            float bulletX = player.getX();
            float bulletY = player.getY() + player.getHeight() / 2;
            playerBullets.add(new Bullet(bulletX, bulletY, 0.5f, new float[]{1.0f, 1.0f, 1.0f}));
            shooting = false;
        }

        // Mise à jour des aliens
        alienManager.update(delta);

        // Mise à jour des projectiles du joueur
        for (Bullet bullet : new ArrayList<>(playerBullets)) {
            bullet.update(delta);

            // Supprimer les projectiles hors écran
            if (bullet.isOffScreen()) {
                playerBullets.remove(bullet);
            } else {
                // Gérer les collisions avec les boucliers
                for (Shield shield : shields) {
                    if (shield.checkCollision(bullet.getX(), bullet.getY())) {
                        shield.takeDamage();
                        playerBullets.remove(bullet);
                        break;
                    }
                }

                // Gérer les collisions avec les aliens
                for (Alien alien : new ArrayList<>(alienManager.getAliens())) {
                    if (bullet.collidesWith(alien)) {
                        playerBullets.remove(bullet);
                        alienManager.getAliens().remove(alien);
                        score += 10; // Ajout de points au score
                        break;
                    }
                }
            }
        }

        // Gérer les collisions des projectiles des aliens avec le joueur ou les boucliers
        for (Bullet alienBullet : new ArrayList<>(alienManager.getAlienBullets())) {
            if (alienBullet.collidesWith(player)) {
                alienManager.getAlienBullets().remove(alienBullet);
                lives--;
                if (lives <= 0) {
                    isGameOver = true;
                }
                break;
            } else {
                // Gérer les collisions des projectiles aliens avec les boucliers
                for (Shield shield : shields) {
                    if (shield.checkCollision(alienBullet.getX(), alienBullet.getY())) {
                        shield.takeDamage();
                        alienManager.getAlienBullets().remove(alienBullet);
                        break;
                    }
                }
            }
        }

        // Supprimer les boucliers détruits
        shields.removeIf(Shield::isDestroyed);
    }

    public void render(Renderer renderer) {
        if (isGameOver) {
            renderer.drawText("GAME OVER", -0.4f, 0.0f, 0.1f, new float[]{1.0f, 0.0f, 0.0f});
            renderer.drawText("SCORE: " + score, -0.3f, -0.2f, 0.05f, new float[]{1.0f, 1.0f, 1.0f});
            return;
        }

        // Rendu du sol
        renderer.drawGround(-0.95f, new float[]{0.0f, 1.0f, 0.0f}); // Sol vert

        // Rendu du joueur
        player.render(renderer);

        // Rendu des aliens
        alienManager.render(renderer);

        // Rendu des projectiles du joueur
        for (Bullet bullet : playerBullets) {
            bullet.render(renderer);
        }

        // Rendu des boucliers
        for (Shield shield : shields) {
            shield.render(renderer);
        }

        // Affichage du score et des vies
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
