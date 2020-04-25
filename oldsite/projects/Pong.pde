//Created by Raymond Ho

int paddleY;
int ballX, ballY;
boolean Start = false; //Game doesn't start until boolean is changed to true
int xVel = 3; //Change value to adjust speed of ball in X direction
int yVel = 2; //Change value to adjust speed of ball in Y direction
int timerStart;
int elapsedTime;

void setup() {
  size(300, 300); //sets canvas size
  noStroke();
}

void startScreen() {
  fill(255, 0, 0);
  //Only displays what is below if the boolean Start is false
  if (Start == false) { //if boolean Start is false.. then
    textAlign(CENTER);// Alligns text in Center
    textSize(12);//Changes text Size to 12
    text("Move mouse up or down to control paddle.", 150, 100);
    text("Click to start.", 150, 150);
    fill(0, 255, 0);
    text("Final Score: " + elapsedTime, 150, 200);
  }
  else {
    //Otherwise startScreen is GONE, and game shows up.
  }
}

void draw() {
  background(0); //Black background
  startScreen(); //Calls startScreen

    if (Start == false) {
    ballY = mouseY+35;
    ballX = 20;
  }
  else if (Start = true) { //When Start is true, the game pops up!
  
    fill(0, 100, 255);//blue paddle
    rect(0, paddleY, 10, 70); // draws a paddle
    
    fill(255, 0, 0);//red ball
    ellipse(ballX, ballY, 20, 20); // draws a ball
    paddleY = mouseY;
    
    startMove(); //calls the move function
    startTimer(); //calls the timer to begin
  }
}

void mouseClicked() {
  //If the mouse is clicked, the timer starts and boolean Start becoems true
  Start = true; //Start becomes true, duh.
  timerStart = millis()/1000; //Converts millis to seconds.
}

void startTimer() {
  elapsedTime = millis()/1000 - timerStart; //time since program opened MINUS time since clicked
  text("Score: " + elapsedTime, 250, 20); //shows the Score in top right corner
}

void startMove() {
  // Tells the ball where to move if it hits top, bottom, left, and right wall
  if (ballX-10 >= 300-20) { //Hits Right wall
    xVel = -xVel;
  }
  else if (ballY >= 300-10) { //Hits Bottom Wall
    yVel = -yVel;
  }
  else if (ballX-10 <=0) { //Hits Left wall
    Start = false; //game lose, back to startScreen.
  }
  else if (ballY <=0+10) { //Hits Top wall
    yVel = -yVel;
  }
  else if (ballY > paddleY && ballY <paddleY+70 && ballX < 20/2+10) { //Hits Paddle
    xVel = -xVel;
  }
  //Gives movement to the ball
    ballX = ballX + xVel; 
    ballY = ballY + yVel;
}


