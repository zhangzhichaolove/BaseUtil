package com.chao.baselib.skin.entity;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chao.baselib.skin.loader.SkinManager;


public class BackgroundAttr extends SkinAttr {

    @Override
    public void apply(View view) {

        if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
            view.setBackgroundColor(SkinManager.getInstance().getColor(attrValueRefId));
            Log.i("attr", "_________________________________________________________");
            Log.i("attr", "apply as color");
        } else if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName) || RES_TYPE_NAME_MIPMAP.equals(attrValueTypeName)) {
            Drawable bg = SkinManager.getInstance().getDrawable(attrValueRefId);
            if (attrName.equals("src") && view instanceof ImageView) {
                ((ImageView) view).setImageDrawable(bg);
            } else {
                view.setBackground(bg);
            }
            Log.i("attr", "_________________________________________________________");
            Log.i("attr", "apply as drawable");
            Log.i("attr", "bg.toString()  " + bg.toString());

            Log.i("attr", this.attrValueRefName + " 是否可变换状态? : " + bg.isStateful());
        }
    }
}
