package com.bomber.game.Ecrans.Editeur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bomber.game.Bomberball;
import com.bomber.game.Bonus.*;
import com.bomber.game.Ennemis.*;
import com.bomber.game.Ecrans.Etat;
import com.bomber.game.Jeu;
import com.bomber.game.MapetObjet.*;


import java.awt.*;
import java.io.*;
import java.util.LinkedList;

import static com.bomber.game.Bomberball.loadFile;

/**
 * Classe EditeurNSolo
 * Elle affiche l'éditeur de niveau pour des maps solo
 *
 */
public class EditeurNSolo extends Etat implements Screen {

    Bomberball game;                //Instance de la classe principale
    Image back;                     //Image de l'arrière-plan
    Image floor;                    //Image du sable du labyrinthe
    Image porte;                    //Image de la porte
    Image murd;                     //Image du mur destructible
    Image muri;                     //Image du mur indestructible
    Image perso;                    //Image du personnage
    Image selectionne;              //Image du bloc sélectionné
    Image bonusB;                   //Image du bonus Bombe
    Image bonusM;                   //Image du bonus de déplacement
    Image bonusE;                   //Image du bonus de portée
    Image bonusP;                   //Image du bonus de poussé
    Image ennemisPassif;            //Image de l'ennemi passif
    Image ennemisActif;             //Image de l'ennemi actif
    Image ennemisPassifAgressif;    //Image de l'ennemi passif aggressif
    Image ennemisActifAgressif;     //Image de l'ennemi actif aggressif

    Label select;                   //Texte précédent le bloc sélectionnée
    Label instruction1;             //Texte indiqué comment poser un bloc
    Label instruction2;             //Texte indiqué comment enlever un bloc
    Label instruction3;             //Texte indiqué comment afficher le chemin des ennemis

    TextButton retour;              //Bouton permettant de revenir au menu éditeur
    TextButton valider;             //Bouton permettant de valider la carte
    TextButton charger;             //Bouton permettant de charger une map précédemment faite

    Map map;                        //Mini-map affichée

    Skin skin;                      //Caractéristiques des éléments graphiques

    Boolean cache=true;             //Permet de savoir si le chemin des ennemis est affiché ou pas

    File f;                         //Fichier de sauvegarde temporaire de la map
    FileWriter fw;                  //Permet d'écrire la nouvelle map

    Color Couleur[]={Color.BLACK,Color.BLUE,Color.BROWN,Color.CHARTREUSE,Color.CORAL,Color.CYAN,Color.DARK_GRAY,Color.FIREBRICK,Color.FOREST,Color.GOLD,Color.GOLDENROD,Color.GRAY,Color.GREEN,Color.LIGHT_GRAY,Color.LIME,Color.MAGENTA,Color.MAROON
    ,Color.NAVY,Color.OLIVE,Color.ORANGE,Color.PINK,Color.PURPLE,Color.RED,Color.ROYAL,Color.SALMON,Color.SCARLET,Color.SKY,Color.SLATE,Color.TAN,Color.TEAL,Color.VIOLET,Color.YELLOW}; //Permet de choisir une couleur aléatoire pour le chemin des ennemis





    /**
     * Générateur de la classe EditeurNSolo
     * @param game
     * @param jeu
     */
    public EditeurNSolo(Bomberball game, Jeu jeu){
        super(jeu);
        this.game=game;
        File directory = new File (".");
        f = Gdx.files.internal("./SaveMapPerso/Mapsolo/tmp.txt").file();//new File(directory.getCanonicalPath() + "/SaveMapPerso/Mapsolo/tmp.txt");



    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        Bomberball.input.addProcessor(this);
        if(f.exists()){                                     //S'il existe un fichier tmp, on le charge
            map=Map.mapFromStringN(loadFile(f));            //Sinon on fournit une map de manière aléatoire
        }
        else{
            map=Map.genererMapSolo(20,10,5);

        }
        map.setPosition(7*Bomberball.taillecase,0);


        for (int i=0;i<15;i++){                                             //On affiche les bonus par desssus
            for (int j=0;j<13;j++){
                if(map.getGrille()[i][j].getBonus()!=null){
                    Bonus b=map.getGrille()[i][j].getBonus();
                    map.getGrille()[i][j].setBonus(null);
                    map.getGrille()[i][j].setBonus(b);
                    map.getGrille()[i][j].getBonus().setScale(0.5f);
                }
                if(map.getGrille()[i][j].getPersonnage()!=null){
                    Personnage papa=map.getGrille()[i][j].getPersonnage();
                    map.getGrille()[i][j].setPersonnage(null);
                    map.getGrille()[i][j].setPersonnage(papa);
                }
            }
        }














        /**Mise en place des éléments pour l'éditeur solo **/


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

        bonusP = new Image(Bomberball.multiTexture[19]);
        bonusP.setName("BonusP");
        bonusP.setBounds(2*Bomberball.taillecase,ymax-2*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);


        ennemisPassif = new Image(Bomberball.multiTexture[17]);
        ennemisPassif.setName("ghost1");
        ennemisPassif.setBounds(3*Bomberball.taillecase,ymax-Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        ennemisActif = new Image(Bomberball.multiTexture[16]);
        ennemisActif.setName("imp1");
        ennemisActif.setBounds(3*Bomberball.taillecase,ymax-2*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        ennemisPassifAgressif = new Image(Bomberball.multiTexture[23]);
        ennemisPassifAgressif.setName("ghost2");
        ennemisPassifAgressif.setBounds(3*Bomberball.taillecase,ymax-3*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        ennemisActifAgressif = new Image(Bomberball.multiTexture[24]);
        ennemisActifAgressif.setName("imp2");
        ennemisActifAgressif.setBounds(3*Bomberball.taillecase,ymax-4*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);



        select= new Label("Bloc selectionne:",skin);
        select.setBounds(0,ymax-5*Bomberball.taillecase,select.getWidth(),select.getHeight());
        select.setName("Select");


        instruction1 = new Label("Clic gauche pour placer un bloc",skin);
        instruction1.setBounds(0,ymax-7*Bomberball.taillecase,instruction1.getWidth(),instruction1.getHeight());
        instruction1.setName("Instruction1");

        instruction2 = new Label("Clic droit pour enlever un bloc",skin);
        instruction2.setBounds(0,ymax-8*Bomberball.taillecase,instruction2.getWidth(),instruction2.getHeight());
        instruction2.setName("Instruction2");

        instruction3 = new Label("Espace pour afficher/cacher \n la trajectoire des ennemis",skin);
        instruction3.setBounds(0,ymax-9*Bomberball.taillecase,instruction3.getWidth(),instruction3.getHeight());
        instruction3.setName("Instruction3");


        retour= new TextButton("Retour",skin);
        retour.setBounds(0,ymax-10*Bomberball.taillecase, retour.getWidth(),retour.getHeight());
        retour.setName("Retour");

        valider = new TextButton("Valider la carte",skin);
        valider.setBounds(0,ymax-11*Bomberball.taillecase,valider.getWidth(),valider.getHeight());
        valider.setName("Valider");

        charger = new TextButton("Charger une carte",skin);
        charger.setBounds(0,ymax-12*Bomberball.taillecase,charger.getWidth(),charger.getHeight());
        charger.setName("Charger");
        /*****************************************************************************************************/

        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(f.exists()){
                    f.delete();
                }
                map.suppActor();
                game.editeurNSolo.removeActor(map);

                jeu.setEtat(game.choixEditeurN);
                game.setScreen(game.choixEditeurN);
            }
        });

        valider.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                try {
                    fw=new FileWriter(f);
                   fw.write(map.mapToTextN());
                   fw.close();
                }
                catch (IOException e) {
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
                if (cptPerso!=1 || cptPorte!=1){            //S'il n'y a pas un joueur et une porte, la carte n'est pas valide
                    map.suppActor();
                    game.editeurNSolo.removeActor(map);
                    jeu.setEtat(game.erreurEditeurS);
                    game.setScreen(game.erreurEditeurS);
                }
                else{                                       //Sinon, on peut l'enregistrer
                    map.suppActor();
                    game.editeurNSolo.removeActor(map);

                    jeu.setEtat(game.validerEditeurSolo);
                    game.setScreen(game.validerEditeurSolo);
                }
            }
        });

        charger.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                map.suppActor();
                game.editeurNSolo.removeActor(map);

                jeu.setEtat(game.choixMapSoloE);
                game.setScreen(game.choixMapSoloE);
            }
        });





        this.addActor(back);
        this.addActor(floor);
        this.addActor(perso);
        this.addActor(murd);
        this.addActor(muri);
        this.addActor(porte);
        this.addActor(bonusB);
        this.addActor(bonusE);
        this.addActor(bonusM);
        this.addActor(bonusP);
        this.addActor(ennemisPassif);
        this.addActor(ennemisActif);
        this.addActor(ennemisPassifAgressif);
        this.addActor(ennemisActifAgressif);
        this.addActor(select);
        this.addActor(selectionne);
        this.addActor(instruction1);
        this.addActor(instruction2);
        this.addActor(instruction3);
        this.addActor(retour);
        this.addActor(valider);
        this.addActor(charger);

        this.addActor(map);
















    }

    /**
     * Met à jour l'affichage
     * @param delta: Intervalle de temps entre deux affichages
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//nettoyage de l'ecran => tout l'ecran prend la couleur donnée (ici noir)

    }

    /**
     * Gère le changement de taille de la fenêtre d'affichage
     *
     * @param width : largeur nouvelle fenêtre
     * @param height : hauteur nouvelle fenêtre
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On n'utilise pas cette fonctionnalité par la suite.
     */
    @Override
    public void pause() {

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On n'utilise pas cette fonctionnalité par la suite.
     */
    @Override
    public void resume() {

    }

    /**
     * Fonction appelée lors d'un changement d'écran.
     */
    @Override
    public void hide() {
    Bomberball.stg.clear();
    Bomberball.input.removeProcessor(this);
    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On n'utilise pas cette fonctionnalité par la suite.
     */
    @Override
    public void dispose() {

    }

    @Override
    /**
     * Indique l'action à effectuer lorsqu'on appuie sur une touche sur le clavier
     * @param keycode   Numéro de la touche appuyée
     */
    public boolean keyDown( int keycode) {
        if(keycode==Input.Keys.SPACE){                                  //Si on appuie sur Espace, on affiche le chemin des ennemis
            LinkedList<Ennemis> liste=new LinkedList<Ennemis>();
            for(int i=0;i<15;i++){
                for(int j=0;j<13;j++){
                    if(map.getGrille()[i][j].getEnnemi()!=null){
                        liste.add(map.getGrille()[i][j].getEnnemi());
                    }
                }
            }
            if(cache) {                                                                     //Si le chemin est caché, on l'affiche en parcourant l'ensemble des ennemis
                for (Ennemis en : liste) {
                    int choix=(int)(Math.random()*32);                                      //On choisit la couleur pour chaque ennemis

                    if(en instanceof EnnemiPassif || en instanceof EnnemiPassifAgressif){   //On distingue si on affiche le chemin pour les ennemis passifs ou le prochain déplacement pour les ennemis actifs
                        LinkedList<Case> caca = en.getChemin();
                        for (Case cas : caca) {
                            int xc = cas.posX();
                            int yc = cas.posY();
                            if (cas.getEnnemi() != null) {
                                if(cas.getEnnemi() instanceof EnnemiPassif){
                                    EnnemiPassif ennemi_passif = (EnnemiPassif) cas.getEnnemi();
                                    map.getGrille()[xc][yc].getBackground().setColor(Couleur[choix]);

                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_passif);
                                }
                                else if(cas.getEnnemi() instanceof EnnemiPassifAgressif){
                                    EnnemiPassifAgressif ennemi_passif_aggressif = (EnnemiPassifAgressif) cas.getEnnemi();
                                    map.getGrille()[xc][yc].getBackground().setColor(Color.WHITE);

                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_passif_aggressif);
                                }
                                else if(cas.getEnnemi() instanceof EnnemiActif){
                                    EnnemiActif ennemi_actif = (EnnemiActif) cas.getEnnemi();
                                    map.getGrille()[xc][yc].getBackground().setColor(Couleur[choix]);

                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_actif);
                                }
                                else if(cas.getEnnemi() instanceof  EnnemiActifAggressif){
                                    map.getGrille()[xc][yc].getBackground().setColor(Couleur[choix]);
                                    EnnemiActifAggressif ennemi_actif_aggressif = (EnnemiActifAggressif) cas.getEnnemi();
                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_actif_aggressif);
                                }

                            } else if (cas.getPersonnage() != null) {
                                Personnage personnage = cas.getPersonnage();
                                map.getGrille()[xc][yc].getBackground().setColor(Couleur[choix]);
                                map.getGrille()[xc][yc].setPersonnage(null);
                                map.getGrille()[xc][yc].setPersonnage(personnage);
                            } else {
                                map.getGrille()[xc][yc].getBackground().setColor(Couleur[choix]);
                            }
                        }
                    }
                    else if(en instanceof EnnemiActif || en instanceof EnnemiActifAggressif){
                        en.miseAjour();
                        LinkedList<Case> caca = en.getProchain_deplacement();
                        for (Case cas : caca) {
                            int xc = cas.posX();
                            int yc = cas.posY();
                            if (cas.getEnnemi() != null) {
                                if(cas.getEnnemi() instanceof EnnemiPassif){
                                    EnnemiPassif ennemi_passif = (EnnemiPassif) cas.getEnnemi();
                                    map.getGrille()[xc][yc].getBackground().setColor(Couleur[choix]);

                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_passif);
                                }
                                else if(cas.getEnnemi() instanceof EnnemiPassifAgressif){
                                    EnnemiPassifAgressif ennemi_passif_aggressif = (EnnemiPassifAgressif) cas.getEnnemi();
                                    map.getGrille()[xc][yc].getBackground().setColor(Couleur[choix]);

                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_passif_aggressif);
                                }
                                else if(cas.getEnnemi() instanceof  EnnemiActif){
                                    EnnemiActif ennemi_actif = (EnnemiActif) cas.getEnnemi();
                                    map.getGrille()[xc][yc].getBackground().setColor(Couleur[choix]);

                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_actif);
                                }
                                else if(cas.getEnnemi() instanceof  EnnemiActifAggressif){
                                    map.getGrille()[xc][yc].getBackground().setColor(Couleur[choix]);
                                    EnnemiActifAggressif ennemi_actif_aggressif = (EnnemiActifAggressif) cas.getEnnemi();
                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_actif_aggressif);
                                }

                            } else if (cas.getPersonnage() != null) {
                                Personnage personnage = cas.getPersonnage();
                                map.getGrille()[xc][yc].getBackground().setColor(Couleur[choix]);
                                map.getGrille()[xc][yc].setPersonnage(null);
                                map.getGrille()[xc][yc].setPersonnage(personnage);
                            } else {
                                map.getGrille()[xc][yc].getBackground().setColor(Couleur[choix]);
                            }
                        }
                    }

                }
                cache=false;
            }
            else{                                                                                                           //On cache le chemin de tous les ennemis
                for (Ennemis en : liste) {
                    if(en instanceof EnnemiPassif || en instanceof EnnemiPassifAgressif){
                        LinkedList<Case> caca = en.getChemin();
                        for(Case cas: caca){
                            int xc=cas.posX();
                            int yc=cas.posY();

                            if(cas.getEnnemi()!=null){
                                if(cas.getEnnemi() instanceof EnnemiPassif){
                                    EnnemiPassif ennemi_passif = (EnnemiPassif) cas.getEnnemi();
                                    map.getGrille()[xc][yc].getBackground().setColor(255,255,255,1);

                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_passif);
                                }
                                else if(cas.getEnnemi() instanceof EnnemiPassifAgressif){
                                    EnnemiPassifAgressif ennemi_passif_aggressif = (EnnemiPassifAgressif) cas.getEnnemi();
                                    map.getGrille()[xc][yc].getBackground().setColor(255,255,255,1);

                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_passif_aggressif);
                                }
                                else if(cas.getEnnemi() instanceof  EnnemiActif){
                                    EnnemiActif ennemi_actif = (EnnemiActif) cas.getEnnemi();
                                    map.getGrille()[xc][yc].getBackground().setColor(255,255,255,1);

                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_actif);
                                }
                                else if(cas.getEnnemi() instanceof  EnnemiActifAggressif){
                                    EnnemiActifAggressif ennemi_actif_aggressif = (EnnemiActifAggressif) cas.getEnnemi();
                                    map.getGrille()[xc][yc].getBackground().setColor(255,255,255,1);
                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_actif_aggressif);
                                }
                            }
                            else if(cas.getPersonnage()!=null){
                                Personnage personnage=cas.getPersonnage();
                                map.getGrille()[xc][yc].setPersonnage(null);
                                map.getGrille()[xc][yc].getBackground().setColor(255,255,255,1);

                                map.getGrille()[xc][yc].setPersonnage(personnage);
                            }
                            else{
                                map.getGrille()[xc][yc].getBackground().setColor(255,255,255,1);

                            }
                        }
                    }
                    else if(en instanceof EnnemiActif || en instanceof EnnemiActifAggressif){
                        en.miseAjour();
                        LinkedList<Case> caca = en.getProchain_deplacement();
                        for (Case cas : caca) {
                            int xc = cas.posX();
                            int yc = cas.posY();
                            if (cas.getEnnemi() != null) {
                                if(cas.getEnnemi() instanceof EnnemiPassif){
                                    EnnemiPassif ennemi_passif = (EnnemiPassif) cas.getEnnemi();
                                    map.getGrille()[xc][yc].getBackground().setColor(255,255,255,1);

                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_passif);
                                }
                                else if(cas.getEnnemi() instanceof EnnemiPassifAgressif){
                                    EnnemiPassifAgressif ennemi_passif_aggressif = (EnnemiPassifAgressif) cas.getEnnemi();
                                    map.getGrille()[xc][yc].getBackground().setColor(255,255,255,1);

                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_passif_aggressif);
                                }
                                else if(cas.getEnnemi() instanceof  EnnemiActif){
                                    EnnemiActif ennemi_actif = (EnnemiActif) cas.getEnnemi();
                                    map.getGrille()[xc][yc].getBackground().setColor(255,255,255,1);

                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_actif);
                                }
                                else if(cas.getEnnemi() instanceof  EnnemiActifAggressif){
                                    map.getGrille()[xc][yc].getBackground().setColor(255,255,255,1);
                                    EnnemiActifAggressif ennemi_actif_aggressif = (EnnemiActifAggressif) cas.getEnnemi();

                                    map.getGrille()[xc][yc].setEnnemi(null);
                                    map.getGrille()[xc][yc].setEnnemi(ennemi_actif_aggressif);
                                }

                            } else if (cas.getPersonnage() != null) {
                                Personnage personnage = cas.getPersonnage();
                                map.getGrille()[xc][yc].getBackground().setColor(255,255,255,1);

                                map.getGrille()[xc][yc].setPersonnage(null);
                                map.getGrille()[xc][yc].setPersonnage(personnage);
                            } else {
                                map.getGrille()[xc][yc].getBackground().setColor(255,255,255,1);

                            }
                        }
                    }

                }
                cache=true;

            }
        }
        return true;

    }

    @Override
    /**
     * Indique l'action à effectuer lorsqu'on clique avec la souris en fonction de l'élément sur lequel on a cliqué
     * @param x abscisse du pointeur sur l'écran
     * @param y ordonnée du pointeur sur l'écran
     * @param pointer pointeur de l'événement (jamais utilisé)
     * @param button bouton de la souris appuyé
     */
    public boolean touchDown(int x, int y, int pointer, int button) {
        Actor hitActor= this.getStage().hit(x,Gdx.graphics.getHeight()-y,true);     //Retourne la référence de l'acteur touché
                                                                                                    //De base, hit fait un setbounds pour voir si l'acteur est dedans
        if(Gdx.graphics.getHeight()-y>=Gdx.graphics.getHeight() || Gdx.graphics.getHeight()-y<=0){
            return true;
        }
        else{
            if (hitActor.getName()!=null) {                         //Même méthode que pour EditeurNMulti
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
                else if(hitActor.getName().equals("ghost1")){
                    selectionne.setDrawable(ennemisPassif.getDrawable());
                    selectionne.setName("Ep");
                    try {
                        fw = new FileWriter(f);
                        fw.write(map.mapToTextN());
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    this.removeActor(map);
                    map.suppActor();
                    cache=false;
                    game.editeurNSolo.removeActor(map);
                    game.editeurNSolo.removeActor(jeu);
                    jeu.setEtat(game.selectionCheminEp);
                    game.setScreen(game.selectionCheminEp);


                }
                else if(hitActor.getName().equals("imp1")){
                    selectionne.setDrawable(ennemisActif.getDrawable());
                    selectionne.setName("Ea");
                }
                else if(hitActor.getName().equals("ghost2")){
                    selectionne.setDrawable(ennemisPassifAgressif.getDrawable());
                    selectionne.setName("Epa");
                    try {
                        fw = new FileWriter(f);
                        fw.write(map.mapToTextN());
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    this.removeActor(map);
                    map.suppActor();
                    cache=false;
                    game.editeurNSolo.removeActor(jeu);
                    game.editeurNSolo.removeActor(map);
                    jeu.setEtat(game.selectionCheminEpa);
                    game.setScreen(game.selectionCheminEpa);
                }
                else if(hitActor.getName().equals("imp2")){
                    selectionne.setDrawable(ennemisActifAgressif.getDrawable());
                    selectionne.setName("Eaa");
                }
                else if(hitActor.getName().equals("BonusP")){
                    selectionne.setDrawable(bonusP.getDrawable());
                    selectionne.setName("bP");
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
                                        c.setPersonnage(new Personnage(true,c,2,1,5,0));
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
                                if(c.getMur()==null){
                                    c.setPersonnage(new Personnage(true,c,2,1,5,0));
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
                            else if(selectionne.getName().equals("bP")){
                                c.setBonus(new BonusPousser(c));
                                c.getBonus().setScale(0.5f);
                            }
                        }
                    }

                }
                else if(hitActor.getName().equals("Porte")){                                            //Différence entre le multi et le solo où l'on peut placer une porte
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
                                c.setPorte(new Porte());
                            }
                        }
                    }

                }
                else if(hitActor.getName().equals("Personnage")){
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
                                    c.setPersonnage(new Personnage(true,c,2,1,5,0));
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
                                    c.setPersonnage(new Personnage(true,c,2,1,5,0));
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
            }else if(hitActor.getParent() instanceof Case){                                                                 //Si on clique sur une case, on peut placer
                Case c = (Case) hitActor.getParent();                                                                       //des ennemis
                if (button == Input.Buttons.RIGHT) {
                    c.setMur(null);
                    c.setPorte(null);
                    c.setPersonnage(null);
                    c.setBonus(null);
                    if(!cache){
                        if(c.getEnnemi()!=null){
                            if(c.getEnnemi() instanceof EnnemiPassif || c.getEnnemi() instanceof EnnemiPassifAgressif){
                                for(Case cas: c.getEnnemi().getChemin()){
                                    cas.getBackground().setColor(255,255,255,1);
                                }
                            }
                            else if(c.getEnnemi() instanceof EnnemiActif || c.getEnnemi() instanceof EnnemiActifAggressif){
                                for (Case cas: c.getEnnemi().getProchain_deplacement()){
                                    cas.getBackground().setColor(255,255,255,1);
                                }
                            }
                        }
                    }
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
                                c.setPersonnage(new Personnage(true,c,2,1,5,0));
                            }

                        }
                        else if(selectionne.getName().equals("Ea")){
                            c.setEnnemi(null);
                            EnnemiActif ea=new EnnemiActif(true,c,3);
                            c.setEnnemi(ea);
                        }
                        else if(selectionne.getName().equals("Eaa")){
                            c.setEnnemi(null);
                            EnnemiActifAggressif eaa=new EnnemiActifAggressif(true,c,3,5,false);
                            c.setEnnemi(eaa);
                        }
                    }
                }
            }
            return true;
        }
    }


    /**Méthode mouseMoved
     * @param screenX x de la souris
     * @param screenY y de la souris
     * @return true si le mouvement est traité
     */
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
}
