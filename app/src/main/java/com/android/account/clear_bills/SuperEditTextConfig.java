package com.android.account.clear_bills;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2017/11/18.
 */

public class SuperEditTextConfig {
    private boolean isNeedToReApply;

    private int deleteResID;//删除图标资源ID
    private Drawable deleteIcon;

    public SuperEditTextConfig(Context context){

    }
    public SuperEditTextConfig(Context context,SuperEditTextConfig config){
        if(config==null){
            
        }
    }
}
