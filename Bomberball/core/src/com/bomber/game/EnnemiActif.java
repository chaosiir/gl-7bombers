package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.LinkedList;

public class EnnemiActif extends Ennemis {

    public EnnemiActif(boolean vivant, Case c, int pm) {
        super(Bomberball.multiTexture[16], vivant, c, pm);
        setAnimationdroite();

    }


    /* fonction renvoyant la liste des cases non occupées par un mur ou un autre ennemi autour de la case donnée*/
    public LinkedList<Case> voisinAccessibles(Case caseC) {
        Map m = caseC.getMap();
        Case[][] grille = m.getGrille();

        int a = caseC.posX();
        int b = caseC.posY();
        System.out.println(a + " " + b);
        LinkedList<Case> voisin = new LinkedList<Case>();

        if (caseLibre(grille[a - 1][b])) {
            voisin.add(grille[a - 1][b]);
        }

        if (caseLibre(grille[a][b + 1])) {
            voisin.add(grille[a][b + 1]);
        }

        if (caseLibre(grille[a + 1][b])) {
            voisin.add(grille[a + 1][b]);
        }

        if (caseLibre(grille[a][b - 1])) {
            voisin.add(grille[a][b - 1]);
        }

        return voisin;
    }


    /* Met à jour le chemin de l'ennemi pour qu'il soit maximum */
    public LinkedList<Case> cheminMax(int pmMax, Case cChemin) {

        LinkedList<Case> visites = new LinkedList<Case>();
        visites.add(cChemin);

        return cheminMaxAux(visites, pmMax, pmMax - 1);
    }


    /* Calcul par récursivité le chemin qui emmène l'ennemi le plus loin possible de la case où il se trouve */
    public LinkedList<Case> cheminMaxAux(LinkedList<Case> visites, int pmMax, int pmRestants) {
        //case initiale = visites[pm - pmRestants -1]
        LinkedList<Case> res = new LinkedList<Case>();

        // cas de base: l'ennemi ne peut pas aller plus loin
        if (pmRestants == 0) {
            return res;
        } else {
            LinkedList<Case> voisins = voisinAccessibles(visites.getLast());

            LinkedList<Case> cheminProvisoire;


            // sinon on parcours les cases voisines non visitées
            for (Case a : voisins) {
                if (!visites.contains(a)) {
                    if (visites.size() == (pmMax - pmRestants)) {
                        visites.add(a);
                    } else {
                        visites.set(pmMax - pmRestants, a);
                    }
                    cheminProvisoire = cheminMaxAux(visites, pmMax, pmRestants - 1);
                    if (cheminProvisoire.size() > res.size()) {
                        res = cheminProvisoire;
                    }
                }
            }
            return res;
        }
    }

    @Override
    public void setAnimationgauche() {
        this.removeAction(animation);
        animation = new Action() {
            float time = 0;

            @Override
            public boolean act(float delta) {
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("bat" + 0 + "" + (int) (time * 6) % 4 + "inv"))));

                return false;
            }
        };
        this.addAction(animation);
    }

    public void setAnimationdefaite() {
        this.removeAction(animation);
        animation = new Action() {
            float time = 0;

            @Override
            public boolean act(float delta) {
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("bat" + 0 + "" + 0 + ((((int) (time * 5) % 2) == 0) ? "" : "inv")))));

                return false;
            }
        };
        this.addAction(animation);
    }

    @Override
    public void setAnimationdroite() {
        this.removeAction(animation);
        animation = new Action() {
            float time = 0;

            @Override
            public boolean act(float delta) {
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("bat" + 0 + "" + (int) (time * 6) % 4))));

                return false;
            }
        };
        this.addAction(animation);

    }

    public void miseAjour() {
        int i = 0;
        Case suivante;
        LinkedList<Case> cheminProvisoire = cheminMax(pm, c);
        if (!cheminProvisoire.isEmpty()) {
            suivante = cheminProvisoire.get(0);
            while (i < pm && caseLibre(suivante)) {
                prochain_deplacement.add(suivante);
                i = i + 1;
                suivante = cheminProvisoire.get(i);
            }
        } else {
            prochain_deplacement = new LinkedList<Case>();
        }
    }

}