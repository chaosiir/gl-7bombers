package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.bomber.game.Bomberball.stg;

public class SelectionCheminEpa extends Etat implements Screen {
    Bomberball game;

    Image back;

    Label indication;
    Label indication1;
    EnnemiPassifAgressif ennemi_passif_aggressif;

    TextButton valider;
    TextButton retour;

    Table table;
    int compteur=0;

    Map map;

    Skin skin;

    File f;
    FileWriter fw;
    public SelectionCheminEpa(Bomberball game,Jeu jeu) {
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
        String text=Bomberball.loadFile(f);
        map=Map.mapFromString(text);
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
        indication1= new Label("Clic droit pour enlever \n la case du chemin de votre ennemi passif",skin);
        indication1.setName("Indication1");

        valider = new TextButton("Valider la trajectoire",skin);
        valider.setName("valider");
        retour = new TextButton("Retour",skin);
        retour.setName("retour");

        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(ennemi_passif_aggressif!=null){
                    ennemi_passif_aggressif.prochain_deplacement.clear();
                    ennemi_passif_aggressif=null;
                }
                compteur=0;
                jeu.setEtat(game.editeurNSolo);
                game.setScreen(game.editeurNSolo);
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
                compteur=0;
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

        jeu.addActor(back);
        jeu.addActor(table);
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
        Actor hitActor= jeu.getStage().hit(x,y,true);
        if(hitActor.getParent() instanceof Case && hitActor.getName()==null){
            Case c=(Case) hitActor.getParent();
            int xc=c.posX();
            int yc=c.posY();
            if(button== Input.Buttons.LEFT){
                if(compteur==0){
                    compteur++;
                    c.setMarque(new Image(Bomberball.multiTexture[18]));
                    ennemi_passif_aggressif=new EnnemiPassifAgressif(true,c,5,5,false,null);
                    ennemi_passif_aggressif.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.setEnnemi(ennemi_passif_aggressif);
                    ennemi_passif_aggressif.prochain_deplacement.add(c);
                }
                else if(compteur==1){
                    if(c.getMap().getGrille()[xc+1][yc].getEnnemi()!=null){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }
                    else if(c.getMap().getGrille()[xc-1][yc].getEnnemi()!=null){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }
                    else if(c.getMap().getGrille()[xc][yc+1].getEnnemi()!=null){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }
                    else if(c.getMap().getGrille()[xc][yc-1].getEnnemi()!=null){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }
                }
                else if(compteur>1){
                    Case CaseC=ennemi_passif_aggressif.prochain_deplacement.getLast();
                    int xactuel=CaseC.posX();
                    int yactuel=CaseC.posY();
                    if(xc==xactuel+1 && yactuel==yc){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }
                    else if(xc==xactuel-1 && yactuel==yc){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }
                    else if(xc==xactuel && yc==yactuel+1){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }
                    else if(xc==xactuel && yc==yactuel-1){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                    }

                }
                return true;
            }

        }
        else if(hitActor.getName()!=null){
            if(hitActor.getName().equals("Ennemis")){
                if(button==Input.Buttons.RIGHT){
                    for(Case c: ennemi_passif_aggressif.prochain_deplacement){
                        c.setMarque(null);
                    }
                    compteur=0;
                    ennemi_passif_aggressif.prochain_deplacement.clear();
                    ennemi_passif_aggressif=null;
                    Case c=(Case) hitActor.getParent();
                    c.setEnnemi(null);
                    Image background=new Image(Bomberball.multiTexture[0]);
                    background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.addActor(background);
                    return true;

                }

            }
            else if(hitActor.getName().equals("red")){
                Case c=(Case) hitActor.getParent();
                if(button==Input.Buttons.RIGHT){
                    while(ennemi_passif_aggressif.prochain_deplacement.getLast()!=c){
                        ennemi_passif_aggressif.prochain_deplacement.getLast().setMarque(null);
                        Image background=new Image(Bomberball.multiTexture[0]);
                        background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                        ennemi_passif_aggressif.prochain_deplacement.getLast().addActor(background);
                        ennemi_passif_aggressif.prochain_deplacement.removeLast();
                        compteur--;
                    }
                    ennemi_passif_aggressif.prochain_deplacement.removeLast();
                    compteur--;
                    c.setMarque(null);
                    Image background=new Image(Bomberball.multiTexture[0]);
                    background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.addActor(background);
                    return true;
                }

            }
            else if(hitActor.getName().equals("Personnage")){
                Case c=(Case) hitActor.getParent();
                int xc=c.posX();
                int yc=c.posY();
                if(button==Input.Buttons.RIGHT){
                    while(ennemi_passif_aggressif.prochain_deplacement.getLast()!=c){
                        ennemi_passif_aggressif.prochain_deplacement.getLast().setMarque(null);
                        Image background=new Image(Bomberball.multiTexture[0]);
                        background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                        ennemi_passif_aggressif.prochain_deplacement.getLast().addActor(background);
                        ennemi_passif_aggressif.prochain_deplacement.removeLast();
                        compteur--;
                    }
                    ennemi_passif_aggressif.prochain_deplacement.removeLast();
                    compteur--;
                    c.setMarque(null);
                    Image background=new Image(Bomberball.multiTexture[0]);
                    background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.addActor(background);
                    return true;
                }
                else if(button==Input.Buttons.LEFT){
                    Case CaseC=ennemi_passif_aggressif.prochain_deplacement.getLast();
                    int xactuel=CaseC.posX();
                    int yactuel=CaseC.posY();
                    Personnage p=c.getPersonnage();
                    if(xc==xactuel+1 && yactuel==yc){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                        c.setPersonnage(p);
                    }
                    else if(xc==xactuel-1 && yactuel==yc){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                        c.setPersonnage(p);
                    }
                    else if(xc==xactuel && yc==yactuel+1){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                        c.setPersonnage(p);
                    }
                    else if(xc==xactuel && yc==yactuel-1){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
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
                if(button==Input.Buttons.RIGHT){
                    while(ennemi_passif_aggressif.prochain_deplacement.getLast()!=c){
                        ennemi_passif_aggressif.prochain_deplacement.getLast().setMarque(null);
                        Image background=new Image(Bomberball.multiTexture[0]);
                        background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                        ennemi_passif_aggressif.prochain_deplacement.getLast().addActor(background);
                        ennemi_passif_aggressif.prochain_deplacement.removeLast();
                        compteur--;
                    }
                    ennemi_passif_aggressif.prochain_deplacement.removeLast();
                    compteur--;
                    c.setMarque(null);
                    Image background=new Image(Bomberball.multiTexture[0]);
                    background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                    c.addActor(background);
                    return true;
                }
                else if(button==Input.Buttons.LEFT){
                    Case CaseC=ennemi_passif_aggressif.prochain_deplacement.getLast();
                    int xactuel=CaseC.posX();
                    int yactuel=CaseC.posY();
                    Personnage p=c.getPersonnage();
                    if(xc==xactuel+1 && yactuel==yc){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                        c.setPersonnage(p);
                    }
                    else if(xc==xactuel-1 && yactuel==yc){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                        c.setPersonnage(p);
                    }
                    else if(xc==xactuel && yc==yactuel+1){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                        c.setPersonnage(p);
                    }
                    else if(xc==xactuel && yc==yactuel-1){
                        compteur++;
                        ennemi_passif_aggressif.prochain_deplacement.add(c);
                        c.setMarque(new Image(Bomberball.multiTexture[18]));
                        c.setPersonnage(p);
                    }
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        return false;
    }
}
