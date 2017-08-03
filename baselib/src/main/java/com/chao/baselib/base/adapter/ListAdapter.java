package com.chao.baselib.base.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class ListAdapter<T> extends BaseAdapter implements
        OnClickListener {

    public Context context;

    public List<T> data;

    private View backgroundview;

    private AdapterUpdate adapterUpdate;

    public boolean isDeBug = false;

    public ListAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data == null ? new ArrayList<T>() : data;
    }


    @Override
    public int getCount() {
        if (isDeBug) {
            return 20;
        }
        if (data != null && data.size() > 0) {
            return data.size();
        }
        return 0;
    }

    public interface AdapterUpdate {
        void adapterUpdate();
    }

    public void setAdapterUpdate(AdapterUpdate adapterUpdate) {
        this.adapterUpdate = adapterUpdate;
    }

    @Override
    public T getItem(int position) {// 这里改泛型方便调用,原Object.

        if (position >= data.size()) {

            return null;
        }

        return data.get(position);

    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    /**
     * 子类实现加载自定义布局。
     *
     * @return 布局ID
     */
    public abstract int getItemResource();

    /**
     * 初始化布局
     *
     * @param position    下标
     * @param convertView 视图
     * @param holder      holder
     * @return 返回Item视图
     */
    public abstract View getItemView(int position, T data, View convertView,
                                     ViewHolder holder);

    /**
     * Item控件监听
     */
    public abstract void onClick(View v);

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null || backgroundview == convertView) {
            if (getItemResource() == 0) {// 如果没有加载布局，new布局将直接调用getView方法
                return getItemView(position, data.get(position), convertView,
                        holder);
            }

            convertView = View.inflate(context, getItemResource(), null);// 加载自定义视图

            holder = new ViewHolder(convertView);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();// 复用

        }

        return getItemView(position, data.get(position), convertView, holder);

    }

    public class ViewHolder {
        /**
         * 稀疏数组(Sparse array)， 我们查看put(int key, E
         * value)的源码可知，在put数据之前，会先查找要put的数据是否已经存在，如果存在就是修改，不存在就添加。
         */
        private SparseArray<View> views = new SparseArray<View>();

        private View convertView;

        public ViewHolder(View convertView) {// 构造，初始化

            this.convertView = convertView;

        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int resId) {

            View v = views.get(resId);// 找到控件ID

            if (null == v) {// 如果未存储

                v = convertView.findViewById(resId);// 找到ID

                views.put(resId, v);// 存储ID

            }

            return (T) v;// 返回控件视图

        }

    }

    public void addAll(List<T> elem) {
        if (elem != null) {
            data.addAll(elem);
        }

        notifyDataSetChanged();

    }

    public void remove(T elem) {

        data.remove(elem);

        notifyDataSetChanged();

    }

    public void remove(int index) {

        data.remove(index);

        notifyDataSetChanged();

    }

    public void replaceAll(List<T> elem) {

        data.clear();

        if (elem != null) {
            data.addAll(elem);
        }

        notifyDataSetChanged();

    }

    public void setData(List<T> data) {
        if (data != null) {
            this.data = data;
        }
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

}
