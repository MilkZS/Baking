package com.example.android.baking.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by milkdz on 2018/2/23.
 */

public class RecipeDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Recipe.db";
    private static final int VERSION = 7;

    public RecipeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable());
        db.execSQL(createTableMaterial());
    }

    private String createTable(){
        return SQLBaseInfo.CREATE_TABLE + RecipeContract.RecipeInfo.TABLE_NAME + " ( "
                + RecipeContract.RecipeInfo._ID + SQLBaseInfo.INT_PRIMARY_KEY + ","
                + RecipeContract.RecipeInfo.COLUMN_ID + ","
                + RecipeContract.RecipeInfo.COLUMN_NAME + ","
                + RecipeContract.RecipeInfo.COLUMN_STEP +  ","
                + RecipeContract.RecipeInfo.COLUMN_SERVINGS  + ","
                + RecipeContract.RecipeInfo.COLUMN_INGREDIENTS + ","
                + RecipeContract.RecipeInfo.COLUMN_JUDGE
                + " ); ";
    }

    private String createTableMaterial(){
        return SQLBaseInfo.CREATE_TABLE + RecipeContract.RecipeMaterial.TABLE_NAME + " ( "
                + RecipeContract.RecipeMaterial._ID + SQLBaseInfo.INT_PRIMARY_KEY + ","
                + RecipeContract.RecipeMaterial.COLUMN_ID + ","
                + RecipeContract.RecipeMaterial.COLUMN_NAME + ","
                + RecipeContract.RecipeMaterial.COLUMN_MATERIAL
                + " ); ";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLBaseInfo.DROP_TABLE + RecipeContract.RecipeInfo.TABLE_NAME);
        db.execSQL(SQLBaseInfo.DROP_TABLE + RecipeContract.RecipeMaterial.TABLE_NAME);
        onCreate(db);
    }
}
