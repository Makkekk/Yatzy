package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Die;
import models.RaffleCup;
import models.YatzyResultCalculator;


public class YatzyGui extends Application {
    private final CheckBox[] holdCheckBoxes = new CheckBox[5];
    private final Label[] dieLabels = new Label[5];
    private RaffleCup raffleCup = new RaffleCup();
    private int remainingThrows = 3;
    private int totalUpperScore = 0;
    private int roundScore = 0;
    private final Label labelRoundScore = new Label("Runde Score");
    Label labelThrowsLeft = new Label("Antal kast tilbage: 3");
    private final String[] labelTexts = {"1'ere", "2'ere", "3'ere", "4'ere", "5'ere", "6'ere", "Et par", "To par", "3 ens", "4 ens", "Lille straight", "Stor straight", "Fuldt hus", "Chance", "Yatcy"};
    private final Label[] scoreLabel = new Label[labelTexts.length];
    private final TextField[] scoreValueField = new TextField[labelTexts.length];
    private int currentPlayerIndex;
    private boolean[] scoreUsed;
    private final int totalPlayers = 2;


    @Override

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Yatzy");
        GridPane pane = new GridPane();
        this.initContent(pane);

        currentPlayerIndex = 0; // Start with player 1 (index 0)
        scoreUsed = new boolean[labelTexts.length]; // 15 scoring categories

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void initContent(GridPane pane) {
        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.CENTER);

        pane.add(labelThrowsLeft, 0, 3, 5, 1);

        TextField textFieldRoundScore = new TextField();
        pane.add(textFieldRoundScore, 3, 10);
        textFieldRoundScore.setPrefWidth(50);
        pane.add(labelRoundScore, 2, 10, 2, 1);

        Label labelSum = new Label("Bonus");
        TextField sumField = new TextField();
        pane.add(sumField, 3, 9);
        pane.add(labelSum, 2, 9);
        labelSum.setPrefWidth(100);
        sumField.setPrefWidth(50);


        //Checkbox
        for (int i = 0; i < holdCheckBoxes.length; i++) {
            dieLabels[i] = new Label("0");
            dieLabels[i].setAlignment(Pos.CENTER);
            dieLabels[i].setPrefSize(50, 50);
            dieLabels[i].setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
            pane.add(dieLabels[i], i, 1);

            holdCheckBoxes[i] = createHoldCheckBox();
            pane.add(holdCheckBoxes[i], i, 2);
        }


        //Button to throw
        Button kastTerning = new Button("Kast terning");
        pane.add(kastTerning, 3, 3, 5, 1);
        kastTerning.setOnAction(event -> throwDice());

        for (int i = 0; i < labelTexts.length; i++) {
            scoreLabel[i] = new Label(labelTexts[i]);
            int index = i;
            scoreLabel[i].setOnMouseClicked(event -> scoreCategorySelected(index));
            pane.add(scoreLabel[i], 0, i + 5);
            scoreValueField[i] = new TextField();
            scoreValueField[i].setEditable(false);
            pane.add(scoreValueField[i], 1, i + 5);
        }


    }

    private CheckBox createHoldCheckBox() {
        CheckBox holdCheckBox = new CheckBox("Hold");
        return holdCheckBox;
    }

    private void throwDice() {
        if (remainingThrows > 0) {
            for (int i = 0; i < dieLabels.length; i++) {
                if (!holdCheckBoxes[i].isSelected()) {
                    raffleCup.getDice()[i].roll();
                }
            }
            updateDiceLabels();
            remainingThrows--;
            updateThrowsLabel();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ikke flere slag");
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }


    private int calculateScore(int index, YatzyResultCalculator calculator) {
        int score = 0;

        if (index < 6) {
            score = calculator.upperSectionScore(index + 1);
        } else if (index == 6) {
            score = calculator.onePairScore();
        } else if (index == 7) {
            score = calculator.twoPairScore();
        } else if (index == 8) {
            score = calculator.threeOfAKindScore();
        } else if (index == 9) {
            score = calculator.fourOfAKindScore();
        } else if (index == 10) {
            score = calculator.smallStraightScore();
        } else if (index == 11) {
            score = calculator.largeStraightScore();
        } else if (index == 12) {
            score = calculator.fullHouseScore();
        } else if (index == 13) {
            score = calculator.chanceScore();
        } else if (index == 14) {
            score = calculator.yatzyScore();
        }

        return score;

    }

    private void resetDice() {
        remainingThrows = 3;
        updateThrowsLabel();
        for (int i = 0; i < dieLabels.length; i++) {
            dieLabels[i].setText("0");
            holdCheckBoxes[i].setSelected(false);
        }
        raffleCup = new RaffleCup();
    }

    private void updateThrowsLabel() {
        labelThrowsLeft.setText("Antal kast tilbage: " + remainingThrows);
    }

    private void scoreCategorySelected(int index) {
        if (!scoreUsed[index]) {
            YatzyResultCalculator calculator = new YatzyResultCalculator(raffleCup.getDice());
            int score = calculateScore(index, calculator);

            scoreUsed[index] = true;
            scoreValueField[index].setText(String.valueOf(score));

            remainingThrows = 3;
            labelThrowsLeft.setText("Antal kast tilbage: " + remainingThrows);
            currentPlayerIndex = (currentPlayerIndex + 1) % totalPlayers;
            resetDice();
        }
    }

    private void updateDiceLabels() {
        Die[] dice = raffleCup.getDice();
        for (int i = 0; i < dice.length; i++) {
            dieLabels[i].setText("" + dice[i].getEyes());
        }
    }

}



