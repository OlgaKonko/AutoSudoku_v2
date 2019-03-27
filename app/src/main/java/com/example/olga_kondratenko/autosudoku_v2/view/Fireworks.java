package com.example.olga_kondratenko.autosudoku_v2.view;


import android.app.Activity;
import android.content.Context;

import com.example.olga_kondratenko.autosudoku_v2.R;
import com.example.olga_kondratenko.autosudoku_v2.view.models.Sizes;
import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fireworks implements Runnable{
        List<ParticleSystem> animations;
        Random random;

        private Fireworks (Activity context){
                random = new Random();
                animations = new ArrayList<>(3);
                animations.add(new ParticleSystem(context, 80, R.drawable.animated_confetti1, 10000)
                        .setSpeedModuleAndAngleRange(0f, 0.3f, 0, 360)
                        .setRotationSpeed(144)
                        .setAcceleration(0.00005f, 90));

                animations.add(new ParticleSystem(context, 80, R.drawable.animated_confetti2, 10000)
                        .setSpeedModuleAndAngleRange(0f, 0.3f, 0, 360)
                        .setRotationSpeed(144)
                        .setAcceleration(0.00005f, 90));

                animations.add(new ParticleSystem(context, 80, R.drawable.animated_confetti3, 10000)
                        .setSpeedModuleAndAngleRange(0f, 0.3f, 0, 360)
                        .setRotationSpeed(144)
                        .setAcceleration(0.00005f, 90));
        }

    @Override
public void run() {
                int paddings = Sizes.layoutWidth/4;
            for(int index = 0; index<animations.size(); index++){
                            int x = paddings+random.nextInt(Sizes.layoutWidth - 2*paddings);
                    int y = paddings+random.nextInt(Sizes.layoutWidth - 2*paddings);
                 //   animations.get(index).oneShot(x, y,20, 1000000);
            }
        //MainFieldActivity.instance.faerworksHandler.postDelayed(this, 500);
        }
}