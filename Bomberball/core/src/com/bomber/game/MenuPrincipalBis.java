package com.bomber.game;


    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.graphics.GL20;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.Sprite;
    import com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import com.badlogic.gdx.scenes.scene2d.Actor;
    import com.badlogic.gdx.scenes.scene2d.InputEvent;
    import com.badlogic.gdx.scenes.scene2d.ui.Image;
    import com.badlogic.gdx.scenes.scene2d.ui.Skin;
    import com.badlogic.gdx.scenes.scene2d.ui.Table;
    import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
    import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
    import com.badlogic.gdx.utils.Align;
/**
 * Classe MenuPrincipalBis
 * Elle affiche le menu principal lorsque le jeu démarre
 * @author Paul-Louis Renard
 *
 */
public class MenuPrincipalBis extends Etat implements Screen {
    private Image back;
    private Skin skin;
    private Table table;
    private TextButton soloButton;
    private  TextButton multiButton;


    private TextButton editeurButton;
    private TextButton quitButton;

    Bomberball game; // Note it's "MyGame" not "Game"


    /**
     * Constructeur de la classe MenuPrincipalBis
     * @param game
     * @param jeu
     */
    public MenuPrincipalBis(Bomberball game,Jeu jeu){
        super(jeu);
        this.game = game;
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        return false;
    }

    @Override
        public void render(float delta) {
            // update and draw stuff
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)

        }





        @Override
        public void resize(int width, int height) {
        }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
        @Override
        public void show() {

            // called when this screen is set as the screen with game.setScreen();
            skin=new Skin(Gdx.files.internal("uiskin.json"));
            back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
            back.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());



            table=new Table(); //Tableau
            table.setWidth(Bomberball.stg.getWidth());
            table.align(Align.center | Align.top); // Middle of the screen start at the top
            table.setPosition(0, 3*Gdx.graphics.getHeight()/4);

            soloButton = new TextButton("Mode Solo",skin);
            multiButton = new TextButton("Mode multijoueur",skin);
            editeurButton = new TextButton("Mode Editeur de niveau",skin);
            quitButton = new TextButton("Quitter le jeu",skin);

            soloButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    jeu.removeActor(back);
                    jeu.removeActor(table);
                    jeu.setEtat(game.menuSolo);
                    game.setScreen(game.menuSolo);
                }
            });
            multiButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    jeu.removeActor(back);
                    jeu.removeActor(table);
                    jeu.setEtat(game.choixMenuMultijoueur);
                    game.setScreen(game.choixMenuMultijoueur);
                }
            });
            editeurButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    jeu.removeActor(back);
                    jeu.removeActor(table);
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

            table.padTop(30); //Espace de 30 entre le premier texte et le haut

            table.add(soloButton).padBottom(30); //Espace de 30 entre les 2 boutons
            //Permet de les mettre sous forme de colonne
            table.row();

            table.add(multiButton).padBottom(30);
            table.row();

            table.add(editeurButton).padBottom(30); //Espace de 30 entre les 2 boutons
            table.row();

            table.add(quitButton); //Espace de 30 entre les 2 boutons
            table.row();

            back.setName("Arrière plan: menu principal");

            jeu.addActor(back);
            jeu.addActor(table);




        }


        @Override
        public void hide() {
            // called when current screen changes from this to a different screen
        }


        @Override
        public void pause() {
        }


        @Override
        public void resume() {
        }


        @Override
        public void dispose() {
            // never called automatically
        }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        return true;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return false;
    }
}

