package com.example.android.baking.base;

import android.net.Uri;

import com.example.android.baking.fragment.StepFragment;
import com.google.android.exoplayer2.ExoPlayer;

import java.util.ArrayList;

/**
 * Created by milkdz on 2018/3/10.
 */

public class TakeValues {

    public static boolean ifUseFragment = false;

    /**  values for step */
    public static String prepareText = "";
    public static String label = "";
    public static ArrayList<RecipeStep> recipeStepsArrayList = new ArrayList<>();

    public static int times = 0;

    /** values for video */
    public static ExoPlayer.EventListener eventListener;
    public static int position = 0;
    public static Uri uri = Uri.parse("");
    public static ArrayList<RecipeStep> recipeVideoArrayList = new ArrayList<>();
}