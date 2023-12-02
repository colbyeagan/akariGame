package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControlsClass;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Puzzle;
import java.sql.SQLData;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GridLinesLayout implements FXComponent {
  private final ControlsClass controller;

  public GridLinesLayout(ControlsClass controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    Puzzle puzzle = controller.getActivePuzzle();
    GridPane layout = new GridPane();
    layout.setGridLinesVisible(true);

    for (int i = 0; i < puzzle.getWidth(); i++) {
      layout.getColumnConstraints().add(new ColumnConstraints(60));
      layout.getRowConstraints().add(new RowConstraints(60));
    }

    for (int r = 0; r < puzzle.getHeight(); r++) {
      for (int c = 0; c < puzzle.getWidth(); c++) {
        if (puzzle.getCellType(r, c) == CellType.CLUE) {
          StackPane square = new StackPane();
          if (controller.isClueSatisfied(r, c)) {
            square.setStyle("-fx-background-color: green;");
          }
          else{
            square.setStyle("-fx-background-color: black;");
          }

          square = this.setSquare(puzzle, r, c, square, layout);
          layout.add(square, c, r);
          layout.setAlignment(Pos.CENTER);

        }
        if (puzzle.getCellType(r, c) == CellType.WALL) {
          StackPane square = new StackPane();
          square.setStyle("-fx-background-color: black;");
          layout.add(square, c, r);
        }
        if (puzzle.getCellType(r, c) == CellType.CORRIDOR) {
          StackPane stack = new StackPane();
          stack.setMaxHeight(60);
          stack.setMaxWidth(60);
          stack.setMinHeight(60);
          stack.setMinWidth(60);
          Image img = new Image("light-bulb.png");
          ImageView imgView = new ImageView(img);
          imgView.setFitWidth(30);
          imgView.setFitHeight(30);

          int r1 = r;
          int c1 = c;
          stack.setOnMouseClicked(
              (event) -> {
                controller.clickCell(r1, c1);
              });

          if (controller.isLit(r, c)) {
            stack.setStyle("-fx-background-color: yellow;");
          }
          if (controller.isLamp(r, c)) {
            if (controller.getIsIllegal(r, c)) {
              stack.getChildren().add(new Label("X"));
            } else {
              stack.getChildren().add(imgView);
            }
          }

          layout.add(stack, c, r);
        }
      }
    }
    return layout;
  }

  private StackPane setSquare(Puzzle puzzle, int r, int c, StackPane square, GridPane layout){
    Text title = new Text(Integer.toString(puzzle.getClue(r, c)));
    title.setFill(Color.WHITE);
    square.getChildren().add(title);
    return square;
  }

  private StackPane setSquareYellow(Puzzle puzzle, int r, int c, StackPane square, GridPane layout){
    Text title = new Text(Integer.toString(puzzle.getClue(r, c)));
    title.setFill(Color.WHITE);
    square.getChildren().add(title);
    return square;
  }


  // Method to create a StackPane for WALL cells
  private StackPane createWallPane() {
    StackPane square = new StackPane();
    square.setStyle("-fx-background-color: black;");
    return square;
  }

  // Method to create a StackPane for CORRIDOR cells
  private StackPane createCorridorPane() {
    StackPane stack = new StackPane();
    stack.setMaxHeight(60);
    stack.setMaxWidth(60);
    stack.setMinHeight(60);
    stack.setMinWidth(60);
    Image img = new Image("light-bulb.png");
    ImageView imgView = new ImageView(img);
    imgView.setFitWidth(30);
    imgView.setFitHeight(30);
    stack.getChildren().add(imgView);
    return stack;
  }


}
