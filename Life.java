import processing.core.*;
import java.util.HashSet;

public class Life {
  
  final int DEAD_COLOR = 0xFF00FF00;
  final int LIVE_COLOR = 0xFFFF0000;
  final int CELL_FLIP_TIMEOUT = 300; // in milliseconds
  final double CELL_LIFE_PROB = 0.25; // probability of cell starting out alive

  private PApplet parent;
  private Cell[][][] cells;
  private int w, h, d;
  
  public Life(PApplet parent, int w, int h, int d) {
    this.parent = parent;
    this.w = w;
    this.h = h;
    this.d = d;
    cells = new Cell[w][h][d];
    
    // Create 3d array of cells
    for(int i = 0; i < w; i++) {
      for(int j = 0; j < h; j++) {
        for(int k = 0; k < d; k++) {
          if(i == 0 || j == 0 || k == 0 || i == w-1 || j == d-1 || k == d-1) {
            cells[i][j][k] = new Cell(this.parent, (float)(i - w/2.0), (float)(j - h/2.0), (float)(k - d/2.0), parent.random(1) < CELL_LIFE_PROB); 
          }
        }
      } 
    }
    
    // Connect the graph
    for(int i = 0; i < w; i++) {
    for(int j = 0; j < h; j++) {
    for(int k = 0; k < d; k++) {
      if(cells[i][j][k] == null) continue;
      
      for(int di = -1; di <= 1; di++) {
      for(int dj = -1; dj <= 1; dj++) {
      for(int dk = -1; dk <= 1; dk++) {
        if(di == 0 && dj == 0 && dk == 0) continue;
        
        if(i + di < w && i + di >= 0
            && j + dj < h && j + dj >= 0
            && k + dk < d && k + dk >= 0) {
          if(cells[i + di][j + dj][k + dk] != null) {
            cells[i][j][k].connect(cells[i + di][j + dj][k + dk]);
          }     
        }
      }
      } 
      }
    }
    } 
    }
  } 
  
  public void update() {
    for(int i = 0; i < cells.length; i++) {
      for(int j = 0; j < cells[0].length; j++) {
        for(int k = 0; k < cells[0][0].length; k++) {
          if(cells[i][j][k] != null) cells[i][j][k].computeNextState();
        }
      } 
    }
    
    for(int i = 0; i < cells.length; i++) {
      for(int j = 0; j < cells[0].length; j++) {
        for(int k = 0; k < cells[0][0].length; k++) {
          if(cells[i][j][k] != null) cells[i][j][k].gotoNextState();
        }
      } 
    }
  }
  
  public void draw() {
    for(int i = 0; i < cells.length; i++) {
      for(int j = 0; j < cells[0].length; j++) {
        for(int k = 0; k < cells[0][0].length; k++) {
          if(cells[i][j][k] != null) cells[i][j][k].draw(LIVE_COLOR, DEAD_COLOR);
        }
      } 
    }
  }
  
  public void colorTest() {
    int x = parent.mouseX;
    int y = parent.mouseY;

    int c = 0xFF000050;
    for(int i = 0; i < cells.length; i++) {
      for(int j = 0; j < cells[0].length; j++) {
        for(int k = 0; k < cells[0][0].length; k++) {
          if(cells[i][j][k] != null) {
            cells[i][j][k].draw(c);
            c += 100;
          }
        }
      } 
    }
    
    c = parent.get(x, y);
    
    for(int i = 0; i < cells.length; i++) {
      for(int j = 0; j < cells[0].length; j++) {
        for(int k = 0; k < cells[0][0].length; k++) {
          if(cells[i][j][k] != null
            && cells[i][j][k].colorTestColor == c
            && parent.millis() - cells[i][j][k].lastFlipped > CELL_FLIP_TIMEOUT) {
              cells[i][j][k].state = !cells[i][j][k].state;
              cells[i][j][k].lastFlipped = parent.millis();
          }
        }
      } 
    }
  }
}
