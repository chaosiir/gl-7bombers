package com.bomber.game.Ecrans.Editeur;

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
import com.bomber.game.Bomberball;
import com.bomber.game.Bonus.BonusBombe;
import com.bomber.game.Bonus.BonusExplo;
import com.bomber.game.Bonus.BonusMove;
import com.bomber.game.Bonus.BonusPousser;
import com.bomber.game.Ecrans.Etat;
import com.bomber.game.Jeu;
import com.bomber.game.MapetObjet.*;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe EditeurNMulti
 * Elle affiche l'éditeur de niveau pour des maps multijoueurs
 * @author Paul-Louis Renard
 *
 */
public class EditeurNMulti extends Etat implements Screen {
    Bomberball game;        //Instance de la classe principale
    Map map;                //Mini-map affichée
    Skin skin;              //Caractéristiques des éléments graphiques
    File f;                 //Fichier de sauvegarde temporaire de la map
    FileWriter fw;          //Permet d'écrire la sauvegarde

    Image back;             //Image de l'arrière-plan
    Image floor;            //Image du sable du labyrinthe
    Image murd;             //Image du mur destructible
    Image muri;             //Image du mur indestructible
    Image selectionne;      //Image du bloc sélectionné
    Image bonusB;           //Image du bonus Bombe
    Image bonusM;           //Image du bonus de déplacement
    Image bonusE;           //Image du bonus de portée
    Image perso1;           //Image du personnage1
    Image perso2;           //Image du personnage2
    Image perso3;           //Image du personnage3
    Image perso4;           //Image du personnage4
    Image bonusP;           //Image du bonus de poussé


    Label select;           //Texte précédent le bloc sélectionnée
    Label instruction1;     //Texte indiqué comment poser un bloc
    Label instruction2;     //Texte indique comment enlever un bloc

    TextButton retour;      //Bouton permettant de revenir au menu éditeur
    TextButton valider;     //Bouton permettant de valider la carte
    TextButton charger;     //Bouton permettant de charger une map précédemment faite



    /**
     * Classe EditeurNMulti
     * Elle affiche l'éditeur de niveau pour des maps multijoueurs
     * @author Paul-Louis Renard
     *
     */
    public EditeurNMulti(Bomberball game,Jeu jeu) {
        super(jeu);
        this.game=game;
        f = Gdx.files.internal("./SaveMapPerso/MapMulti/tmp.txt").file();

    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        Bomberball.input.addProcessor(this);
        if(f.exists()){                             //S'il existe un fichier tmp, on le charge
            String text=Bomberball.loadFile(f);
            map=Map.mapFromStringN(text);
        }
        else{                                       //Sinon on fournit une map de manière aléatoire
            map=Map.generatePvp(20,5);

        }
        map.setPosition(7*Bomberball.taillecase,0);


        /**Mise en place des éléments pour l'éditeur multijoueur **/

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

        perso1= new Image(Bomberball.multiTexture[4]);
        perso1.setName("perso1");
        perso1.setBounds(2*Bomberball.taillecase,ymax-Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        perso2= new Image(Bomberball.multiTexture[20]);
        perso2.setName("perso2");
        perso2.setBounds(2*Bomberball.taillecase,ymax-2*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        perso3= new Image(Bomberball.multiTexture[21]);
        perso3.setName("perso3");
        perso3.setBounds(2*Bomberball.taillecase,ymax-3*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        perso4= new Image(Bomberball.multiTexture[22]);
        perso4.setName("perso4");
        perso4.setBounds(2*Bomberball.taillecase,ymax-4*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);



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
        bonusP.setName("bonusP");
        bonusP.setBounds(Bomberball.taillecase,ymax-Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

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
        charger.setBounds(0,ymax-11*Bomberball.taillecase,charger.getWidth(),charger.getHeight());
        charger.setName("Charger");

        /*****************************************************************************************************/

        retour.addListener(new ClickListener(){                         //Permet de retourner au menu choix du caractère solo ou multijoueur de l'éditeur
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(f.exists()){
                    f.delete();
                }
                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.editeurNMulti.removeActor(jeu);


                jeu.setEtat(game.choixEditeurN);
                game.setScreen(game.choixEditeurN);
            }
        });

        valider.addListener(new ClickListener(){                        //Permet d'enregistrer le niveau
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    fw = new FileWriter(f);
                    fw.write(map.mapToTextN());
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /** Préparation pour que la map soit valide **/

                int tabid[]=new int[4];
                int cptPerso=0;
                for(int i=0;i<15;i++){
                    for(int j=0;j<13;j++){
                        if(map.getGrille()[i][j].getPersonnage()!=null){
                            cptPerso++;
                            tabid[map.getGrille()[i][j].getPersonnage().getId()]=1;
                        }
                    }
                }
                Boolean different=true;
                for(int i=0;i<4;i++){
                    if(tabid[i]==0){
                       different=false;
                    }
                }
                /************************************************/

                if (cptPerso!=4 || !different){             //Si le nombre de joueur n'est pas 4 et qu'ils ne sont pas tous différent
                    jeu.removeActor(jeu.map);               //la map n'est pas valide et on va l'indiquer au joueur
                    jeu.map=null;
                    game.editeurNMulti.removeActor(jeu);

                    jeu.setEtat(game.erreurEditeurM);
                    game.setScreen(game.erreurEditeurM);
                }
                else{                                       //Si la map est valide, on lui permet de donner un nom à sa map
                    jeu.removeActor(jeu.map);
                    jeu.map=null;
                    game.editeurNMulti.removeActor(jeu);
                    jeu.setEtat(game.validerEditeurMulti);
                    game.setScreen(game.validerEditeurMulti);
                }
            }
        });

        charger.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.editeurNMulti.removeActor(jeu);
                jeu.setEtat(game.choixMapMultiE);
                game.setScreen(game.choixMapMultiE);
            }
        });





        this.addActor(back);
        this.addActor(floor);
        this.addActor(perso1);
        this.addActor(perso2);
        this.addActor(perso3);
        this.addActor(perso4);
        this.addActor(murd);
        this.addActor(muri);
        this.addActor(bonusB);
        this.addActor(bonusE);
        this.addActor(bonusM);
        this.addActor(bonusP);
        this.addActor(select);
        this.addActor(selectionne);
        this.addActor(instruction1);
        this.addActor(instruction2);
        this.addActor(retour);
        this.addActor(valider);
        this.addActor(charger);
        this.addActor(map);






    }

    /**
     * Met à jour l'affichage
     * @param delta: Interval de temps entre deux affichages
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)

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
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas cette fonctionnalité par la suite.
     */
    @Override
    public void pause() {

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas cette fonctionnalité par la suite.
     */
    @Override
    public void resume() {

    }

    /**
     * Fonction appellé lors d'un changement d'écran.
     */
    @Override
    public void hide() {
    Bomberball.stg.clear();
    Bomberball.input.removeProcessor(this);
    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas cette fonctionnalité par la suite.
     */
    @Override
    public void dispose() {

    }

    /**
     * Permet de détecter un appui sur une touche
     *
     * @param keycode : vaut le numéro de la touche enfoncée
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean keyDown( int keycode) {
        return false;
    }

    @Override
    /**
     * Indique l'action à effectuer lorsqu'on clique avec la souris en fonction de l'élément sur lequel on a cliqué
     * @param x abscisse du pointeur sur l'écran
     * @param y ordonnée du pointeur sur l'écran
     * @param pointer
     * @param button bouton de la souris appuyé
     */
    public boolean touchDown(int x, int y, int pointer, int button) {
        Actor hitActor= this.getStage().hit(x,Gdx.graphics.getHeight()-y,true);     //Retourne référence de l'acteur touché
                                                                                                    // hit fait un setbounds pour voir si l'acteur est dedans
        if (hitActor.getName()!=null) {                         //On vérifie quelle case le joueur a sélectionné
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
            else if(hitActor.getName().equals("perso1")){
                selectionne.setDrawable(perso1.getDrawable());
                selectionne.setName("player1");
            }
            else if(hitActor.getName().equals("perso2")){
                selectionne.setDrawable(perso2.getDrawable());
                selectionne.setName("player2");
            }
            else if(hitActor.getName().equals("perso3")){
                selectionne.setDrawable(perso3.getDrawable());
                selectionne.setName("player3");
            }
            else if(hitActor.getName().equals("perso4")){
                selectionne.setDrawable(perso4.getDrawable());
                selectionne.setName("player4");
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
            else if(hitActor.getName().equals("bonusP")){
                selectionne.setDrawable(bonusP.getDrawable());
                selectionne.setName("bP");
            }
            else if(hitActor.getName().equals("MurI")) {                                    //Si on clique sur un mur indestructible dans la map
                Case c = (Case) hitActor.getParent();
                if (c.posX() == 0 || c.posX() == 14 || c.posY() == 0 || c.posY() == 12) {
                    //rien
                } else {
                    if (button == Input.Buttons.RIGHT) {                                    //le click droit permet de supprimer un ennemi
                        Map m = c.getMap();

                        int xp = c.posX();
                        int yp = c.posY();

                        c.setMur(null);
                        m.getGrille()[14 - xp][yp].setMur(null);                //On applique la symétrie pour les blocs indestructibles
                        m.getGrille()[14 - xp][12 - yp].setMur(null);
                        m.getGrille()[xp][12 - yp].setMur(null);

                        c.setPorte(null);
                        m.getGrille()[14 - xp][yp].setPorte(null);
                        m.getGrille()[14 - xp][12 - yp].setPorte(null);
                        m.getGrille()[xp][12 - yp].setPorte(null);

                        c.setPersonnage(null);
                        m.getGrille()[14 - xp][yp].setPersonnage(null);
                        m.getGrille()[14 - xp][12 - yp].setPersonnage(null);
                        m.getGrille()[xp][12 - yp].setPersonnage(null);

                        c.setBonus(null);
                        m.getGrille()[14 - xp][yp].setBonus(null);
                        m.getGrille()[14 - xp][12 - yp].setBonus(null);
                        m.getGrille()[xp][12 - yp].setBonus(null);

                        Image background = new Image(Bomberball.multiTexture[0]);
                        background.setBounds(0, 0, Bomberball.taillecase, Bomberball.taillecase);
                        c.addActor(background);

                        m.getGrille()[14 - xp][yp].addActor(background);
                        m.getGrille()[14 - xp][12 - yp].addActor(background);
                        m.getGrille()[xp][12 - yp].addActor(background);


                    } else if (button == Input.Buttons.LEFT) {                                              //On applique la symétrie pour les blocs indestructibles
                        if (selectionne.getDrawable() != null) {
                            if (selectionne.getName().equals("murdes")) {
                                Map m = c.getMap();

                                int xp = c.posX();
                                int yp = c.posY();

                                c.setMur(null);
                                m.getGrille()[14 - xp][yp].setMur(null);
                                m.getGrille()[14 - xp][12 - yp].setMur(null);
                                m.getGrille()[xp][12 - yp].setMur(null);

                                c.setPorte(null);
                                m.getGrille()[14 - xp][yp].setPorte(null);
                                m.getGrille()[14 - xp][12 - yp].setPorte(null);
                                m.getGrille()[xp][12 - yp].setPorte(null);

                                c.setPersonnage(null);
                                m.getGrille()[14 - xp][yp].setPersonnage(null);
                                m.getGrille()[14 - xp][12 - yp].setPersonnage(null);
                                m.getGrille()[xp][12 - yp].setPersonnage(null);

                                c.setBonus(null);
                                m.getGrille()[14 - xp][yp].setBonus(null);
                                m.getGrille()[14 - xp][12 - yp].setBonus(null);
                                m.getGrille()[xp][12 - yp].setBonus(null);

                                Image background = new Image(Bomberball.multiTexture[0]);
                                background.setBounds(0, 0, Bomberball.taillecase, Bomberball.taillecase);
                                c.addActor(background);

                                m.getGrille()[14 - xp][yp].addActor(background);
                                m.getGrille()[14 - xp][12 - yp].addActor(background);
                                m.getGrille()[xp][12 - yp].addActor(background);
                                c.setMur(new MurD());
                            } else if (selectionne.getName().equals("sol")) {                                   //On applique la symétrie pour les blocs indestructibles
                                Map m = c.getMap();

                                int xp = c.posX();
                                int yp = c.posY();

                                c.setMur(null);
                                m.getGrille()[14 - xp][yp].setMur(null);
                                m.getGrille()[14 - xp][12 - yp].setMur(null);
                                m.getGrille()[xp][12 - yp].setMur(null);

                                c.setPorte(null);
                                m.getGrille()[14 - xp][yp].setPorte(null);
                                m.getGrille()[14 - xp][12 - yp].setPorte(null);
                                m.getGrille()[xp][12 - yp].setPorte(null);

                                c.setPersonnage(null);
                                m.getGrille()[14 - xp][yp].setPersonnage(null);
                                m.getGrille()[14 - xp][12 - yp].setPersonnage(null);
                                m.getGrille()[xp][12 - yp].setPersonnage(null);

                                c.setBonus(null);
                                m.getGrille()[14 - xp][yp].setBonus(null);
                                m.getGrille()[14 - xp][12 - yp].setBonus(null);
                                m.getGrille()[xp][12 - yp].setBonus(null);

                                Image background = new Image(Bomberball.multiTexture[0]);
                                background.setBounds(0, 0, Bomberball.taillecase, Bomberball.taillecase);
                                c.addActor(background);

                                m.getGrille()[14 - xp][yp].addActor(background);
                                m.getGrille()[14 - xp][12 - yp].addActor(background);
                                m.getGrille()[xp][12 - yp].addActor(background);
                            }
                        }
                    }

                }

            }
            else if(hitActor.getName().equals("MurD")){                                             //Si on clique sur un mur destructible dans la mini map
                Case c = (Case) hitActor.getParent();
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
                            Map m=c.getMap();
                            c.setBonus(null);
                            c.setMur(new MurI());
                            int xp=c.posX();
                            int yp=c.posY();
                            m.getGrille()[14-xp][yp].setMur(new MurI());        //Si on met un bloc indestructible, il y a une symétrie à appliquer
                            m.getGrille()[14-xp][yp].setBonus(null);
                            m.getGrille()[14-xp][12-yp].setMur(new MurI());
                            m.getGrille()[14-xp][12-yp].setBonus(null);
                            m.getGrille()[xp][12-yp].setMur(new MurI());
                            m.getGrille()[xp][12-yp].setBonus(null);
                        } else if (selectionne.getName().equals("p")) {
                            c.setBonus(null);
                            c.setPorte(new Porte());
                        }
                        else if(selectionne.getName().equals("player")){
                            c.setBonus(null);
                            if(c.getMur()==null){
                                c.setPersonnage(new Personnage(true,c,2,1,5,0));
                            }
                        }
                        else if(selectionne.getName().equals("bB")){            //On peut placer des bonus sur les blocs destructibles
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
            else if(hitActor.getName().equals("Personnage")){           //Si on clique sur un joueur
                Case c = (Case) hitActor.getParent();
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
                            c.setMur(null);
                            c.setPorte(null);
                            c.setPersonnage(null);
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
                            m.getGrille()[14-xp][yp].setMur(null);                              //On applique la symétrie dans le cas de blocs indestructibles
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
                        else if(selectionne.getName().equals("player1")){                                       //On peut remplacer un joueur par un autre joueur
                            if(c.getMur()==null){
                                c.setPersonnage(null);
                                c.setPersonnage(new Personnage(true,c,2,1,5,0));
                            }
                        }
                        else if(selectionne.getName().equals("player2")){
                            if(c.getMur()==null){
                                c.setPersonnage(null);
                                c.setPersonnage(new Personnage(true,c,2,1,5,1));
                            }
                        }
                        else if(selectionne.getName().equals("player3")){
                            if(c.getMur()==null){
                                c.setPersonnage(null);
                                c.setPersonnage(new Personnage(true,c,2,1,5,2));
                            }
                        }
                        else if(selectionne.getName().equals("player4")){
                            if(c.getMur()==null){
                                c.setPersonnage(null);
                                c.setPersonnage(new Personnage(true,c,2,1,5,3));
                            }
                        }
                    }
                }

            }
            else if(hitActor.getName().equals("bonus")){                                            //Si on clique sur un bonus
                Case c = (Case) hitActor.getParent();
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
        }else if(hitActor.getParent() instanceof Case){                                             //Si on clique sur une case de la map inoccupée
            Case c = (Case) hitActor.getParent();
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
                        c.setMur(null);
                        c.setPorte(null);
                        c.setPersonnage(null);
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
                        c.setMur(null);
                        c.setPorte(null);
                        c.setPersonnage(null);
                        c.setBonus(null);
                        Map m=map;
                        c.setMur(new MurI());
                        int xp=c.posX();
                        int yp=c.posY();

                        c.setMur(null);
                        m.getGrille()[14-xp][yp].setMur(null);                  //Symétrie pour les blocs indestructibles
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
                        c.setPorte(new Porte());
                    }
                    else if(selectionne.getName().equals("player1")){
                        if(c.getMur()==null){
                            c.setPersonnage(null);
                            c.setPersonnage(new Personnage(true,c,2,1,5,0));
                        }
                    }
                    else if(selectionne.getName().equals("player2")){
                        if(c.getMur()==null){
                            c.setPersonnage(null);
                            c.setPersonnage(new Personnage(true,c,2,1,5,1));
                        }
                    }
                    else if(selectionne.getName().equals("player3")){
                        if(c.getMur()==null){
                            c.setPersonnage(null);
                            c.setPersonnage(new Personnage(true,c,2,1,5,2));
                        }
                    }
                    else if(selectionne.getName().equals("player4")){
                        if(c.getMur()==null){
                            c.setPersonnage(null);
                            c.setPersonnage(new Personnage(true,c,2,1,5,3));
                        }
                    }
                }
            }
        }
        return true;
    }
    /**
     * Fonction détectant un mouvement de la souris. On n'utilise pas cette fonctionnalité par la suite.
     * @param x: récupère la position x du pointeur.
     * @param y : récupère la position x du pointeur.
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }
}
