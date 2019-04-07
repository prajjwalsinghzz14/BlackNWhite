package com.blacknwhite.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.blacknwhite.game.states.GameStateManager;
import com.blacknwhite.game.states.MenuState;

public class BlackNWhite extends ApplicationAdapter {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;

	public static final String TITLE = "Black N White";
	private GameStateManager gsm;
	public SpriteBatch batch;
	Texture img;
	private MenuState ms;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		//gsm.push(new MenuState(gsm));
		try {
			ms = new MenuState(gsm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		gsm.push(ms);
    }

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}

//	public boolean isDead(){
//		return true;
//	}
}
