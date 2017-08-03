package com.chao.baselib.base.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Chao on 2017/3/24.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    /**
     * 稀疏数组(Sparse array)， 我们查看put(int key, E
     * value)的源码可知，在put数据之前，会先查找要put的数据是否已经存在，如果存在就是修改，不存在就添加。
     */
    private SparseArray<View> childViews = new SparseArray<>();

    public ViewHolder(View itemView) {
        super(itemView);
    }

    public static ViewHolder get(View convertView, View itemView) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder(itemView);
            convertView = itemView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return holder;
    }

    /**
     * 复用模式
     *
     * @param convertView
     * @return
     */
    public static ViewHolder get(View convertView) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                return new ViewHolder(convertView);
            } else {
                return holder;
            }
        } else {
            //new NullPointerException("NullPointerException:ViewHolder is null");
            return null;
        }
    }

    @Deprecated
    public <T extends View> T getView(int id) {
        return findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(int id) {
        View childView = childViews.get(id);
        if (childView == null) {
            childView = itemView.findViewById(id);
            //childView.setTag(ExtendViewHolder.getInstance(childView));//首次设置TAG
            if (childView != null)
                childViews.put(id, childView);
        }
        return (T) childView;
    }


    public ExtendViewHolder view(int viewId) {
        //ExtendViewHolder extendViewHolder = (ExtendViewHolder) findViewById(viewId).getTag();//复用
        return ExtendViewHolder.getInstance(findViewById(viewId));
    }


//    public ViewHolder setText(String text) {
//        View textView = findViewById(viewId);
//        return this;
//    }

//    public TextView setText(TextView view, CharSequence text) {
//        TextView textView = findViewById(viewId);
//        textView.setText(text);
//        return textView;
//    }
//
//    public TextView setText(int viewId, CharSequence text) {
//        TextView textView = findViewById(viewId);
//        textView.setText(text);
//        return textView;
//    }

}
