package com.example.android.baking.base;

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
    public static ArrayList<RecipeStep> recipeStepArrayList = new ArrayList<>();

    /** values for video */
    public static ExoPlayer.EventListener eventListener;
    public static long videoPosition = 0;
    public static boolean ifChange = false;
}
