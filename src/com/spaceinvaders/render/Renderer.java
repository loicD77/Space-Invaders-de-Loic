package com.spaceinvaders.render;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    public void drawRect(float x, float y, float width, float height, float[] color) {
        glColor3f(color[0], color[1], color[2]);
        glBegin(GL_QUADS);
        glVertex2f(x - width / 2, y - height / 2);
        glVertex2f(x + width / 2, y - height / 2);
        glVertex2f(x + width / 2, y + height / 2);
        glVertex2f(x - width / 2, y + height / 2);
        glEnd();
    }

    public void drawText(String text, float x, float y, float size, float[] color) {
        glColor3f(color[0], color[1], color[2]);
        float cursorX = x;
        for (char c : text.toCharArray()) {
            drawRect(cursorX, y, size / 2, size, color);
            cursorX += size / 2 + 0.01f;
        }
    }

    public void drawGround(float y, float[] color) {
        glColor3f(color[0], color[1], color[2]);
        glBegin(GL_QUADS);
        glVertex2f(-1.0f, y);
        glVertex2f(1.0f, y);
        glVertex2f(1.0f, y + 0.05f);
        glVertex2f(-1.0f, y + 0.05f);
        glEnd();
    }
}
