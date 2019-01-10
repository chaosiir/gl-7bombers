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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;


import java.awt.*;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;

import static com.bomber.game.Bomberball.stg;

public class EditeurNSolo extends Etat implements Screen {

    Bomberball game;
    Image back;
    Image floor;
    Image porte;
    Image murd;
    Image muri;
    Image perso;
    Image selectionne;
    Image bonusB;
    Image bonusM;
    Image bonusE;
    Image ennemisPassif;

    Label select;
    Label instruction1;
    Label instruction2;
    Label instruction3;

    TextButton retour;
    TextButton valider;
    TextButton charger;

    Map map;

    Skin skin;

    Boolean cache=true;

    File f;
    FileWriter fw;






    public EditeurNSolo(Bomberball game, Jeu jeu){
        super(jeu);
        this.game=game;
        File directory = new File (".");
        try {
            f = new File(directory.getCanonicalPath() + "/SaveMapPerso/Mapsolo/tmp.txt");

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
            map=Map.genererMapSolo(20,10,5);

        }
        map.setPosition(7*Bomberball.taillecase,0);


        for (int i=0;i<15;i++){
            for (int j=0;j<13;j++){
                if(map.getGrille()[i][j].getBonus()!=null){


                    Bonus b=map.getGrille()[i][j].getBonus();
                    map.getGrille()[i][j].setBonus(null);
                    map.getGrille()[i][j].setBonus(b);
                    map.getGrille()[i][j].getBonus().setScale(0.5f);
                }
            }
        }

















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

        porte= new Image(Bomberball.multiTexture[3]);
        porte.setName("porte");
        porte.setBounds(0,ymax-4*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        selectionne= new Image();
        selectionne.setName("selection");
        selectionne.setBounds(0,ymax-6*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        bonusB=new Image(Bomberball.multiTexture[6]);
        bonusB.setName("bonusB");
        bonusB.setBounds(Bomberball.taillecase,ymax-2*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        bonusE=new Image(Bomberball.multiTexture[8]);
        bonusE.setName("bonusE");
        bonusE.setBounds(Bomberball.taillecase,ymax-3*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        bonusM = new Image(Bomberball.multiTexture[7]);
        bonusM.setName("bonusM");
        bonusM.setBounds(Bomberball.taillecase,ymax-4*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        ennemisPassif = new Image(Bomberball.multiTexture[17]);
        ennemisPassif.setName("ghost");
        ennemisPassif.setBounds(2*Bomberball.taillecase,ymax-Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        select= new Label("Bloc selectionne:",skin);
        select.setBounds(0,ymax-5*Bomberball.taillecase,select.getWidth(),select.getHeight());
        select.setName("Select");


        instruction1 = new Label("Clic gauche pour placer un bloc",skin);
        instruction1.setBounds(0,ymax-7*Bomberball.taillecase,instruction1.getWidth(),instruction1.getHeight());
        instruction1.setName("Instruction1");

        instruction2 = new Label("Clic droit pour enlever un bloc",skin);
        instruction2.setBounds(0,ymax-8*Bomberball.taillecase,instruction2.getWidth(),instruction2.getHeight());
        instruction2.setName("Instruction2");

        instruction3 = new Label("Clic mollette sur un ennemi pour afficher/cacher \n sa trajectoire",skin);
        instruction3.setBounds(0,ymax-9*Bomberball.taillecase,instruction3.getWidth(),instruction3.getHeight());
        instruction3.setName("Instruction3");


        retour= new TextButton("Retour",skin);
        retour.setBounds(0,ymax-10*Bomberball.taillecase, retour.getWidth(),retour.getHeight());
        retour.setName("Retour");

        valider = new TextButton("Valider la carte",skin);
        valider.setBounds(0,ymax-11*Bomberball.taillecase,valider.getWidth(),valider.getHeight());
        valider.setName("Valider");

        charger = new TextButton("Charger une carte",skin);
        charger.setBounds(0,ymax-12*Bomberball.taillecase,valider.getWidth(),valider.getHeight());
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


                int cptPorte=0;
                int cptPerso=0;
                for(int i=0;i<15;i++){
                    for(int j=0;j<13;j++){
                        if(map.getGrille()[i][j].getPersonnage()!=null){
                            cptPerso++;
                        }
                        else if(map.getGrille()[i][j].getPorte()!=null){
                            cptPorte++;
                        }
                    }
                }
                if (cptPerso!=1 || cptPorte!=1){
                    jeu.setEtat(game.erreurEditeurS);
                    game.setScreen(game.erreurEditeurS);
                }
                else{
                    jeu.setEtat(game.validerEditeurSolo);
                    game.setScreen(game.validerEditeurSolo);
                }
            }
        });

        charger.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.setEtat(game.choixMapSoloE);
                game.setScreen(game.choixMapSoloE);
            }
        });





        jeu.addActor(back);
        jeu.addActor(floor);
        jeu.addActor(perso);
        jeu.addActor(murd);
        jeu.addActor(muri);
        jeu.addActor(porte);
        jeu.addActor(bonusB);
        jeu.addActor(bonusE);
        jeu.addActor(bonusM);
        jeu.addActor(ennemisPassif);
        jeu.addActor(select);
        jeu.addActor(selectionne);
        jeu.addActor(instruction1);
        jeu.addActor(instruction2);
        jeu.addActor(instruction3);
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
            } else if (hitActor.getName().equals("porte")) {
                selectionne.setDrawable(porte.getDrawable());
                selectionne.setName("p");
            }
            else if(hitActor.getName().equals("perso")){
                selectionne.setDrawable(perso.getDrawable());
                selectionne.setName("player");
            }
            else if(hitActor.getName().equals("bonusB")){
                selectionne.setDrawable(bonusB.getDrawable());
                selectionne.setName("bB");
            }
            else if(hitActor.getName().equals("bonusE")){
                selectionne.setDrawable(bonusE.getDrawable());
                selectionne.setName("bE");
            }
            else if(hitActor.getName().equals("bonusM")){
                selectionne.setDrawable(bonusM.getDrawable());
                selectionne.setName("bM");
            }
            else if(hitActor.getName().equals("ghost")){
                selectionne.setDrawable(ennemisPassif.getDrawable());
                selectionne.setName("Ep");
                try {
                    fw = new FileWriter(f);
                    fw.write(map.mapToText());
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                jeu.setEtat(game.selectionCheminEp);
                game.setScreen(game.selectionCheminEp);


            }
            else if(hitActor.getName().equals("MurI")){
                Case c = (Case) hitActor.getParent();
                if(c.posX()==0 || c.posX()==14 || c.posY()==0 || c.posY()==12){

                }
                else{
                    if (button == Input.Buttons.RIGHT) {
                        c.setMur(null);
                        c.setPorte(null);
                        c.setPersonnage(null);
                        c.setBonus(null);
                        c.setEnnemi(null);
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
                                c.setEnnemi(null);
                                Image background=new Image(Bomberball.multiTexture[0]);
                                background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                                c.addActor(background);
                            } else if (selectionne.getName().equals("murin")) {
                                c.setMur(new MurI());
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


            }
            else if(hitActor.getName().equals("MurD")){
                Case c = (Case) hitActor.getParent();
                if (button == Input.Buttons.RIGHT) {
                    c.setMur(null);
                    c.setPorte(null);
                    c.setPersonnage(null);
                    c.setBonus(null);
                    c.setEnnemi(null);
                    Image background=new Image(Bomberball.multiTexture[0]);
                    background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.addActor(background);
                } else if (button == Input.Buttons.LEFT) {
                    if (selectionne.getDrawable() != null) {
                        if (selectionne.getName().equals("murdes")) {
                            c.setBonus(null);
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
                            c.setBonus(null);
                            c.setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            c.setBonus(null);
                            c.setPorte(new Porte());
                        }
                        else if(selectionne.getName().equals("player")){
                            c.setBonus(null);
                            if(c.getMur()==null){
                                c.setPersonnage(new Personnage(true,c,2,1,5));
                            }

                        }
                        else if(selectionne.getName().equals("bB")){
                            c.setBonus(new BonusBombe(c));
                            c.getBonus().setScale(0.5f);
                        }
                        else if(selectionne.getName().equals("bM")){
                            c.setBonus(new BonusMove(c));
                            c.getBonus().setScale(0.5f);
                        }
                        else if(selectionne.getName().equals("bE")){
                            c.setBonus(new BonusExplo(c));
                            c.getBonus().setScale(0.5f);
                        }
                    }
                }

            }
            else if(hitActor.getName().equals("Porte")){
                Case c = (Case) hitActor.getParent();
                if (button == Input.Buttons.RIGHT) {
                    c.setMur(null);
                    c.setPorte(null);
                    c.setPersonnage(null);
                    c.setBonus(null);
                    c.setEnnemi(null);
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
                            c.setEnnemi(null);
                            Image background=new Image(Bomberball.multiTexture[0]);
                            background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                            c.addActor(background);
                        } else if (selectionne.getName().equals("murin")) {
                            c.setMur(new MurI());
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
                    c.setEnnemi(null);
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
                            c.setEnnemi(null);
                            Image background=new Image(Bomberball.multiTexture[0]);
                            background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                            c.addActor(background);
                        } else if (selectionne.getName().equals("murin")) {
                            Map m=c.getMap();
                            c.setMur(new MurI());
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
            else if(hitActor.getName().equals("bonus")){
                Case c = (Case) hitActor.getParent();
                if (button == Input.Buttons.RIGHT) {
                    c.setMur(null);
                    c.setPorte(null);
                    c.setPersonnage(null);
                    c.setBonus(null);
                    c.setEnnemi(null);
                    Image background=new Image(Bomberball.multiTexture[0]);
                    background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.addActor(background);
                } else if (button == Input.Buttons.LEFT) {
                    if (selectionne.getDrawable() != null) {
                        if (selectionne.getName().equals("murdes")) {
                            c.setBonus(null);
                            c.setEnnemi(null);
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("sol")) {
                            c.setMur(null);
                            c.setPorte(null);
                            c.setPersonnage(null);
                            c.setBonus(null);
                            c.setEnnemi(null);
                            Image background=new Image(Bomberball.multiTexture[0]);
                            background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                            c.addActor(background);
                        } else if (selectionne.getName().equals("murin")) {
                            c.setBonus(null);
                            c.setEnnemi(null);
                            c.setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            c.setBonus(null);
                            c.setEnnemi(null);
                            c.setPorte(new Porte());
                        }
                        else if(selectionne.getName().equals("player")){
                            c.setBonus(null);
                            c.setEnnemi(null);
                            if(c.getMur()==null){
                                c.setPersonnage(new Personnage(true,c,2,1,5));
                            }

                        }
                        else if(selectionne.getName().equals("bB")){
                            c.setBonus(new BonusBombe(c));
                            c.getBonus().setScale(0.5f);
                        }
                        else if(selectionne.getName().equals("bM")){
                            c.setBonus(new BonusMove(c));
                            c.getBonus().setScale(0.5f);
                        }
                        else if(selectionne.getName().equals("bE")){
                            c.setBonus(new BonusExplo(c));
                            c.getBonus().setScale(0.5f);
                        }

                    }
                }

            }
            else if(hitActor.getName().equals("Ennemis")){ //Je vais d'abord supposer qu'il n'y a que des ennemis passifs pour commencer
                Case c = (Case) hitActor.getParent();
                if(button==Input.Buttons.RIGHT){
                    c.getEnnemi().prochain_deplacement.clear();
                    c.setEnnemi(null);
                    c.setMur(null);
                    c.setPorte(null);
                    c.setPersonnage(null);
                    c.setBonus(null);
                    c.setEnnemi(null);
                    Image background=new Image(Bomberball.multiTexture[0]);
                    background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.addActor(background);
                }
                if(button==Input.Buttons.MIDDLE){ //Il faut afficher tous les ennemis
                    LinkedList<Ennemis> liste=new LinkedList<Ennemis>();
                    for(int i=0;i<15;i++){
                        for(int j=0;j<13;j++){
                            if(c.getMap().getGrille()[i][j].getEnnemi()!=null){
                                liste.add(c.getMap().getGrille()[i][j].getEnnemi());
                            }
                        }
                    }
                    if(cache) {
                        for (Ennemis en : liste) {
                            LinkedList<Case> caca = en.prochain_deplacement;
                            for (Case cas : caca) {
                                int xc = cas.posX();
                                int yc = cas.posY();
                                if (cas.getEnnemi() != null) {
                                    Ennemi_passif ennemi_passif = (Ennemi_passif) cas.getEnnemi();
                                    c.getMap().getGrille()[xc][yc].setEnnemi(null);
                                    c.getMap().getGrille()[xc][yc].setMarque(new Image(Bomberball.multiTexture[18]));
                                    c.getMap().getGrille()[xc][yc].setEnnemi(ennemi_passif);
                                } else if (cas.getPersonnage() != null) {
                                    Personnage personnage = cas.getPersonnage();
                                    c.getMap().getGrille()[xc][yc].setPersonnage(null);
                                    c.getMap().getGrille()[xc][yc].setMarque(new Image(Bomberball.multiTexture[18]));
                                    c.getMap().getGrille()[xc][yc].setPersonnage(personnage);
                                } else {
                                    c.getMap().getGrille()[xc][yc].setMarque(new Image(Bomberball.multiTexture[18]));
                                }
                            }
                        }
                        cache=false;
                    }
                    else{
                        for (Ennemis en : liste) {
                            LinkedList<Case> caca = en.prochain_deplacement;
                            for(Case cas: caca){
                                int xc=cas.posX();
                                int yc=cas.posY();
                                System.out.println("Ennemi n "+en.getC().posX()+" "+ en.getC().posY()+" xc="+xc+" yc="+yc);
                                if(cas.getEnnemi()!=null){
                                    Ennemi_passif ennemi_passif=(Ennemi_passif)cas.getEnnemi();
                                    c.getMap().getGrille()[xc][yc].setEnnemi(null);
                                    c.getMap().getGrille()[xc][yc].setMarque(null);
                                    c.getMap().getGrille()[xc][yc].setEnnemi(ennemi_passif);
                                }
                                else if(cas.getPersonnage()!=null){
                                    Personnage personnage=cas.getPersonnage();
                                    c.getMap().getGrille()[xc][yc].setPersonnage(null);
                                    c.getMap().getGrille()[xc][yc].setMarque(null);
                                    c.getMap().getGrille()[xc][yc].setPersonnage(personnage);
                                }
                                else{
                                    c.getMap().getGrille()[xc][yc].setMarque(null);
                                }
                            }
                        }
                        cache=true;

                    }


                }
                if(button==Input.Buttons.LEFT){
                    if (selectionne.getDrawable() != null) {
                        if (selectionne.getName().equals("murdes")) {
                            c.getEnnemi().prochain_deplacement.clear();
                            c.setEnnemi(null);
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("sol")) {
                            c.setMur(null);
                            c.setPorte(null);
                            c.setPersonnage(null);
                            c.setBonus(null);
                            c.getEnnemi().prochain_deplacement.clear();
                            c.setEnnemi(null);
                            Image background=new Image(Bomberball.multiTexture[0]);
                            background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                            c.addActor(background);
                        } else if (selectionne.getName().equals("murin")) {
                            c.getEnnemi().prochain_deplacement.clear();
                            c.setEnnemi(null);
                            c.setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            c.getEnnemi().prochain_deplacement.clear();
                            c.setEnnemi(null);
                            c.setPorte(new Porte());
                        }
                        else if(selectionne.getName().equals("player")){
                            c.getEnnemi().prochain_deplacement.clear();
                            c.setEnnemi(null);
                            if(c.getMur()==null){
                                c.setPersonnage(new Personnage(true,c,2,1,5));
                            }

                        }
                    }
                }
            }

            }else if(hitActor.getParent() instanceof Case){
                Case c = (Case) hitActor.getParent();
                if (button == Input.Buttons.RIGHT) {
                    c.setMur(null);
                    c.setPorte(null);
                    c.setPersonnage(null);
                    c.setBonus(null);
                    c.setEnnemi(null);
                    Image background=new Image(Bomberball.multiTexture[0]);
                    background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.addActor(background);
                } else if (button == Input.Buttons.LEFT) {
                    if (selectionne.getDrawable() != null) {
                        if (selectionne.getName().equals("murdes")) {
                            c.setMur(null);
                            c.setPorte(null);
                            c.setPersonnage(null);
                            c.setBonus(null);
                            c.setEnnemi(null);
                            c.setMur(new MurD());
                        } else if (selectionne.getName().equals("sol")) {
                            c.setMur(null);
                            c.setPorte(null);
                            c.setPersonnage(null);
                            c.setBonus(null);
                            c.setEnnemi(null);
                            Image background=new Image(Bomberball.multiTexture[0]);
                            background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                            c.addActor(background);
                        } else if (selectionne.getName().equals("murin")) {
                            c.setMur(null);
                            c.setPorte(null);
                            c.setPersonnage(null);
                            c.setBonus(null);
                            c.setEnnemi(null);
                            c.setMur(new MurI());
                        } else if (selectionne.getName().equals("p")) {
                            c.setMur(null);
                            c.setPorte(null);
                            c.setPersonnage(null);
                            c.setBonus(null);
                            c.setEnnemi(null);
                            c.setPorte(new Porte());
                        }
                        else if(selectionne.getName().equals("player")){
                            if(c.getMur()==null){
                                c.setPersonnage(new Personnage(true,c,2,1,5));
                            }

                        }
                        else if(selectionne.getName().equals("Ep")){
                            c.setEnnemi(new Ennemi_passif(true,c,5));
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
