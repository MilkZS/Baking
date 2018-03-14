package com.example.android.baking.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by milkdz on 2018/2/23.
 */

public class RecipeProvider extends ContentProvider {

    private String TAG = "RecipeProvider";
    private boolean DBG = true;

    private RecipeDBHelper recipeDBHelper;

    private static final int CODE_RECIPE = 100;
    private static final int CODE_RECIPE_ID = 101;
    private static final int CODE_MATERIAL = 102;
    private static final int CODE_MATERIAL_ID = 103;

    private static UriMatcher uriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        recipeDBHelper = new RecipeDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case CODE_RECIPE: {
                cursor = recipeDBHelper.getReadableDatabase().query(
                        RecipeContract.RecipeInfo.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            }
            break;
            case CODE_RECIPE_ID: {
                String sUri = uri.getLastPathSegment();
                String[] selectionUriId = new String[]{sUri};
                cursor = recipeDBHelper.getReadableDatabase().query(
                        RecipeContract.RecipeInfo.TABLE_NAME,
                        projection,
                        RecipeContract.RecipeInfo.COLUMN_ID + "=" + selectionUriId[0],
                        null,
                        null,
                        null,
                        null
                        );
            } break;
            case CODE_MATERIAL:{
                cursor = recipeDBHelper.getReadableDatabase().query(
                        RecipeContract.RecipeMaterial.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            }break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        int rowId = 0;
        switch (uriMatcher.match(uri)){
            case CODE_RECIPE:{
                recipeDBHelper.getWritableDatabase().beginTransaction();
                try{
                    for (ContentValues contentValues:values){
                        long countId = recipeDBHelper.getWritableDatabase().insert(
                                RecipeContract.RecipeInfo.TABLE_NAME,
                                null,
                                contentValues
                        );
                        if(countId != -1){
                            rowId ++;
                        }
                    }
                    recipeDBHelper.getWritableDatabase().setTransactionSuccessful();
                }finally {
                    recipeDBHelper.getWritableDatabase().endTransaction();
                }
                if (rowId > 0){
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                return rowId;
            }

            case CODE_MATERIAL:{
                recipeDBHelper.getWritableDatabase().beginTransaction();
                try{
                    for (ContentValues contentValues:values){
                        long countId = recipeDBHelper.getWritableDatabase().insert(
                                RecipeContract.RecipeMaterial.TABLE_NAME,
                                null,
                                contentValues
                        );
                        if(countId != -1){
                            rowId ++;
                        }
                    }
                    recipeDBHelper.getWritableDatabase().setTransactionSuccessful();
                }finally {
                    recipeDBHelper.getWritableDatabase().endTransaction();
                }
                if (rowId > 0){
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                return rowId;
            }
        }
        return super.bulkInsert(uri, values);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int rowId = 0;
        if (null == selection) selection = "1";

        switch (uriMatcher.match(uri)) {
            case CODE_RECIPE: {
                rowId = recipeDBHelper.getWritableDatabase().delete(
                        RecipeContract.RecipeInfo.TABLE_NAME, selection, selectionArgs);
            }
            break;
            case CODE_MATERIAL:{
                rowId = recipeDBHelper.getWritableDatabase().delete(
                        RecipeContract.RecipeMaterial.TABLE_NAME, selection, selectionArgs);
            }break;
            default:
                throw new UnsupportedOperationException("Un know uri :" + uri);
        }
        if (rowId != -1) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowId;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public void shutdown() {
        recipeDBHelper.close();
        super.shutdown();
    }

    /**
     * Add content uri in urimatcher
     *
     * @return urimatcher
     */
    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = RecipeContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, RecipeContract.RECIPE_INFO, CODE_RECIPE);
        matcher.addURI(authority, RecipeContract.RECIPE_INFO + "/#", CODE_RECIPE_ID);
        matcher.addURI(authority, RecipeContract.RecipeMaterial.RECIPE_MATERIAL,CODE_MATERIAL);
        matcher.addURI(authority, RecipeContract.RecipeMaterial.RECIPE_MATERIAL + "/#",CODE_MATERIAL_ID);
        return matcher;
    }
}
