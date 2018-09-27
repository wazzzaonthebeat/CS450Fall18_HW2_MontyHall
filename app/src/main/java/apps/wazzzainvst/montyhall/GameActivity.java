package apps.wazzzainvst.montyhall;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private Game game;
    private ImageButton door1Button, door2Button, door3Button;
    private Button stickButton, switchButton;
    private TextView prompt, winsTextView, loseTextView;
    private SharedPreferences pref = null;
    private int wins = 0;
    private int loses = 0;
    private AudioAttributes aa = null;
    private SoundPool soundPool;
    private int goatSound = 0;
    private int carSound = 0;
    private int clickSound = 0;
    private int swtichSound = 0;
    private int stickSound = 0;
    CountDownTimer timer;
    private Animator anim;
    private LinearLayout gameWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //shared prefs
         pref = getApplicationContext().getSharedPreferences("Profile", 0); // 0 - for private mode

        //create door objects
        door1Button = findViewById(R.id.door1);
        door2Button = findViewById(R.id.door2);
        door3Button = findViewById(R.id.door3);
        prompt = findViewById(R.id.prompt);
        winsTextView = findViewById(R.id.textViewWins);
        loseTextView = findViewById(R.id.textViewLoses);
        gameWindow = findViewById(R.id.gameWindow);

        //update score
        loses = pref.getInt("loses",0);
        wins = pref.getInt("wins",0);


        loseTextView.setText("Loses: " + String.valueOf(loses));
        winsTextView.setText("Wins: " + String.valueOf(wins));

        //hide stick or switch button
        stickButton = findViewById(R.id.buttonStick);
        switchButton = findViewById(R.id.buttonSwitch);

        stickButton.setVisibility(View.GONE);
        switchButton.setVisibility(View.GONE);
        startGame();

    //set audio attributes
        aa = new AudioAttributes.Builder() //factory class
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        this.soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(aa)
                .build();

        //declare sound effects
        this.goatSound = this.soundPool.load(this, R.raw.goat, 1);
        this.carSound = this.soundPool.load(this, R.raw.car, 1);
        this.clickSound = this.soundPool.load(this, R.raw.click, 1);
        this.swtichSound = this.soundPool.load(this, R.raw.swoosh, 1);
        this.stickSound = this.soundPool.load(this, R.raw.stick, 1);


    }

    public void startGame() {
        //assign the roles of each door
        game = new Game();
        pref.edit().putInt("round", 1).apply();
        anim =  AnimatorInflater.loadAnimator(this,R.animator.door_bounce);
        gameWindow.setBackgroundColor(getResources().getColor(R.color.trans));
    }

    //door 1 click actions
    public void door1(View view) {

        if (pref.getInt("round", 1) == 1) { //round one

            doorSelected(1);
            pref.edit().putInt("selected", 1).apply();
            door1Button.setImageDrawable(getResources().getDrawable(R.drawable.closed_door_chosen));

            anim.setTarget(door1Button);
            anim.start();

        }
    }

    //door 2 click actions
    public void door2(View view) {

        if (pref.getInt("round", 1) == 1) { //round one


            doorSelected(2);
            pref.edit().putInt("selected", 2).apply();
            door2Button.setImageDrawable(getResources().getDrawable(R.drawable.closed_door_chosen));
            anim.setTarget(door2Button);
            anim.start();

        }
    }
    //door 2 click actions
    public void door3(View view) {

        if (pref.getInt("round", 1) == 1) { //round one


            doorSelected(3);
            pref.edit().putInt("selected", 3).apply();
            door3Button.setImageDrawable(getResources().getDrawable(R.drawable.closed_door_chosen));
            anim.setTarget(door3Button);
            anim.start();
        }
    }
    public void doorSelected(int doorNumber) {
        soundPool.play(clickSound, 1f, 1f, 1, 0, 1f);

        prompt.setText(R.string.stickorswitch);

        int goats[] = game.getGoats();
        int carDoor = game.getcarDoor();

        System.out.println("THE CAR IS IN " + carDoor);
        //if user selects correct door the first time you can show any of the other doors
        if (doorNumber == carDoor) {

            Random random = new Random();
            int showDoor = random.nextInt(2);
            System.out.println("Picked correct door We will show door:" + goats[showDoor]);
            showGoat(goats[showDoor]);
            pref.edit().putInt("round", 2).apply(); //update 2 round 2

            //save the closed door number


            //set switch door if you decide to switchh
            if (showDoor == 1) {
                game.setSwitchDoor(goats[0]);
            } else {
                game.setSwitchDoor(goats[1]);
            }

        } else if (doorNumber == goats[0]) {

            //if user picks a goat, show the other goat
            System.out.println("Picked wrong door 1 We will show door:" + goats[1]);

            showGoat(goats[1]);

            pref.edit().putInt("round", 2).apply(); //update 2 round 2

            //set switch door if you decide to switchh
            game.setSwitchDoor(carDoor);
        } else if (doorNumber == goats[1]) {

            //if user picks a goat, show the other goat
            System.out.println("Picked wrong 2 door We will show door:" + goats[0]);
            showGoat(goats[0]);
            pref.edit().putInt("round", 2).apply(); //update 2 round 2
            //set switch door if you decide to switchh
            game.setSwitchDoor(carDoor);
        }

        stickButton.setVisibility(View.VISIBLE);
        switchButton.setVisibility(View.VISIBLE);
    }
    public void stick(View view) {
        //stick or switch round
        soundPool.play(stickSound, 1f, 1f, 1, 0, 1f);
        anim =  AnimatorInflater.loadAnimator(this,R.animator.door_open);
        pref.edit().putInt("round", 1).apply(); //update 2 round 1
        int doorSelected = pref.getInt("selected", 1);
        //stick with selected so open the door selected
        gameWindow.setBackgroundColor(getResources().getColor(R.color.black));
        prompt.setText(R.string.please);
        switch (doorSelected) {

            case 1:
                if (doorSelected == game.carDoor) {
                    countDownTimer(door1Button, true);
                    anim.setTarget(door1Button);
                    anim.start();
                    break;


                    //you win
                } else {
                    countDownTimer(door1Button, false);
                    anim.setTarget(door1Button);
                    anim.start();
                    break;
                    //you lose
                }

            case 2:
                if (doorSelected == game.carDoor) {
                    //     openDoor(1,true);
                    countDownTimer(door2Button, true);
                    anim.setTarget(door2Button);
                    anim.start();
                    break;
                    //you win
                } else {
                    //      openDoor(1,false);
                    countDownTimer(door2Button, false);
                    anim.setTarget(door2Button);
                    anim.start();
                    break;
                    //you lose
                }

            case 3:
                if (doorSelected == game.carDoor) {
                    //      openDoor(1,true);
                    countDownTimer(door3Button, true);
                    anim.setTarget(door3Button);
                    anim.start();
                    break;
                    //you win
                } else {
                    //      openDoor(1,false);
                    countDownTimer(door3Button, true);
                    anim.setTarget(door3Button);
                    anim.start();
                    break;
                    //you lose
                }

        }
        stickButton.setVisibility(View.GONE);
        switchButton.setVisibility(View.GONE);
    }
    public void switchDoor(View view) {
        //stick or switch round
        soundPool.play(swtichSound, 1f, 1f, 1, 0, 1f);
        anim =  AnimatorInflater.loadAnimator(this,R.animator.door_open);
        gameWindow.setBackgroundColor(getResources().getColor(R.color.black));
        pref.edit().putInt("round", 1).apply(); //update 2 round 1

        prompt.setText(R.string.please);
        //switch to other unopened door
        int selected = pref.getInt("selected", 1);
        int correct = game.carDoor;
        final int switchDoor = game.getSwitchDoor(); //door to switch to if we switch

        //if you selected the correct door but decide to switch
        if (selected == correct) {


            timer = new CountDownTimer(3000, 1000) {

                public void onTick(long millisUntilFinished) {
                    if (millisUntilFinished < 1000) {

                        if (switchDoor == 1) {

                            door1Button.setImageDrawable(getResources().getDrawable(R.drawable.one));
                            anim.setTarget(door1Button);
                            anim.start();
                        } else if (switchDoor == 2) {
                            door2Button.setImageDrawable(getResources().getDrawable(R.drawable.one));
                            anim.setTarget(door2Button);
                            anim.start();
                        } else if (switchDoor == 3) {
                            door3Button.setImageDrawable(getResources().getDrawable(R.drawable.one));
                            anim.setTarget(door3Button);
                            anim.start();
                        }

                    } else if (millisUntilFinished < 2000) {
                        if (switchDoor == 1) {
                            door1Button.setImageDrawable(getResources().getDrawable(R.drawable.two));
                            anim.setTarget(door1Button);
                            anim.start();
                        } else if (switchDoor == 2) {
                            door2Button.setImageDrawable(getResources().getDrawable(R.drawable.two));
                            anim.setTarget(door2Button);
                            anim.start();

                        } else if (switchDoor == 3) {
                            door3Button.setImageDrawable(getResources().getDrawable(R.drawable.two));
                            anim.setTarget(door3Button);
                            anim.start();

                        }
                    } else if (millisUntilFinished < 3000) {

                        if (switchDoor == 1) {
                            door1Button.setImageDrawable(getResources().getDrawable(R.drawable.three));
                            anim.setTarget(door1Button);
                            anim.start();
                        } else if (switchDoor == 2) {
                            door2Button.setImageDrawable(getResources().getDrawable(R.drawable.three));
                            anim.setTarget(door2Button);
                            anim.start();

                        } else if (switchDoor == 3) {
                            door3Button.setImageDrawable(getResources().getDrawable(R.drawable.three));
                            anim.setTarget(door3Button);
                            anim.start();

                        }

                    }
                }

                @Override
                public void onFinish() {
                    showGoat(switchDoor);
                    prompt.setText(R.string.lose);
                    loseSound();
                    restart();//start new game

                }
            }.start();


            //you loose
            //show goat on the other door

        } else {
            //you win
            //show car on other door
            switch (switchDoor) {

                case 1:

                    countDownTimer(door1Button, true);
                    anim.setTarget(door1Button);
                    anim.start();
                    break;
                case 2:
                    countDownTimer(door2Button, true);
                    anim.setTarget(door2Button);
                    anim.start();
                    break;
                case 3:
                    countDownTimer(door3Button, true);
                    anim.setTarget(door3Button);
                    anim.start();


                    break;
            }
        }

        stickButton.setVisibility(View.GONE);
        switchButton.setVisibility(View.GONE);
    }
    private void showGoat(int doorNumber) {

        switch (doorNumber) {

            case 1:
                door1Button.setImageDrawable(getResources().getDrawable(R.drawable.goat));
                break;
            case 2:
                door2Button.setImageDrawable(getResources().getDrawable(R.drawable.goat));
                break;
            case 3:
                door3Button.setImageDrawable(getResources().getDrawable(R.drawable.goat));
                break;


        }


    }
    public void countDownTimer(final ImageButton button, final boolean win) {
        anim =  AnimatorInflater.loadAnimator(this,R.animator.door_open);

        timer = new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < 1000) {
                    button.setImageDrawable(getResources().getDrawable(R.drawable.one));
                    anim.setTarget(button);
                    anim.start();

                } else if (millisUntilFinished < 2000) {
                    button.setImageDrawable(getResources().getDrawable(R.drawable.two));
                    anim.setTarget(button);
                    anim.start();

                } else if (millisUntilFinished < 3000) {
                    button.setImageDrawable(getResources().getDrawable(R.drawable.three));
                    anim.setTarget(button);
                    anim.start();
                }
            }

            @Override
            public void onFinish() {
                if (win) {
                    button.setImageDrawable(getResources().getDrawable(R.drawable.car));
                    prompt.setText(R.string.win);
                    winSound();
                    restart();//start new game
                    anim.setTarget(button);
                    anim.start();
                } else {
                    button.setImageDrawable(getResources().getDrawable(R.drawable.goat));
                    prompt.setText(R.string.lose);
                    loseSound();
                    restart();//start new game
                    anim.setTarget(button);
                    anim.start();
                }

            }
        }.start();
    }
    public void loseSound() {
        soundPool.play(goatSound, 1f, 1f, 1, 0, 1f);
        loses++;
        //update score
        loseTextView.setText("Loses: " + String.valueOf(loses));

        pref.edit().putInt("loses", loses).apply();
    }

    public void winSound() {
        soundPool.play(carSound, 1f, 1f, 1, 0, 1f);
        wins++;
        //update score
        winsTextView.setText("Wins: " + String.valueOf(wins));

        pref.edit().putInt("wins", wins).apply();
    }
    public void restart() {
        timer = new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                startGame();
                door1Button.setImageDrawable(getResources().getDrawable(R.drawable.door));
                door2Button.setImageDrawable(getResources().getDrawable(R.drawable.door));
                door3Button.setImageDrawable(getResources().getDrawable(R.drawable.door));
                prompt.setText(R.string.choose_a_door);

            }
        }.start();

    }
}
