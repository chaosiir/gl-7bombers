package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;


import java.awt.*;

public class EditeurNSolo extends Etat implements Screen {

    Bomberball game;
    Image back;
    Image floor;
    Image porte;
    Image murd;
    Image muri;
    Image selectionne;

    Label select;

    Map map;

    Skin skin;


    Table listeObjet;


    public EditeurNSolo(Bomberball game, Jeu jeu){
        super(jeu);
        this.game=game;
    }

    @Override
    public void show() {

        map=Map.genererMapSolo(20,10);




        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        back.setName("Je suis ton arrière plan");

        int ymax= Toolkit.getDefaultToolkit().getScreenSize().height;

        skin=new Skin(Gdx.files.internal("uiskin.json"));
        floor=new Image(Bomberball.multiTexture[0]);
        floor.setName("floor");
        floor.setBounds(0,ymax-Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        murd = new Image(Bomberball.multiTexture[1]);
        murd.setName("murd");
        murd.setBounds(0,ymax-2*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        muri= new Image(Bomberball.multiTexture[2]);
        muri.setName("muri");
        muri.setBounds(0,ymax-3*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);


        porte= new Image(Bomberball.multiTexture[3]);
        porte.setName("porte");
        porte.setBounds(0,ymax-4*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        selectionne= new Image();
        selectionne.setName("selection");
        selectionne.setBounds(0,ymax-6*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        select= new Label("Bloc selectionne:",skin);
        select.setPosition(0,ymax-5*Bomberball.taillecase);











        //Il faut faire un test pour voir si on n'a pas cliqué sur autre chose que la map. Si on a cliqué sur la map, on prend le parent et on le modifie



        jeu.addActor(back);
        jeu.addActor(floor);
        jeu.addActor(murd);
        jeu.addActor(muri);
        jeu.addActor(porte);
        jeu.addActor(select);
        jeu.addActor(selectionne);
        jeu.addActor(map);

        map.setName("Mappy");
        int i;
        int j;

        for (i = 0; i < map.tailleX(); i++) {
            for (j = 0; j < map.tailleY(); j++) {
                map.getGrille()[i][j].setName("Case"+i+j);
                map.getGrille()[i][j].setBounds(map.getGrille()[i][j].getX()+3*Bomberball.taillecase,map.getGrille()[i][j].getY(),Bomberball.taillecase,Bomberball.taillecase);
                map.addActor(map.getGrille()[i][j]);

            }

        }













    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)

        int i;
        int j;
        for (i = 0; i < map.tailleX(); i++) {
            for (j = 0; j < map.tailleY(); j++) {
                map.getGrille()[i][j].setName("Case"+i+j);
                map.getGrille()[i][j].setPosition( map.getGrille()[i][j].getX()+3*Bomberball.taillecase, map.getGrille()[i][j].getY());
                jeu.addActor( map.getGrille()[i][j]);


            }

        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Actor hitActor= jeu.getStage().hit(x,y,true); //Retourne référence de l'acteur touché
        //De base, hit fait un setbounds pour voir si l'acteur est dedans | On peut réécrire le hit (par exemple si on a un cercle)
        if(hitActor.getName()!=null){
            System.out.println("Je suis là:"+hitActor.getName());
            if (hitActor.getName().equals("murd")){
                selectionne.setDrawable(murd.getDrawable());
                selectionne.setName("murdes");
            }
            else if(hitActor.getName().equals("floor")){
                selectionne.setDrawable(floor.getDrawable());
                selectionne.setName("sol");
            }
            else if(hitActor.getName().equals("muri")){
                selectionne.setDrawable(muri.getDrawable());
                selectionne.setName("murin");
            }
            else if(hitActor.getName().equals("porte")){
                selectionne.setDrawable(porte.getDrawable());
                selectionne.setName("p");
            }

            else if(hitActor.getName().equals("Case00")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case00");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case00");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case00");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case00");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case01")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case01");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case01");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case01");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case01");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case02")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case02");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case02");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case02");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case02");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case03")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case03");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case03");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case03");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case03");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case04")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case04");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case04");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case04");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case04");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case05")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case05");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case05");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case05");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case05");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case06")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case06");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case06");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case06");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case06");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case07")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case07");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case07");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case07");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case07");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case08")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case08");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case08");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case08");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case08");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case09")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case09");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case09");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case09");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case09");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case010")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case010");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case010");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case010");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case010");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case011")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case011");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case011");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case011");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case011");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case012")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case012");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case012");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case012");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case012");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case10")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case10");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case10");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case10");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case10");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case11")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case11");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case11");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case11");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case11");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case12")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case12");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case12");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case12");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case12");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case13")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case13");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case13");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case13");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case13");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case14")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case14");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case14");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case14");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case14");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case15")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case15");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case15");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case15");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case15");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case16")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case16");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case16");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case16");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case16");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case17")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case17");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case17");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case17");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case17");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case18")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case18");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case18");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case18");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case18");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case19")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case19");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case19");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case19");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case19");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case110")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case110");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case110");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case110");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case110");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case111")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case111");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case111");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case111");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case111");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case112")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case112");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case112");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case112");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case112");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case20")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case20");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case20");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case20");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case20");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case21")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case21");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case21");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case21");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case21");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case22")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case22");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case22");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case22");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case22");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case23")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case23");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case23");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case23");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case23");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case24")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case24");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case24");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case24");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case24");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case25")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case25");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case25");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case25");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case25");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case26")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case26");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case26");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case26");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case26");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case27")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case27");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case27");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case27");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case27");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case28")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case28");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case28");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case28");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case28");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case29")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case29");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case29");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case29");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case29");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case210")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case210");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case210");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case210");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case210");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case211")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case211");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case211");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case211");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case211");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case212")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case212");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case212");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case212");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case212");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case30")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case30");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case30");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case30");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case30");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case31")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case31");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case31");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case31");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case31");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case32")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case32");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case32");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case32");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case32");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case33")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case33");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case33");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case33");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case33");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case34")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case34");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case34");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case34");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case34");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case35")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case35");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case35");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case35");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case35");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case36")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case36");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case36");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case36");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case36");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case37")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case37");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case37");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case37");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case37");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case38")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case38");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case38");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case38");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case38");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case39")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case39");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case39");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case39");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case39");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case310")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case310");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case310");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case310");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case310");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case311")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case311");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case311");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case311");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case311");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case312")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case312");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case312");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case312");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case312");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case40")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case40");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case40");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case40");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case40");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case41")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case41");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case41");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case41");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case41");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case42")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case42");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case42");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case42");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case42");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case43")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case43");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case43");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case43");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case43");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case44")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case44");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case44");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case44");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case44");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case45")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case45");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case45");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case45");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case45");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case46")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case46");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case46");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case46");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case46");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case47")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case47");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case47");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case47");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case47");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case48")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case48");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case48");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case48");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case48");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case49")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case49");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case49");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case49");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case49");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case410")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case410");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case410");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case410");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case410");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case411")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case411");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case411");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case411");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case411");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case412")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case412");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case412");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case412");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case412");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case50")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case50");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case50");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case50");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case50");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case51")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case51");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case51");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case51");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case51");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case52")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case52");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case52");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case52");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case52");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case53")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case53");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case53");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case53");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case53");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case54")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case54");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case54");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case54");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case54");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case55")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case55");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case55");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case55");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case55");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case56")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case56");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case56");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case56");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case56");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case57")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case57");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case57");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case57");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case57");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case58")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case58");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case58");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case58");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case58");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case59")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case59");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case59");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case59");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case59");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case510")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case510");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case510");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case510");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case510");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case511")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case511");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case511");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case511");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case511");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case512")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case512");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case512");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case512");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case512");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case60")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case60");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case60");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case60");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case60");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case61")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case61");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case61");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case61");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case61");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case62")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case62");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case62");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case62");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case62");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case63")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case63");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case63");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case63");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case63");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case64")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case64");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case64");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case64");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case64");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case65")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case65");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case65");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case65");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case65");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case66")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case66");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case66");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case66");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case66");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case67")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case67");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case67");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case67");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case67");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case68")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case68");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case68");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case68");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case68");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case69")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case69");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case69");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case69");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case69");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case610")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case610");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case610");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case610");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case610");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case611")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case611");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case611");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case611");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case611");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case612")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case612");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case612");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case612");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case612");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case70")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case70");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case70");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case70");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case70");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case71")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case71");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case71");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case71");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case71");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case72")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case72");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case72");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case72");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case72");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case73")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case73");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case73");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case73");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case73");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case74")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case74");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case74");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case74");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case74");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case75")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case75");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case75");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case75");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case75");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case76")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case76");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case76");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case76");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case76");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case77")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case77");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case77");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case77");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case77");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case78")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case78");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case78");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case78");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case78");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case79")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case79");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case79");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case79");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case79");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case710")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case710");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case710");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case710");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case710");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case711")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case711");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case711");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case711");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case711");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case712")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case712");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case712");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case712");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case712");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case80")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case80");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case80");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case80");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case80");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case81")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case81");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case81");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case81");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case81");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case82")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case82");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case82");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case82");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case82");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case83")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case83");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case83");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case83");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case83");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case84")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case84");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case84");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case84");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case84");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case85")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case85");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case85");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case85");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case85");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case86")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case86");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case86");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case86");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case86");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case87")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case87");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case87");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case87");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case87");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case88")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case88");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case88");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case88");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case88");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case89")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case89");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case89");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case89");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case89");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case810")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case810");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case810");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case810");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case810");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case811")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case811");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case811");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case811");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case811");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case812")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case812");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case812");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case812");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case812");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case90")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case90");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case90");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case90");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case90");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case91")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case91");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case91");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case91");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case91");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case92")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case92");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case92");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case92");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case92");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case93")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case93");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case93");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case93");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case93");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case94")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case94");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case94");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case94");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case94");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case95")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case95");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case95");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case95");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case95");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case96")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case96");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case96");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case96");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case96");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case97")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case97");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case97");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case97");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case97");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case98")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case98");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case98");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case98");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case98");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case99")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case99");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case99");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case99");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case99");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case910")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case910");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case910");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case910");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case910");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case911")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case911");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case911");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case911");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case911");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case912")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case912");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case912");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case912");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case912");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case100")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case100");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case100");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case100");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case100");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case101")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case101");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case101");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case101");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case101");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case102")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case102");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case102");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case102");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case102");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case103")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case103");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case103");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case103");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case103");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case104")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case104");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case104");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case104");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case104");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case105")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case105");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case105");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case105");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case105");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case106")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case106");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case106");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case106");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case106");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case107")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case107");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case107");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case107");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case107");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case108")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case108");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case108");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case108");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case108");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case109")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case109");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case109");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case109");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case109");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case1010")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case1010");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case1010");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case1010");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case1010");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case1011")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case1011");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case1011");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case1011");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case1011");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case1012")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case1012");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case1012");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case1012");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case1012");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case110")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case110");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case110");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case110");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case110");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case111")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case111");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case111");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case111");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case111");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case112")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case112");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case112");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case112");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case112");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case113")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case113");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case113");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case113");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case113");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case114")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case114");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case114");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case114");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case114");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case115")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case115");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case115");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case115");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case115");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case116")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case116");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case116");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case116");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case116");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case117")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case117");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case117");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case117");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case117");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case118")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case118");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case118");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case118");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case118");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case119")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case119");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case119");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case119");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case119");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case1110")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case1110");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case1110");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case1110");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case1110");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case1111")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case1111");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case1111");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case1111");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case1111");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case1112")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case1112");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case1112");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case1112");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case1112");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case120")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case120");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case120");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case120");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case120");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case121")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case121");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case121");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case121");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case121");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case122")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case122");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case122");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case122");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case122");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case123")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case123");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case123");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case123");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case123");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case124")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case124");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case124");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case124");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case124");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case125")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case125");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case125");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case125");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case125");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case126")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case126");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case126");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case126");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case126");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case127")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case127");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case127");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case127");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case127");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case128")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case128");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case128");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case128");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case128");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case129")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case129");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case129");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case129");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case129");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case1210")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case1210");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case1210");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case1210");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case1210");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case1211")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case1211");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case1211");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case1211");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case1211");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case1212")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case1212");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case1212");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case1212");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case1212");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case130")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case130");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case130");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case130");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case130");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case131")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case131");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case131");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case131");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case131");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case132")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case132");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case132");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case132");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case132");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case133")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case133");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case133");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case133");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case133");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case134")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case134");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case134");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case134");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case134");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case135")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case135");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case135");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case135");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case135");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case136")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case136");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case136");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case136");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case136");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case137")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case137");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case137");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case137");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case137");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case138")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case138");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case138");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case138");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case138");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case139")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case139");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case139");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case139");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case139");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case1310")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case1310");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case1310");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case1310");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case1310");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case1311")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case1311");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case1311");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case1311");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case1311");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case1312")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case1312");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case1312");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case1312");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case1312");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case140")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case140");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case140");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case140");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case140");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case141")){
                if(selectionne.getDrawable()!=null){
                    if(button==Input.Buttons.LEFT){
                        if(selectionne.getName().equals("murdes")){
                            Case c=jeu.findActor("Case141");
                            c.setMur(new MurD());
                        }else if(selectionne.getName().equals("murin")){
                            Case c=jeu.findActor("Case141");
                            c.setMur(new MurI());
                        }
                        else if(selectionne.getName().equals("p")){
                            Case c=jeu.findActor("Case141");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button==Input.Buttons.RIGHT){
                    Case c=jeu.findActor("Case141");
                    c.setMur(null);
                    c.setPorte(null);
                }
            }
            else if(hitActor.getName().equals("Case142")) {
                if (selectionne.getDrawable() != null) {
                    if (button == Input.Buttons.LEFT) {
                        if (selectionne.getName().equals("murdes")) {
                            Case c = jeu.findActor("Case142");
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("murin")) {
                            Case c = jeu.findActor("Case142");
                            c.setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            Case c = jeu.findActor("Case142");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button == Input.Buttons.RIGHT) {
                    Case c = jeu.findActor("Case142");
                    c.setMur(null);
                    c.setMur(null);
                }
            }
            else if(hitActor.getName().equals("Case143")) {
                if (selectionne.getDrawable() != null) {
                    if (button == Input.Buttons.LEFT) {
                        if (selectionne.getName().equals("murdes")) {
                            Case c = jeu.findActor("Case143");
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("murin")) {
                            Case c = jeu.findActor("Case143");
                            c.setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            Case c = jeu.findActor("Case143");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button == Input.Buttons.RIGHT) {
                    Case c = jeu.findActor("Case143");
                    c.setMur(null);
                    c.setMur(null);
                }
            }
            else if(hitActor.getName().equals("Case144")) {
                if (selectionne.getDrawable() != null) {
                    if (button == Input.Buttons.LEFT) {
                        if (selectionne.getName().equals("murdes")) {
                            Case c = jeu.findActor("Case144");
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("murin")) {
                            Case c = jeu.findActor("Case144");
                            c.setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            Case c = jeu.findActor("Case144");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button == Input.Buttons.RIGHT) {
                    Case c = jeu.findActor("Case144");
                    c.setMur(null);
                    c.setMur(null);
                }
            }
            else if(hitActor.getName().equals("Case145")) {
                if (selectionne.getDrawable() != null) {
                    if (button == Input.Buttons.LEFT) {
                        if (selectionne.getName().equals("murdes")) {
                            Case c = jeu.findActor("Case145");
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("murin")) {
                            Case c = jeu.findActor("Case145");
                            c.setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            Case c = jeu.findActor("Case145");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button == Input.Buttons.RIGHT) {
                    Case c = jeu.findActor("Case145");
                    c.setMur(null);
                    c.setMur(null);
                }
            }
            else if(hitActor.getName().equals("Case146")) {
                if (selectionne.getDrawable() != null) {
                    if (button == Input.Buttons.LEFT) {
                        if (selectionne.getName().equals("murdes")) {
                            Case c = jeu.findActor("Case146");
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("murin")) {
                            Case c = jeu.findActor("Case146");
                            c.setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            Case c = jeu.findActor("Case146");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button == Input.Buttons.RIGHT) {
                    Case c = jeu.findActor("Case146");
                    c.setMur(null);
                    c.setMur(null);
                }
            }
            else if(hitActor.getName().equals("Case147")) {
                if (selectionne.getDrawable() != null) {
                    if (button == Input.Buttons.LEFT) {
                        if (selectionne.getName().equals("murdes")) {
                            Case c = jeu.findActor("Case147");
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("murin")) {
                            Case c = jeu.findActor("Case147");
                            c.setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            Case c = jeu.findActor("Case147");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button == Input.Buttons.RIGHT) {
                    Case c = jeu.findActor("Case147");
                    c.setMur(null);
                    c.setMur(null);
                }
            }
            else if(hitActor.getName().equals("Case148")) {
                if (selectionne.getDrawable() != null) {
                    if (button == Input.Buttons.LEFT) {
                        if (selectionne.getName().equals("murdes")) {
                            Case c = jeu.findActor("Case148");
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("murin")) {
                            Case c = jeu.findActor("Case148");
                            c.setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            Case c = jeu.findActor("Case148");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button == Input.Buttons.RIGHT) {
                    Case c = jeu.findActor("Case148");
                    c.setMur(null);
                    c.setMur(null);
                }
            }
            else if(hitActor.getName().equals("Case149")) {
                if (selectionne.getDrawable() != null) {
                    if (button == Input.Buttons.LEFT) {
                        if (selectionne.getName().equals("murdes")) {
                            Case c = jeu.findActor("Case149");
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("murin")) {
                            Case c = jeu.findActor("Case149");
                            c.setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            Case c = jeu.findActor("Case149");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button == Input.Buttons.RIGHT) {
                    Case c = jeu.findActor("Case149");
                    c.setMur(null);
                    c.setMur(null);
                }
            }
            else if(hitActor.getName().equals("Case1410")) {
                if (selectionne.getDrawable() != null) {
                    if (button == Input.Buttons.LEFT) {
                        if (selectionne.getName().equals("murdes")) {
                            Case c = jeu.findActor("Case1410");
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("murin")) {
                            Case c = jeu.findActor("Case1410");
                            c.setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            Case c = jeu.findActor("Case1410");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button == Input.Buttons.RIGHT) {
                    Case c = jeu.findActor("Case1410");
                    c.setMur(null);
                    c.setMur(null);
                }
            }
            else if(hitActor.getName().equals("Case1411")) {
                if (selectionne.getDrawable() != null) {
                    if (button == Input.Buttons.LEFT) {
                        if (selectionne.getName().equals("murdes")) {
                            Case c = jeu.findActor("Case1411");
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("murin")) {
                            Case c = jeu.findActor("Case1411");
                            c.setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            Case c = jeu.findActor("Case1411");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button == Input.Buttons.RIGHT) {
                    Case c = jeu.findActor("Case1411");
                    c.setMur(null);
                    c.setMur(null);
                }
            }
            else if(hitActor.getName().equals("Case1412")) {
                if (selectionne.getDrawable() != null) {
                    if (button == Input.Buttons.LEFT) {
                        if (selectionne.getName().equals("murdes")) {
                            Case c = jeu.findActor("Case1412");
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("murin")) {
                            Case c = jeu.findActor("Case1412");
                            c.setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            Case c = jeu.findActor("Case1412");
                            c.setPorte(new Porte());
                        }
                    }
                }
                if (button == Input.Buttons.RIGHT) {
                    Case c = jeu.findActor("Case1412");
                    c.setMur(null);
                    c.setMur(null);
                }
            }
            Gdx.app.log("HIT",hitActor.getName()); //print dans le log de l'application
        }
        else {
            System.out.println("hit " +" "+hitActor.getClass().toString());
        }



        return true;

    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        return false;
    }
}
