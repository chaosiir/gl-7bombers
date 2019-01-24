
package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.io.Serializable;

import static com.badlogic.gdx.math.MathUtils.random;

// !!! a faire très important lorqu'on enleve les mur / perso/ acteur  => enlever l'acteur
//ce serait mieux de supprimer les parametre et de prendre les acteurs par nom à chaque fois (à voir si plus pratique => on peut le recuperer
// en damandant à un groupe de nous donner un acteur  avec un nom via group.getActor(nom) => voir tuto Acteur
public class Case extends Group  {// case est un group d'acteur  (bombe/mur /bonus /personnage)
    Map map;
    private int x;
    private int y;
    private Bombe bombe;
    private Bonus bonus;
    private Mur mur;
    private Personnage personnage;
    private Porte porte;
    private Ennemis ennemi;
    private Image marque; //Elle me sert pour l'éditeur de déplacement des ennemis passifs


    public Image getBackground() {
        return background;
    }

    public void setBackground(Image background) {
        this.background = background;
    }

    private Image background;
    /**
     * Constructeur de la classe Case
     * @return une case
     */
    public Case() {
        this.setPosition((x)*Bomberball.taillecase,(y)*Bomberball.taillecase);//definition de la position  = coordonnées * taille d'une case
       background=new Image(Bomberball.multiTexture[0]);//de base une image s'affiche vide (sol) si il y a qqc il sera afficher au dessus
        background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);//definition de la taille de l'image
        //et de la position (0,0) = sur la case car position relative
        this.addActor(background);// une image est un acteur => voir tuto acteur


    }

    /**
     * Accesseur de l'ennemi de la case
     * @return la grille
     */
    public Ennemis getEnnemi(){
        return ennemi;
    }

    /**
     * Modificateur de l'ennemi de la case
     * @param E
     */
    public void setEnnemi(Ennemis E){
        removeActor(this.ennemi);
        this.ennemi=E;
        if(E!=null){
            this.addActor(E);
        }
    }

    /**
     * Accesseur de la map de la case
     * @return la Map sur laquelle se trouve la case
     */
    public Map getMap() {
        return map;
    }

    /**
     * Modificateur de la map de la case
     * @param map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Modificateur de la marque de passage d'un ennemi dans l'éditeur
     * @param r
     */
    public void setMarque(Image r){
        removeActor(this.marque);
        this.marque=r;
        if(r!=null){
            this.addActor(r);
            r.setName("red");
            r.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        }
    }

    /**
     * Accesseur de la marque de passage d'un ennemi dans l'éditeur
     * @return la marque de passage d'un ennemi dans l'éditeur
     */
    public Image getMarque(){
        return this.marque;
    }

    /**
     * Accesseur de la bombe de la case
     * @return la bombe de la case
     */
    public Bombe getBombe() {
        return bombe;
    }

    /**
     * Modificateur de la bombe de la case
     * @param bombe
     */
    public void setBombe(Bombe bombe) {
        this.removeActor(this.bombe);
        this.bombe = bombe;
        if (bombe != null) {
            this.addActor(bombe);
        }
    }

    /**
     * Accesseur du bonus de la case
     * @return un bonus
     */
    public Bonus getBonus() {
        return bonus;
    }

    /**
     * Modificateur du bonus de la case
     * @param bonus
     */
    public void setBonus(Bonus bonus) {
        this.removeActor(findActor("bonus"));
        this.removeActor(this.bonus);
        this.bonus = bonus;
        if (bonus != null) {
            this.addActor(bonus);
        }
    }
    /**
     * Accesseur du mur de la case
     * @return un mur
     */
    public Mur getMur() {
        return mur;
    }

    /**
     * Accesseur de la porte de la case
     * @return une porte
     */
    public Porte getPorte() {
        return porte;
    }

    /**
     * Modificateur de la porte de la case
     * @param porte
     */

    public void setPorte(Porte porte) {
        this.removeActor(this.findActor("Porte"));
        if (porte!=null){
            this.porte=porte;
            porte.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
            this.addActor(porte);
        }
        else{
            this.porte=null;
        }

    }

    /**
     * Modificateur du mur de la case
     * @param mur
     */
    public void setMur(Mur mur) {
        removeActor(findActor("MurD"));
        removeActor(findActor("MurI"));
        this.mur = mur;
        if (mur != null) {
            mur.setBounds(0, 0, Bomberball.taillecase, Bomberball.taillecase);
            this.addActor(mur);// rajout d'un mur à la bonne taille est possition
        }
    }
    /**
     * Accesseur du personnage de la case
     * @return un personnage
     */
    public Personnage getPersonnage() {
        return personnage;
    }

    /**
     * Modificateur du personnage de la case
     * @param personnage
     */

    public void setPersonnage(Personnage personnage) {// meme chose que pour mur
        this.removeActor(this.personnage);
        this.personnage = personnage;
        if (personnage != null) {
            this.addActor(personnage);
        }
    }

    /**
     * Accesseur de l'ordonnée
     * @return l'ordonnée
     */
    public int posY() {
        return y;
    }

    /**
     * Modificateur de l'ordonnée
     * @param y
     */
    public void setposY(int y) { this.y = y;
        this.setY(y*Bomberball.taillecase);}//convertion du y de position dans la grille à la coordonnee de l'ecran

    /**
     * Accesseur de l'abscisse
     * @return l'abscisse
     */
    public int posX() {
        return x;
    }

    /**
     * Modificateur de l'abscisse
     * @param x
     */
    public void setposX(int x) {
        this.x = x;
        this.setX(2*Bomberball.taillecase+x*Bomberball.taillecase);
    }


    /**
     * Declenche une explosion sur la case et la propage vers la case du haut si l'entier en paramètre n'est pas nul
     * @param longueur
     */
    public void explosionHaute(int longueur){
        /*Elimination des joueurs et des ennemis pris dans l'explosion*/
        if(this.personnage!=null){
            this.personnage.setVivant(false);
            this.removeActor(personnage);
        } if (this.ennemi!=null){
            this.ennemi.setVivant(false);
            this.removeActor(ennemi);
        }
        /*Destruction des murs destructibles*/
        if (this.mur instanceof MurD){
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    if(time>1){
                        removeActor(mur);
                        setMur(null);
                        return true;
                    }
                    return false;
                }
            });

        } else if (this.mur instanceof MurI){
            //rien
        /*Propagation de l'explosion si la case est vide et la portee non atteinte*/
        }else {
            Image explo=new Image(Bomberball.multiTexture[(longueur>0)?11:12]);
            explo.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
            explo.setName("explo");
            this.addActor(explo);
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    if(time>1){
                        removeActor(findActor("explo"));
                        return true;
                    }
                    return false;
                }
            });
            if (longueur>0){
                this.getMap().getGrille()[x][y+1].explosionHaute(longueur-1);
            }
        }

    }
    /**
     * Declenche une explosion sur la case et la propage vers la case du bas si l'entier en paramètre n'est pas nul
     * @param longueur
     */
    public void explosionBasse(int longueur){
        /*Elimination des joueurs et des ennemis pris dans l'explosion*/
        if(this.personnage!=null){
            this.personnage.setVivant(false);
            this.removeActor(personnage);
        }
        if (this.ennemi!=null){
              this.ennemi.setVivant(false);
        }
        /*Destruction des murs destructibles*/
        if (this.mur instanceof MurD){
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    if(time>1){
                        removeActor(mur);
                        setMur(null);
                        return true;
                    }
                    return false;
                }
            });
        } else if (this.mur instanceof MurI){
            //rien
        /*Propagation de l'explosion si la case est vide et la portee non atteinte*/
        }else {
            Image explo=new Image(Bomberball.multiTexture[(longueur>0)?11:13]);
            explo.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
            explo.setName("explo");
            this.addActor(explo);
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    if(time>1){
                        removeActor(findActor("explo"));
                        return true;
                    }
                    return false;
                }
            });
            if (longueur>0){
                this.getMap().getGrille()[x][y-1].explosionBasse(longueur-1);
            }
        }

    }
    /**
     * Declenche une explosion sur la case et la propage vers la case de droite si l'entier en paramètre n'est pas nul
     * @param longueur
     */
    public void explosionDroite(int longueur){
        /*Elimination des joueurs et des ennemis pris dans l'explosion*/
        if(this.personnage!=null){
            this.personnage.setVivant(false);
            this.removeActor(personnage);
        }
        if (this.ennemi!=null){
            this.ennemi.setVivant(false);
        }
        /*Destryctino des murs destructibles*/
        if (this.mur instanceof MurD){
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    if(time>1){
                        removeActor(mur);
                        setMur(null);
                        return true;
                    }
                    return false;
                }
            });
        } else if (this.mur instanceof MurI){
            //rien
        }else {
            Image explo=new Image(Bomberball.multiTexture[(longueur>0)?10:14]);
            explo.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
            explo.setName("explo");
            this.addActor(explo);
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    if(time>1){
                        removeActor(findActor("explo"));
                        return true;
                    }
                    return false;
                }
            });
            if (longueur>0){
                this.getMap().getGrille()[x+1][y].explosionDroite(longueur-1);
            }
        }

    }
    /**
     * Declenche une explosion sur la case et la propage vers la case de gauche si l'entier en paramètre n'est pas nul
     * @param longueur
     */
    public void explosionGauche(int longueur){
        if(this.personnage!=null){
            this.personnage.setVivant(false);
            this.removeActor(personnage);
        }
        if (this.ennemi!=null){
            this.ennemi.setVivant(false);
        }
        if (this.mur instanceof MurD){
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    if(time>1){
                        removeActor(mur);
                        setMur(null);
                        return true;
                    }
                    return false;
                }
            });
        } else if (this.mur instanceof MurI){
            //rien
        /*Propagation de l'explosion si la case est vide et la portee non atteinte*/
        }else {
            Image explo=new Image(Bomberball.multiTexture[(longueur>0)?10:15]);
            explo.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
            explo.setName("explo");
            this.addActor(explo);
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    if(time>1){
                        removeActor(findActor("explo"));
                        return true;
                    }
                    return false;
                }
            });
            if (longueur>0){
                this.getMap().getGrille()[x-1][y].explosionGauche(longueur-1);
            }
        }
    }


    /**
     * Supprime la bombe de la case
     */
    public void suppBombe(){
        this.bombe=null;
        this.removeActor(this.findActor("bombe"));
    }

    /**
     * Supprime le bonus de la case
     */
    public void suppBonus(){
        this.bonus=null;
        this.removeActor(this.findActor("bonus"));

    }

    /**
     * Verifie que la case est vide
     * @return boolean
     */
    public boolean estVide(){
        if(porte==null && personnage==null && mur==null && ennemi==null){
            return true;
        }
        return false;
    }


}