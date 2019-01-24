package com.bomber.game.Ecrans.Campagne;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bomber.game.*;
import com.bomber.game.Bonus.Bonus;
import com.bomber.game.Ecrans.Etat;
import com.bomber.game.Ecrans.Solo.Defaite;
import com.bomber.game.Ecrans.Victoire;
import com.bomber.game.Ennemis.EnnemiActifAggressif;
import com.bomber.game.Ennemis.EnnemiPassifAgressif;
import com.bomber.game.Ennemis.Ennemis;
import com.bomber.game.MapetObjet.Map;
import com.bomber.game.MapetObjet.Personnage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Campagne extends Etat implements Screen {
    int pm=5;
    int nb=1;
    Image back;

    ArrayList<Ennemis> ennemis=new ArrayList<Ennemis>();
    Image joueur;
    Image bombe;
    Image mouvement;
    Image pousse;
    Image explosion;

    Label nbmvt;
    Label nbBombe;
    Label porteExplo;
    Label poussee;

    int mapactuel; //Niveau actuel

    TextButton retour;

    Personnage personnage;

    Skin skin;

    Image player;

    File f;
    File frecommencer;
    FileWriter fw;
    FileWriter fwr;
    Dialog dialog;
    int u=0;


    private Bomberball game;
    public Campagne(Bomberball game,Jeu jeu) {
        super(jeu);
        this.game=game;
        File directory = new File (".");
        f = Gdx.files.internal("./SaveTempo/tmp.txt").file();
        frecommencer = Gdx.files.internal( "./SaveTempo/debut.txt").file();


    }

    public void setMapactuel(int e){
        this.mapactuel=e;
    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        System.out.println(mapactuel);
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        Bomberball.input.addProcessor(this);
        jeu.removeActor(jeu.map);
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


        if(f.exists()){
            jeu.removeActor(jeu.map);
            this.removeActor(jeu);
            jeu.map=null;
            if(jeu.recommencer){
                u=0;
                jeu.map=Map.mapFromStringN(Bomberball.loadFile(f));
                jeu.recommencer=false;
                for (int i=0;i<15;i++){
                    for(int j=0;j<13;j++){
                        if(jeu.map.getGrille()[i][j].getPersonnage()!=null){
                            pm=jeu.map.getGrille()[i][j].getPersonnage().getPm();
                            nb=jeu.map.getGrille()[i][j].getPersonnage().getNbBombe();
                        }
                    }
                }
                f.delete();
                try {
                    fwr = new FileWriter(frecommencer);
                    fwr.write(jeu.map.mapToTextN());
                    fwr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                jeu.map=Map.mapFromStringNP(Bomberball.loadFile(f),this.jeu);
                f.delete();
                pm=jeu.pmtmp1; //Remise à jour des valeurs de pm et du nb de bombes restantes
                jeu.pmtmp1=-1;
                nb=jeu.nbtmp1;
                jeu.nbtmp1=-1;
            }

        }
        else if(jeu.map==null){
            //Rien
        }
        else{
            u=0;
            for (int i=0;i<15;i++){
                for(int j=0;j<13;j++){
                    if(jeu.map.getGrille()[i][j].getPersonnage()!=null){
                        pm=jeu.map.getGrille()[i][j].getPersonnage().getPm();
                        nb=jeu.map.getGrille()[i][j].getPersonnage().getNbBombe();
                    }
                }
            }
            try {
                fwr = new FileWriter(frecommencer);
                fwr.write(jeu.map.mapToTextN());
                fwr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(jeu.difficulte!=-1){
                LinkedList<Ennemis> ennemis=new LinkedList<Ennemis>();
                for(int i=0;i<15;i++){
                    for(int j=0;j<13;j++){
                        if(jeu.map.getGrille()[i][j].getEnnemi()!=null){
                            ennemis.add(jeu.map.getGrille()[i][j].getEnnemi());
                        }
                    }
                }
                switch (jeu.difficulte){
                    case 1:
                        for(Ennemis en: ennemis){
                            if(en instanceof EnnemiActifAggressif || en instanceof EnnemiPassifAgressif){
                                en.setPortee(3);
                            }
                            en.setPm(1);
                        } break;
                    case 2:
                        for(Ennemis en: ennemis){
                            if(en instanceof EnnemiActifAggressif || en instanceof EnnemiPassifAgressif){
                                en.setPortee(5);
                            }
                            en.setPm(3);
                        } break;
                    case 3:
                        for(Ennemis en: ennemis){
                            if(en instanceof EnnemiActifAggressif || en instanceof EnnemiPassifAgressif){
                                en.setPortee(10);
                            }
                            en.setPm(5);
                        } break;
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
        jeu.map.setPosition(Gdx.graphics.getWidth()-(jeu.map.getGrille().length+2f)*Bomberball.taillecase ,0);
        jeu.map.setScaleY(27f/26f);
        joueur=new Image(new Texture(Gdx.files.internal("Panneau_joueur.png")));
        joueur.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        joueur.setHeight(3*Bomberball.taillecase);
        joueur.setPosition(0,Gdx.graphics.getHeight()-3*Bomberball.taillecase);

        personnage=jeu.map.findActor("Personnage");

        if(jeu.porteeBombe!=-1){
            personnage.setTaille(jeu.porteeBombe);
        }
        if(jeu.nbBombe!=-1){
            personnage.setNbBombe(jeu.nbBombe);
        }
        if(jeu.nbDeplaP!=-1){
            personnage.setPm(jeu.nbDeplaP);
        }

        if(mapactuel==1 && u==0){
            dialog = new Dialog("Tutoriel", skin, "dialog") {
                public void result(Object obj) {
                    System.out.println("result "+obj);
                }
            };
            dialog.text("Pour vous deplacer, appuyez sur les fleches directionnels ");
            dialog.setBounds(Gdx.graphics.getWidth()-(jeu.map.getGrille().length-1)*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase*13,Bomberball.taillecase*2);
            dialog.button("OK");

        }

        if(mapactuel==2 && u==0){
            dialog = new Dialog("Tutoriel", skin, "dialog") {
                public void result(Object obj) {
                    System.out.println("result "+obj);
                }
            };
            dialog.text("Dans ce niveau, vous allez decouvrir les differents bonus");
            dialog.setBounds(Gdx.graphics.getWidth()-(jeu.map.getGrille().length-1)*Bomberball.taillecase,5*Bomberball.taillecase,Bomberball.taillecase*13,Bomberball.taillecase*2);
            dialog.button("OK");

        }

        if(mapactuel==3 && u==0){
            dialog = new Dialog("Tutoriel", skin, "dialog") {
                public void result(Object obj) {
                    System.out.println("result "+obj);
                }
            };
            dialog.text("Dans ce niveau, vous allez decouvrir les differents types d'ennemis");
            dialog.setBounds(Gdx.graphics.getWidth()-(jeu.map.getGrille().length-1)*Bomberball.taillecase,5*Bomberball.taillecase,Bomberball.taillecase*13,Bomberball.taillecase*2);
            dialog.button("OK");
        }






        mouvement = new Image(new Texture(Gdx.files.internal("Nombre_mouvement.png")));
        mouvement.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        mouvement.setHeight(2*Bomberball.taillecase);
        mouvement.setPosition(0,Gdx.graphics.getHeight()-5*Bomberball.taillecase);

        nbmvt= new Label(""+personnage.getPm(),skin);
        nbmvt.setBounds(Bomberball.taillecase*6+50,Gdx.graphics.getHeight()-4*Bomberball.taillecase-20,nbmvt.getWidth(),nbmvt.getHeight()); //Positionnement à la main

        bombe=new Image(new Texture(Gdx.files.internal("Nombre_bombe.png")));
        bombe.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        bombe.setHeight(2*Bomberball.taillecase);
        bombe.setPosition(0,Gdx.graphics.getHeight()- 7*Bomberball.taillecase);

        nbBombe= new Label(""+personnage.getNbBombe(),skin);
        nbBombe.setBounds(Bomberball.taillecase*6+50,Gdx.graphics.getHeight()-6*Bomberball.taillecase-20,nbBombe.getWidth(),nbBombe.getHeight()); //Positionnement à la main

        explosion=new Image(new Texture(Gdx.files.internal("Portée_bombe.png")));
        explosion.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        explosion.setHeight(2*Bomberball.taillecase);
        explosion.setPosition(0,Gdx.graphics.getHeight()-9*Bomberball.taillecase);

        porteExplo = new Label(""+personnage.getTaille(),skin);
        porteExplo.setBounds(Bomberball.taillecase*6+50,Gdx.graphics.getHeight()-8*Bomberball.taillecase-20,porteExplo.getWidth(),porteExplo.getHeight()); //Positionnement à la main


        pousse=new Image(new Texture(Gdx.files.internal("icone_Bonus_pousser.png")));
        pousse.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        pousse.setHeight(2*Bomberball.taillecase);
        pousse.setPosition(0,Gdx.graphics.getHeight()-11*Bomberball.taillecase);
        if(personnage.isPoussee()){
            poussee = new Label("X",skin);
        }
        else{
            poussee = new Label("",skin);
        }
        poussee.setBounds(Bomberball.taillecase*6+50,Gdx.graphics.getHeight()-10*Bomberball.taillecase-20,poussee.getWidth(),poussee.getHeight());


        player=new Image(Bomberball.multiTexture[4]);
        player.setBounds(6*Bomberball.taillecase,Gdx.graphics.getHeight()-2*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        retour = new TextButton("Retour",skin);
        retour.setBounds(0,0,retour.getWidth(),retour.getHeight());







        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.map.suppActor();
                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.jeuSolo.removeActor(jeu);
                Bomberball.input.removeProcessor(game.campagne);
                frecommencer.delete();

                jeu.difficulte=-1;
                jeu.nbDeplaP=-1;
                jeu.porteeBombe=-1;
                jeu.nbBombe=-1;


                jeu.setEtat(game.menuSolo);
                game.setScreen(game.menuSolo);
            }
        });

        ennemis.clear();
        for (int i=1;i<jeu.map.getGrille().length-1;i++){
            for (int j=1;j<jeu.map.getGrille()[0].length-1;j++){
                if (jeu.map.getGrille()[i][j].getEnnemi()!=null){
                    ennemis.add(jeu.map.getGrille()[i][j].getEnnemi());
                }
            }
        }


        this.addActor(back);
        this.addActor(joueur);
        this.addActor(bombe);
        this.addActor(mouvement);
        this.addActor(explosion);
        this.addActor(pousse);
        this.addActor(player);
        this.addActor(nbmvt);
        this.addActor(nbBombe);
        this.addActor(porteExplo);
        this.addActor(poussee);
        this.addActor(retour);
        jeu.addActor(jeu.map);
        this.addActor(jeu);
        if(dialog!=null){
            this.addActor(dialog);
        }

        jeu.pmtmp1=-1;
        jeu.pmtmp2=-1;
        jeu.pmtmp3=-1;
        jeu.pmtmp4=-1;
        jeu.nbtmp1=-1;
        jeu.nbtmp2=-1;
        jeu.nbtmp3=-1;
        jeu.nbtmp4=-1;


    }

    @Override
    public boolean keyDown( int keycode) {//delpacement = fleche pas encore implementer
        Personnage joueur = jeu.map.findActor("Personnage");
        if(jeu.findActor("explo")==null) {

            if ((joueur != null) && (!joueur.hasActions())) {
                boolean b = false;
                if (keycode == Input.Keys.RIGHT) {
                    if (pm > 0) {
                        b = joueur.deplacerDroite();
                        pm = ((b) ? pm - 1 : pm);
                        this.removeActor(nbmvt);
                        nbmvt.setText(""+pm);
                        this.addActor(nbmvt);

                    }
                }

                if (keycode == Input.Keys.LEFT) {
                    if (pm > 0) {
                        b = joueur.deplacerGauche();
                        pm = ((b) ? pm - 1 : pm);
                        this.removeActor(nbmvt);
                        nbmvt.setText(""+pm);
                        this.addActor(nbmvt);
                    }
                }
                if (keycode == Input.Keys.DOWN) {
                    if (pm > 0) {
                        b = joueur.deplacerBas();
                        pm = ((b) ? pm - 1 : pm);
                        this.removeActor(nbmvt);
                        nbmvt.setText(""+pm);
                        this.addActor(nbmvt);
                    }
                }
                if (keycode == Input.Keys.UP) {
                    if (pm > 0) {
                        b = joueur.deplacerHaut();
                        pm = ((b) ? pm - 1 : pm);
                        this.removeActor(nbmvt);
                        nbmvt.setText(""+pm);
                        this.addActor(nbmvt);
                    }
                }
                if (keycode == Input.Keys.SPACE) {
                    if (nb > 0) {
                        b = joueur.poserBombe();
                        nb = ((b) ? nb - 1 : nb);
                        nbBombe.setText(""+nb);
                    }
                }
                if (keycode == Input.Keys.ENTER) {
                    jeu.map.explosion();
                    porteExplo.setText("" + personnage.getTaille());
                    if (joueur.isPoussee()) {
                        poussee.setText("X");
                    }
                    if (joueur.getC().getPorte() != null) {
                        jeu.removeActor(joueur);
                        jeu.removeActor(jeu.map);
                        jeu.map = null;

                        if(mapactuel==5){

                            game.victoire=new Victoire(game,jeu,"Vous avez battu le jeu ! Bravo !");
                            game.campagne.removeActor(jeu);
                            jeu.setEtat(game.victoire);
                            game.setScreen(game.victoire);
                        }
                        else{
                            game.victoireCampagne.setNiveaugag(mapactuel);
                            game.campagne.removeActor(jeu);
                            jeu.setEtat(game.victoireCampagne);
                            game.setScreen(game.victoireCampagne);
                        }

                    }
                    pm = joueur.getPm();
                    nb = joueur.getNbBombe();
                    nbBombe.setText("" + nb);
                    nbmvt.setText("" + pm);
                    if (joueur.isVivant()) {

                        if(ennemis.size()!=0) {
                            Bomberball.input.removeProcessor(this);
                            tourEnnemi();
                        }

                    }
                    if (!joueur.isVivant()) {

                        for (Ennemis en : ennemis) {
                            if (en.isVivant()) {
                                en.setAnimationdefaite();
                            }
                        }
                        jeu.addAction(new Action() {
                            float time = 0;

                            @Override
                            public boolean act(float delta) {
                                time += delta;
                                if (time > 3) {
                                    jeu.removeActor(jeu.map);
                                    jeu.map = null;
                                    game.defaite = new Defaite(game, jeu, "gdjdj");
                                    game.defaite.setEtat(game.campagne);
                                    game.campagne.removeActor(jeu);
                                    jeu.setEtat(game.defaite);
                                    game.setScreen(game.defaite);
                                    return true;
                                }
                                return false;
                            }
                        });

                    }
                }
            }
        }
        if (keycode == Input.Keys.ESCAPE) {
            try {
                fw = new FileWriter(f);
                fw.write(jeu.map.mapToTextNP());
                if(joueur.getC().getBombe()!=null){
                    fw.write(joueur.getId()+" "+pm+" "+nb+" 1\n"); //Le 1 indique d'une bombe est sur la position du joueur
                }
                else{
                    fw.write(joueur.getId()+" "+pm+" "+nb+" 0\n"); //Le 0 indique qu'il n'y a pas de bombe sur la position du joueur
                }
                fw.write("111 "); //Symbole de fin pour la fin de la mise à jour des personnages
                fw.write(""+joueur.getId());
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            jeu.map.suppActor();
            jeu.removeActor(jeu.map);
            jeu.map=null;
            game.campagne.removeActor(jeu);
            game.menuPause.setEtatAnterieur(this);
            jeu.setEtat(game.menuPause);
            game.setScreen(game.menuPause);
        }
        if(personnage.isVivant()) {
            if (u == 0 && mapactuel == 1 && pm == 2) {
                u++;
                dialog = new Dialog("Tutoriel", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                dialog.text("Appuyez sur ESPACE pour poser une bombe ");
                dialog.setBounds(Gdx.graphics.getWidth() - (jeu.map.getGrille().length - 1) * Bomberball.taillecase, Bomberball.taillecase, Bomberball.taillecase * 13, Bomberball.taillecase * 2);
                dialog.button("OK");
                dialog.show(jeu.getStage());
            }

            if (u == 1 && mapactuel == 1 && pm == 0) {
                u++;
                dialog = new Dialog("Tutoriel", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                dialog.text("Vous n'avez plus de points de deplacement, appuyez sur ENTREE pour passer votre tour ");
                dialog.setBounds(Gdx.graphics.getWidth() - (jeu.map.getGrille().length - 1) * Bomberball.taillecase, Bomberball.taillecase, Bomberball.taillecase * 13, Bomberball.taillecase * 2);
                dialog.button("OK");
                dialog.show(jeu.getStage());
            }

            if (u == 2 && mapactuel == 1 && pm == 4) {
                u++;
                dialog = new Dialog("Tutoriel", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                dialog.text("Votre objectif est d'atteindre la porte");
                dialog.setBounds(Gdx.graphics.getWidth() - (jeu.map.getGrille().length - 1) * Bomberball.taillecase, Bomberball.taillecase, Bomberball.taillecase * 13, Bomberball.taillecase * 2);
                dialog.button("OK");
                dialog.show(jeu.getStage());
            }

            if (u == 3 && mapactuel == 1 && pm == 2) {
                u++;
                dialog = new Dialog("Tutoriel", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                dialog.text("Les blocs de briques sont destructibles contrairement aux blocs de metal");
                dialog.setBounds(Gdx.graphics.getWidth() - (jeu.map.getGrille().length - 1) * Bomberball.taillecase, Bomberball.taillecase, Bomberball.taillecase * 13, Bomberball.taillecase * 2);
                dialog.button("OK");
                dialog.show(jeu.getStage());
            }

            if (u == 4 && mapactuel == 1 && joueur.getC().getPorte() != null) {
                u++;
                dialog = new Dialog("Tutoriel", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                dialog.text("Appuyez sur ENTREE pour passer au niveau suivant");
                dialog.setBounds(Gdx.graphics.getWidth() - (jeu.map.getGrille().length - 1) * Bomberball.taillecase, Bomberball.taillecase, Bomberball.taillecase * 13, Bomberball.taillecase * 2);
                dialog.button("OK");
                dialog.show(jeu.getStage());

            }

            if (u == 0 && mapactuel == 2 && joueur.getTaille() > 2) {
                u++;
                dialog = new Dialog("Tutoriel", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                dialog.text("La taille de l'explosion de votre bombe a augmente de 1");
                dialog.setBounds(Gdx.graphics.getWidth() - (jeu.map.getGrille().length - 1) * Bomberball.taillecase, Bomberball.taillecase, Bomberball.taillecase * 13, Bomberball.taillecase * 2);
                dialog.button("OK");
                dialog.show(jeu.getStage());
            }

            if (u == 1 && mapactuel == 2 && joueur.getPm() > 5) {
                u++;
                dialog = new Dialog("Tutoriel", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                dialog.text("Le nombre de points de deplacement a augmente de 1");
                dialog.setBounds(Gdx.graphics.getWidth() - (jeu.map.getGrille().length - 1) * Bomberball.taillecase, Bomberball.taillecase, Bomberball.taillecase * 13, Bomberball.taillecase * 2);
                dialog.button("OK");
                dialog.show(jeu.getStage());
            }

            if (u == 2 && mapactuel == 2 && joueur.getNbBombe() > 1) {
                u++;
                dialog = new Dialog("Tutoriel", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                dialog.text("Le nombre de bombe utilisable par tour a augmente de 1");
                dialog.setBounds(Gdx.graphics.getWidth() - (jeu.map.getGrille().length - 1) * Bomberball.taillecase, Bomberball.taillecase, Bomberball.taillecase * 13, Bomberball.taillecase * 2);
                dialog.button("OK");
                dialog.show(jeu.getStage());
            }

            if (u == 3 && mapactuel == 2 && joueur.isPoussee()) {
                u++;
                dialog = new Dialog("Tutoriel", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                dialog.text("Vous avez desormais la possibilite de pousser une bombe");
                dialog.setBounds(Gdx.graphics.getWidth() - (jeu.map.getGrille().length - 1) * Bomberball.taillecase, Bomberball.taillecase, Bomberball.taillecase * 13, Bomberball.taillecase * 2);
                dialog.button("OK");
                dialog.show(jeu.getStage());

            }

            if (u == 0 && mapactuel == 3 && pm == 3) {
                u++;
                dialog = new Dialog("Tutoriel", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                dialog.text("L'ennemi en bas a gauche est un ennemi passif qui suit un chemin predefini");
                dialog.setBounds(Gdx.graphics.getWidth() - (jeu.map.getGrille().length - 1) * Bomberball.taillecase, 6 * Bomberball.taillecase, Bomberball.taillecase * 13, Bomberball.taillecase * 2);
                dialog.button("OK");
                dialog.show(jeu.getStage());
            }

            if (u == 1 && mapactuel == 3 && pm == 1) {
                u++;
                dialog = new Dialog("Tutoriel", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                dialog.text("L'ennemi en bas a droite est un ennemi actif qui choisit le chemin le plus long");
                dialog.setBounds(Gdx.graphics.getWidth() - (jeu.map.getGrille().length - 1) * Bomberball.taillecase, 6 * Bomberball.taillecase, Bomberball.taillecase * 13, Bomberball.taillecase * 2);
                dialog.button("OK");
                dialog.show(jeu.getStage());
            }

            if (u == 2 && mapactuel == 3 && pm == 4) {
                u++;
                dialog = new Dialog("Tutoriel", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                dialog.text("Les ennemis en haut a gauche sont des ennemis passifs-agressifs qui suivent \nun chemin predefini mais qui vous suivent s'ils vous reperent");
                dialog.setBounds(Gdx.graphics.getWidth() - (jeu.map.getGrille().length - 1) * Bomberball.taillecase, 6 * Bomberball.taillecase, Bomberball.taillecase * 13, Bomberball.taillecase * 2);
                dialog.button("OK");
                dialog.show(jeu.getStage());
            }

            if (u == 3 && mapactuel == 3 && pm == 2) {
                u++;
                dialog = new Dialog("Tutoriel", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                dialog.text("Les ennemis en haut a droite sont des ennemis actifs-agressifs qui choisissent\n le chemin le plus long mais qui vous suivent s'ils vous reperent");
                dialog.setBounds(Gdx.graphics.getWidth() - (jeu.map.getGrille().length - 1) * Bomberball.taillecase, 6 * Bomberball.taillecase, Bomberball.taillecase * 13, Bomberball.taillecase * 2);
                dialog.button("OK");
                dialog.show(jeu.getStage());

            }
            if (u == 4 && mapactuel == 3 && personnage.getC().getPorte() != null) {
                u++;
                dialog = new Dialog("Tutoriel", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                dialog.text("Le tutoriel est fini ! Appuyez sur ENTREE pour terminer le niveau ");
                dialog.setBounds(Gdx.graphics.getWidth() - (jeu.map.getGrille().length - 1) * Bomberball.taillecase, 6 * Bomberball.taillecase, Bomberball.taillecase * 13, Bomberball.taillecase * 2);
                dialog.button("OK");
                dialog.show(jeu.getStage());
            }

        }

        return true;
    }

    public void tourEnnemi() {

        this.addAction(new Action() {
            Ennemis en=ennemis.get(0);
            int i=-1;
            float time=0;

            @Override
            public boolean act(float delta) {
                time+=delta;
                if (time>1.01&&en.getActions().size==1) {
                    i++;
                    if (i == ennemis.size()) {
                        Bomberball.input.addProcessor((Campagne) target);
                        return true;
                    }
                    en = ennemis.get(i);
                    if (en.isVivant()) {

                        en.deplacer();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {

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
        Bomberball.stg.clear();
        jeu.removeActor(jeu.map);
        Bomberball.input.removeProcessor(this);

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
