package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControlsClass;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class View implements FXComponent {
  private final ControlsClass controller;

  public View(ControlsClass controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();

    TitleView titleView = new TitleView(controller);
    ControlView controlsView = new ControlView(controller);
    SolvedView solvedView = new SolvedView(controller);
    GridLinesLayout gridLinesLayout = new GridLinesLayout(controller);
    layout.getChildren().add(titleView.render());
    layout.getChildren().add(controlsView.render());
    layout.getChildren().add(gridLinesLayout.render());
    layout.getChildren().add(solvedView.render());
    return layout;
  }
}
