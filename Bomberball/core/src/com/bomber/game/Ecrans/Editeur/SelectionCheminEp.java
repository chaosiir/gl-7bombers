package com.bomber.game.Ecrans.Editeur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bomber.game.Bomberball;
import com.bomber.game.Bonus.Bonus;
import com.bomber.game.Ennemis.EnnemiPassif;
import com.bomber.game.Ecrans.Etat;
import com.bomber.game.Jeu;
import com.bomber.game.MapetObjet.Case;
import com.bomber.game.MapetObjet.Map;
import com.bomber.game.MapetObjet.Personnage;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe SelectionCheminEp
 * Elle permet au joueur de placer un ennemi passif sur la carte dans l'éditeur de niveau
 *
 */
public class SelectionCheminEp extends Etat implements Screen {
    Bomberball game;            //Instance de la classe principale
    int compteur=0;             //Compteur de la taille du chemin de l'ennemi
    EnnemiPassif ennemi_passif; //Ennemi que l'on place
    Map map;                    //Map sur laquelle on place l'ennemi

    Image back;                 //Image de l'arrière-plan
    Label indication;           //Indiquer comment placer un ennemi
    Label indication1;          //Indiquer comment supprimer un ennemi
    TextButton valider;         //Permet de valider le chemin effectué
    TextButton retour;          //Permet de retourner sur l'éditeur
    Table table;                //Contient les boutons
    Skin skin;                  //Caractéristiques des éléments graphiques

    File f;                     //Sauvegarde temporaire de la map
    FileWriter fw;              //Ecrire dans la sauvegarde temporaire

    public SelectionCheminEp(Bomberball game,Jeu jeu) {
        super(jeu);
        this.game = game;
        f = Gdx.files.internal("./SaveMapPerso/Mapsolo/tmp.txt").file();
    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        Bomberball.input.addProcessor(this);
        String text=Bomberball.loadFile(f);
        map=Map.mapFromStringN(text);
        map.setName("map");
        skin=new Skin(Gdx.files.internal("uiskin.json"));
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

        table=new Table(); //Tableau
        table.setBounds(Bomberball.taillecase,0,6*Bomberball.taillecase,Gdx.graphics.getHeight());
        table.setName("table");

        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        back.setName("Je suis ton arrière plan");



        indication = new Label("Clic gauche pour indiquer \n le chemin de votre ennemi passif",skin);
        indication.setName("Indication");
        indication1= new Label("Clic droit pour enlever  la case \n du chemin de votre ennemi passif",skin);
        indication1.setName("Indication1");

        valider = new TextButton("Valider la trajectoire",skin);
        valider.setName("valider");
        retour = new TextButton("Retour",skin);
        retour.setName("retour");

        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(ennemi_passif!=null){
                    ennemi_passif.prochain_deplacement.clear();
                    ennemi_passif=null;
                }
                compteur=0;

                map.suppActor();
                jeu.removeActor(map);
                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.selectionCheminEp.removeActor(jeu);

                jeu.setEtat(game.editeurNSolo);
                game.setScreen(game.editeurNSolo);
            }
        });

        valider.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    fw = new FileWriter(f);
                    fw.write(map.mapToTextN());
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                compteur=0;                                             //Mettre à 0 le compteur de taille de chemin après validation
                map.suppActor();
                jeu.removeActor(map);
                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.selectionCheminEp.removeActor(jeu);
                jeu.setEtat(game.editeurNSolo);
                game.setScreen(game.editeurNSolo);
            }
        });


        table.add(indication).padBottom(30).padLeft(30);
        table.row();
        table.add(indication1).padBottom(30).padLeft(30);
        table.row();
        table.add(valider).padBottom(30);
        table.add(retour).padBottom(30);

        this.addActor(back);
        this.addActor(table);
        jeu.addActor(map);
        this.addActor(jeu);

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
    Bomberball.stg.clear();
    jeu.removeActor(jeu.map);
    Bomberball.input.removeProcessor(this);

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean touchDown( int x, int y, int pointer, int button) {
        Actor hitActor= this.getStage().hit(x,Gdx.graphics.getHeight()-y,true);
        if(hitActor.getParent() instanceof Case && hitActor.getName()==null){                               //Si on clique sur une case dont le nom n'existe pas,
            Case c=(Case) hitActor.getParent();                                                             //c'est donc une case sans rien desssus
            int xc=c.posX();
            int yc=c.posY();
            if(button== Input.Buttons.LEFT){
                if(compteur==0){                                                                            //Si la taille du chemin est de 0,
                    compteur++;                                                                             //on pose un ennemi
                    c.setMarque(new Image(Bomberball.multiTexture[18]));
                    ennemi_passif=new EnnemiPassif(true,c,3);
                    ennemi_passif.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.setEnnemi(ennemi_passif);
                    ennemi_passif.getChemin().add(c);
                }
                else if(compteur==1){                                                                       //Si la taille est de 1, on peut ajouter une case à proximité
                    if(c.getMap().getGrille()[xc+1][yc].getEnnemi()!=null){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }
                    else if(c.getMap().getGrille()[xc-1][yc].getEnnemi()!=null){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }
                    else if(c.getMap().getGrille()[xc][yc+1].getEnnemi()!=null){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }
                    else if(c.getMap().getGrille()[xc][yc-1].getEnnemi()!=null){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }
                }
                else if(compteur>1){                                                               //si la taille est supérieur à 1, on peut ajouter une case à proximité
                    Case CaseC=ennemi_passif.getChemin().getLast();
                    int xactuel=CaseC.posX();
                    int yactuel=CaseC.posY();
                    if(xc==xactuel+1 && yactuel==yc){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }
                    else if(xc==xactuel-1 && yactuel==yc){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }
                    else if(xc==xactuel && yc==yactuel+1){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }
                    else if(xc==xactuel && yc==yactuel-1){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }

                }
                return true;
            }

        }
        else if(hitActor.getName()!=null){                                                          //Si on a cliqué sur un endroit portant un nom
            if(hitActor.getName().equals("Ennemis")){                                               //Si on a cliqué sur ennemi, on peut le supprimer et supprimer son chemin
                if(button==Input.Buttons.RIGHT){
                    for(Case c: ennemi_passif.getChemin()){
                        c.setMarque(null);
                    }
                    compteur=0;
                    ennemi_passif.getChemin().clear();
                    ennemi_passif=null;
                    Case c=(Case) hitActor.getParent();
                    c.setEnnemi(null);
                    Image background=new Image(Bomberball.multiTexture[0]);
                    background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.addActor(background);
                    return true;

                }

            }
            else if(hitActor.getName().equals("red")){                                              //Si on clique sur une position marquée, on supprime tous le chemin après ce point
                Case c=(Case) hitActor.getParent();
                if(button==Input.Buttons.RIGHT){
                    while(ennemi_passif.getChemin().getLast()!=c){
                        ennemi_passif.getChemin().getLast().setMarque(null);
                        Image background=new Image(Bomberball.multiTexture[0]);
                        background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                        ennemi_passif.getChemin().getLast().addActor(background);
                        ennemi_passif.getChemin().removeLast();
                        compteur--;
                    }
                    ennemi_passif.getChemin().removeLast();
                    compteur--;
                    c.setMarque(null);
                    Image background=new Image(Bomberball.multiTexture[0]);
                    background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.addActor(background);
                    return true;
                }

            }
            else if(hitActor.getName().equals("Personnage")){                                           //On a la possibilité de faire passer un chemin sur un joueur
                Case c=(Case) hitActor.getParent();
                int xc=c.posX();
                int yc=c.posY();
                if(button==Input.Buttons.RIGHT){
                    while(ennemi_passif.getChemin().getLast()!=c){                                  //On enlève tous le chemin après le joueur s'il y a un click droit
                        ennemi_passif.getChemin().getLast().setMarque(null);
                        Image background=new Image(Bomberball.multiTexture[0]);
                        background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                        ennemi_passif.getChemin().getLast().addActor(background);
                        ennemi_passif.getChemin().removeLast();
                        compteur--;
                    }
                    ennemi_passif.getChemin().removeLast();
                    compteur--;
                    c.setMarque(null);
                    Image background=new Image(Bomberball.multiTexture[0]);
                    background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.addActor(background);
                    return true;
                }
                else if(button==Input.Buttons.LEFT){                                                //Si on clique gauche, on peut continuer le chemin
                    Case CaseC=ennemi_passif.getChemin().getLast();
                    int xactuel=CaseC.posX();
                    int yactuel=CaseC.posY();
                    Personnage p=c.getPersonnage();
                    if(xc==xactuel+1 && yactuel==yc){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                        c.setPersonnage(p);
                    }
                    else if(xc==xactuel-1 && yactuel==yc){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                        c.setPersonnage(p);
                    }
                    else if(xc==xactuel && yc==yactuel+1){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                        c.setPersonnage(p);
                    }
                    else if(xc==xactuel && yc==yactuel-1){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                        c.setPersonnage(p);
                    }
                    return true;
                }
            }
            else if(hitActor.getName().equals("Ennemis")){
                Case c=(Case) hitActor.getParent();
                int xc=c.posX();
                int yc=c.posY();
                if(button==Input.Buttons.RIGHT){                                                        //Si on clique droit sur un ennemi, il est entièrement supprimé
                    while(ennemi_passif.getChemin().getLast()!=c){
                        ennemi_passif.getChemin().getLast().setMarque(null);
                        Image background=new Image(Bomberball.multiTexture[0]);
                        background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                        ennemi_passif.getChemin().getLast().addActor(background);
                        ennemi_passif.getChemin().removeLast();
                        compteur--;
                    }
                    ennemi_passif.getChemin().removeLast();
                    compteur--;
                    c.setMarque(null);
                    Image background=new Image(Bomberball.multiTexture[0]);
                    background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.addActor(background);
                    return true;
                }
                else if(button==Input.Buttons.LEFT){                                                    //Si on clique gauche, on est dans le cas où on peut continuer
                    Case CaseC=ennemi_passif.getChemin().getLast();                                     //le chemin de l'ennemi
                    int xactuel=CaseC.posX();
                    int yactuel=CaseC.posY();
                    Personnage p=c.getPersonnage();
                    if(xc==xactuel+1 && yactuel==yc){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                        c.setPersonnage(p);
                    }
                    else if(xc==xactuel-1 && yactuel==yc){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                        c.setPersonnage(p);
                    }
                    else if(xc==xactuel && yc==yactuel+1){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                        c.setPersonnage(p);
                    }
                    else if(xc==xactuel && yc==yactuel-1){
                        compteur++;
                        ennemi_passif.getChemin().add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                        c.setPersonnage(p);
                    }
                    return true;
                }
            }
        }
        return false;
    }

}
