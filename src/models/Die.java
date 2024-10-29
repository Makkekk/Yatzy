package models;

import java.util.Random;

public class Die {
    private int eyes = 0;
    private final Random random = new Random();

    /**
     * Creates a new Die object, with face set to eyes. Used for test purpose
     *
     * @param eyes value should be between 1 and 6
     */
    public Die(int eyes) {
        if (eyes < 1 || eyes > 6) {
            System.out.println("Terningen kastet skal være mellem 1 og 6");
        }
        this.eyes = eyes;
    }

    public Die() {
    }

    public void roll() {
        //TODO: implement roll method.
        this.eyes = random.nextInt(6) + 1;
    }

    public int getEyes() {
        return eyes;
    }
}
