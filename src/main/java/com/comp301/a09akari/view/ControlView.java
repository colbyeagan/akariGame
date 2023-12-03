package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControlsClass;
import com.comp301.a09akari.model.Puzzle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ControlView implements FXComponent {
  private final ControlsClass controller;

  public ControlView(ControlsClass controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();
    layout.setAlignment(Pos.CENTER);

    Button reset = new Button("Reset Puzzle");
    Button next = new Button("->");
    Button prev = new Button("<-");
    Button rand = new Button("Random Puzzle");

    rand.setOnAction((event) -> controller.clickRandPuzzle());
    layout.getChildren().add(rand);

    // reset
    reset.setOnAction((event) -> controller.clickResetPuzzle());
    layout.getChildren().add(reset);

    // prev
    prev.setOnAction((event) -> controller.clickPrevPuzzle());
    layout.getChildren().add(prev);

    // next
    next.setOnAction((event) -> controller.clickNextPuzzle());
    layout.getChildren().add(next);

    // Showing number of puzzle out of total puzzles
    Puzzle puzzle = controller.getActivePuzzle();
    layout.setAlignment(Pos.CENTER);
    String numPuzzles = Integer.toString(controller.getNumberOfPuzzles());
    String indexS = Integer.toString(controller.getIndex() + 1);
    Text puzzleText = new Text("Puzzle: " + indexS + " of " + numPuzzles);
    layout.getChildren().add(puzzleText);
    layout.setPadding(new Insets(10, 10, 20, 10));
    return layout;
  }
}
