package com.example.android.baking.base;

import com.google.android.exoplayer2.ExoPlayer;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by milkdz on 2018/3/10.
 */

public class TakeValues {

    public static boolean ifUseFragment = false;

    public static ArrayList<ArrayList<String>> widgetArr = new ArrayList<>();
    public static ArrayList<RecipeSteps> recipeArr = new ArrayList<>();


    /**  values for step */
    public static String prepareText = "";
    public static String label = "";

    /** values for video */
    public static ExoPlayer.EventListener eventListener;
}
