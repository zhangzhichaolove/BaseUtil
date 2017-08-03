package com.chao.baselib.base.adapter;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.chao.baselib.base.adapter.holder.ViewHolder;

import java.util.List;

/**
 * 兼容ListAdapter
 * Created by Chao on 2017/3/24.
 */

public abstract class CustomAdapter<T> extends BaseAdapter<T> implements ListAdapter, View.OnClickListener {
    private DataSetObservable mDataSetObservable = new DataSetObservable();
    private AbsListView mAbsListView;

    public CustomAdapter(Context context, List<T> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
    }

    public CustomAdapter(Context context, List<T> mDatas, RLItemViewType<T> viewType) {
        super(context, mDatas, viewType);
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    public void notifyDataSetHasChanged() {
        if (mRecyclerView == null) {
            mDataSetObservable.notifyChanged();
        } else {
            notifyDataSetChanged();
        }
    }

    public void notifyDataSetInvalidated() {
        if (mRecyclerView == null)
            mDataSetObservable.notifyInvalidated();
    }

    public List<T> getData() {
        return mDatas;
    }

    public void setData(List<T> mDatas) {
        if (mDatas != null) {
            this.mDatas = mDatas;
            if (mRecyclerView == null) {
                mDataSetObservable.notifyChanged();
            } else {
                notifyDataSetChanged();
            }
        }
    }

    public void addFirst(T item) {
        if (item != null) {
            mDatas.add(0, item);
            if (mRecyclerView == null) {
                mDataSetObservable.notifyChanged();
            } else {
                notifyItemInserted(getHeaderSize());
            }
        }
    }

    public void addIndex(int index, T item) {
        if (item != null) {
            mDatas.add(index, item);
            if (mRecyclerView == null) {
                mDataSetObservable.notifyChanged();
            } else {
                notifyItemInserted(getHeaderSize() + index);
            }
        }
    }

    public void addLast(T item) {
        if (item != null) {
            mDatas.add(item);
            if (mRecyclerView == null) {
                mDataSetObservable.notifyChanged();
            } else {
                notifyItemInserted(getHeaderSize() + mDatas.size());
            }
        }
    }

    public void addAll(List<T> list) {
        if (mDatas != null && list != null) {
            mDatas.addAll(list);
            if (mRecyclerView == null) {
                mDataSetObservable.notifyChanged();
            } else {
                notifyItemRangeInserted(getHeaderSize() + mDatas.size(), list.size());
            }
        }
    }

    public void set(int index, T item) {
        if (mDatas != null && mDatas.size() >= 1 && index < mDatas.size()) {
            mDatas.set(index, item);
            if (mRecyclerView == null) {
                mDataSetObservable.notifyChanged();
            } else {
                notifyItemChanged(getHeaderSize() + index);
            }
        }
    }

    public void remove(int index) {
        if (mDatas != null && mDatas.size() >= 1 && index < mDatas.size()) {
            mDatas.remove(index);
            if (mRecyclerView == null) {
                mDataSetObservable.notifyChanged();
            } else {
                notifyItemRemoved(getHeaderSize() + index);
            }
        }
    }

    public void clear() {
        if (mDatas != null) {
            mDatas.clear();
            if (mRecyclerView == null) {
                mDataSetObservable.notifyChanged();
            } else {
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mAbsListView == null && parent instanceof AbsListView) {
            mAbsListView = (AbsListView) parent;
        }
        ViewHolder holder = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(itemViewType == null ?
                    layoutId : itemViewType.getLayoutId(type), parent, false);// 加载自定义视图
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        onBind(holder, getItemViewType(position), position, mDatas.get(position));
        addLoadAnimation(holder, position); // Load animation
        return holder.itemView;
    }

    @Override
    public int getViewTypeCount() {
        if (itemViewType != null)
            return itemViewType.getViewTypeCount();
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
        //return itemViewType == null ? super.getItemViewType(position) : itemViewType.getItemViewType(position, mDatas.get(position));
    }


    @Override
    public ViewHolder onCreate(@Nullable View convertView, ViewGroup parent, int viewType) {
        return super.onCreate(convertView, parent, viewType);
    }

//    @Override
//    public void onBind(ViewHolder holder, int viewType, int position, T item) {
//
//    }


    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void addHeader(View header) {
        if (mAbsListView != null && mAbsListView instanceof ListView) {
            ((ListView) mAbsListView).addHeaderView(header);
        } else {
            super.addHeader(header);
        }
    }

    @Override
    public void removeHeader(View mHeader) {
        if (mAbsListView != null && mAbsListView instanceof ListView) {
            ((ListView) mAbsListView).removeHeaderView(mHeader);
        } else {
            super.removeHeader(mHeader);
        }
    }

    public boolean hasHeaderView() {
        if (mAbsListView != null && mAbsListView instanceof ListView) {
            return ((ListView) mAbsListView).getHeaderViewsCount() > 0;
        } else {
            return super.getHeaderSize() > 0;
        }
    }

    @Override
    public void addFooter(View footer) {
        if (mAbsListView != null && mAbsListView instanceof ListView) {
            ((ListView) mAbsListView).addFooterView(footer);
        } else {
            super.addFooter(footer);
        }
    }

    @Override
    public void removeFooter(View mFooter) {
        if (mAbsListView != null && mAbsListView instanceof ListView) {
            ((ListView) mAbsListView).removeFooterView(mFooter);
        } else {
            super.removeFooter(mFooter);
        }
    }

    public boolean hasFooterView() {
        if (mAbsListView != null && mAbsListView instanceof ListView) {
            return ((ListView) mAbsListView).getFooterViewsCount() > 0;
        } else {
            return super.getFootSize() > 0;
        }
    }

}
