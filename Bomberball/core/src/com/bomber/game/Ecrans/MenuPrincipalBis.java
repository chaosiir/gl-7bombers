package com.bomber.game.Ecrans;


    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.graphics.GL20;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.scenes.scene2d.InputEvent;
    import com.badlogic.gdx.scenes.scene2d.ui.*;
    import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
    import com.badlogic.gdx.utils.Align;
    import com.bomber.game.Bomberball;
    import com.bomber.game.Jeu;

/**
 * Classe MenuPrincipalBis
 * Elle affiche le menu principal lorsque le jeu démarre
 *
 */
public class MenuPrincipalBis extends Etat implements Screen {
    private Image back;                 //Image de l'arrière-plan
    private Skin skin;                  //Caractéristiques des éléments graphiques
    private Table table;                //Permet d'organiser l'écran
    private TextButton soloButton;      //Permet d'aller dans le mode solo
    private  TextButton multiButton;    //Permet d'aller dans le mode multi


    private TextButton editeurButton;   //Permet d'aller dans l'éditeur de map
    private TextButton quitButton;      //Permet de fermer le jeu

    Bomberball game;                    //Instance de la classe principale




    /**
     * Constructeur de la classe MenuPrincipalBis
     *
     * @param game : l'application BomberBall
     * @param jeu : Map avec informations supplémentaires
     */
        public MenuPrincipalBis(Bomberball game,Jeu jeu){
            super(jeu);
            this.game = game;

        }
    /**
     * Fonction détectant un mouvement de la souris. On n'utilise pas cette fonctionnalité par la suite.
     *
     * @param x : récupère la position x du pointeur.
     * @param y : récupère la position x du pointeur.
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    /**
     * Met à jour l'affichage
     * @param delta: Intervalle de temps entre deux affichages
     */
    @Override
        public void render(float delta) {
            // update and draw stuff
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
     * Méthode appelée pour afficher la fenêtre
     */
        @Override
        public void show() {
            Bomberball.stg.addActor(this);
            Bomberball.stg.setKeyboardFocus(this);
            skin=new Skin(Gdx.files.internal("uiskin.json"));
            back= new Image(new Texture(Gdx.files.internal("main.png")) );
            back.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());



            table=new Table(); //Tableau
            table.setWidth(Bomberball.stg.getWidth());
            table.align(Align.center | Align.top); // Middle of the screen start at the top
            table.setPosition(-Gdx.graphics.getWidth()/7, 2*Gdx.graphics.getHeight()/4);

            soloButton = new TextButton("Mode Solo",skin);
            multiButton = new TextButton("Mode multijoueur",skin);
            editeurButton = new TextButton("Mode Editeur de niveau",skin);
            quitButton = new TextButton("Quitter le jeu",skin);

            soloButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.menuPrincipalBis.removeActor(back);
                    game.menuPrincipalBis.removeActor(table);
                    jeu.setEtat(game.menuSolo);
                    game.setScreen(game.menuSolo);
                }
            });
            multiButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    jeu.nbJoueur=4;                                             //Par defaut, on propose de faire une partie à 4 joueurs
                    game.menuPrincipalBis.removeActor(back);
                    game.menuPrincipalBis.removeActor(table);
                    jeu.setEtat(game.choixMenuMultijoueur);
                    game.setScreen(game.choixMenuMultijoueur);
                }
            });
            editeurButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.menuPrincipalBis.removeActor(back);
                    game.menuPrincipalBis.removeActor(table);
                    jeu.setEtat(game.choixEditeurN);
                    game.setScreen(game.choixEditeurN);
                }
            });
            quitButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.exit();
                }
            });

            table.padTop(30);                       //Espace de 30 entre le premier texte et le haut

            table.add(soloButton).padBottom(30);    //Espace de 30 entre les 2 boutons

            table.row();                            //Permet de les mettre sous forme de colonne

            table.add(multiButton).padBottom(30);
            table.row();

            table.add(editeurButton).padBottom(30); //Espace de 30 entre les 2 boutons
            table.row();

            table.add(quitButton);                  //Espace de 30 entre les 2 boutons
            table.row();

            back.setName("Arrière plan: menu principal");

            this.addActor(back);
            this.addActor(table);




        }

    /**
     * Fonction appelée lors d'un changement d'écran.
     */
        @Override
        public void hide() {
            Bomberball.stg.clear();
        }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas dans le code par la suite.
     */
        @Override
        public void pause() {
        }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas dans le code par la suite.
     */
        @Override
        public void resume() {
        }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas dans le code par la suite.
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
        return true;
    }
    /**
     * Fonction détectant un appui d'un des touche de la souris. On n'utilise pas cette fonctionnalité par la suite.
     *
     * @param x : récupère la position x du pointeur.
     * @param y : récupère la position y du pointeur.
     * @param pointer : récupère le pointeur sur événement.
     * @param button  : donne le bouton appuyé.
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }
}

