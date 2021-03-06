package com.example.pannam.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeScroll;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;

public class secondActivity extends AppCompatActivity {

    private LinearLayout scenesHolder;
    private Scene all;
    private Scene office;
    private Scene gym;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        scenesHolder = (LinearLayout)findViewById(R.id.scene_holder);
        all = Scene.getSceneForLayout(scenesHolder,R.layout.all_scene,this);
        office= Scene.getSceneForLayout(scenesHolder,R.layout.office_scene,this);
        gym = Scene.getSceneForLayout(scenesHolder,R.layout.gym_scene,this);


    }

    public void showAllScene(View view) {
        TransitionManager.go(all,new ChangeBounds());
    }

    public void showOfficeScene(View view) {
        TransitionManager.go(office,new ChangeImageTransform());
    }


    public void showGymScene(View view) {
        TransitionManager.go(gym, new ChangeClipBounds());
    }
}
