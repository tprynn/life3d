import processing.core.*;
import java.util.HashSet;

public class Cell {
  
  final int CELL_SIZE = 30;
  
  PApplet parent;
  
  int colorTestColor;
  int lastFlipped = 0;
  boolean state = false;
  boolean nextState;
  float x, y, z;
  HashSet<Cell> neighbors = new HashSet<Cell>();
  
  public Cell(PApplet parent, float x, float y, float z, boolean state) {
    this.parent = parent;
    this.x = x;
    this.y = y;
    this.z = z;
    this.state = state;
  } 
  
  public String toString() {
    return "Cell: " + x + "," + y + "," + z;  
  }
  
  public void connect(Cell other) {
    neighbors.add(other); 
  }
  
  public void computeNextState() {
    int count = 0;
    for(Cell c : neighbors) {
      count += c.state ? 1 : 0; 
    }
    
    if(count == 3) nextState = true;
    else if(count == 2 && state) nextState = true;
    else nextState = false;
  }
  
  public void gotoNextState() {
    state = nextState;  
  }
  
  public void draw(int liveColor, int deadColor) {
    parent.stroke(0xFFFFFFFF);
    parent.fill(state ? liveColor : deadColor);
    parent.pushMatrix();
      parent.translate(x*CELL_SIZE, y*CELL_SIZE, z*CELL_SIZE);
      parent.box(CELL_SIZE);
    parent.popMatrix();
  }
  
  public void draw(int colorTestColor) {
    this.colorTestColor = colorTestColor;
    
    parent.noStroke();
    parent.fill(colorTestColor);
    parent.pushMatrix();
      parent.translate(x*CELL_SIZE, y*CELL_SIZE, z*CELL_SIZE);
      parent.box(CELL_SIZE);
    parent.popMatrix();
  }
}
