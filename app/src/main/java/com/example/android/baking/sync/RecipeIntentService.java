package com.example.android.baking.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by milkdz on 2018/2/25.
 */

public class RecipeIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public RecipeIntentService() {
        super("RecipeIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        RecipeSyncTask.syncRecipe(this);
    }
}
