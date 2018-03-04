package com.example.android.baking.util;

import android.util.Log;
import android.widget.TextView;

import com.example.android.baking.db.RecipeContract;

/**
 * Created by milkdz on 2018/3/4.
 */

public class FormRecipe {

    private static String TAG = "FormRecipe";

    public static String formIngredients(String sArray){

        Log.d(TAG,"input array string is " + sArray);
        String[] s = sArray.split(ReadFromJsonString.DIVIDE_TYPE);
        String showData = "\t\t\t\t";
        for (int i=0;i<s.length;i++) {
            String[] ss = s[i].split(ReadFromJsonString.DIVIDE_CONTENT);
            Log.d(TAG,"ss[i] content is " + s[i]);
            Log.d(TAG,"ss length is " + ss.length);
            if(i != s.length - 1){
                showData = showData + ss[0] + "\t" + ss[1] + "\t" + ss[2] + ",\t";
            }else{
                showData = showData + ss[0] + "\t" + ss[1] + "\t" + ss[2] + ".\t\n";
            }
        }
        return showData;
    }
}
