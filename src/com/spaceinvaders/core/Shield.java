package com.spaceinvaders.entities;

import com.spaceinvaders.render.Renderer;

public class Shield {
<<<<<<< HEAD
    private float x, y, width, height;
    private int[][] pixels; // Représente les pixels du bouclier (0 = détruit, 1 = intact)

    public Shield(float x, float y, float width, float height, int rows, int cols) {
=======
    private float x, y;
    private float width, height;
    private int health;

    public Shield(float x, float y, float width, float height, int health) {
>>>>>>> f6ecbdeb7f14403636951663fbdc6cba695fe0ed
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
<<<<<<< HEAD
        this.pixels = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.pixels[i][j] = 1; // Initialise tous les pixels comme intact
            }
        }
    }

    public boolean isFullyDestroyed() {
        for (int[] row : pixels) {
            for (int pixel : row) {
                if (pixel == 1) return false; // Si au moins un pixel est intact, le bouclier n'est pas détruit
            }
        }
        return true; // Tous les pixels sont détruits
    }

    public void render(Renderer renderer) {
        float pixelWidth = width / pixels[0].length;
        float pixelHeight = height / pixels.length;

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                if (pixels[i][j] == 1) {
                    float pixelX = x - width / 2 + j * pixelWidth + pixelWidth / 2;
                    float pixelY = y - height / 2 + i * pixelHeight + pixelHeight / 2;
                    renderer.drawRect(pixelX, pixelY, pixelWidth, pixelHeight, new float[]{0.0f, 1.0f, 0.0f});
                }
            }
        }
    }

    public boolean checkCollision(float bulletX, float bulletY) {
        float pixelWidth = width / pixels[0].length;
        float pixelHeight = height / pixels.length;

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                if (pixels[i][j] == 1) {
                    float pixelX = x - width / 2 + j * pixelWidth;
                    float pixelY = y - height / 2 + i * pixelHeight;

                    if (bulletX >= pixelX && bulletX <= pixelX + pixelWidth &&
                            bulletY >= pixelY && bulletY <= pixelY + pixelHeight) {
                        pixels[i][j] = 0; // Détruit le pixel touché
                        return true;
                    }
                }
            }
        }
        return false;
    }
}


=======
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
>>>>>>> f6ecbdeb7f14403636951663fbdc6cba695fe0ed
