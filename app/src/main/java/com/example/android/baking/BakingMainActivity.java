package com.example.android.baking;

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

import com.example.android.baking.adapter.PicRecycleAdapter;
import com.example.android.baking.db.RecipeContract;
import com.example.android.baking.db.SQLBaseInfo;
import com.example.android.baking.sync.RecipeSyncTask;
import com.example.android.baking.sync.RecipeThread;

/**
 * Created by milkdz on 2018/2/20.
 */

public class BakingMainActivity extends AppCompatActivity
        implements PicRecycleAdapter.RecipeClickHandle,LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView recyclerView;
    private PicRecycleAdapter picRecycleAdapter;

    private int mPosition = RecyclerView.NO_POSITION;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baking_main_activity);

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
    public void onClick(String index) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri foodUri = RecipeContract.CONTENT_BASE;
        String order = RecipeContract.RecipeInfo.COLUMN_ID + SQLBaseInfo.SORT_AES;
        return new CursorLoader(this,foodUri,null,null,null,order);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        picRecycleAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION) {
            mPosition = 0;//recyclerView.getChildAdapterPosition(view);
        }
        recyclerView.smoothScrollToPosition(mPosition);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        picRecycleAdapter.swapCursor(null);
    }
}
