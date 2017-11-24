package com.android.account.clear_bills.Control;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by 陈岗不姓陈 on 2017/10/30.
 * <p>
 */

public class DisplayUtil {
    public static int dp2px(Context context, float dpValue) {
        if (context == null) return (int) (dpValue * 1.5f + 0.5f);
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int sp2px(Context context,float sp){
        if(context == null) return (int)(sp * 1.5f + 0.5f);
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,context.getResources().getDisplayMetrics());
    }
}
