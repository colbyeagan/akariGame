package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private List<ModelObserver> modelObservers;
  private PuzzleLibrary puzzleLibrary;
  private boolean[][] lampGrid;
  private int currentPuzzle;
  private Puzzle puzzle;

  public ModelImpl(PuzzleLibrary library) {
    // Your constructor code here
    if (library == null) {
      throw new IllegalArgumentException("Cannot accept null library");
    }
    this.puzzleLibrary = library; // puzzles
    this.currentPuzzle = 0;
    this.puzzle = library.getPuzzle(0);
    this.lampGrid = new boolean[puzzle.getHeight()][puzzle.getWidth()];
    this.modelObservers = new ArrayList<>();
    this.resetPuzzle();
  }

  @Override
  public void addLamp(int r, int c) {
    if (this.outOfBTest(r, c)) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Cell is not a corridor");
    }
    lampGrid[r][c] = true;
    notify(this);
  }

  @Override
  public void removeLamp(int r, int c) {
    if (this.outOfBTest(r, c)) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    } else if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Cell isn't a corridor");
    }
    lampGrid[r][c] = false;
    notify(this);
  }

  private boolean outOfBTest(int r, int c) {
    if (r > getActivePuzzle().getHeight() - 1
        || c > getActivePuzzle().getWidth() - 1
        || r < 0
        || c < 0) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isLit(int r, int c) {
    if (this.outOfBTest(r, c)) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Cell isn't corridor");
    }

    for (int j = c; j < puzzle.getWidth(); j++) {
      if (lampGrid[r][j]) {
        return true;
      } else if (puzzle.getCellType(r, j) != CellType.CORRIDOR) {
        break;
      }
    }

    for (int j = c; j >= 0; j--) {
      if (lampGrid[r][j]) {
        return true;
      } else if (puzzle.getCellType(r, j) != CellType.CORRIDOR) {
        break;
      }
    }

    for (int i = r; i < puzzle.getHeight(); i++) {
      System.out.println("Height: " + puzzle.getHeight());
      System.out.println("Width: " + puzzle.getWidth());
      System.out.println("Row: " + i + "Col: " + c);
      if (lampGrid[i][c]) {
        return true;
      } else if (puzzle.getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
    }

    for (int i = r; i >= 0; i--) {
      if (lampGrid[i][c]) {
        return true;
      } else if (puzzle.getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
    }
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (outOfBTest(r, c)) {
      throw new IndexOutOfBoundsException("out of bounds.");
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Cell isn't corridor");
    }
    return lampGrid[r][c];
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    int height = this.getActivePuzzle().getHeight();
    int width = this.getActivePuzzle().getWidth();

    if (outOfBTest(r, c)) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    } else if (!(lampGrid[r][c])) {
      throw new IllegalArgumentException("No light put here");
    }

    for (int i = r - 1; i >= 0; i--) {
      if (lampGrid[i][c]) {
        return true;
      } else if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
    }

    for (int i = r + 1; i < height; i++) {
      if (lampGrid[i][c]) {
        return true;
      } else if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
    }

    for (int i = c + 1; i < width; i++) {
      if (lampGrid[r][i]) {
        return true;
      } else if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
    }

    for (int i = c - 1; i >= 0; i--) {
      if (lampGrid[r][i]) {
        return true;
      } else if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return puzzleLibrary.getPuzzle(currentPuzzle);
  }

  @Override
  public int getActivePuzzleIndex() {
    return this.currentPuzzle;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index >= puzzleLibrary.size() || index < 0) {
      throw new IndexOutOfBoundsException();
    } else {
      this.puzzle = puzzleLibrary.getPuzzle(index);
      this.currentPuzzle = index;
      this.resetPuzzle();
    }
  }

  @Override
  public int getPuzzleLibrarySize() {
    return this.puzzleLibrary.size();
  }

  @Override
  public void resetPuzzle() {
    int rows = this.getActivePuzzle().getHeight();
    int columns = this.getActivePuzzle().getWidth();
    this.lampGrid = new boolean[rows][columns];
    notify(this);
  }

  @Override
  public boolean isSolved() {
    for (int r = 0; r < puzzle.getHeight(); r++) {
      for (int c = 0; c < puzzle.getWidth(); c++) {

        if (puzzle.getCellType(r, c) == CellType.CORRIDOR) {
          if (!isLit(r, c)) {
            return false;
          } else if (isLamp(r, c) && isLampIllegal(r, c)) {
            return false;
          }
        } else if (puzzle.getCellType(r, c) == CellType.CLUE) {
          if (!isClueSatisfied(r, c)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    int height = puzzle.getHeight();
    int width = puzzle.getWidth();
    if (this.outOfBTest(r, c)) {
      throw new IndexOutOfBoundsException();
    } else if (getActivePuzzle().getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("Not a clue cell");
    }
    int touchingLamps = 0;

    if (c > 0 && lampGrid[r][c - 1]) {
      touchingLamps += 1;
    }

    if (r > 0 && lampGrid[r - 1][c]) {
      touchingLamps += 1;
    }
    if (c < width - 1 && lampGrid[r][c + 1]) {
      touchingLamps += 1;
    }
    if (r < height - 1 && lampGrid[r + 1][c]) {
      touchingLamps += 1;
    }
    return (touchingLamps == puzzle.getClue(r, c));
  }

  @Override
  public void addObserver(ModelObserver observer) {
    modelObservers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    modelObservers.remove(observer);
  }

  private void notify(ModelImpl model) {
    for (ModelObserver o : modelObservers) {
      o.update(model);
    }
  }
}
