package com.blacknwhite.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;


public class Level {
    public static int nrows = 0;
    public static int  ncols = 0;
    public static String filepath;

    public Level(String filePath){
        this.filepath = filePath;
    }

    public static int getNrows() {
        return nrows;
    }

    public static int getNcols() {
        return ncols;
    }

    public void update(float dt){

    }

    public static String charRemoveAt(String str, int p) {
        return str.substring(0, p) + str.substring(p + 1);
    }

    public void randomLevel(){
        int min =0, max= 10;
        Random r= new Random();
        nrows = r.nextInt((max-min)+1) + min;
        System.out.println(nrows);
        ncols = r.nextInt((max-min)+1) + min;
        System.out.println(ncols);
        String str = new String();
        String characters = "wb01pk";
        int temp = 0;
        for(int i = 0; i<nrows;i++){
            for(int j=0; j<ncols;j++){
                temp = r.nextInt(characters.length()-1);
                char newChar = characters.charAt(temp);
                str+=newChar;
                if(newChar == 'b' || newChar =='w' || newChar == 'p' || newChar == 'k'){
                    characters = charRemoveAt(characters, temp);
                }
                System.out.println("str is : " + str);
            }
            str+="\r\n";
        }
        try{
            FileWriter fw=new FileWriter(filepath);
            System.out.println("str is: \n" + str);
            fw.write(str);
            fw.close();
        }catch(Exception e){System.out.println(e);}
        System.out.println("Success...");
    }

    public String[] createImageFile(){
        randomLevel();
        FileHandle file = Gdx.files.internal("level1.txt");
        String text = file.readString();
        int j = 0;
        int fr_count = 0;
        for(int k =0; k<text.length(); k++) {
            fr_count++;
        }
        String[] bImg = new String[fr_count];
        for(int k =0; k<text.length(); k++){
            char i = text.charAt(k);
            switch(i){
                case 'b':
                    //System.out.println("b");
                    bImg[j] = "b.png";
                    j++;
                    break;
                case 'w':
                    //System.out.println("w");
                    bImg[j] = "w.png";
                    j++;
                    break;
                case '1':
                    //System.out.println("1");
                    bImg[j] = "1.png";
                    j++;
                    break;
                case '0':
                    //System.out.println("0");
                    bImg[j] = "0.png";
                    j++;
                    break;
                case 'p':
                    //System.out.println("p");
                    bImg[j] = "p.png";
                    j++;
                    break;
                case 'k':
                    //System.out.println("k");
                    bImg[j] = "k.png";
                    j++;
                    break;
                case '\n':
                    //System.out.println("NL");
                    bImg[j] = null;
                    j++;
                    break;
                default:
                    bImg[j] = null;
                    break;

            }
        }
        return bImg;
    }
}
