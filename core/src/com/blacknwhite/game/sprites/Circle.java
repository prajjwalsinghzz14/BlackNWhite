package com.blacknwhite.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.blacknwhite.game.states.MenuState;
import com.blacknwhite.game.states.PlayState;

public class Circle {
    private Vector3 position;

    private Texture circle;
    private Sound move;
    private Sound notMove;
    private Sound reachHome;

    public Sound getReachHome() {
        return reachHome;
    }

    public Circle(int x, int y, String filepath){
        position = new Vector3(x, y, 0);
        circle = new Texture(filepath);
        move = Gdx.audio.newSound(Gdx.files.internal("misc_menu_2.wav"));
        notMove = Gdx.audio.newSound(Gdx.files.internal("negative_2.wav"));
        reachHome = Gdx.audio.newSound(Gdx.files.internal("positive.wav"));
    }

    public void update(float dt){
        if(position.y <0 )
            position.y = 0;
    }

    public void move(Vector3 newPos){
        this.position = newPos;
        move.play();
        System.out.println("This is move right and position is :" + this.position);
    }

    public void notMove(){
        notMove.play();
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return circle;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void dispose(){
        circle.dispose();
        move.dispose();
        notMove.dispose();
    }
}
