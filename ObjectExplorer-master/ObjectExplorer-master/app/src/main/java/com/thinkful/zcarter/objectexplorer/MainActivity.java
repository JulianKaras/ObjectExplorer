package com.thinkful.zcarter.objectexplorer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;

import main.java.com.thinkful.zcarter.objectexplorer.ApplicationSettings;

// This class handles output of log to the emulator screen
class Screen {
    // static fields
    static TextView textView;
    static String text = new String();

    public static void outputToScreen() {
        textView.setText(text);
    }

    public static void log(String textToLog) {
        text += textToLog + "\n";
        textView.setText(text);
    }
}

abstract class Ball extends Observable {
    public abstract void roll();
    public abstract void caught();
}

class crowd implements Observer {
    public void update (Observable observable, Object data){
        if(((Hardball)observable).isHomeRun){
            Screen.log("The crowd cheers!");
        }
    }
}

class Football extends Ball {

    // Note the @Override is a compiler directive.
    // It is not required, but is a best practice to use
    // for your own benefit
    @Override
    public void roll() {
        Screen.log("This football is rolling");
        this.setChanged();
        this.notifyObservers();
    }
}

class Referee implements Observer {

    @Override
    public void update(Observable observable, Object data) {
        String[] parts = observable.getClass().toString().split("\\.");
        String ballClass = parts[parts.length-1];
        Screen.log("The " + ballClass + " has been changed.");

    }
}

class Umpire implements Observer {

    public void update(Observable observable, Object data){
        Screen.log("Umpire observes the ball has been changed.");
        if(((Hardball)(observable)).isCaught){
            Screen.log("Umpire observes the ball was caught.")
        }
    }
}

Umpire firstUmpire = new Umpire();
Umpire secondUmpire = new Umpire();
Umpire thirdUmpire = new Umpire();
Hardball theBall = new Hardball(firstUmpire);
theBall.addObserver(secondUmpire);

theBall.caught();


abstract class Baseball extends Ball {

    public int speed;
    public abstract void pitch();
}

class Softball extends Baseball {
    @Override
    public void pitch(){
        Log.i("Softball", "A soft ball is pitched underhand");
    }

    @Override
    public void roll() {
        Screen.log("This soft ball is rolling");
        this.setChanged();
        this.notifyObservers();
    }
}

class Hardball extends Ball {

    boolean isCaught = false;
    boolean isHomeRun = false;

    public Hardball (Observer observer){
        this.addObserver(observer);
    }

    public Hardball(int speed) {
       this.speed = speed;
    }

    @Override
    public void pitch() {
        Screen.log("A hard ball is pitched overhand with speed " + this.speed);
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void roll() {
        Screen.log("This hard ball is rolling");
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void homeRunHit(){
        this.isHomeRun = true;
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void caught(){
        this.isCaught = true;
        this.setChanged();
        this.notifyObservers();
    }
}
class BouncyBall{
    public void bounce(){
        Screen.log("The BouncyBall object bounces");
    }
}
class SuperBall extends BouncyBall{
    @Override
    public void bounce(){
        Screen.log("The SuperBall object bounces super high.");
    }
}
class KnuckleBall extends Hardball{
    super();
    Screen.log("...and floats slowly toward the plate.");
}

abstract class Vehicle {
    public void Speed(int speed);
    public void FuelCapacity(int fuelCapacity);
}

interface TrailerTower {
    public void TowingCapacity(int speed);
}

class sportsCar extends Vehicle {

}

class BigRig extends Vehicle implements TrailerTower {
    public void TowingCapacity(int speed) {
        Log.d("The rig pulls the trailer forward at a speed of " + speed + " miles per hour.");
    }


}



//-------------------------------------------------------------------
// This space is for students to define classes in Thinkful Unit 2
// Alternatively, create classes in a new file in the same package,
// and they will be available here

//-------------------------------------------------------------------


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Screen.textView = (TextView) this.findViewById(R.id.textView);
        Button startButton = (Button)this.findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Screen.outputToScreen();
            }
        });
        this.playBall();

        Button myButton = (Button)this.findViewById(R.id.myButton);//Use your unique ID here
        myButton.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick (View v){
                Screen.log("My Button was clicked");
            }
        });

    }

    public void playBall() {

        ApplicationSettings appSettings = ApplicationSettings.getInstance();
        int numberOfBallsNeeded = appSettings.numberOfBallsInGame();

        Hardball myHardball = new Hardball (90);
        myHardball.pitch() {

        }
        SuperBall superBall = new SuperBall();
        superBall.bounce(){

        }

        KnuckleBall myKnuckleBall = new KnuckleBall ();
        myKnuckleBall.pitch(){

        }
        crowd theCrowd = new crowd();
        Hardball theBall = new Hardball(firstUmpire);
        theBall.addObserver(theCrowd);
        theBall.homeRunHit();




        // Students experiment with their classes here by instantiating their objects
        // and calling methods on those objects
        // example using the Football class:
        //Football football = new Football();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
