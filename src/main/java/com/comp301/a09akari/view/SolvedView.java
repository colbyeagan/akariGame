package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControlsClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class SolvedView implements FXComponent {
  private final ControlsClass controller;

  public SolvedView(ControlsClass controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();
    if (controller.isSolved()) {
      layout.getChildren().add(new Text("SOLVED"));
    }
    else {
      layout.getChildren().add(new Text("NOT SOLVED"));
    }
    layout.setPadding(new Insets(10, 10, 10, 10));
    layout.setAlignment(Pos.CENTER);
    return layout;
  }
}
