import com.bomber.game.Bomberball;

import static org.junit.jupiter.api.Assertions.*;

class CaseTest {

    Case C;
    Map M;

        C=new Case();
        M=new Map();
        C.setMap(M);


        C.setMur(new MurD());
        try{
            C.explosionHaute();
        }catch (Exception e){
            System.out.println("Can't explode up");
        }
        if (C.getMur() is not null && C.getMur() instanceof (MurD)){
            System.out.println("Can't explode up");
        }
        C.setMur(null);

        C.setMur(new MurI());
        try{
            C.explosionHaute();
        }catch (Exception e){
            System.out.println("Can't explode up");
        }
        if (C.getMur() is not null && C.getMur() instanceof (MurD)){
            System.out.println("Can't explode up");
        }
        C.setMur(null);

        C.setPersonnage(new Personnage(true,C,1,1,1,1));
        try{
            C.explosionHaute();
        }catch (Exception e){
            System.out.println("Can't explode up");
        }
        if (C.getPersonnage().iVivant()){
            System.out.println("Can't explode up");
        }
        C.setPersonnage(null);

        C.setEnnemi(new Ennemi(Bomberball.multiTexture[17],true,C,1));
        try{
            C.explosionHaute();
        }catch (Exception e){
            System.out.println("Can't explode up");
        }
        if (C.getEnnemi().isVivant()){
            System.out.println("Can't explode up");
        }
        C.setEnnemi(null);

        C.setMur(new MurD());
        try{
            C.explosionBasse();
        }catch (Exception e){
            System.out.println("Can't explode up");
        }
        if (C.getMur() is not null && C.getMur() instanceof (MurD)){
            System.out.println("Can't explode down");
        }
        C.setMur(null);

        C.setMur(new MurI());
        try{
            C.explosionBasse();
        }catch (Exception e){
            System.out.println("Can't explode down");
        }
        if (C.getMur() is not null && C.getMur() instanceof (MurD)){
            System.out.println("Can't explode down");
        }
        C.setMur(null);

        C.setPersonnage(new Personnage(true,C,1,1,1,1));
        try{
            C.explosionBasse();
        }catch (Exception e){
            System.out.println("Can't explode down");
        }
        if (C.getPersonnage().isVivant()){
            System.out.println("Can't explode down");
        }
        C.setPersonnage(null);

        C.setEnnemi(new Ennemi(Bomberball.multiTexture[17],true,C,1));
        try{
            C.explosionBasse();
        }catch (Exception e){
            System.out.println("Can't explode down");
        }
        if (C.getEnnemi().isVivant()){
            System.out.println("Can't explode down");
        }
        C.setEnnemi(null);

        C.setMur(new MurD());
        try{
            C.explosionDroite();
        }catch (Exception e){
            System.out.println("Can't explode right");
        }
        if (C.getMur() is not null && C.getMur() instanceof (MurD)){
            System.out.println("Can't explode right");
        }
        C.setMur(null);

        C.setMur(new MurI());
        try{
            C.explosionDroite();
        }catch (Exception e){
            System.out.println("Can't explode right");
        }
        if (C.getMur() is not null && C.getMur() instanceof (MurD)){
            System.out.println("Can't explode right");
        }
        C.setMur(null);

        C.setPersonnage(new Personnage(true,C,1,1,1,1));
        try{
            C.explosionDroite();
        }catch (Exception e){
            System.out.println("Can't explode right");
        }
        if (C.getPersonnage().isVivant()){
            System.out.println("Can't explode right");
        }
        C.setPersonnage(null);

        C.setEnnemi(new Ennemi(Bomberball.multiTexture[17],true,C,1));
        try{
            C.explosionDroite();
        }catch (Exception e){
            System.out.println("Can't explode right");
        }
        if (C.getEnnemi().isVivant()){
            System.out.println("Can't explode right");
        }
        C.setEnnemi(null);

        C.setMur(new MurD());
        try{
            C.explosionGauche();
        }catch (Exception e){
            System.out.println("Can't explode left");
        }
        if (C.getMur() is not null && C.getMur() instanceof (MurD)){
            System.out.println("Can't explode left");
        }
        C.setMur(null);

        C.setMur(new MurI());
        try{
            C.explosionGauche();
        }catch (Exception e){
            System.out.println("Can't explode left");
        }
        if (C.getMur() is not null && C.getMur() instanceof (MurD)){
            System.out.println("Can't explode left");
        }
        C.setMur(null);

        C.setPersonnage(new Personnage(true,C,1,1,1,1));
        try{
            C.explosionGauche();
        }catch (Exception e){
            System.out.println("Can't explode left");
        }
        if (C.getPersonnage().isVivant()){
            System.out.println("Can't explode left");
        }
        C.setPersonnage(null);

        C.setEnnemi(new Ennemi(Bomberball.multiTexture[17],true,C,1));
        try{
            C.explosionGauche();
        }catch (Exception e){
            System.out.println("Can't explode left");
        }
        if (C.getEnnemi().isVivant()){
            System.out.println("Can't explode left");
        }
        C.setEnnemi(null);

        C.setBombe(new Bombe(4,C));
        try {
            C.suppBombe();
        }catch (Exception e){
            System.out.println("Can't remove bomb");
        }
        if (C.getBombe() is not null){
            System.out.println("Can't remove bomb");
        }

        C.setBonus(new Bonus(C));
        try {
            C.suppBonus();
        }catch (Exception e){
            System.out.println("Can't remove bonus");
        }
        if (C.getBombe() is not null){
            System.out.println("Can't remove bonus");
        }

        C.setBonus(new Bonus(C));
        try{
            if (C.estVide()){
                System.out.println("Can't detect empty")
            }
        }catch (Exception e){
            System.out.println("Can't detect empty");
        }
        C.setBonus(null);

        C.setBombe(new Bombe(4,C));
        try{
            if (C.estVide()){
                System.out.println("Can't detect empty")
            }
        }catch (Exception e){
            System.out.println("Can't detect empty");
        }
        C.setBombe(null);

        C.setPersonnage(new Personnage(true,C,1,1,1,1));
        try{
            if (C.estVide()){
                System.out.println("Can't detect empty")
            }
        }catch (Exception e){
            System.out.println("Can't detect empty");
        }
        C.setPersonnage(null);

        C.setEnnemi(new Ennemi(Bomberball.multiTexture[17],true,C,1));
        try{
            if (C.estVide()){
                System.out.println("Can't detect empty")
            }
        }catch (Exception e){
            System.out.println("Can't detect empty");
        }
        C.setPersonnage(null);

        C.setMur(new MurD());
        try{
            if (C.estVide()){
                System.out.println("Can't detect empty")
            }
        }catch (Exception e){
            System.out.println("Can't detect empty");
        }
        C.setMur(null);

        C.setMur(new MurI());
        try{
            if (C.estVide()){
                System.out.println("Can't detect empty")
            }
        }catch (Exception e){
            System.out.println("Can't detect empty");
        }
        C.setMur(null);

}