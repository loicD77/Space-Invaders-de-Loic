package com.spaceinvaders.render;

public class ScoreRenderer {

    public void drawScore(Renderer renderer, int score, float x, float y, float size, float[] color) {
        String scoreStr = String.valueOf(score);
        renderer.drawText(scoreStr, x, y, size, color);
    }
}
