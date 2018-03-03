package com.example.android.baking.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.db.RecipeContract;

/**
 * Created by milkdz on 2018/2/28.
 */

public class PicRecycleAdapter extends RecyclerView.Adapter<PicRecycleAdapter.MyPicRecycleHolder> {

    private String TAG = "PicRecycleAdapter";
    private boolean DBG = true;
    private Cursor mCursor;
    private RecipeClickHandle recipeClickHandle;

    public PicRecycleAdapter(RecipeClickHandle recipeClickHandle){
        this.recipeClickHandle = recipeClickHandle;
    }

    @Override
    public MyPicRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutList = R.layout.fragment_card_food_recipe;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutList,parent,false);
        return new MyPicRecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(MyPicRecycleHolder holder, int position) {
        mCursor.moveToPosition(position);

        String name = mCursor.getString(
                mCursor.getColumnIndex(RecipeContract.RecipeInfo.COLUMN_NAME));
        holder.nameTextView.setText(name);

        String number = mCursor.getString(
                mCursor.getColumnIndex(RecipeContract.RecipeInfo.COLUMN_SERVINGS));
        holder.numberTextView.setText(number);

        String ingredients = mCursor.getString(
                mCursor.getColumnIndex(RecipeContract.RecipeInfo.COLUMN_INGREDIENTS));
        if(ingredients == null){
            if (DBG) Log.e(TAG,"it is wrong,ingredients is null");
        }

        String[] s = ingredients.split("###");
        String sInte = "";
        for (int i=0;i<s.length;i++) {
            sInte = sInte + RecipeContract.QUERY_INGREDIENTS[i] + "\t:\t" + s[i] + '\n';
        }
        holder.ingredientTextView.setText(sInte);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null){
            return 0;
        }
        return mCursor.getCount();
    }

    class MyPicRecycleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CardView cardView;
        private TextView nameTextView;
        private TextView numberTextView;
        private TextView ingredientTextView;

        public MyPicRecycleHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.food_card_view);
            cardView.setOnClickListener(this);
            nameTextView = itemView.findViewById(R.id.food_name_text_view);
            numberTextView = itemView.findViewById(R.id.food_serving_text_view);
            ingredientTextView = itemView.findViewById(R.id.food_step_textView);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void swapCursor(Cursor cursor){
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public interface RecipeClickHandle{
        void onClick(String index);
    }
}
