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

public class Multijoueur extends Etat implements Screen {//etat multijoueur
    int pm;
    int nb;
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

    public Multijoueur(Bomberball game,Jeu jeu) {
        super(jeu);
        this.game=game;

    }


    @Override
    public void show() {
        skin=new Skin(Gdx.files.internal("uiskin.json"));

        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        back.setName("Je suis ton arrière plan");

        if(jeu.map==null){
            jeu.map=Map.generatePvp(65);
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
        pm=5;
        nb=1;

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

        player1=new Image(Bomberball.multiTexture[4]);
        player1.setBounds(3*Bomberball.taillecase+30,Gdx.graphics.getHeight()-Bomberball.taillecase-50,Bomberball.taillecase,Bomberball.taillecase);






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
                    }
                }

                if (keycode == Input.Keys.LEFT) {
                    if (pm > 0) {
                        b = joueur.deplacerGauche();
                        pm = ((b) ? pm - 1 : pm);
                    }
                }
                if (keycode == Input.Keys.DOWN) {
                    if (pm > 0) {
                        b = joueur.deplacerBas();
                        pm = ((b) ? pm - 1 : pm);
                    }
                }
                if (keycode == Input.Keys.UP) {
                    if (pm > 0) {
                        b = joueur.deplacerHaut();
                        pm = ((b) ? pm - 1 : pm);
                    }
                }
                if (keycode == Input.Keys.SPACE) {
                    if (nb > 0) {
                        b = joueur.poserBombe();
                        nb = ((b) ? nb - 1 : nb);
                    }
                }
                if (keycode == Input.Keys.ENTER) {
                    jeu.map.explosion();
                    tour=(tour+1)%4;
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

                        game.victoire=new Victoire(game,jeu,"Match nul");
                        jeu.setEtat(game.victoire);
                        game.setScreen(game.victoire);


                    }
                    else if(nbviv==1){
                        jeu.map=null;
                        jeu.removeActor(jeu.findActor("Map"));
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
                    }



                }
            }
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
