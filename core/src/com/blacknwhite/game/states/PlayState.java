package com.blacknwhite.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.blacknwhite.game.BlackNWhite;
import com.blacknwhite.game.sprites.Circle;
import com.blacknwhite.game.sprites.Level;
import com.blacknwhite.game.sprites.Square;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static com.badlogic.gdx.math.Interpolation.circle;
import static java.lang.Thread.sleep;

public class PlayState extends State {
    private Circle blackCircle;
    private Circle whiteCircle;
    private Level level;
    private String[] imgPaths = new String[100];
    private Square[] squares;
    public static int bImgWidth =0, bImgHeight =0;
    private int blackHome = 0;
    private int whiteHome = 0;
    private int blackLock =0;
    private int whiteLock = 0;
    private int nrows = 0;
    private int ncols = 0;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        blackCircle = new Circle(0,0,"Bcircle.png");
        whiteCircle = new Circle(50, 50, "Wcircle.png");
        level = new Level("level1.txt");
        try {
            imgPaths = level.createImageFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        nrows = level.getNrows();
        ncols = level.getNcols();
        squares = new Square[100];
        for(int i = 0; i<imgPaths.length; i++){
            if(imgPaths[i]!=null){
                squares[i] = new Square(imgPaths[i]);
            }
            else{
                squares[i] = null;
            }
        }
        cam.setToOrtho(false, BlackNWhite.WIDTH , BlackNWhite.HEIGHT );
    }

    public int getSquare(Vector3 pos){
        int index = 0;
        System.out.println("pos IS:"+pos);
        for(int i=0; i<squares.length; i++){
            if(squares[i]!=null){
                System.out.println("squares[i] get position is: "+ squares[i].getPosition());
                if(squares[i].getPosition().equals(pos) ){
                    index = i;
                    break;
                }
            }

        }
        return index;
    }

    public int blackMovePossible(int index){
        if(squares[index] != null){
            if(squares[index].getFilepath() == "0.png" || squares[index].getFilepath() == "p.png"){
                return 1;
            }
        }
        return 0;
    }

    public int whiteMovePossible(int index){
        if(squares[index] != null){
            if(squares[index].getFilepath() == "1.png" || squares[index].getFilepath() == "k.png"){
                return 1;
            }
        }
        return 0;
    }

    public int noPossibeMove(Vector3 circle){
        int count = 0;
        int index = 0;
        Vector3 nextPos = circle;
        nextPos.y += bImgHeight;
        index = getSquare(nextPos);
        if(circle == blackCircle.getPosition())
            count += blackMovePossible(index);
        else if(circle == whiteCircle.getPosition())
            count += whiteMovePossible(index);

        nextPos = circle;
        nextPos.y -= bImgHeight;
        index = getSquare(nextPos);
        if(circle == blackCircle.getPosition())
            count += blackMovePossible(index);
        else if(circle == whiteCircle.getPosition())
            count += whiteMovePossible(index);

        nextPos = circle;
        nextPos.x += bImgWidth;
        index = getSquare(nextPos);
        if(circle == blackCircle.getPosition())
            count += blackMovePossible(index);
        else if(circle == whiteCircle.getPosition())
            count += whiteMovePossible(index);

        nextPos = circle;
        nextPos.x -= bImgWidth;
        index = getSquare(nextPos);
        if(circle == blackCircle.getPosition())
            count += blackMovePossible(index);
        else if(circle == whiteCircle.getPosition())
            count += whiteMovePossible(index);

        if(count>0)
            return 0;
        else
            return 1;
    }

    public boolean gameOver(){
        if(blackHome == 1 && whiteHome ==1){
            return true;
        }
        else if(blackLock == 1 && whiteHome == 1){
            try {
                sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        else if(blackHome == 1 && whiteLock == 1){
            try {
                sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        else if(blackLock == 1 && whiteLock == 1){
            try {
                sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    protected void handleInput() {
        Vector3 blackPos = blackCircle.getPosition();
        Vector3 whitePos= whiteCircle.getPosition();
        System.out.println("Black circle position is: " + blackPos);
        System.out.println("White Circle position is: " + whitePos);
        Vector3 nextBlackPos = blackPos;
        Vector3 nextWhitePos = whitePos;
        int index = 0;
        blackLock = noPossibeMove(blackPos);
        whiteLock = noPossibeMove(whitePos);
        if(gameOver()){
            try {
                gsm.set(new MenuState(gsm));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(blackHome == 0){
            System.out.println("in blackhome == 0");
            if(Gdx.input.isKeyPressed(Input.Keys.W)){
                nextBlackPos.y +=bImgHeight;
                index = getSquare(nextBlackPos);
                if(squares[index].getFilepath() == "0.png"){
                    if(!whiteCircle.getPosition().equals(nextBlackPos)){
                        blackCircle.move(nextBlackPos);
                        squares[index].setTexture(new Texture("1.png"));
                        squares[index].setFilepath("1.png");
                    }
                    else{
                        blackCircle.notMove();
                        nextBlackPos.y -= bImgHeight;
                    }

                }
                else if(squares[index].getFilepath() == "p.png"){
                    blackCircle.getReachHome().play();
                    blackCircle.move(nextBlackPos);
                    blackHome =1;
                }
                else{
                    blackCircle.notMove();
                    nextBlackPos.y -=bImgHeight;
                }
            }
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                nextBlackPos.x -=bImgWidth;
                index = getSquare(nextBlackPos);
                if(squares[index].getFilepath() == "0.png"){
                    if(!whiteCircle.getPosition().equals(nextBlackPos)){
                        blackCircle.move(nextBlackPos);
                        squares[index].setTexture(new Texture("1.png"));
                        squares[index].setFilepath("1.png");
                    }
                    else{
                        blackCircle.notMove();
                        nextBlackPos.x +=bImgWidth;
                    }
                }
                else if(squares[index].getFilepath() == "p.png"){
                    blackCircle.getReachHome().play();
                    blackCircle.move(nextBlackPos);
                    blackHome =1;
                }
                else{
                    blackCircle.notMove();
                    nextBlackPos.x +=bImgWidth;
                }
            }
            if(Gdx.input.isKeyPressed(Input.Keys.S)){
                nextBlackPos.y -= bImgHeight;       // check here
                index = getSquare(nextBlackPos);
                if(squares[index].getFilepath() == "0.png"){
                    if(!whiteCircle.getPosition().equals(nextBlackPos)){
                        blackCircle.move(nextBlackPos);
                        squares[index].setTexture(new Texture("1.png"));
                        squares[index].setFilepath("1.png");
                    }
                    else{
                        blackCircle.notMove();
                        nextBlackPos.y += bImgHeight;
                    }
                }
                else if(squares[index].getFilepath() == "p.png"){
                    blackCircle.getReachHome().play();
                    blackCircle.move(nextBlackPos);
                    blackHome =1;
                }
                else{
                    blackCircle.notMove();
                    nextBlackPos.y += bImgHeight;
                }
            }
            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                nextBlackPos.x += bImgWidth;
                index = getSquare(nextBlackPos);
                if(squares[index].getFilepath() == "0.png"){
                    if(!whiteCircle.getPosition().equals(nextBlackPos)){
                        blackCircle.move(nextBlackPos);
                        squares[index].setTexture(new Texture("1.png"));
                        squares[index].setFilepath("1.png");
                    }
                    else{
                        blackCircle.notMove();
                        nextBlackPos.x -= bImgWidth;

                    }
                }
                else if(squares[index].getFilepath() == "p.png"){
                    blackCircle.getReachHome().play();
                    blackCircle.move(nextBlackPos);
                    blackHome =1;
                }
                else{
                    blackCircle.notMove();
                    nextBlackPos.x -= bImgWidth;
                }
            }
        }
        if(whiteHome == 0){
            System.out.println("in whitehome == 0");
            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                nextWhitePos.y +=bImgHeight;
                index = getSquare(nextWhitePos);
                if(squares[index].getFilepath() == "1.png"){
                    if(!blackCircle.getPosition().equals(nextWhitePos)){
                        whiteCircle.move(nextWhitePos);
                        squares[index].setTexture(new Texture("0.png"));
                        squares[index].setFilepath("0.png");
                    }
                    else{
                        whiteCircle.notMove();
                        nextWhitePos.y -=bImgHeight;
                    }
                }
                else if(squares[index].getFilepath() == "k.png"){
                    whiteCircle.getReachHome().play();
                    whiteCircle.move(nextWhitePos);
                    whiteHome =1;
                }
                else{
                    whiteCircle.notMove();
                    nextWhitePos.y -= bImgHeight;
                }
            }
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                nextWhitePos.x -=bImgWidth;
                index = getSquare(nextWhitePos);
                if(squares[index].getFilepath() == "1.png"){
                    if(!blackCircle.getPosition().equals(nextWhitePos)){
                        whiteCircle.move(nextWhitePos);
                        squares[index].setTexture(new Texture("0.png"));
                        squares[index].setFilepath("0.png");
                    }
                    else{
                        whiteCircle.notMove();
                        nextWhitePos.x +=bImgWidth;
                    }

                }
                else if(squares[index].getFilepath() == "k.png"){
                    whiteCircle.getReachHome().play();
                    whiteCircle.move(nextWhitePos);
                    whiteHome =1;
                }
                else{
                    whiteCircle.notMove();
                    nextWhitePos.x +=bImgWidth;
                }
            }
            if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                nextWhitePos.y -=bImgHeight;
                index = getSquare(nextWhitePos);
                if(squares[index].getFilepath() == "1.png"){
                    if(!blackCircle.getPosition().equals(nextWhitePos)){
                        whiteCircle.move(nextWhitePos);
                        squares[index].setTexture(new Texture("0.png"));
                        squares[index].setFilepath("0.png");
                    }
                    else{
                        whiteCircle.notMove();
                        nextWhitePos.y += bImgHeight;
                    }
                }
                else if(squares[index].getFilepath() == "k.png"){
                    whiteCircle.getReachHome().play();
                    whiteCircle.move(nextWhitePos);
                    whiteHome =1;
                }
                else{
                    whiteCircle.notMove();
                    nextWhitePos.y +=bImgHeight;
                }
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                System.out.println("white to move right");
                nextWhitePos.x +=bImgWidth;
                System.out.println("next white position is: "+ nextWhitePos);
                index = getSquare(nextWhitePos);
                System.out.println("this is index:" + index);
                if(squares[index].getFilepath() == "1.png"){
                    if(!blackCircle.getPosition().equals(nextWhitePos)){
                        System.out.println("white can move right to black box at position:" + nextWhitePos);
                        whiteCircle.move(nextWhitePos);
                        squares[index].setTexture(new Texture("0.png"));
                        squares[index].setFilepath("0.png");
                    }
                    else{
                        whiteCircle.notMove();
                        nextWhitePos.x -= bImgWidth;
                    }
                }
                else if(squares[index].getFilepath() == "k.png"){
                    whiteCircle.move(nextWhitePos);
                    whiteCircle.getReachHome().play();
                    whiteHome =1;
                }
                else{
                    whiteCircle.notMove();
                    nextWhitePos.x -=bImgWidth;
                }
            }
        }
        Gdx.graphics.setContinuousRendering(false);
        dispose();
    }

    @Override
    public void update(float dt) {
        handleInput();
        blackCircle.update(dt);
        whiteCircle.update(dt);
        level.update(dt);
        System.out.println("THE VALUE OF dt IS: "+ dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        //int width = BlackNWhite.WIDTH - ((BlackNWhite.WIDTH)/2) - ((ncols*bImgWidth)/2);
        //int height = ((BlackNWhite.HEIGHT)/2) + ((nrows*bImgHeight)/2);
        int width=200, height = 600;
        BufferedImage bImg;
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        for (int i=0;i < squares.length;i++){
            if(squares[i] != null) {

                if(squares[i].getFilepath() == "b.png"){
                    squares[i] = new Square("BGtile.png");
                    blackCircle.setPosition(new Vector3(width, height,0));
                    continue;
                }
                if(squares[i].getFilepath() == "w.png"){
                    squares[i] = new Square("WGtile.png");
                    whiteCircle.setPosition(new Vector3(width, height,0));
                    continue;
                }
                squares[i].setPosition(new Vector3(width, height, 0));
                sb.draw(squares[i].getTexture(), width, height);
                bImgWidth = squares[0].getTexture().getWidth();
                width += bImgWidth;
                bImgHeight = squares[0].getTexture().getHeight();
                }
            else{
                height -= bImgHeight;
                width = 200;
                //width = BlackNWhite.WIDTH - ((BlackNWhite.WIDTH)/2) - ((ncols*bImgWidth)/2);
            }
        }
        sb.draw(blackCircle.getTexture(), blackCircle.getPosition().x, blackCircle.getPosition().y);
        sb.draw(whiteCircle.getTexture(), whiteCircle.getPosition().x, whiteCircle.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
