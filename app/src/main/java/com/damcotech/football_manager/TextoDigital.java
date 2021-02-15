package com.damcotech.football_manager;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by nacho on 15/05/2015.
 */
public class TextoDigital extends TextView {
    public TextoDigital(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "digital.ttf"));
    }
}
