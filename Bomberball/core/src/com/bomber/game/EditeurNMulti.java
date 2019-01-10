package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.bomber.game.Bomberball.stg;

public class EditeurNMulti extends Etat implements Screen {
    Bomberball game;
    Image back;
    Image floor;
    Image perso;
    Image murd;
    Image muri;
    Image selectionne;

    Label select;
    Label instruction1;
    Label instruction2;

    TextButton retour;
    TextButton valider;
    TextButton charger;

    Map map;

    Skin skin;

    File f;
    FileWriter fw;

    public EditeurNMulti(Bomberball game,Jeu jeu) {
        super(jeu);
        this.game=game;
        File directory = new File (".");
        try {
            f = new File(directory.getCanonicalPath() + "/SaveMapPerso/MapMulti/tmp.txt");

        } catch (IOException e) {

        }
    }

    @Override
    public void show() {
        if(f.exists()){
            String text=Bomberball.loadFile(f);
            map=Map.mapFromString(text);
        }
        else{
            map=Map.generatePvp(20);

        }
        map.setPosition(7*Bomberball.taillecase,0);




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

        perso= new Image(Bomberball.multiTexture[4]);
        perso.setName("perso");
        perso.setBounds(Bomberball.taillecase,ymax-Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);



        selectionne= new Image();
        selectionne.setName("selection");
        selectionne.setBounds(0,ymax-6*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        select= new com.badlogic.gdx.scenes.scene2d.ui.Label("Bloc selectionne:",skin);
        select.setBounds(0,ymax-5*Bomberball.taillecase,select.getWidth(),select.getHeight());
        select.setName("Select");


        instruction1 = new com.badlogic.gdx.scenes.scene2d.ui.Label("Clic gauche pour placer un bloc",skin);
        instruction1.setBounds(0,ymax-7*Bomberball.taillecase,instruction1.getWidth(),instruction1.getHeight());
        instruction1.setName("Instruction1");

        instruction2 = new Label("Clic droit pour enlever un bloc",skin);
        instruction2.setBounds(0,ymax-8*Bomberball.taillecase,instruction2.getWidth(),instruction2.getHeight());
        instruction2.setName("Instruction2");


        retour= new TextButton("Retour",skin);
        retour.setBounds(0,ymax-9*Bomberball.taillecase, retour.getWidth(),retour.getHeight());
        retour.setName("Retour");

        valider = new TextButton("Valider la carte",skin);
        valider.setBounds(0,ymax-10*Bomberball.taillecase,valider.getWidth(),valider.getHeight());
        valider.setName("Valider");

        charger = new TextButton("Charger une carte",skin);
        charger.setBounds(0,ymax-11*Bomberball.taillecase,valider.getWidth(),valider.getHeight());
        charger.setName("Charger");

        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(f.exists()){
                    f.delete();
                }
                jeu.setEtat(game.choixEditeurN);
                game.setScreen(game.choixEditeurN);
            }
        });

        valider.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    fw = new FileWriter(f);
                    fw.write(map.mapToText());
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int cptPerso=0;
                for(int i=0;i<15;i++){
                    for(int j=0;j<13;j++){
                        if(map.getGrille()[i][j].getPersonnage()!=null){
                            cptPerso++;
                        }
                    }
                }
                if (cptPerso!=4){
                    jeu.setEtat(game.erreurEditeurM);
                    game.setScreen(game.erreurEditeurM);
                }
                else{
                    jeu.setEtat(game.validerEditeurMulti);
                    game.setScreen(game.validerEditeurMulti);
                }
            }
        });

        charger.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.setEtat(game.choixMapMultiE);
                game.setScreen(game.choixMapMultiE);
            }
        });





        jeu.addActor(back);
        jeu.addActor(floor);
        jeu.addActor(perso);
        jeu.addActor(murd);
        jeu.addActor(muri);
        jeu.addActor(select);
        jeu.addActor(selectionne);
        jeu.addActor(instruction1);
        jeu.addActor(instruction2);
        jeu.addActor(retour);
        jeu.addActor(valider);
        jeu.addActor(charger);
        jeu.addActor(map);






    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)
        stg.draw();
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
        if (hitActor.getName()!=null) {
            if (hitActor.getName().equals("murd")) {
                selectionne.setDrawable(murd.getDrawable());
                selectionne.setName("murdes");
            } else if (hitActor.getName().equals("floor")) {
                selectionne.setDrawable(floor.getDrawable());
                selectionne.setName("sol");
            } else if (hitActor.getName().equals("muri")) {
                selectionne.setDrawable(muri.getDrawable());
                selectionne.setName("murin");
            }
            else if(hitActor.getName().equals("perso")){
                selectionne.setDrawable(perso.getDrawable());
                selectionne.setName("player");
            }
            else if(hitActor.getName().equals("MurI")){
                Case c = (Case) hitActor.getParent();


                System.out.println("x="+c.posX()+" y="+c.posY());
                if (button == Input.Buttons.RIGHT) {
                    Map m=c.getMap();

                    int xp=c.posX();
                    int yp=c.posY();

                    c.setMur(null);
                    m.getGrille()[14-xp][yp].setMur(null);
                    m.getGrille()[14-xp][12-yp].setMur(null);
                    m.getGrille()[xp][12-yp].setMur(null);

                    c.setPorte(null);
                    m.getGrille()[14-xp][yp].setPorte(null);
                    m.getGrille()[14-xp][12-yp].setPorte(null);
                    m.getGrille()[xp][12-yp].setPorte(null);

                    c.setPersonnage(null);
                    m.getGrille()[14-xp][yp].setPersonnage(null);
                    m.getGrille()[14-xp][12-yp].setPersonnage(null);
                    m.getGrille()[xp][12-yp].setPersonnage(null);

                    c.setBonus(null);
                    m.getGrille()[14-xp][yp].setBonus(null);
                    m.getGrille()[14-xp][12-yp].setBonus(null);
                    m.getGrille()[xp][12-yp].setBonus(null);

                    Image background=new Image(Bomberball.multiTexture[0]);
                    background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.addActor(background);

                    m.getGrille()[14-xp][yp].addActor(background);
                    m.getGrille()[14-xp][12-yp].addActor(background);
                    m.getGrille()[xp][12-yp].addActor(background);


                } else if (button == Input.Buttons.LEFT) {
                    if (selectionne.getDrawable() != null) {
                        if (selectionne.getName().equals("murdes")) {
                            Map m=c.getMap();

                            int xp=c.posX();
                            int yp=c.posY();

                            c.setMur(null);
                            m.getGrille()[14-xp][yp].setMur(null);
                            m.getGrille()[14-xp][12-yp].setMur(null);
                            m.getGrille()[xp][12-yp].setMur(null);

                            c.setPorte(null);
                            m.getGrille()[14-xp][yp].setPorte(null);
                            m.getGrille()[14-xp][12-yp].setPorte(null);
                            m.getGrille()[xp][12-yp].setPorte(null);

                            c.setPersonnage(null);
                            m.getGrille()[14-xp][yp].setPersonnage(null);
                            m.getGrille()[14-xp][12-yp].setPersonnage(null);
                            m.getGrille()[xp][12-yp].setPersonnage(null);

                            c.setBonus(null);
                            m.getGrille()[14-xp][yp].setBonus(null);
                            m.getGrille()[14-xp][12-yp].setBonus(null);
                            m.getGrille()[xp][12-yp].setBonus(null);

                            Image background=new Image(Bomberball.multiTexture[0]);
                            background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                            c.addActor(background);

                            m.getGrille()[14-xp][yp].addActor(background);
                            m.getGrille()[14-xp][12-yp].addActor(background);
                            m.getGrille()[xp][12-yp].addActor(background);
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("sol")) {
                            Map m=c.getMap();

                            int xp=c.posX();
                            int yp=c.posY();

                            c.setMur(null);
                            m.getGrille()[14-xp][yp].setMur(null);
                            m.getGrille()[14-xp][12-yp].setMur(null);
                            m.getGrille()[xp][12-yp].setMur(null);

                            c.setPorte(null);
                            m.getGrille()[14-xp][yp].setPorte(null);
                            m.getGrille()[14-xp][12-yp].setPorte(null);
                            m.getGrille()[xp][12-yp].setPorte(null);

                            c.setPersonnage(null);
                            m.getGrille()[14-xp][yp].setPersonnage(null);
                            m.getGrille()[14-xp][12-yp].setPersonnage(null);
                            m.getGrille()[xp][12-yp].setPersonnage(null);

                            c.setBonus(null);
                            m.getGrille()[14-xp][yp].setBonus(null);
                            m.getGrille()[14-xp][12-yp].setBonus(null);
                            m.getGrille()[xp][12-yp].setBonus(null);

                            Image background=new Image(Bomberball.multiTexture[0]);
                            background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                            c.addActor(background);

                            m.getGrille()[14-xp][yp].addActor(background);
                            m.getGrille()[14-xp][12-yp].addActor(background);
                            m.getGrille()[xp][12-yp].addActor(background);
                        } else if (selectionne.getName().equals("murin")) {
                            //rien








                        } else if (selectionne.getName().equals("p")) {
                            Map m=c.getMap();

                            int xp=c.posX();
                            int yp=c.posY();

                            c.setMur(null);
                            m.getGrille()[14-xp][yp].setMur(null);
                            m.getGrille()[14-xp][12-yp].setMur(null);
                            m.getGrille()[xp][12-yp].setMur(null);

                            c.setPorte(null);
                            m.getGrille()[14-xp][yp].setPorte(null);
                            m.getGrille()[14-xp][12-yp].setPorte(null);
                            m.getGrille()[xp][12-yp].setPorte(null);

                            c.setPersonnage(null);
                            m.getGrille()[14-xp][yp].setPersonnage(null);
                            m.getGrille()[14-xp][12-yp].setPersonnage(null);
                            m.getGrille()[xp][12-yp].setPersonnage(null);

                            c.setBonus(null);
                            m.getGrille()[14-xp][yp].setBonus(null);
                            m.getGrille()[14-xp][12-yp].setBonus(null);
                            m.getGrille()[xp][12-yp].setBonus(null);

                            Image background=new Image(Bomberball.multiTexture[0]);
                            background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                            c.addActor(background);

                            m.getGrille()[14-xp][yp].addActor(background);
                            m.getGrille()[14-xp][12-yp].addActor(background);
                            m.getGrille()[xp][12-yp].addActor(background);
                            c.setPorte(new Porte());
                        }
                        else if(selectionne.getName().equals("player")){
                            if(c.getMur()==null){
                                c.setPersonnage(new Personnage(true,c,2,1,5));
                            }

                        }
                    }
                }

            }
            else if(hitActor.getName().equals("MurD")){
                Case c = (Case) hitActor.getParent();
                System.out.println("x="+c.posX()+" y="+c.posY());
                if (button == Input.Buttons.RIGHT) {
                    c.setMur(null);
                    c.setPorte(null);
                    c.setPersonnage(null);
                    c.setBonus(null);
                    Image background=new Image(Bomberball.multiTexture[0]);
                    background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.addActor(background);
                } else if (button == Input.Buttons.LEFT) {
                    if (selectionne.getDrawable() != null) {
                        if (selectionne.getName().equals("murdes")) {
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("sol")) {
                            c.setMur(null);
                            c.setPorte(null);
                            c.setPersonnage(null);
                            c.setBonus(null);
                            Image background=new Image(Bomberball.multiTexture[0]);
                            background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                            c.addActor(background);
                        } else if (selectionne.getName().equals("murin")) {
                            Map m=c.getMap();
                            c.setMur(new MurI());
                            int xp=c.posX();
                            int yp=c.posY();
                            m.getGrille()[14-xp][yp].setMur(new MurI());
                            m.getGrille()[14-xp][12-yp].setMur(new MurI());
                            m.getGrille()[xp][12-yp].setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            c.setPorte(new Porte());
                        }
                        else if(selectionne.getName().equals("player")){
                            if(c.getMur()==null){
                                c.setPersonnage(new Personnage(true,c,2,1,5));
                            }
                        }
                    }
                }

            }
            else if(hitActor.getName().equals("Personnage")){
                Case c = (Case) hitActor.getParent();
                System.out.println("x="+c.posX()+" y="+c.posY());
                if (button == Input.Buttons.RIGHT) {
                    c.setMur(null);
                    c.setPorte(null);
                    c.setPersonnage(null);
                    c.setBonus(null);
                    Image background=new Image(Bomberball.multiTexture[0]);
                    background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.addActor(background);
                } else if (button == Input.Buttons.LEFT) {
                    if (selectionne.getDrawable() != null) {
                        if (selectionne.getName().equals("murdes")) {
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("sol")) {
                            c.setMur(null);
                            c.setPorte(null);
                            c.setPersonnage(null);
                            c.setBonus(null);
                            Image background=new Image(Bomberball.multiTexture[0]);
                            background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                            c.addActor(background);
                        } else if (selectionne.getName().equals("murin")) {
                            Map m=c.getMap();

                            int xp=c.posX();
                            int yp=c.posY();

                            c.setMur(null);
                            m.getGrille()[14-xp][yp].setMur(null);
                            m.getGrille()[14-xp][12-yp].setMur(null);
                            m.getGrille()[xp][12-yp].setMur(null);

                            c.setPorte(null);
                            m.getGrille()[14-xp][yp].setPorte(null);
                            m.getGrille()[14-xp][12-yp].setPorte(null);
                            m.getGrille()[xp][12-yp].setPorte(null);

                            c.setPersonnage(null);
                            m.getGrille()[14-xp][yp].setPersonnage(null);
                            m.getGrille()[14-xp][12-yp].setPersonnage(null);
                            m.getGrille()[xp][12-yp].setPersonnage(null);

                            c.setBonus(null);
                            m.getGrille()[14-xp][yp].setBonus(null);
                            m.getGrille()[14-xp][12-yp].setBonus(null);
                            m.getGrille()[xp][12-yp].setBonus(null);

                            c.setMur(new MurI());
                            m.getGrille()[14-xp][yp].setMur(new MurI());
                            m.getGrille()[14-xp][12-yp].setMur(new MurI());
                            m.getGrille()[xp][12-yp].setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            c.setPersonnage(null);
                            c.setPorte(new Porte());
                        }
                        else if(selectionne.getName().equals("player")){
                            if(c.getMur()==null){
                                c.setPersonnage(new Personnage(true,c,2,1,5));
                            }
                        }
                    }
                }

            }
        }else if(hitActor.getParent() instanceof Case){
            Case c = (Case) hitActor.getParent();
            System.out.println("x="+c.posX()+" y="+c.posY());
            if (button == Input.Buttons.RIGHT) {
                c.setMur(null);
                c.setPorte(null);
                c.setPersonnage(null);
                c.setBonus(null);
                Image background=new Image(Bomberball.multiTexture[0]);
                background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                c.addActor(background);
            } else if (button == Input.Buttons.LEFT) {
                if (selectionne.getDrawable() != null) {
                    if (selectionne.getName().equals("murdes")) {
                        c.setMur(new MurD());
                    } else if (selectionne.getName().equals("sol")) {
                        c.setMur(null);
                        c.setPorte(null);
                        c.setPersonnage(null);
                        c.setBonus(null);
                        Image background=new Image(Bomberball.multiTexture[0]);
                        background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                        c.addActor(background);
                    } else if (selectionne.getName().equals("murin")) {
                        Map m=c.getMap();
                        c.setMur(new MurI());
                        int xp=c.posX();
                        int yp=c.posY();
                        m.getGrille()[14-xp][yp].setMur(new MurI());
                        m.getGrille()[14-xp][12-yp].setMur(new MurI());
                        m.getGrille()[xp][12-yp].setMur(new MurI());

                    } else if (selectionne.getName().equals("p")) {
                        c.setPorte(new Porte());
                    }
                    else if(selectionne.getName().equals("player")){
                        if(c.getMur()==null){
                            c.setPersonnage(new Personnage(true,c,2,1,5));
                        }
                    }
                }
            }




        }



        Gdx.app.log("HIT", hitActor.getName());

        return true;
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        return false;
    }
}
