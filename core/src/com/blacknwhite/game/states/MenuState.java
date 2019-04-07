package com.blacknwhite.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blacknwhite.game.BlackNWhite;


public class MenuState extends State {
    private Texture background;
    private Texture playBtn;
    public MenuState(GameStateManager gsm) throws Exception {
        super(gsm);
        background = new Texture("background.jpg");
        playBtn = new Texture("playBtn.png");

    }


    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0, BlackNWhite.WIDTH, BlackNWhite.HEIGHT);
        sb.draw(playBtn, (BlackNWhite.WIDTH / 2) - (playBtn.getWidth() / 2), BlackNWhite.HEIGHT/2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
}
