package com.spaceinvaders.render;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    // Dessiner un rectangle avec des couleurs
    public void drawRect(float x, float y, float width, float height, float[] color) {
        glColor3f(color[0], color[1], color[2]); // Appliquer la couleur RGB

        glBegin(GL_QUADS);
        glVertex2f(x - width / 2, y - height / 2); // Coin bas-gauche
        glVertex2f(x + width / 2, y - height / 2); // Coin bas-droite
        glVertex2f(x + width / 2, y + height / 2); // Coin haut-droite
        glVertex2f(x - width / 2, y + height / 2); // Coin haut-gauche
        glEnd();
    }

    // Dessiner du texte simulé (placeholder)
    public void drawText(String text, float x, float y, float size, float[] color) {
        glColor3f(color[0], color[1], color[2]); // Applique la couleur du texte
        float cursorX = x;

        for (char c : text.toCharArray()) {
            drawRect(cursorX, y, size / 2, size, color);
            cursorX += size / 2 + 0.01f; // Ajoute un espacement
        }
    }

    // Dessiner un bouclier
    public void drawShield(float x, float y, float width, float height, int durability) {
        float[] color;
        if (durability > 2) color = new float[]{0.0f, 1.0f, 0.0f}; // Vert
        else if (durability > 1) color = new float[]{1.0f, 1.0f, 0.0f}; // Jaune
        else color = new float[]{1.0f, 0.0f, 0.0f}; // Rouge

        drawRect(x, y, width, height, color);
    }

    // Dessiner un projectile
    public void drawProjectile(float x, float y, float width, float height, float[] color) {
        glColor3f(color[0], color[1], color[2]); // Couleur du projectile
        glBegin(GL_QUADS);
        glVertex2f(x - width / 2, y - height / 2); // Coin bas-gauche
        glVertex2f(x + width / 2, y - height / 2); // Coin bas-droite
        glVertex2f(x + width / 2, y + height / 2); // Coin haut-droite
        glVertex2f(x - width / 2, y + height / 2); // Coin haut-gauche
        glEnd();
    }

    // Dessiner une bordure (par exemple pour les limites du jeu)
    public void drawGround(float y, float[] color) {
        glColor3f(color[0], color[1], color[2]); // Couleur du sol

        glBegin(GL_QUADS);
        glVertex2f(-1.0f, y);           // Coin bas-gauche
        glVertex2f(1.0f, y);            // Coin bas-droite
        glVertex2f(1.0f, y + 0.05f);    // Coin haut-droite (épaisseur)
        glVertex2f(-1.0f, y + 0.05f);   // Coin haut-gauche (épaisseur)
        glEnd();
    }
}
