package com.example.android.baking.db;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.android.baking.base.BaseInfo;

/**
 * Created by milkdz on 2018/2/23.
 */

public class RecipeContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.baking";
    public static final Uri CONTENT_BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final class RecipeMaterial implements BaseColumns{

        public static final String RECIPE_MATERIAL = "material";
        public static final Uri CONTENT_BASE =
                CONTENT_BASE_URI.buildUpon().appendPath(RECIPE_MATERIAL).build();

        public static String TABLE_NAME = "recipe_material_table";

        public static String COLUMN_ID = "recipe_id";
        public static String COLUMN_NAME = "recipe_name_material";
        public static String COLUMN_MATERIAL = "recipe_material";

        public static String[] QUERY_MATERIAL = {
                _ID,
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_MATERIAL
        };
    }


    public static final class RecipeInfo implements BaseColumns {
        public static final String RECIPE_INFO = "recipe";

        /** content://com.example.android.baking/recipe */
        public static final Uri CONTENT_BASE = CONTENT_BASE_URI.buildUpon().appendPath(RECIPE_INFO).build();

        public static String TABLE_NAME = "recipe_table";

        public static String COLUMN_ID = "recipe_id";
        public static String COLUMN_NAME = "recipe_name";
        public static String COLUMN_INGREDIENTS = "recipe_material";
        public static String COLUMN_STEP = "recipe_step_word";
        public static String COLUMN_SERVINGS = "recipe_servings";
        public static String COLUMN_JUDGE = "recipe_judge";
    }

    public static final String[] RECIPE_MAIN_UI_COL = {
            RecipeInfo.COLUMN_ID,
            RecipeInfo._ID,
            RecipeInfo.COLUMN_SERVINGS,
            RecipeInfo.COLUMN_NAME,
            RecipeInfo.COLUMN_INGREDIENTS,
            RecipeInfo.COLUMN_JUDGE,
            RecipeInfo.COLUMN_STEP
    };

    public static final String[] QUERY_INGREDIENTS = {
            BaseInfo.RECIPE_INGREDIENTS_QUANTITY,
            BaseInfo.RECIPE_INGREDIENTS_MEASURE,
            BaseInfo.RECIPE_INGREDIENTS_INGREDIENT
    };

    public static final String[] QUERY_STEP = {
            BaseInfo.RECIPE_STEP_ID,
            BaseInfo.RECIPE_STEP_SHORT_DESCRIPTION,
            BaseInfo.RECIPE_STEP_DESCRIPTION,
            BaseInfo.RECIPE_STEP_VIDEO,
            BaseInfo.RECIPE_STEP_THUMBNAIL
    };

    public static String buildSelect(){
        return RecipeInfo.COLUMN_JUDGE + "=1";
    }

    public static String buildSelectForMaterial(){
        return RecipeMaterial.COLUMN_ID + "=?";
    }
}
