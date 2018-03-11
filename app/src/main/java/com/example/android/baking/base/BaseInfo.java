package com.example.android.baking.base;

import java.util.ArrayList;

/**
 * Created by milkdz on 2018/2/22.
 */

public interface BaseInfo {
    String REMOTE_HTTP = "https://s3.cn-north-1.amazonaws.com.cn/"
            + "static-documents/nd801/ProjectResources/Baking/baking-cn.json";

    String RECIPE_ID = "id";

    String RECIPE_NAME = "name";

    String RECIPE_INGREDIENTS = "ingredients";
    String RECIPE_INGREDIENTS_QUANTITY = "quantity";
    String RECIPE_INGREDIENTS_MEASURE = "measure";
    String RECIPE_INGREDIENTS_INGREDIENT = "ingredient";

    String RECIPE_STEP = "steps";
    String RECIPE_STEP_ID = "id";
    String RECIPE_STEP_SHORT_DESCRIPTION = "shortDescription";
    String RECIPE_STEP_DESCRIPTION = "description";
    String RECIPE_STEP_VIDEO = "videoURL";
    String RECIPE_STEP_THUMBNAIL = "thumbnailURL";

    String RECIPE_SERVINGS = "servings";

    String INTENT_TITLE = "recipe_title";
    String INTENT_RECIPE = "recipe_video";
    String INTENT_LIST = "recipe_step_list";
    String INTENT_LIST_INDEX = "recipe_step_list_index";
    String INTENT_PREPARE = "recipe_prepare";

    String BUNDLE_VIDEO_POSITION = "position_video";
    String BUNDLE_VIDEO_ARRAYLIST = "array_list_video";

    String BUNDLE_STEP_POSITION = "position_step";
    String BUNDLE_STEP_RECIPES = "recipes_step";

    String ACTIVITY_POSITION = "video_position";



    //chose mode to dis step or ingredient
    int RECIPE_MODE_INGREDIENTS = 1;
    int RECIPE_MODE_STEP = 2;
}
