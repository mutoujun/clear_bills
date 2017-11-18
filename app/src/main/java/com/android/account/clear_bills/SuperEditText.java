package com.android.account.clear_bills;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/11/18.
 */

public class SuperEditText extends AppCompatEditText {
    public SuperEditText(Context context) {
        this(context,null);
    }

    public SuperEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SuperEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
