
final int WIDTH = 800;
final int HEIGHT = 600;
final int DEPTH = 600;

final int LIFE_W = 10;
final int LIFE_H = 10;
final int LIFE_D = 10;

final int LIFE_RATE = 30; // update every LIFE_RATE frames

float rotx = -30;
float roty = 30;
float rotz = 0;

Life life;

void setup() {
  size(WIDTH, HEIGHT, P3D);
  frameRate(30);
  
  life = new Life(this, LIFE_W, LIFE_H, LIFE_D);
}

void keyPressed() {
  if(key == 'r') life = new Life(this, LIFE_W, LIFE_H, LIFE_D);
  else if(keyCode == UP) rotz += 10;
  else if(keyCode == DOWN) rotz -= 10;
  else if(keyCode == RIGHT) rotx += 10;
  else if(keyCode == LEFT) rotx -= 10;
}

void mousePressed() {
  //timePressed = millis();
}

void mouseReleased() {
  //life.colorTest();
}

void draw() {
  if(mousePressed) {
    colorTest();
  }
  
  if(keyPressed && key == 'c') {
    colorTest();
    roty += 0.5;
    return;
  }
  
  drawLife();
  //text("" + frameRate, 5, 10);
  
  if(frameCount % LIFE_RATE == 0) life.update();
  roty += 0.5;
}

void drawLife() {
  background(0);
  
  pushMatrix();
    translate(WIDTH/2, HEIGHT/2);
    rotateX(radians(rotx));
    rotateY(radians(roty));
    rotateZ(radians(rotz));
    
    life.draw();
  popMatrix();
}

void colorTest() {
  background(0);
  
  pushMatrix();
    translate(WIDTH/2, HEIGHT/2);
    rotateX(radians(rotx));
    rotateY(radians(roty));
    rotateZ(radians(rotz));
    
    life.colorTest();
  popMatrix();
}

