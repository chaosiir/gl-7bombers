package com.bomber.game.MapetObjet;

import com.bomber.game.Bomberball;

public class MurD extends Mur {

    public MurD(){
       super(Bomberball.multiTexture[1]);
       setName("MurD");
    }

    @Override
    public boolean destructible() {
        return true;
    }

}
