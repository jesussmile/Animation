package com.example.pannam.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    private View welcome;
    private View container;
    private View profilePic;
    private boolean playAnimation = true;
    private View signIn;
    private EditText username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find linear layout
        container = findViewById(R.id.container);
        welcome = findViewById(R.id.welcome);
        profilePic = findViewById(R.id.profilePic);
        signIn = findViewById(R.id.signIn);
        username = (EditText) findViewById(R.id.userName);

        //when focus changes on text box (username) after typing ana
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && username.getText().toString().equals("anna")) {
                    changeProfilePic();
                }
            }
        });
    }

    private void changeProfilePic() {
       // rotate y axis 90 deg
        profilePic.animate().rotationY(90).setDuration(750).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((ImageView)profilePic).setImageResource(R.drawable.photo1);
                profilePic.animate().rotationY(0).setDuration(750).setInterpolator(new OvershootInterpolator());
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    @Override
    //we could use oncreate but this makes sure animation takes place
    //much like buffer

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && playAnimation) {
            showContainer();
            //set animation to false so it doesn't always start after the app has launched

            playAnimation = false;
        }
    }

    private void showContainer() {
        //animate alpha start and end
        container.animate().alpha(1f).setDuration(1000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                showOtherItems();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void showOtherItems() {
        //check from where it should start can't start from left because the view has some width
        float startXwelcome = 0 - welcome.getWidth();
        //view is already in final position
        float endXwelcome = welcome.getX();
        //object, change coordinates, start , end
        //single plane animation along x axis
        ObjectAnimator animWelcome = ObjectAnimator.ofFloat(welcome, View.X, startXwelcome, endXwelcome);
        animWelcome.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                welcome.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        animWelcome.setDuration(1500);
       // animWelcome.start();

        // 2 plane animation accross x & y axis
        PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f);
        PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f);

        ObjectAnimator animProfilePic = ObjectAnimator.ofPropertyValuesHolder(profilePic, scaleXholder, scaleYholder);
        animProfilePic.setDuration(1500);
      //  animProfilePic.start();

        //animation using the xml code there is some code in xml file aswell
        ObjectAnimator animSignin = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.sign_in_animator);
        //associate
        animSignin.setTarget(signIn);
       // animSignin.start();


        AnimatorSet set = new AnimatorSet();
        set.play(animWelcome).after(animProfilePic);
        set.play(animWelcome).before(animSignin);
        set.start();
    }

    public void showSecondActivity(View view) {
        Intent i = new Intent(this, secondActivity.class);
        //activity is fast so use this
        ActivityOptions opts = ActivityOptions.makeSceneTransitionAnimation(this,
                new android.util.Pair<View, String>(welcome,"welcome"),
                new android.util.Pair<View, String>(profilePic,"profilePicture"));

       //CONVERT TO BUNDLE AND SEND
        startActivity(i,opts.toBundle());

    }
}

