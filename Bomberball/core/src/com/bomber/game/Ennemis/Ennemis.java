package com.bomber.game.Ennemis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bomber.game.Bomberball;
import com.bomber.game.MapetObjet.Case;
import com.bomber.game.MapetObjet.Mur;

import java.util.LinkedList;

/**
 * Classe abstraite Ennemis
 * permet d'acceder à tout les types d'ennemis et de coder les fonctions communes à tous les ennemis
 */
public abstract class Ennemis extends Image {
    protected Case c;                               //case sur laquelle se trouve l'ennemis
    protected boolean vivant;                       //boolean indiquant si l'ennemis est vivant
    protected Case prochaine;                       //prochaine case sur laquelle l'ennemi doit aller
    protected int pm;                               //points de mouvement, 3 par defaut
    public LinkedList<Case> prochain_deplacement;   //contient la liste des cases sur lesquelles l'ennemis doit se deplacer pendant son tour
    protected Action animation;                     //animation actuelle de l'ennemi
    boolean teleportation=false;                    //l'ennemis doit se teleporter, uniquement pour les ennemis passifs aggressif

    /**
     * Accesseur du chemin de l'ennemi
     * @return une LinkedList<Case>
     */
    public LinkedList<Case> getProchain_deplacement() {
        return prochain_deplacement;
    }


    /**
     * change l'annimation de l'ennemis pour qu'il regarde à gauche
     */
    public abstract void setAnimationgauche();

    /**
     * change l'annimation de l'ennemis pour qu'il regarde à droite
     */
    public abstract void setAnimationdroite();

    /**
     * change l'annimation de l'ennemis pour qu'il danse lorsque le joueur meurt
     */
    public abstract void setAnimationdefaite();

    /**
     * Modificateur du chemin de l'ennemi
     * @param chemin
     */
    public void setProchain_deplacement(LinkedList<Case> chemin) {
        this.prochain_deplacement = chemin;
    }

    /**
     * Constucteur de la classe Ennemis
     * @param t texture de l'ennemi
     * @param vivant état de l'ennemi
     * @param c case où l'ennemi apparait
     * @param pm nombre de déplacement de l'ennemi
     */
    public Ennemis(Texture t,boolean vivant, Case c, int pm){
        super(t);
        this.c=c;
        this.vivant=vivant;
        this.pm=pm;

        prochain_deplacement=new LinkedList<Case>();
        setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
    }

    /**
     * Accesseur de la portée de l'ennemi uniquement pour les ennemis aggressifs
     * @return la portee de detection de l'ennemis
     */
    public abstract int getPortee();

    /**
     * Accesseur de la case de l'ennemi
     * @return la case de l'ennemis
     */
    public Case getC() {
        return c;
    }

    /**
     * Modificateur de la case de l'ennemi
     * @param  c
     */
    public void setC(Case c) {
        this.c = c;
    }

    /**
     * Accesseur de la case de l'ennemi
     * @return vivant
     */
    public boolean isVivant() {
        return vivant;
    }

    /**
     * Modificateur de vivant
     * @param vivant
     */
    public void setVivant(boolean vivant) {
        this.vivant = vivant;
        if(!vivant){
            c.setEnnemi(null);
        }
    }

    /**
     *
     * Accesseur du nombre de mouvement
     * @return pm le nombre de point de mouvement
     */
    public int getPm() {
        return pm;
    }

    /**
     *
     * Modificateur du nombre de mouvement
     * @param  pm
     */
    public void setPm(int pm) {
        this.pm = pm;
    }

    /**
     *
     * Accesseur du chemin de l'ennemis utilisable que pour les ennemis passifs
     * @return LinkedList<Case> un chemin
     */
    public abstract LinkedList<Case> getChemin();

    /**
     *
     * calcul le prochain deplacement de l'ennemis et le place dans prochain_deplacement
     *
     */
    public abstract void miseAjour();

    /**
     *
     * Accesseur de Aggro
     * @return boolean
     */
    public abstract boolean isAgro();

    /**
     *
     * Accesseur de la portée des ennemis aggressifs
     * @return LinkedList<Case> un chemin
     */
    public abstract void setPortee(int x);

    /**
     * Déplace l'ennemi de son nombre de mouvement sur son chemin
     */
    public void deplacer(){
        int i = pm;                                             //compteur du nombre de points de deplacement
        this.miseAjour();                                       //actualisation du chemin
        SequenceAction seq=new SequenceAction();                //sequence d'action qui vont etre executée à la suite pour afficher le deplacement
        Case actuel=c;
        if (!prochain_deplacement.isEmpty()){                   //dans le deplacement il ya a la case actuelle qu'on retire
            prochaine=prochain_deplacement.removeFirst();
        }

        if(teleportation){                                      //si on doit se teleporter
            prochaine=prochain_deplacement.removeFirst();
            teleportation(prochaine.posX(),prochaine.posY());   //on le fait via une fonction auxiliaire
        }
        while(!prochain_deplacement.isEmpty() && i>0){          //tant qu'il reste des déplacement ou des mouvement à faire
            prochaine=prochain_deplacement.removeFirst();       //on prend la prochaine case
            if(prochaine.getEnnemi()!=null && prochaine.getEnnemi()!=this){ //s'il y a un ennemi on arrete le mouvement
                break;
            }
            else{
                if(actuel.posX()!=prochaine.posX()){
                    if(actuel.posX()<prochaine.posX()){         //si la case est à droite
                        seq.addAction(deplacementdroite());     //on ajoute un deplacement à gauche et de meme pour les autres directions
                    }
                    else {
                        seq.addAction(deplacementgauche());
                    }
                }
                if(actuel.posY()!=prochaine.posY()){
                    if(actuel.posY()<prochaine.posY()){
                        seq.addAction(deplacementhaut());
                    }
                    else {
                        seq.addAction(deplacementbas());

                    }
                }
                actuel=prochaine;                               //on change la case actuelle
                i--;                                            //on retire un point de mouvement

            }


        }
        this.addAction(seq);                                    //un fois l'action prete on la donne à l'ennemis
    }


    /**
     * Vérifie si une case est vide
     * @param caseC
     * @return true si la case est libre
     */
    public boolean caseLibre(Case caseC){
        Mur mur=caseC.getMur();
        Ennemis ennemi=caseC.getEnnemi();
        if ((ennemi==this||ennemi==null)&&(mur==null)){
            return true;
        }
        else return false;
    }

    /**
     * renvois une action affichant un deplacement vers la droite de l'ennemis
     * @return Action
     */
    public Action deplacementdroite(){
        final Case proch=prochaine;                         //recuperation de la destination
        SequenceAction seq=new SequenceAction();            //on creer une sequence d'action
        seq.addAction(new Action() {
            @Override
            public boolean act(float delta) {                           //la premiere action

                ((Ennemis) target).setAnimationdroite();    //consiste à changer l'animation
                c.setEnnemi(null);                          //de retirer l'ennemis de la case actuelle
                c= proch;
                c.setEnnemi((Ennemis) target);
                target.setX(-Bomberball.taillecase);        //de le changer de case et de continuer à l'afficher à la position actuelle

                return true;
            }
        });
        MoveToAction mv= new MoveToAction();                //puis on revient au centre de la case
        mv.setPosition(0,0);
        mv.setDuration(0.2f);                               //en 0.2seconde
        seq.addAction(mv);
        seq.addAction(new Action() {
            @Override
            public boolean act(float delta) {                           //puis on teste si on tue le joueur
                if(c.getPersonnage()!=null){
                    c.getPersonnage().setVivant(false);
                }
                return true;
            }
        });
        return seq;                                         //on renvoie la sequence des 3 actions

    }

    /**
     * renvoie une action affichant un deplacement vers la gauche de l'ennemi
     * @return Action
     */
    public Action deplacementgauche(){
        final Case proch=prochaine;                             //fonctionnement similaire au précedent
        SequenceAction seq=new SequenceAction();
        seq.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                ((Ennemis) target).setAnimationgauche();
                return true;
            }
        });
        MoveByAction action=new MoveByAction();
        action.setAmount(-Bomberball.taillecase,0);         //mais on inverse le deplacement et le changement de case car comme on affiche la map case par case de gauche à droite
        action.setDuration(0.2f);                               //et de bas en haut on doit toujours sur la case qui se dessine en derniere parmis les case sur lesquelles on se deplace sinon l'ennemis est
        seq.addAction(action);                                  //recouvert
        seq.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                target.setX(0);
                c.setEnnemi(null);
                c=proch;
                c.setEnnemi((Ennemis) target);
                if(c.getPersonnage()!=null){
                    c.getPersonnage().setVivant(false);
                }
                return true;
            }
        });
        return seq;
    }

    /**
     * renvois une action affichant un deplacement vers le haut de l'ennemi
     * @return Action
     */
    public Action deplacementhaut(){
        SequenceAction seq=new SequenceAction();
        final Case proch=prochaine;
        seq.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                c.setEnnemi(null);
                ((Ennemis) target).setAnimationdroite();
                target.setY(-Bomberball.taillecase);
                c=proch;
                c.setEnnemi((Ennemis) target);
                return true;
            }
        });
        MoveToAction mv=new MoveToAction();
        mv.setPosition(0,0);
        mv.setDuration(0.2f);
        seq.addAction(mv);
        seq.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if(c.getPersonnage()!=null){
                    c.getPersonnage().setVivant(false);
                }
                return true;
            }
        });
        return seq;

    }

    /**
     * renvois une action affichant un deplacement vers le bas de l'ennemis
     * @return Action
     */
    public Action deplacementbas(){
        SequenceAction seq=new SequenceAction();
        final Case proch=prochaine;
        seq.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                ((Ennemis) target).setAnimationgauche();
                return true;
            }
        });
        MoveByAction action=new MoveByAction();
        action.setAmount(0,-Bomberball.taillecase);
        action.setDuration(0.2f);
        seq.addAction(action);
        seq.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                c.setEnnemi(null);
                target.setY(0);
                c=proch;
                c.setEnnemi((Ennemis) target);
                if(c.getPersonnage()!=null){
                    c.getPersonnage().setVivant(false);
                }

                return true;
            }
        });
        return seq;
    }

    /**
     * fait une animation de teleportation de l'ennemis vers la case de coordonné données
     * @param x
     * @param y
     */
    public void teleportation(int x,int y){
        SequenceAction seq=new SequenceAction();
        final Case proch=prochaine;
        final int a=x;
        final int b=y;
        MoveByAction mv=new MoveByAction();
        mv.setAmount(0,Bomberball.taillecase);      //on monte sans changer de case pour passer sous la case superieur et disparaitre
        mv.setDuration(0.3f);
        seq.addAction(mv);
        seq.addAction(new Action() {
            @Override
            public boolean act(float delta) {           //puis on change de case
                c.setEnnemi(null);
                c=c.map.getGrille()[a][b];
                c.setEnnemi((Ennemis) target);
                return true;
            }
        });
        MoveByAction action=new MoveByAction();         //puis on redescent de la case superieur pour apparaitre
        action.setAmount(0,-Bomberball.taillecase);
        action.setDuration(0.3f);
        seq.addAction(action);
        this.addAction(seq);                            //on l'ajoute directement car on ne fait que se teleporter et pas d'autre teleporation
        teleportation=false;                            //puis on reinitialise la teleportation

    }
}