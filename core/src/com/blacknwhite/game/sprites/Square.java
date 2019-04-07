package com.blacknwhite.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class Square {
    private Vector3 position;
    private Texture square;
    private String filepath;

    public Square(String filepath) {
        this.filepath = filepath;
        square = new Texture(this.filepath);
        position = new Vector3(0,0,0);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return square;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setTexture(Texture square) {
        this.square = square;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
