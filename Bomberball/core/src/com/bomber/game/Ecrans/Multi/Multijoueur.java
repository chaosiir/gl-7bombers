package com.bomber.game.Ecrans.Multi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.bomber.game.Bomberball;
import com.bomber.game.Bonus.Bonus;
import com.bomber.game.Ecrans.Etat;
import com.bomber.game.Jeu;
import com.bomber.game.MapetObjet.Map;
import com.bomber.game.MapetObjet.Personnage;
import com.bomber.game.Ecrans.Victoire;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe qui affiche le jeu dans le multijoueur
 */
public class Multijoueur extends Etat implements Screen {
    int pm=5;                                           //Nombre de pm initialisé
    int nb=1;                                           //Nombre de bombe initial
    private Bomberball game;                            //Instance de la classe principale
    private Personnage joueurs[]=new Personnage[4];     //Tableau des joueurs
    private int tour=0;                                 //Variable qui s'incrémente à chaque personnage
    Image back;                                         //Image de l'arrière-plan

    Skin skin;                                          //Caractéristiques des éléments graphiques

    Image joueur1;                                      //Panneau du joueur 1
    Image bombe1;                                       //Affiche le nombre de bombe du joueur 1
    Image mouvement1;                                   //Affiche le nombre des mouvements du joueur 1
    Image pousse1;                                      //Affiche si le joueur 1 a la poussée
    Image explosion1;                                   //Affiche la portée de l'explosion du joueur 1
    Image player1;                                      //Affichage de l'image du joueur 1

    Label nbmvt1;                                       //Contient le nombre de points de mouvement actuel du joueur 1
    Label nbBombe1;                                     //Contient le nombre de bombe actuel du joueur 1
    Label  porteExplo1;                                 //Contient la valeur de la portée du joueur 1

    Personnage personnage1;                             //Stockage du personnage 1
    Personnage personnage2;                             //Stockage du personnage 2
    Personnage personnage3;                             //Stockage du personnage 3
    Personnage personnage4;                             //Stockage du personnage 3

    Label nbmvt2;                                       //Contient le nombre de points de mouvement actuel du joueur 2
    Label nbBombe2;                                     //Contient le nombre de bombe actuel du joueur 2
    Label porteExplo2;                                  //Contient la valeur de la portée du joueur 2

    Label nbmvt3;                                       //Contient le nombre de points de mouvement actuel du joueur 3
    Label nbBombe3;                                     //Contient le nombre de bombe actuel du joueur 3
    Label porteExplo3;                                  //Contient la valeur de la portée du joueur 3

    Label nbmvt4;                                       //Contient le nombre de points de mouvement actuel du joueur 4
    Label nbBombe4;                                     //Contient le nombre de bombe actuel du joueur 4
    Label porteExplo4;                                  //Contient la valeur de la portée du joueur 4

    Label poussee1;                                     //Contient un X si le joueur 1 peut poussé sinon rien
    Label poussee2;                                     //Contient un X si le joueur 2 peut poussé sinon rien
    Label poussee3;                                     //Contient un X si le joueur 3 peut poussé sinon rien
    Label poussee4;                                     //Contient un X si le joueur 4 peut poussé sinon rien



    Image joueur2;                                          //Panneau du joueur 2
    Image bombe2;                                           //Affiche le nombre de bombe du joueur 2
    Image mouvement2;                                       //Affiche le nombre des mouvements du joueur 2
    Image pousse2;                                          //Affiche si le joueur 2 a la poussée
    Image explosion2;                                       //Affiche la portée de l'explosion du joueur 2
    Image player2;                                          //Affichage de l'image du joueur 2

    Image joueur3;                                          //Panneau du joueur 3
    Image bombe3;                                           //Affiche le nombre de bombe du joueur 3
    Image mouvement3;                                       //Affiche le nombre des mouvements du joueur 3
    Image pousse3;                                          //Affiche si le joueur 3 a la poussée
    Image explosion3;                                       //Affiche la portée de l'explosion du joueur 3
    Image player3;                                          //Affichage de l'image du joueur 3

    Image joueur4;                                          //Panneau du joueur 4
    Image bombe4;                                           //Affiche le nombre de bombe du joueur 4
    Image mouvement4;                                       //Affiche le nombre des mouvements du joueur 4
    Image pousse4;                                          //Affiche si le joueur 4 a la poussée
    Image explosion4;                                       //Affiche la portée de l'explosion du joueur 4
    Image player4;                                          //Affichage de l'image du joueur 4

    File f;                                                 //Fichier qui sert pour la pause ou pour recommencer un niveau
    File frecommencer;                                      //Fichier qui permet de sauvegarder le début d'une partie
    FileWriter fw;                                          //Cela permet d'écrire dans le fichier f
    FileWriter fwr;                                         //Cela permet d'écrire dans le fichier frecommencer

    /**
     * Constructeur de la classe Multijoueur
     * @param game  instance de la classe principale nécessaire au bon fonctionnement
     * @param jeu   instance de jeu pour gérer l'affichage
     */
    public Multijoueur(Bomberball game,Jeu jeu) {
        super(jeu);
        this.game = game;
        f = Gdx.files.internal("./SaveTempo/tmp.txt").file();
        frecommencer = Gdx.files.internal("./SaveTempo/debut.txt").file();

    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    /** Majorité de commentaire similaire à Campagne **/
    public void show() {
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        Bomberball.input.addProcessor(this);
        jeu.removeActor(jeu.map);
        jeu.removeActor(jeu.findActor("Map"));
        skin=new Skin(Gdx.files.internal("uiskin.json"));

        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        back.setName("Je suis ton arrière plan");

        if(f.exists()){
            if(jeu.recommencer){
                jeu.map=Map.mapFromStringN(Bomberball.loadFile(f));
                jeu.recommencer=false;
                tour=0;
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
                pm=jeu.nbDeplaP;
                nb=jeu.nbBombe;
            }
            else{
                jeu.map=Map.mapFromStringNP(Bomberball.loadFile(f),this.jeu);
                f.delete();
                switch (tour%4){                                                //En fonction du tour, on peut déterminer le joueur qui doit reprendre des valeurs de pm et nb
                    case 0:                                                     //particulière en revenant d'une pause afin qu'il ne retrouve pas ses pm et nb après une pause
                        if(jeu.pmtmp1!=-1){
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
                    case 1:
                        if(jeu.pmtmp2!=-1){
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
                    case 2:
                        if(jeu.pmtmp3!=-1){
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
                    case 3:
                        if(jeu.pmtmp4!=-1){
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
       else if(jeu.map==null){                                                      //En fonction des paramètres que le joueur a rentré, on peut générer une map personnalisé
            tour=0;
            if(jeu.nbBonus!=-1 && jeu.nbBlocD!=-1){
                jeu.map=Map.generatePvp(jeu.nbBlocD, jeu.nbBonus);
                jeu.nbBonus=-1;
                jeu.nbBlocD=-1;
                try {
                    fwr = new FileWriter(frecommencer);
                    fwr.write(jeu.map.mapToTextN());
                    fwr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(jeu.nbBonus!=-1 && jeu.nbBlocD==-1){
                jeu.map=Map.generatePvp(40, jeu.nbBonus);
                jeu.nbBonus=-1;
                try {
                    fwr = new FileWriter(frecommencer);
                    fwr.write(jeu.map.mapToTextN());
                    fwr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(jeu.nbBonus==-1 && jeu.nbBlocD!=-1){
                jeu.map=Map.generatePvp(jeu.nbBlocD, 12);
                jeu.nbBlocD=-1;
                try {
                    fwr = new FileWriter(frecommencer);
                    fwr.write(jeu.map.mapToTextN());
                    fwr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                jeu.map=Map.generatePvp(40,12);
                try {
                    fwr = new FileWriter(frecommencer);
                    fwr.write(jeu.map.mapToTextN());
                    fwr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (int i=0;i<15;i++){
                for(int j=0;j<13;j++){
                    if(jeu.map.getGrille()[i][j].getPersonnage()!=null){

                        pm=jeu.map.getGrille()[i][j].getPersonnage().getPm();
                        nb=jeu.map.getGrille()[i][j].getPersonnage().getNbBombe();
                    }
                }
            }
            if(jeu.nbDeplaP!=-1){
                pm=jeu.nbDeplaP;
            }
            if(jeu.nbBombe!=-1){
                nb=jeu.nbBombe;
            }

        }
        else{
            tour=0;
            try {
                fwr = new FileWriter(frecommencer);
                fwr.write(jeu.map.mapToTextN());
                fwr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pm=jeu.nbDeplaP;
            nb=jeu.nbBombe;
        }

        int a=0;
        for(int i=0;i<jeu.map.getGrille().length;i++){
            for (int j=0;j<jeu.map.getGrille()[1].length;j++){
                Personnage p=jeu.map.getGrille()[i][j].getPersonnage();
                if(p!=null){
                    if(jeu.porteeBombe!=-1){
                        p.setTaille(jeu.porteeBombe);
                    }
                    if(jeu.nbDeplaP!=-1){
                        p.setPm(jeu.nbDeplaP);
                    }
                    if(jeu.nbBombe!=-1){
                        p.setNbBombe(jeu.nbBombe);
                    }
                    joueurs[p.getId()]=p;
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
        joueur1.setPosition(0,Gdx.graphics.getHeight()-9*Bomberball.taillecase);


        mouvement1 = new Image(new Texture(Gdx.files.internal("Nombre_mouvement.png")));
        mouvement1.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        mouvement1.setHeight(Bomberball.taillecase);
        mouvement1.setPosition(0,Gdx.graphics.getHeight()-10*Bomberball.taillecase);

        nbmvt1= new Label(""+personnage1.getPm(),skin);
        nbmvt1.setBounds(3*Bomberball.taillecase+30,Gdx.graphics.getHeight()-9*Bomberball.taillecase-50,nbmvt1.getWidth(),nbmvt1.getHeight()); //Positionnement à la main

        bombe1=new Image(new Texture(Gdx.files.internal("Nombre_bombe.png")));
        bombe1.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        bombe1.setHeight(Bomberball.taillecase);
        bombe1.setPosition(0,Gdx.graphics.getHeight()- 11*Bomberball.taillecase);

        nbBombe1= new Label(""+personnage1.getNbBombe(),skin);
        nbBombe1.setBounds(Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-10*Bomberball.taillecase-60,nbBombe1.getWidth(),nbBombe1.getHeight()); //Positionnement à la main

        explosion1=new Image(new Texture(Gdx.files.internal("Portée_bombe.png")));
        explosion1.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        explosion1.setHeight(Bomberball.taillecase);
        explosion1.setPosition(0,Gdx.graphics.getHeight()-12*Bomberball.taillecase);

        porteExplo1 = new Label(""+personnage1.getTaille(),skin);
        porteExplo1.setBounds(Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-11*Bomberball.taillecase-60,porteExplo1.getWidth(),porteExplo1.getHeight()); //Positionnement à la main


        pousse1=new Image(new Texture(Gdx.files.internal("icone_Bonus_pousser.png")));
        pousse1.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        pousse1.setHeight(Bomberball.taillecase);
        pousse1.setPosition(0,Gdx.graphics.getHeight()-13*Bomberball.taillecase);

        poussee1=new Label("",skin);
        poussee2=new Label("",skin);
        poussee3=new Label("",skin);
        poussee4=new Label("",skin);

        poussee1.setPosition(Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-13*Bomberball.taillecase);





        player1=new Image(Bomberball.multiTexture[4]);
        player1.setBounds(3*Bomberball.taillecase+30,Gdx.graphics.getHeight()-8*Bomberball.taillecase-50,Bomberball.taillecase,Bomberball.taillecase);


        joueur2=new Image(new Texture(Gdx.files.internal("Panneau_joueur.png")));
        joueur2.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        joueur2.setHeight(2*Bomberball.taillecase);
        joueur2.setPosition(0,Gdx.graphics.getHeight()-2*Bomberball.taillecase);


        mouvement2 = new Image(new Texture(Gdx.files.internal("Nombre_mouvement.png")));
        mouvement2.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        mouvement2.setHeight(Bomberball.taillecase);
        mouvement2.setPosition(0,Gdx.graphics.getHeight()-3*Bomberball.taillecase);

        nbmvt2= new Label(""+personnage2.getPm(),skin);
        nbmvt2.setBounds(3*Bomberball.taillecase+30,Gdx.graphics.getHeight()-2*Bomberball.taillecase-50,nbmvt2.getWidth(),nbmvt2.getHeight()); //Positionnement à la main

        bombe2=new Image(new Texture(Gdx.files.internal("Nombre_bombe.png")));
        bombe2.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        bombe2.setHeight(Bomberball.taillecase);
        bombe2.setPosition(0,Gdx.graphics.getHeight()- 4*Bomberball.taillecase);

        nbBombe2= new Label(""+personnage2.getNbBombe(),skin);
        nbBombe2.setBounds(Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-3*Bomberball.taillecase-60,nbBombe1.getWidth(),nbBombe1.getHeight()); //Positionnement à la main

        explosion2=new Image(new Texture(Gdx.files.internal("Portée_bombe.png")));
        explosion2.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        explosion2.setHeight(Bomberball.taillecase);
        explosion2.setPosition(0,Gdx.graphics.getHeight()-5*Bomberball.taillecase);

        porteExplo2 = new Label(""+personnage2.getTaille(),skin);
        porteExplo2.setBounds(Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-4*Bomberball.taillecase-60,porteExplo1.getWidth(),porteExplo1.getHeight()); //Positionnement à la main


        pousse2=new Image(new Texture(Gdx.files.internal("icone_Bonus_pousser.png")));
        pousse2.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        pousse2.setHeight(Bomberball.taillecase);
        pousse2.setPosition(0,Gdx.graphics.getHeight()-6*Bomberball.taillecase);

        poussee2.setPosition(Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-6*Bomberball.taillecase);

        player2=new Image(Bomberball.multiTexture[20]);
        player2.setBounds(3*Bomberball.taillecase+30,Gdx.graphics.getHeight()-Bomberball.taillecase-50,Bomberball.taillecase,Bomberball.taillecase);

        joueur3=new Image(new Texture(Gdx.files.internal("Panneau_joueur.png")));
        joueur3.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        joueur3.setHeight(2*Bomberball.taillecase);
        joueur3.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-9*Bomberball.taillecase);


        mouvement3 = new Image(new Texture(Gdx.files.internal("Nombre_mouvement.png")));
        mouvement3.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        mouvement3.setHeight(Bomberball.taillecase);
        mouvement3.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-10*Bomberball.taillecase);

        nbmvt3= new Label(""+personnage3.getPm(),skin);
        nbmvt3.setBounds(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-9*Bomberball.taillecase-50,nbmvt3.getWidth(),nbmvt3.getHeight()); //Positionnement à la main

        bombe3=new Image(new Texture(Gdx.files.internal("Nombre_bombe.png")));
        bombe3.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        bombe3.setHeight(Bomberball.taillecase);
        bombe3.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()- 11*Bomberball.taillecase);

        nbBombe3= new Label(""+personnage3.getNbBombe(),skin);
        nbBombe3.setBounds(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-10*Bomberball.taillecase-60,nbBombe3.getWidth(),nbBombe3.getHeight()); //Positionnement à la main

        explosion3=new Image(new Texture(Gdx.files.internal("Portée_bombe.png")));
        explosion3.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        explosion3.setHeight(Bomberball.taillecase);
        explosion3.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-12*Bomberball.taillecase);

        porteExplo3 = new Label(""+personnage3.getTaille(),skin);
        porteExplo3.setBounds(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-11*Bomberball.taillecase-60,porteExplo1.getWidth(),porteExplo1.getHeight()); //Positionnement à la main


        pousse3=new Image(new Texture(Gdx.files.internal("icone_Bonus_pousser.png")));
        pousse3.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        pousse3.setHeight(Bomberball.taillecase);
        pousse3.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-13*Bomberball.taillecase);

        poussee3.setPosition(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-13*Bomberball.taillecase);

        player3=new Image(Bomberball.multiTexture[21]);
        player3.setBounds(jeu.map.getX()+jeu.map.tailleX()+17*Bomberball.taillecase+3*Bomberball.taillecase,Gdx.graphics.getHeight()-8*Bomberball.taillecase-50,Bomberball.taillecase,Bomberball.taillecase);

        joueur4=new Image(new Texture(Gdx.files.internal("Panneau_joueur.png")));
        joueur4.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        joueur4.setHeight(2*Bomberball.taillecase);
        joueur4.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-2*Bomberball.taillecase);


        mouvement4 = new Image(new Texture(Gdx.files.internal("Nombre_mouvement.png")));
        mouvement4.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        mouvement4.setHeight(Bomberball.taillecase);
        mouvement4.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-3*Bomberball.taillecase);

        nbmvt4= new Label(""+personnage4.getPm(),skin);
        nbmvt4.setBounds(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-2*Bomberball.taillecase-50,nbmvt3.getWidth(),nbmvt3.getHeight()); //Positionnement à la main

        bombe4=new Image(new Texture(Gdx.files.internal("Nombre_bombe.png")));
        bombe4.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        bombe4.setHeight(Bomberball.taillecase);
        bombe4.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()- 4*Bomberball.taillecase);

        nbBombe4= new Label(""+personnage4.getNbBombe(),skin);
        nbBombe4.setBounds(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-3*Bomberball.taillecase-60,nbBombe3.getWidth(),nbBombe3.getHeight()); //Positionnement à la main

        explosion4=new Image(new Texture(Gdx.files.internal("Portée_bombe.png")));
        explosion4.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        explosion4.setHeight(Bomberball.taillecase);
        explosion4.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-5*Bomberball.taillecase);

        porteExplo4 = new Label(""+personnage4.getTaille(),skin);
        porteExplo4.setBounds(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-4*Bomberball.taillecase-60,porteExplo1.getWidth(),porteExplo1.getHeight()); //Positionnement à la main


        pousse4=new Image(new Texture(Gdx.files.internal("icone_Bonus_pousser.png")));
        pousse4.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        pousse4.setHeight(Bomberball.taillecase);
        pousse4.setPosition(jeu.map.getX()+17f*Bomberball.taillecase,Gdx.graphics.getHeight()-6*Bomberball.taillecase);

        poussee4.setPosition(jeu.map.getX()+17f*Bomberball.taillecase+Bomberball.taillecase*3+30,Gdx.graphics.getHeight()-6*Bomberball.taillecase);

        player4=new Image(Bomberball.multiTexture[22]);
        player4.setBounds(jeu.map.getX()+jeu.map.tailleX()+17*Bomberball.taillecase+3*Bomberball.taillecase,Gdx.graphics.getHeight()-Bomberball.taillecase-50,Bomberball.taillecase,Bomberball.taillecase);

        //Modification en fonction du nombre de joueurs
        if(jeu.nbJoueur==2){
            personnage3.setVivant(false);
            personnage4.setVivant(false);
            this.addActor(back);
            this.addActor(joueur1);
            this.addActor(mouvement1);
            this.addActor(nbmvt1);
            this.addActor(bombe1);
            this.addActor(nbBombe1);
            this.addActor(explosion1);
            this.addActor(porteExplo1);
            this.addActor(pousse1);
            this.addActor(player1);
            this.addActor(joueur2);
            this.addActor(mouvement2);
            this.addActor(nbmvt2);
            this.addActor(bombe2);
            this.addActor(nbBombe2);
            this.addActor(explosion2);
            this.addActor(porteExplo2);
            this.addActor(pousse2);
            this.addActor(player2);
            jeu.addActor(jeu.map);
            this.addActor(jeu);
        }
        else if(jeu.nbJoueur==3){
            personnage4.setVivant(false);
            this.addActor(back);
            this.addActor(joueur1);
            this.addActor(mouvement1);
            this.addActor(nbmvt1);
            this.addActor(bombe1);
            this.addActor(nbBombe1);
            this.addActor(explosion1);
            this.addActor(porteExplo1);
            this.addActor(pousse1);
            this.addActor(player1);
            this.addActor(joueur2);
            this.addActor(mouvement2);
            this.addActor(nbmvt2);
            this.addActor(bombe2);
            this.addActor(nbBombe2);
            this.addActor(explosion2);
            this.addActor(porteExplo2);
            this.addActor(pousse2);
            this.addActor(player2);
            this.addActor(joueur3);
            this.addActor(mouvement3);
            this.addActor(nbmvt3);
            this.addActor(bombe3);
            this.addActor(nbBombe3);
            this.addActor(explosion3);
            this.addActor(porteExplo3);
            this.addActor(pousse3);
            this.addActor(player3);
            jeu.addActor(jeu.map);
            this.addActor(jeu);
        }
        else{
            this.addActor(back);
            this.addActor(joueur1);
            this.addActor(mouvement1);
            this.addActor(nbmvt1);
            this.addActor(bombe1);
            this.addActor(nbBombe1);
            this.addActor(explosion1);
            this.addActor(porteExplo1);
            this.addActor(pousse1);
            this.addActor(player1);
            this.addActor(joueur2);
            this.addActor(mouvement2);
            this.addActor(nbmvt2);
            this.addActor(bombe2);
            this.addActor(nbBombe2);
            this.addActor(explosion2);
            this.addActor(porteExplo2);
            this.addActor(pousse2);
            this.addActor(player2);
            this.addActor(joueur3);
            this.addActor(mouvement3);
            this.addActor(nbmvt3);
            this.addActor(bombe3);
            this.addActor(nbBombe3);
            this.addActor(explosion3);
            this.addActor(porteExplo3);
            this.addActor(pousse3);
            this.addActor(player3);
            this.addActor(joueur4);
            this.addActor(mouvement4);
            this.addActor(nbmvt4);
            this.addActor(bombe4);
            this.addActor(nbBombe4);
            this.addActor(explosion4);
            this.addActor(porteExplo4);
            this.addActor(pousse4);
            this.addActor(player4);
            jeu.addActor(jeu.map);
            this.addActor(jeu);
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
    /**
     * Effectue l'action liée à un appui de touche en fonction de la touche appuyée
     * @param keycode code de la touche appuyée
     */
    public boolean keyDown( int keycode) {
        Personnage joueur = joueurs[tour%4];
        if(jeu.findActor("explo")==null) {
            if ((joueur != null) && (!joueur.hasActions())) {                                                           //Cette méthode est la même que le mode solo mais
                boolean b ;                                                                                             //adaptée à plusieurs personnages sans tour ennemis
                if (keycode == Input.Keys.RIGHT) {
                    if (pm > 0) {
                        b = joueur.deplacerDroite();
                        pm = ((b) ? pm - 1 : pm);
                        switch(tour%4){
                            case 0:     this.removeActor(nbmvt1);nbmvt1.setText(""+pm);this.addActor(nbmvt1);break;
                            case 1:     this.removeActor(nbmvt2);nbmvt2.setText(""+pm);this.addActor(nbmvt2);break;
                            case 2:     this.removeActor(nbmvt3);nbmvt3.setText(""+pm);this.addActor(nbmvt3);break;
                            case 3:     this.removeActor(nbmvt4);nbmvt4.setText(""+pm);this.addActor(nbmvt4);break;
                        }
                    }
                }

                if (keycode == Input.Keys.LEFT) {
                    if (pm > 0) {
                        b = joueur.deplacerGauche();
                        pm = ((b) ? pm - 1 : pm);
                        switch(tour%4){
                            case 0:     this.removeActor(nbmvt1);nbmvt1.setText(""+pm);this.addActor(nbmvt1);break;
                            case 1:     this.removeActor(nbmvt2);nbmvt2.setText(""+pm);this.addActor(nbmvt2);break;
                            case 2:     this.removeActor(nbmvt3);nbmvt3.setText(""+pm);this.addActor(nbmvt3);break;
                            case 3:     this.removeActor(nbmvt4);nbmvt4.setText(""+pm);this.addActor(nbmvt4);break;
                        }
                    }
                }
                if (keycode == Input.Keys.DOWN) {
                    if (pm > 0) {
                        b = joueur.deplacerBas();
                        pm = ((b) ? pm - 1 : pm);
                        switch(tour%4){
                            case 0:     this.removeActor(nbmvt1);nbmvt1.setText(""+pm);this.addActor(nbmvt1);break;
                            case 1:     this.removeActor(nbmvt2);nbmvt2.setText(""+pm);this.addActor(nbmvt2);break;
                            case 2:     this.removeActor(nbmvt3);nbmvt3.setText(""+pm);this.addActor(nbmvt3);break;
                            case 3:     this.removeActor(nbmvt4);nbmvt4.setText(""+pm);this.addActor(nbmvt4);break;
                        }
                    }
                }
                if (keycode == Input.Keys.UP) {
                    if (pm > 0) {
                        b = joueur.deplacerHaut();
                        pm = ((b) ? pm - 1 : pm);
                        switch(tour%4){
                            case 0:     this.removeActor(nbmvt1);nbmvt1.setText(""+pm);this.addActor(nbmvt1);break;
                            case 1:     this.removeActor(nbmvt2);nbmvt2.setText(""+pm);this.addActor(nbmvt2);break;
                            case 2:     this.removeActor(nbmvt3);nbmvt3.setText(""+pm);this.addActor(nbmvt3);break;
                            case 3:     this.removeActor(nbmvt4);nbmvt4.setText(""+pm);this.addActor(nbmvt4);break;
                        }
                    }
                }
                if (keycode == Input.Keys.SPACE) {
                    if (nb > 0) {
                        b = joueur.poserBombe();
                        nb = ((b) ? nb - 1 : nb);
                        switch(tour%4){
                            case 0:     nbBombe1.setText(""+nb);break;
                            case 1:     nbBombe2.setText(""+nb);break;
                            case 2:     nbBombe3.setText(""+nb);break;
                            case 3:     nbBombe4.setText(""+nb);break;
                        }
                    }
                }
                if (keycode == Input.Keys.ENTER) {
                    jeu.map.explosion();
                    tour++;
                    System.out.println(tour);
                    if(tour%8==0 && tour>65 && tour<100){
                        jeu.map.alertecontour(((tour-64)/8));
                    }
                    if(tour%8==4 && tour>65 && tour<103){
                        jeu.map.rapprochementDesMurs(((tour-64)/8));
                    }
                    switch(tour%4){
                        case 0:     porteExplo1.setText(""+joueurs[tour%4].getTaille());break;
                        case 1:     porteExplo2.setText(""+joueurs[tour%4].getTaille());break;
                        case 2:     porteExplo3.setText(""+joueurs[tour%4].getTaille());break;
                        case 3:     porteExplo4.setText(""+joueurs[tour%4].getTaille());break;
                    }
                    if(joueurs[tour%4].isPoussee()){
                        switch (tour%4){
                            case 0:     poussee1.setText("X");break;
                            case 1:     poussee2.setText("X");break;
                            case 2:     poussee3.setText("X");break;
                            case 3:     poussee4.setText("X");break;
                        }

                    }
                    int nbviv=0;
                    for(int i=0;i<4;i++){
                        if(joueurs[i].isVivant()){
                            nbviv++;
                        }
                    }
                    if(nbviv==0){
                        tour=0;
                        MoveByAction action=new MoveByAction();
                        action.setDuration(12);
                        action.setAmount(0,0);
                        joueurs[0].addAction(action);
                        joueurs[1].addAction(action);
                        joueurs[2].addAction(action);
                        joueurs[3].addAction(action);
                        jeu.addAction(new Action() {
                            float time = 0;

                            @Override
                            public boolean act(float delta) {
                                time += delta;
                                if (time > 3) {
                                    tour=0;
                                    jeu.map = null;
                                    jeu.removeActor(jeu.findActor("Map"));

                                    for (int i = 0; i < 4; i++) {
                                        jeu.removeActor(joueurs[i]);
                                    }
                                    jeu.porteeBombe=-1;
                                    jeu.nbDeplaP=-1;
                                    jeu.nbBombe=-1;
                                    jeu.removeActor(jeu.map);
                                    jeu.map=null;
                                    game.multijoueur.removeActor(jeu);
                                    game.victoire = new Victoire(game, jeu, "                         Match nul");
                                    jeu.setEtat(game.victoire);
                                    game.setScreen(game.victoire);
                                    return true;
                                }
                                return false;
                            }
                        });

                    }
                    else if(nbviv==1){
                        tour=0;
                        MoveByAction action=new MoveByAction();
                        action.setDuration(16);
                        action.setAmount(0,0);
                        joueurs[0].addAction(action);               //on met une action qui ne fait rien à tout les joueur pour qu'il ne puissent pas bouger
                        joueurs[1].addAction(action);               //et on attend 16/4 =>4s
                        joueurs[2].addAction(action);
                        joueurs[3].addAction(action);
                        jeu.addAction(new Action() {
                            float time=0;
                            @Override
                            public boolean act(float delta) {
                                time+=delta;
                                if(time>3){
                                    jeu.map=null;
                                    int viv=0;
                                    tour=0;
                                    for(int i=0;i<4;i++){
                                        if(joueurs[i].isVivant()){
                                            viv=i;
                                        }
                                    }
                                    jeu.removeActor(jeu.findActor("Map"));
                                    game.victoire=new Victoire(game,jeu,"                     Victoire joueur "+(viv+1));
                                    for(int i=0;i<4;i++){
                                        jeu.removeActor(joueurs[i]);
                                    }

                                    jeu.porteeBombe=-1;
                                    jeu.nbDeplaP=-1;
                                    jeu.nbBombe=-1;
                                    jeu.removeActor(jeu.map);
                                    jeu.map=null;
                                    game.multijoueur.removeActor(jeu);

                                    jeu.setEtat(game.victoire);
                                    game.setScreen(game.victoire);
                                    return true;
                                }
                                return false;
                            }
                        });

                    }
                    else {
                        while (!joueurs[tour%4].isVivant()) {
                            tour ++;
                            System.out.println(tour);
                            if(tour%8==0 && tour>65 && tour<100 ){              //Si un nombre de tour est passé, on réduit la taille de la map
                                jeu.map.alertecontour(((tour-64)/8));
                            }
                            if(tour%8==4 && tour>65 && tour<103){
                                jeu.map.rapprochementDesMurs(((tour-64)/8));
                            }

                        }
                        pm = joueurs[tour%4].getPm();
                        nb = joueurs[tour%4].getNbBombe();
                        switch(tour%4){
                            case 0:     nbBombe1.setText(""+nb);break;
                            case 1:     nbBombe2.setText(""+nb);break;
                            case 2:     nbBombe3.setText(""+nb);break;
                            case 3:     nbBombe4.setText(""+nb);break;

                        }
                        switch(tour%4){
                            case 0:     this.removeActor(nbmvt1);nbmvt1.setText(""+pm);this.addActor(nbmvt1);porteExplo1.setText(""+joueurs[tour%4].getTaille());break;
                            case 1:     this.removeActor(nbmvt2);nbmvt2.setText(""+pm);this.addActor(nbmvt2);porteExplo2.setText(""+joueurs[tour%4].getTaille());break;
                            case 2:     this.removeActor(nbmvt3);nbmvt3.setText(""+pm);this.addActor(nbmvt3);porteExplo3.setText(""+joueurs[tour%4].getTaille());break;
                            case 3:     this.removeActor(nbmvt4);nbmvt4.setText(""+pm);this.addActor(nbmvt4);porteExplo4.setText(""+joueurs[tour%4].getTaille());break;
                        }
                    }


//Animation bombe + ennemis Map
                }
            }
        }
        if(keycode==Input.Keys.ESCAPE){
            try {
                fw = new FileWriter(f);
                fw.write(jeu.map.mapToTextNP());
                for(int i=0;i<4;i++){
                    if(joueurs[i].getC().getBombe()!=null){
                        fw.write(joueurs[i].getId()+" "+pm+" "+nb+" 1\n"); //Le 1 indique d'une bombe est sur la position du joueur
                    }
                    else{
                        fw.write(joueurs[i].getId()+" "+pm+" "+nb+" 0\n"); //Le 0 indique qu'il n'y a pas de bombe sur la position du joueur
                    }
                }
                fw.write("111 "); //Symbole de fin pour la fin de la mise à jour des personnages
                fw.write(""+tour);

                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            jeu.map.suppActor();
            jeu.removeActor(jeu.map);
            jeu.map=null;
            game.multijoueur.removeActor(jeu);

            game.menuPause.setEtatAnterieur(game.multijoueur);
            jeu.setEtat(game.menuPause);
            game.setScreen(game.menuPause);

        }


        return true;
    }
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {           //test de fonction clic

        return true;
    }

    @Override
    public boolean mouseMoved( int x, int y) {
        return false;
    }




    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                           //nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)
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
}
