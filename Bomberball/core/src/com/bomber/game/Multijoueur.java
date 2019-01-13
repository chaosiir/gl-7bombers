package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Multijoueur extends Etat implements Screen {//etat multijoueur
    int pm=5;
    int nb=1;
    private Bomberball game;
    private Personnage joueurs[]=new Personnage[4];
    private int tour=0;
    Image back;

    Skin skin;

    Image joueur1;
    Image bombe1;
    Image mouvement1;
    Image pousse1;
    Image explosion1;
    Image player1;

    Label nbmvt1;
    Label nbBombe1;
    Label  porteExplo1;

    Personnage personnage1;
    Personnage personnage2;
    Personnage personnage3;
    Personnage personnage4;

    Label nbmvt2;
    Label nbBombe2;
    Label porteExplo2;

    Label nbmvt3;
    Label nbBombe3;
    Label porteExplo3;

    Label nbmvt4;
    Label nbBombe4;
    Label porteExplo4;

    Label poussee1;
    Label poussee2;
    Label poussee3;
    Label poussee4;



    Image joueur2;
    Image bombe2;
    Image mouvement2;
    Image pousse2;
    Image explosion2;
    Image player2;

    Image joueur3;
    Image bombe3;
    Image mouvement3;
    Image pousse3;
    Image explosion3;
    Image player3;

    Image joueur4;
    Image bombe4;
    Image mouvement4;
    Image pousse4;
    Image explosion4;
    Image player4;

    File f;
    File frecommencer;
    FileWriter fw;
    FileWriter fwr;

    public Multijoueur(Bomberball game,Jeu jeu) {
        super(jeu);
        this.game=game;
        File directory = new File (".");
        try {
            f = new File(directory.getCanonicalPath() + "/SaveTempo/tmp.txt");
            frecommencer = new File(directory.getCanonicalPath() + "/SaveTempo/debut.txt");

        } catch (IOException e) {

        }

    }


    @Override
    public void show() {
        skin=new Skin(Gdx.files.internal("uiskin.json"));

        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        back.setName("Je suis ton arrière plan");

        if(f.exists()){
            jeu.map.suppActor();
            jeu.removeActor(jeu.map);
            jeu.map=null;
            if(jeu.recommencer){
                jeu.map=Map.mapFromString(Bomberball.loadFile(f));
                jeu.recommencer=false;
                tour=0;
                f.delete();
                try {
                    fwr = new FileWriter(frecommencer);
                    fwr.write(jeu.map.mapToText());
                    fwr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                jeu.map=Map.mapFromStringP(Bomberball.loadFile(f),this.jeu);
                f.delete();
                switch (tour){
                    case 0: if(jeu.pmtmp1!=-1){
                        pm=jeu.pmtmp1;
                        nbmvt1.setText(""+pm);
                        jeu.pmtmp1=-1;
                    }
                        if(jeu.nbtmp1!=-1){
                            nb=jeu.nbtmp1;
                            nbBombe1.setText(""+nb);
                            jeu.nbtmp1=-1;
                        }
                        if(jeu.poussee1){
                            poussee1=new Label("X",skin);
                        }
                        break;
                    case 1: if(jeu.pmtmp2!=-1){
                        pm=jeu.pmtmp2;
                        nbmvt2.setText(""+pm);
                        jeu.pmtmp2=-1;
                    }
                        if(jeu.nbtmp2!=-1){
                            nb=jeu.nbtmp2;
                            nbBombe2.setText(""+nb);
                            jeu.nbtmp2=-1;
                        }
                        if(jeu.poussee2){
                            poussee2=new Label("X",skin);
                        }
                        break;
                    case 2: if(jeu.pmtmp3!=-1){
                        pm=jeu.pmtmp3;
                        nbmvt3.setText(""+pm);
                        jeu.pmtmp3=-1;
                    }
                        if(jeu.nbtmp3!=-1){
                            nb=jeu.nbtmp3;
                            nbBombe3.setText(""+nb);
                            jeu.nbtmp3=-1;
                        }
                        if(jeu.poussee3){
                            poussee3=new Label("X",skin);
                        }
                        break;
                    case 3: if(jeu.pmtmp4!=-1){
                        pm=jeu.pmtmp4;
                        nbmvt4.setText(""+pm);
                        jeu.pmtmp4=-1;
                    }



                        if(jeu.nbtmp4!=-1){
                            nb=jeu.nbtmp4;
                            nbBombe4.setText(""+nb);
                            jeu.nbtmp4=-1;
                        }



                        if(jeu.poussee4){
                            poussee4=new Label("X",skin);
                        }
                        break;

                }
            }

        }
       else if(jeu.map==null){
            if(jeu.nbBonus!=-1){
                jeu.map=Map.generatePvp(65, jeu.nbBonus);
                jeu.nbBonus=-1;
                try {
                    fwr = new FileWriter(frecommencer);
                    fwr.write(jeu.map.mapToText());
                    fwr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                jeu.map=Map.generatePvp(65,5);
                try {
                    fwr = new FileWriter(frecommencer);
                    fwr.write(jeu.map.mapToText());
                    fwr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            try {
                fwr = new FileWriter(frecommencer);
                fwr.write(jeu.map.mapToText());
                fwr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int a=0;
        for(int i=0;i<jeu.map.getGrille().length;i++){
            for (int j=0;j<jeu.map.getGrille()[1].length;j++){
                Personnage p=jeu.map.getGrille()[i][j].getPersonnage();
                if(p!=null){
                    joueurs[a]=p;
                    a++;
                    switch (p.getId()){
                        case 0:  personnage1=p; break;
                        case 1:  personnage2=p; break;
                        case 2:  personnage3=p; break;
                        case 3:  personnage4=p;break;
                    }
                }
            }
        }
        for (int i=0;i<15;i++){
            for (int j=0;j<13;j++){
                if(jeu.map.getGrille()[i][j].getBonus()!=null){


                    Bonus b=jeu.map.getGrille()[i][j].getBonus();
                    jeu.map.getGrille()[i][j].setBonus(null);
                    jeu.map.getGrille()[i][j].setBonus(b);
                    jeu.map.getGrille()[i][j].getBonus().setScale(0.5f);
                }
            }
        }

        jeu.map.setBounds(Gdx.graphics.getWidth()/9,0,jeu.map.getWidth(),jeu.map.getHeight());
        jeu.map.setScaleY(27f/26f);

        joueur1=new Image(new Texture(Gdx.files.internal("Panneau_joueur.png")));
        joueur1.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        joueur1.setHeight(2*Bomberball.taillecase);
        joueur1.setPosition(0,Gdx.graphics.getHeight()-2*Bomberball.taillecase);


        mouvement1 = new Image(new Texture(Gdx.files.internal("Nombre_mouvement.png")));
        mouvement1.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        mouvement1.setHeight(Bomberball.taillecase);
        mouvement1.setPosition(0,Gdx.graphics.getHeight()-3*Bomberball.taillecase);

        nbmvt1= new Label(""+personnage1.getPm(),skin);
        nbmvt1.setBounds(3*Bomberball.taillecase+30,Gdx.graphics.getHeight()-2*Bomberball.taillecase-50,nbmvt1.getWidth(),nbmvt1.getHeight()); //Positionnement à la main

        bombe1=new Image(new Texture(Gdx.files.internal("Nombre_bombe.png")));
        bombe1.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        bombe1.setHeight(Bomberball.taillecase);
        bombe1.setPosition(0,Gdx.graphics.getHeight()- 4*Bomberball.taillecase);

        nbBombe1= new Label(""+personnage1.getNbBombe(),skin);
        nbBombe1.setBounds(Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-3*Bomberball.taillecase-60,nbBombe1.getWidth(),nbBombe1.getHeight()); //Positionnement à la main

        explosion1=new Image(new Texture(Gdx.files.internal("Portée_bombe.png")));
        explosion1.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        explosion1.setHeight(Bomberball.taillecase);
        explosion1.setPosition(0,Gdx.graphics.getHeight()-5*Bomberball.taillecase);

        porteExplo1 = new Label(""+personnage1.getTaille(),skin);
        porteExplo1.setBounds(Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-4*Bomberball.taillecase-60,porteExplo1.getWidth(),porteExplo1.getHeight()); //Positionnement à la main


        pousse1=new Image(new Texture(Gdx.files.internal("icone_Bonus_pousser.png")));
        pousse1.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        pousse1.setHeight(Bomberball.taillecase);
        pousse1.setPosition(0,Gdx.graphics.getHeight()-6*Bomberball.taillecase);

        poussee1=new Label("",skin);
        poussee2=new Label("",skin);
        poussee3=new Label("",skin);
        poussee4=new Label("",skin);

        poussee1.setPosition(Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-6*Bomberball.taillecase);





        player1=new Image(Bomberball.multiTexture[4]);
        player1.setBounds(3*Bomberball.taillecase+30,Gdx.graphics.getHeight()-Bomberball.taillecase-50,Bomberball.taillecase,Bomberball.taillecase);


        joueur2=new Image(new Texture(Gdx.files.internal("Panneau_joueur.png")));
        joueur2.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        joueur2.setHeight(2*Bomberball.taillecase);
        joueur2.setPosition(0,Gdx.graphics.getHeight()-9*Bomberball.taillecase);


        mouvement2 = new Image(new Texture(Gdx.files.internal("Nombre_mouvement.png")));
        mouvement2.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        mouvement2.setHeight(Bomberball.taillecase);
        mouvement2.setPosition(0,Gdx.graphics.getHeight()-10*Bomberball.taillecase);

        nbmvt2= new Label(""+personnage2.getPm(),skin);
        nbmvt2.setBounds(3*Bomberball.taillecase+30,Gdx.graphics.getHeight()-9*Bomberball.taillecase-50,nbmvt2.getWidth(),nbmvt2.getHeight()); //Positionnement à la main

        bombe2=new Image(new Texture(Gdx.files.internal("Nombre_bombe.png")));
        bombe2.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        bombe2.setHeight(Bomberball.taillecase);
        bombe2.setPosition(0,Gdx.graphics.getHeight()- 11*Bomberball.taillecase);

        nbBombe2= new Label(""+personnage2.getNbBombe(),skin);
        nbBombe2.setBounds(Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-10*Bomberball.taillecase-60,nbBombe1.getWidth(),nbBombe1.getHeight()); //Positionnement à la main

        explosion2=new Image(new Texture(Gdx.files.internal("Portée_bombe.png")));
        explosion2.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        explosion2.setHeight(Bomberball.taillecase);
        explosion2.setPosition(0,Gdx.graphics.getHeight()-12*Bomberball.taillecase);

        porteExplo2 = new Label(""+personnage2.getTaille(),skin);
        porteExplo2.setBounds(Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-11*Bomberball.taillecase-60,porteExplo1.getWidth(),porteExplo1.getHeight()); //Positionnement à la main


        pousse2=new Image(new Texture(Gdx.files.internal("icone_Bonus_pousser.png")));
        pousse2.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        pousse2.setHeight(Bomberball.taillecase);
        pousse2.setPosition(0,Gdx.graphics.getHeight()-13*Bomberball.taillecase);

        poussee2.setPosition(Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-13*Bomberball.taillecase);

        player2=new Image(Bomberball.multiTexture[20]);
        player2.setBounds(3*Bomberball.taillecase+30,Gdx.graphics.getHeight()-8*Bomberball.taillecase-50,Bomberball.taillecase,Bomberball.taillecase);

        joueur3=new Image(new Texture(Gdx.files.internal("Panneau_joueur.png")));
        joueur3.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        joueur3.setHeight(2*Bomberball.taillecase);
        joueur3.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-2*Bomberball.taillecase);


        mouvement3 = new Image(new Texture(Gdx.files.internal("Nombre_mouvement.png")));
        mouvement3.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        mouvement3.setHeight(Bomberball.taillecase);
        mouvement3.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-3*Bomberball.taillecase);

        nbmvt3= new Label(""+personnage3.getPm(),skin);
        nbmvt3.setBounds(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-2*Bomberball.taillecase-50,nbmvt3.getWidth(),nbmvt3.getHeight()); //Positionnement à la main

        bombe3=new Image(new Texture(Gdx.files.internal("Nombre_bombe.png")));
        bombe3.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        bombe3.setHeight(Bomberball.taillecase);
        bombe3.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()- 4*Bomberball.taillecase);

        nbBombe3= new Label(""+personnage3.getNbBombe(),skin);
        nbBombe3.setBounds(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-3*Bomberball.taillecase-60,nbBombe3.getWidth(),nbBombe3.getHeight()); //Positionnement à la main

        explosion3=new Image(new Texture(Gdx.files.internal("Portée_bombe.png")));
        explosion3.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        explosion3.setHeight(Bomberball.taillecase);
        explosion3.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-5*Bomberball.taillecase);

        porteExplo3 = new Label(""+personnage3.getTaille(),skin);
        porteExplo3.setBounds(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-4*Bomberball.taillecase-60,porteExplo1.getWidth(),porteExplo1.getHeight()); //Positionnement à la main


        pousse3=new Image(new Texture(Gdx.files.internal("icone_Bonus_pousser.png")));
        pousse3.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        pousse3.setHeight(Bomberball.taillecase);
        pousse3.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-6*Bomberball.taillecase);

        poussee3.setPosition(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-6*Bomberball.taillecase);

        player3=new Image(Bomberball.multiTexture[21]);
        player3.setBounds(jeu.map.getX()+jeu.map.tailleX()+17*Bomberball.taillecase+3*Bomberball.taillecase,Gdx.graphics.getHeight()-Bomberball.taillecase-50,Bomberball.taillecase,Bomberball.taillecase);

        joueur4=new Image(new Texture(Gdx.files.internal("Panneau_joueur.png")));
        joueur4.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        joueur4.setHeight(2*Bomberball.taillecase);
        joueur4.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-9*Bomberball.taillecase);


        mouvement4 = new Image(new Texture(Gdx.files.internal("Nombre_mouvement.png")));
        mouvement4.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        mouvement4.setHeight(Bomberball.taillecase);
        mouvement4.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-10*Bomberball.taillecase);

        nbmvt4= new Label(""+personnage4.getPm(),skin);
        nbmvt4.setBounds(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-9*Bomberball.taillecase-50,nbmvt3.getWidth(),nbmvt3.getHeight()); //Positionnement à la main

        bombe4=new Image(new Texture(Gdx.files.internal("Nombre_bombe.png")));
        bombe4.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        bombe4.setHeight(Bomberball.taillecase);
        bombe4.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()- 11*Bomberball.taillecase);

        nbBombe4= new Label(""+personnage4.getNbBombe(),skin);
        nbBombe4.setBounds(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-10*Bomberball.taillecase-60,nbBombe3.getWidth(),nbBombe3.getHeight()); //Positionnement à la main

        explosion4=new Image(new Texture(Gdx.files.internal("Portée_bombe.png")));
        explosion4.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        explosion4.setHeight(Bomberball.taillecase);
        explosion4.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-12*Bomberball.taillecase);

        porteExplo4 = new Label(""+personnage4.getTaille(),skin);
        porteExplo4.setBounds(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-11*Bomberball.taillecase-60,porteExplo1.getWidth(),porteExplo1.getHeight()); //Positionnement à la main


        pousse4=new Image(new Texture(Gdx.files.internal("icone_Bonus_pousser.png")));
        pousse4.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        pousse4.setHeight(Bomberball.taillecase);
        pousse4.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-13*Bomberball.taillecase);

        poussee4.setPosition(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-13*Bomberball.taillecase);

        player4=new Image(Bomberball.multiTexture[22]);
        player4.setBounds(jeu.map.getX()+jeu.map.tailleX()+17*Bomberball.taillecase+3*Bomberball.taillecase,Gdx.graphics.getHeight()-8*Bomberball.taillecase-50,Bomberball.taillecase,Bomberball.taillecase);



        jeu.addActor(back);
        jeu.addActor(joueur1);
        jeu.addActor(mouvement1);
        jeu.addActor(nbmvt1);
        jeu.addActor(bombe1);
        jeu.addActor(nbBombe1);
        jeu.addActor(explosion1);
        jeu.addActor(porteExplo1);
        jeu.addActor(pousse1);
        jeu.addActor(player1);
        jeu.addActor(joueur2);
        jeu.addActor(mouvement2);
        jeu.addActor(nbmvt2);
        jeu.addActor(bombe2);
        jeu.addActor(nbBombe2);
        jeu.addActor(explosion2);
        jeu.addActor(porteExplo2);
        jeu.addActor(pousse2);
        jeu.addActor(player2);
        jeu.addActor(joueur3);
        jeu.addActor(mouvement3);
        jeu.addActor(nbmvt3);
        jeu.addActor(bombe3);
        jeu.addActor(nbBombe3);
        jeu.addActor(explosion3);
        jeu.addActor(porteExplo3);
        jeu.addActor(pousse3);
        jeu.addActor(player3);
        jeu.addActor(joueur4);
        jeu.addActor(mouvement4);
        jeu.addActor(nbmvt4);
        jeu.addActor(bombe4);
        jeu.addActor(nbBombe4);
        jeu.addActor(explosion4);
        jeu.addActor(porteExplo4);
        jeu.addActor(pousse4);
        jeu.addActor(player4);
        jeu.addActor(jeu.map);


    }


    @Override
    public boolean keyDown(InputEvent event, int keycode) {//delpacement = fleche pas encore implementer
        Personnage joueur = joueurs[tour];
        if(jeu.findActor("explo")==null) {
            if ((joueur != null) && (!joueur.hasActions())) {
                boolean b = false;
                if (keycode == Input.Keys.RIGHT) {
                    if (pm > 0) {
                        b = joueur.deplacerDroite();
                        pm = ((b) ? pm - 1 : pm);
                        switch(tour){
                            case 0:     jeu.removeActor(nbmvt1);nbmvt1.setText(""+pm);jeu.addActor(nbmvt1);break;
                            case 1:     jeu.removeActor(nbmvt2);nbmvt2.setText(""+pm);jeu.addActor(nbmvt2);break;
                            case 2:     jeu.removeActor(nbmvt3);nbmvt3.setText(""+pm);jeu.addActor(nbmvt3);break;
                            case 3:     jeu.removeActor(nbmvt4);nbmvt4.setText(""+pm);jeu.addActor(nbmvt4);break;
                        }
                    }
                }

                if (keycode == Input.Keys.LEFT) {
                    if (pm > 0) {
                        b = joueur.deplacerGauche();
                        pm = ((b) ? pm - 1 : pm);
                        switch(tour){
                            case 0:     jeu.removeActor(nbmvt1);nbmvt1.setText(""+pm);jeu.addActor(nbmvt1);break;
                            case 1:     jeu.removeActor(nbmvt2);nbmvt2.setText(""+pm);jeu.addActor(nbmvt2);break;
                            case 2:     jeu.removeActor(nbmvt3);nbmvt3.setText(""+pm);jeu.addActor(nbmvt3);break;
                            case 3:     jeu.removeActor(nbmvt4);nbmvt4.setText(""+pm);jeu.addActor(nbmvt4);break;
                        }
                    }
                }
                if (keycode == Input.Keys.DOWN) {
                    if (pm > 0) {
                        b = joueur.deplacerBas();
                        pm = ((b) ? pm - 1 : pm);
                        switch(tour){
                            case 0:     jeu.removeActor(nbmvt1);nbmvt1.setText(""+pm);jeu.addActor(nbmvt1);break;
                            case 1:     jeu.removeActor(nbmvt2);nbmvt2.setText(""+pm);jeu.addActor(nbmvt2);break;
                            case 2:     jeu.removeActor(nbmvt3);nbmvt3.setText(""+pm);jeu.addActor(nbmvt3);break;
                            case 3:     jeu.removeActor(nbmvt4);nbmvt4.setText(""+pm);jeu.addActor(nbmvt4);break;
                        }
                    }
                }
                if (keycode == Input.Keys.UP) {
                    if (pm > 0) {
                        b = joueur.deplacerHaut();
                        pm = ((b) ? pm - 1 : pm);
                        switch(tour){
                            case 0:     jeu.removeActor(nbmvt1);nbmvt1.setText(""+pm);jeu.addActor(nbmvt1);break;
                            case 1:     jeu.removeActor(nbmvt2);nbmvt2.setText(""+pm);jeu.addActor(nbmvt2);break;
                            case 2:     jeu.removeActor(nbmvt3);nbmvt3.setText(""+pm);jeu.addActor(nbmvt3);break;
                            case 3:     jeu.removeActor(nbmvt4);nbmvt4.setText(""+pm);jeu.addActor(nbmvt4);break;
                        }
                    }
                }
                if (keycode == Input.Keys.SPACE) {
                    if (nb > 0) {
                        b = joueur.poserBombe();
                        nb = ((b) ? nb - 1 : nb);
                        switch(tour){
                            case 0:     nbBombe1.setText(""+nb);break;
                            case 1:     nbBombe2.setText(""+nb);break;
                            case 2:     nbBombe3.setText(""+nb);break;
                            case 3:     nbBombe4.setText(""+nb);break;
                        }
                    }
                }
                if (keycode == Input.Keys.ENTER) {
                    jeu.map.explosion();
                    tour=(tour+1)%4;
                    switch(tour){
                        case 0:     porteExplo1.setText(""+joueurs[tour].getTaille());break;
                        case 1:     porteExplo2.setText(""+joueurs[tour].getTaille());break;
                        case 2:     porteExplo3.setText(""+joueurs[tour].getTaille());break;
                        case 3:     porteExplo4.setText(""+joueurs[tour].getTaille());break;
                    }
                    if(joueurs[tour].isPoussee()){
                        switch (tour){
                            case 0:     poussee1.setText("X");break;
                            case 1:     poussee2.setText("X");break;
                            case 2:     poussee3.setText("X");break;
                            case 3:     poussee4.setText("X");break;
                        }

                    }
                    int nbviv=0;
                    int viv=0;
                    for(int i=0;i<4;i++){
                        if(joueurs[i].isVivant()){
                            nbviv++;
                            viv=i;
                        }
                    }
                    if(nbviv==0){
                        jeu.map=null;
                        jeu.removeActor(jeu.findActor("Map"));

                        for(int i=0;i<4;i++){
                            jeu.removeActor(joueurs[i]);
                        }
                        frecommencer.delete();
                        game.victoire=new Victoire(game,jeu,"Match nul");
                        jeu.setEtat(game.victoire);
                        game.setScreen(game.victoire);


                    }
                    else if(nbviv==1){
                        jeu.map=null;
                        jeu.removeActor(jeu.findActor("Map"));
                        frecommencer.delete();
                        game.victoire=new Victoire(game,jeu,"Victoire joueur "+(viv+1));
                        for(int i=0;i<4;i++){
                            jeu.removeActor(joueurs[i]);
                        }
                        jeu.setEtat(game.victoire);
                        game.setScreen(game.victoire);
                    }
                    else {
                        while (!joueurs[tour].isVivant()) {
                            tour = (tour + 1) % 4;
                        }
                        pm = joueurs[tour].getPm();
                        nb = joueurs[tour].getNbBombe();
                        switch(tour){
                            case 0:     nbBombe1.setText(""+nb);break;
                            case 1:     nbBombe2.setText(""+nb);break;
                            case 2:     nbBombe3.setText(""+nb);break;
                            case 3:     nbBombe4.setText(""+nb);break;

                        }
                        switch(tour){
                            case 0:     jeu.removeActor(nbmvt1);nbmvt1.setText(""+pm);jeu.addActor(nbmvt1);porteExplo1.setText(""+joueurs[tour].getTaille());break;
                            case 1:     jeu.removeActor(nbmvt2);nbmvt2.setText(""+pm);jeu.addActor(nbmvt2);porteExplo2.setText(""+joueurs[tour].getTaille());break;
                            case 2:     jeu.removeActor(nbmvt3);nbmvt3.setText(""+pm);jeu.addActor(nbmvt3);porteExplo3.setText(""+joueurs[tour].getTaille());break;
                            case 3:     jeu.removeActor(nbmvt4);nbmvt4.setText(""+pm);jeu.addActor(nbmvt4);porteExplo4.setText(""+joueurs[tour].getTaille());break;
                        }
                    }


//Animation bombe + ennemis Map
                }
            }
        }
        if(keycode==Input.Keys.ESCAPE){
            try {
                fw = new FileWriter(f);
                fw.write(jeu.map.mapToTextP());
                for(int i=0;i<4;i++){
                    fw.write(joueurs[i].getC().posX()+" "+joueurs[i].getC().posY()+" 1212 "+joueurs[i].getId()+" "+pm+" "+nb+" "+joueurs[i].getPm()+" "+joueurs[i].isVivant()+" "+joueurs[i].getTaille()+" "+joueurs[i].getNbBombe()+" "+joueurs[i].isPoussee()+"\n");

                }
                fw.write("0 0 9999 "+tour+"\n");

                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            game.menuPause.setEtatAnterieur(game.multijoueur);
            jeu.setEtat(game.menuPause);
            game.setScreen(game.menuPause);

        }


        return true;
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {//test de fonction clic

        return true;
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        return false;
    }




    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)
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
}
