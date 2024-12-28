package com.spaceinvaders.core;

import com.spaceinvaders.entities.Alien;
import com.spaceinvaders.entities.Bullet;
import com.spaceinvaders.render.Renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlienManager {
    private List<Alien> aliens;
    private List<Bullet> alienBullets; // Projectiles des aliens
    private boolean movingRight = true;
    private float speed = 0.2f;
    private float descentAmount = 0.05f;
    private boolean reachedEdge = false;
    private Random random;

    // Variables pour le cooldown des tirs
    private float alienShootCooldown = 2.0f; // Temps (en secondes) entre deux vagues de tirs
    private float alienShootTimer = 0.0f;    // Timer pour suivre le temps écoulé

    public AlienManager() {
        aliens = new ArrayList<>();
        alienBullets = new ArrayList<>();
        random = new Random();

        float[][] colors = {
                {1.0f, 0.0f, 0.0f}, // Rouge
                {0.0f, 1.0f, 0.0f}, // Vert
                {0.0f, 0.0f, 1.0f}  // Bleu
        };

        // Création des aliens en grille
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {
                aliens.add(new Alien(-0.8f + col * 0.3f, 0.6f - row * 0.2f, 0.1f, 0.1f, colors[row]));
            }
        }
    }

    public void update(float delta) {
        reachedEdge = false;

        // Vérifier si un alien atteint un bord
        for (Alien alien : aliens) {
            if ((movingRight && alien.getX() + alien.getWidth() >= 1.0f) ||
                    (!movingRight && alien.getX() <= -1.0f)) {
                reachedEdge = true;
                break;
            }
        }

        // Déplacer les aliens
        for (Alien alien : aliens) {
            if (reachedEdge) {
                alien.descend(descentAmount);
            }
            alien.update(delta, movingRight);
        }

        // Gestion du cooldown des tirs
        alienShootTimer -= delta;
        if (alienShootTimer <= 0) {
            spawnAlienProjectiles();
            alienShootTimer = alienShootCooldown; // Réinitialise le cooldown
        }

        // Mise à jour des projectiles des aliens
        for (Bullet bullet : new ArrayList<>(alienBullets)) {
            bullet.update(delta);

            // Supprimer les projectiles hors écran
            if (bullet.isOffScreen()) {
                alienBullets.remove(bullet);
            }
        }

        // Changer de direction si un bord est atteint
        if (reachedEdge) {
            movingRight = !movingRight;
        }
    }

    private void spawnAlienProjectiles() {
        // Sélectionner un alien aléatoire pour tirer
        if (!aliens.isEmpty()) {
            Alien shooter = aliens.get(random.nextInt(aliens.size()));
            alienBullets.add(new Bullet(shooter.getX(), shooter.getY() - shooter.getHeight() / 2, -0.8f, new float[]{1.0f, 1.0f, 1.0f}));
        }
    }

    public void render(Renderer renderer) {
        for (Alien alien : aliens) {
            alien.render(renderer);
        }
        for (Bullet bullet : alienBullets) {
            bullet.render(renderer);
        }
    }

    public List<Alien> getAliens() {
        return aliens;
    }

    public List<Bullet> getAlienBullets() {
        return alienBullets;
    }
}
