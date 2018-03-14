package com.example.android.baking;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.baking.adapter.PicRecycleAdapter;
import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.base.TakeValues;
import com.example.android.baking.db.RecipeContract;
import com.example.android.baking.db.SQLBaseInfo;
import com.example.android.baking.sync.RecipeThread;


/**
 * Created by milkdz on 2018/2/20.
 */

public class BakingMainActivity extends AppCompatActivity
        implements PicRecycleAdapter.RecipeClickHandle,LoaderManager.LoaderCallbacks<Cursor> {

    private String TAG = "BakingMainActivity";
    private RecyclerView recyclerView;
    private PicRecycleAdapter picRecycleAdapter;
    private int mPosition = RecyclerView.NO_POSITION;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baking_main_activity);

        sharedPreferences = getSharedPreferences(BaseInfo.PREFERENCE_WIDGET,MODE_PRIVATE);

        mPosition = sharedPreferences.getInt(BaseInfo.ACTIVITY_POSITION_LAND,0);

        String rowNumber = getResources().getString(R.string.card_view_col);
        if(rowNumber.equals("1")){
            TakeValues.ifUseFragment = false;
        }else if(rowNumber.equals("3")){
            TakeValues.ifUseFragment = true;
        }

        recyclerView = findViewById(R.id.recipe_recycle);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                this,Integer.parseInt(getString(R.string.card_view_col)));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        picRecycleAdapter = new PicRecycleAdapter(this);
        recyclerView.setAdapter(picRecycleAdapter);
        getSupportLoaderManager().initLoader(0,null,this);
        RecipeThread.initialize(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri foodUri = RecipeContract.CONTENT_BASE;
        String order = RecipeContract.RecipeInfo.COLUMN_ID + SQLBaseInfo.SORT_AES;
        return new CursorLoader(this,foodUri,RecipeContract.RECIPE_MAIN_UI_COL,null,null,order);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        picRecycleAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION) {
            mPosition = 0;
        }
        recyclerView.smoothScrollToPosition(mPosition);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        picRecycleAdapter.swapCursor(null);
    }

    @Override
    public void onClick(int mPosition) {
    }

    @Override
    protected void onResume() {

        super.onResume();
        Log.d(TAG,"onResume--mPosition : " + mPosition);
        recyclerView.getLayoutManager().scrollToPosition(mPosition);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        SharedPreferences sharedPreferences = getSharedPreferences(BaseInfo.PREFERENCE_WIDGET,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        mPosition = ((GridLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        editor.putInt(BaseInfo.ACTIVITY_POSITION_LAND,mPosition);
        editor.apply();
        editor.commit();
        super.onSaveInstanceState(outState);

    }


}
