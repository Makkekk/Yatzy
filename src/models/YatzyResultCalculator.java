package models;

/**
 * Used to calculate the score of throws with 5 dice
 */
public class YatzyResultCalculator {
    private static int[] eyesCount = new int[6];
    private static Die[] diceResult;

    /**
     * @param dice
     */
    public YatzyResultCalculator(Die[] dice) {
        //TODO: implement YatzyResultCalculator constructor.
        this.diceResult = dice;


        //Reset eyescount to zero
        for (int i = 0; i < eyesCount.length; i++) {
            eyesCount[i] = 0;
        }

        for (Die die : diceResult) {
            int eyesValue = die.getEyes();
            if (eyesValue >= 1 && eyesValue <= 6) ;
            {
                eyesCount[eyesValue - 1]++;
            }
        }

    }

    /**
     * Calculates the score for Yatzy uppersection
     *
     * @param eyes eye value to calculate score for. eyes should be between 1 and 6
     * @return the score for specified eye value
     */
    public int upperSectionScore(int eyes) {
        //TODO: Implement upperSectionScore method.
        return eyesCount[eyes - 1] * eyes;
    }

    public int onePairScore() {
        //TODO: implement onePairScore method.
        //Iterer fra højeste til laveste; 6 til 1
        for (int i = 5; i >= 0; i--) {
            //Test for to ens
            if (eyesCount[i] >= 2) {
                return (i + 1) * 2;
            }

        }
        return 0; //Returner 0 hvis der ikke er et par.
    }

    public int twoPairScore() {
        //TODO: implement twoPairScore method.
        int firstPairValue = 0;
        int secondPairValue = 0;

        for (int i = 5; i >= 0; i--) {
            //Test for om der er mindst to med samme øjne.
            if (eyesCount[i] >= 2) {
                if (firstPairValue == 0) {
                    //find højest par
                    firstPairValue = i + 1;
                } else if (secondPairValue == 0) {
                    //Find det andet højeste
                    secondPairValue = i + 1;

                }
            }
        }
        //Ved to par, send den kombinerede score tilbage
        if (firstPairValue != 0 && secondPairValue != 0) {
            return (firstPairValue * 2) + (secondPairValue * 2);
        }
        return 0;
    }

    public int threeOfAKindScore() {
        //TODO: implement threeOfAKindScore method.
        for (int i = 5; i >= 0; i--) {
            //Find tre af en slags
            if (eyesCount[i] >= 3) {
                return (i + 1) * 3;
            }
        }
        return 0;
    }

    public int fourOfAKindScore() {
        //TODO: implement fourOfAKindScore method.
        for (int i = 5; i >= 0; i--) {
            if (eyesCount[i] >= 4) {
                return (i + 1) * 4;
            }
        }
        return 0;
    }

    public int smallStraightScore() {
        //TODO: implement smallStraightScore method.
        boolean isSmallStraight = eyesCount[0] > 0 && eyesCount[1] > 0 && eyesCount[2] > 0 && eyesCount[3] > 0 && eyesCount[4] > 0; // 1, 2, 3, 4, 5

        if (isSmallStraight) {
            return 15;
        }
        return 0;
    }


    public int largeStraightScore() {
        //TODO: implement largeStraightScore method.
        boolean isLargeStraight = eyesCount[1] > 0 && eyesCount[2] > 0 && eyesCount[3] > 0 && eyesCount[4] > 0 && eyesCount[5] > 0; //2,3,4,5,6

        if (isLargeStraight) {
            return 20;
        }
        return 0;
    }

    public int fullHouseScore() {
        //TODO: implement fullHouseScore method.
        int threeOfAKindValue = 0;
        int pairValue = 0;

        for (int i = 0; i < eyesCount.length; i++) {
            if (eyesCount[i] >= 3) {
                threeOfAKindValue = i + 1; // Gem tallet af tre ens
            } else if (eyesCount[i] == 2) {
                pairValue = i + 1; // Gem tallet af par.
            }
        }
        //Tjek for om der er både tre af en slags og et par.
        if (threeOfAKindValue > 0 && pairValue > 0) {
            return (threeOfAKindValue * 3) + (pairValue * 2);
        }

        return 0;
    }


    public int chanceScore() {
        //TODO: implement chanceScore method.
        int totalScore = 0;
        for (Die die : diceResult) {
            totalScore += die.getEyes();
        }
        return totalScore;
    }

    public int yatzyScore() {
        //TODO: implement yatzyScore method.
        if (diceResult.length == 5) {
            int firstDieValue = diceResult[0].getEyes(); //Find den første terning værdi

            for (Die die : diceResult) {
                if (die.getEyes() != firstDieValue) {
                    return 0;
                }
            }
            return 50;

        }
        return 0; //Hvis der er mindre end 5 med samme værdi.
    }
}

