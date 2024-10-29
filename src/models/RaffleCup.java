package models;

public class RaffleCup {
    private Die[] dice = new Die[5];


    public RaffleCup() {
        //TODO: Create an instance of RaffleCup.
        for (int i = 0; i < dice.length; i++) {
            dice[i] = new Die(); //Laver en ny instance i hver poisition i arrayet.
        }
    }

    public void throwDice() {
        //TODO: implement throwDice method.
        for (Die die : dice) {
            die.roll();
        }
    }
    public Die[] getDice() {
        return dice;
    }

}

