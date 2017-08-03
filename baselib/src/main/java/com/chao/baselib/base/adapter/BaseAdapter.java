package com.chao.baselib.base.adapter;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.chao.baselib.base.adapter.animation.AlphaInAnimation;
import com.chao.baselib.base.adapter.animation.BaseAnimation;
import com.chao.baselib.base.adapter.holder.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义适配器基类
 * Created by Chao on 2017/3/24.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> implements RLHeaderFooter, RLViewBindData<T, ViewHolder>, IAnimation, ILayoutManager {

    protected final int TYPE_HEADER = -0x100;
    protected final int TYPE_FOOTER = -0x101;
    protected Context mContext;
    protected int layoutId;
    protected List<T> mDatas;
    protected RLItemViewType<T> itemViewType;
    protected RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    protected List<View> mHeaders = new ArrayList<>();
    protected List<View> mFooters = new ArrayList<>();


    public BaseAdapter(Context context, List<T> mDatas, int layoutId) {
        this.mContext = context;
        this.mDatas = mDatas;
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
        this.layoutId = layoutId;
        this.itemViewType = null;
    }

    public BaseAdapter(Context context, List<T> mDatas, RLItemViewType<T> viewType) {
        this.mContext = context;
        this.mDatas = mDatas;
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
        this.itemViewType = itemViewType == null ? offerRLItemViewType() : itemViewType;
        if (itemViewType == null) {
            //使用这个构造函数，你必须通过实现RLItemViewType接口。
            new NullPointerException("NullPointerException  With this constructor, you must implement the RLItemViewType interface.");
        }
    }

    protected RLItemViewType<T> offerRLItemViewType() {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (isHeaderView(position)) {//是头布局
            viewType = TYPE_HEADER;
        } else if (isFooterView(position)) {//是尾布局
            viewType = TYPE_FOOTER;
        } else {//是用户自定义布局
            if (itemViewType != null) {//用户实现了RLItemViewType接口
                if (mHeaders.size() > 0) {
                    position = position - mHeaders.size();
                }
                return itemViewType.getItemViewType(position, mDatas == null ? null : position >= mDatas.size() ? null : mDatas.get(position));//返回用户itemViewType
            }
            return 0;//默认单布局
        }
        return viewType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final ViewHolder holder;
        if (viewType == TYPE_HEADER || viewType == TYPE_FOOTER) {
            FrameLayout frameLayout = new FrameLayout(parent.getContext());
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT));
            return new ViewHolder(frameLayout);
        } else {
            holder = onCreate(null, parent, viewType);
        }
        if (!(holder.itemView instanceof AdapterView) && !(holder.itemView instanceof RecyclerView)) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, viewType, getHeaderSize() > 0 ? holder.getAdapterPosition() - getHeaderSize() : holder.getAdapterPosition());
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemLongClickListener != null) {
                        mOnItemLongClickListener.onItemLongClick(v, viewType, getHeaderSize() > 0 ? holder.getAdapterPosition() - getHeaderSize() : holder.getAdapterPosition());
                        return true;
                    }
                    return false;
                }
            });
        }
        return holder;
    }


    @Override
    public ViewHolder onCreate(@Nullable View convertView, ViewGroup parent, int viewType) {
        if (convertView == null) {
            View inflate = LayoutInflater.from(mContext).inflate(itemViewType == null ?
                    layoutId : itemViewType.getLayoutId(viewType), parent, false);
            return ViewHolder.get(null, inflate);
        } else { // When convertView != null, parent must be an AbsListView.
            return ViewHolder.get(convertView, null);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = getItemViewType(position);//避免调用子类方法，子类super
        if (viewType == TYPE_HEADER) {
            View v = mHeaders.get(position);
            prepareHeaderFooter(holder, v);
        } else if (viewType == TYPE_FOOTER) {
            View v = mFooters.get(position - getDebugCount() - getHeaderSize());
            prepareHeaderFooter(holder, v);
        } else {
            int currposition = getHeaderSize() > 0 ? position - getHeaderSize() : position;
            onBind(holder, viewType, currposition, mDatas == null || mDatas.size() <= 0 || currposition >= mDatas.size() ? null : mDatas.get(currposition));
            addLoadAnimation(holder, currposition); // Load animation
        }
//        if (viewType != TYPE_HEADER && viewType != TYPE_FOOTER) {
//            int currposition = getHeaderSize() > 0 ? position - getHeaderSize() : position;
//            onBind(holder, viewType, currposition, mDatas == null || mDatas.size() <= 0 || currposition >= mDatas.size() ? null : mDatas.get(currposition));
//            addLoadAnimation(holder, currposition); // Load animation
//        }
    }

    private void prepareHeaderFooter(ViewHolder vh, View view) {

        // if it's a staggered grid, span the whole layout
//        if (mManagerType == TYPE_MANAGER_STAGGERED_GRID) {
//            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams
//                    (ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.MATCH_PARENT);
//            layoutParams.setFullSpan(true);
//            vh.itemView.setLayoutParams(layoutParams);
//        }

        // if the view already belongs to another layout, remove it
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }

        // empty out our FrameLayout and replace with our header/footer
        ((FrameLayout) vh.itemView).removeAllViews();
        ((FrameLayout) vh.itemView).addView(view);

    }


    @Override
    public int getItemCount() {//用户是否重写了数据
        int size = getBaseCount() != -1 ? getBaseCount() : mDatas == null ? 0 : mDatas.size();
        size += getHeaderSize();
        size += getFootSize();
        return size;
    }

    public int getBaseCount() {
        return -1;
    }

    /**
     * 获取测试数据总数
     */
    private int getDebugCount() {
        return getItemCount() - getHeaderSize() - getFootSize();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (mRecyclerView != null && mRecyclerView != recyclerView)
            Log.i(getClass().getSimpleName(), "Does not support multiple RecyclerViews now.");
        mRecyclerView = recyclerView;
        // Ensure a situation that add header or footer before setAdapter().
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = null;
    }

    private void setLayoutParams(View view) {
        if (getHeaderSize() > 0 || getFootSize() > 0) {
            RecyclerView.LayoutManager layoutManager = getLayoutManager();
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                view.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(
                        StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT,
                        StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT));
            } else if (layoutManager instanceof GridLayoutManager) {
                view.setLayoutParams(new GridLayoutManager.LayoutParams(
                        GridLayoutManager.LayoutParams.MATCH_PARENT,
                        GridLayoutManager.LayoutParams.WRAP_CONTENT));
            } else {
                view.setLayoutParams(new RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    @Override
    public boolean hasLayoutManager() {
        return mRecyclerView != null && mRecyclerView.getLayoutManager() != null;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return hasLayoutManager() ? mRecyclerView.getLayoutManager() : null;
    }


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    private long mDuration = 200;
    private boolean mOnlyOnce = false;
    private BaseAnimation mLoadAnimation;
    private Interpolator mInterpolator = new LinearInterpolator();
    private boolean mLoadAnimationEnabled;
    private int mLastPosition = -1;

    public void setAnimationConfig(long mDuration, boolean mOnlyOnce, BaseAnimation mLoadAnimation, Interpolator mInterpolator) {
        if (mDuration > 0)
            this.mDuration = mDuration;
        this.mOnlyOnce = mOnlyOnce;
        if (mLoadAnimation != null)
            this.mLoadAnimation = mLoadAnimation;
        if (mInterpolator != null)
            this.mInterpolator = mInterpolator;
    }

    /**
     * 默认动画
     */
    @Override
    public void enableLoadAnimation() {
        enableLoadAnimation(mDuration, new AlphaInAnimation());
    }

    /**
     * 设置自定义动画
     */
    @Override
    public void enableLoadAnimation(long duration, BaseAnimation animation) {
        if (duration > 0) {
            mDuration = duration;
        } else {
            Log.w("TAG", "Invalid animation duration");
        }
        mLoadAnimationEnabled = true;
        mLoadAnimation = animation;
    }

    /**
     * 设置自定义动画
     */
    public void enableLoadAnimation(long duration, AnimationEnum animation) {
        if (duration > 0) {
            mDuration = duration;
        } else {
            Log.w("TAG", "Invalid animation duration");
        }
        mLoadAnimationEnabled = true;
        switch (animation) {
            case ALPHA:
                mLoadAnimation = AnimationEnum.ALPHA.animation;
                break;
            case SCALE:
                mLoadAnimation = AnimationEnum.SCALE.animation;
                break;
            case BOTTOM:
                mLoadAnimation = AnimationEnum.BOTTOM.animation;
                break;
            case LEFT:
                mLoadAnimation = AnimationEnum.LEFT.animation;
                break;
            case RIGHT:
                mLoadAnimation = AnimationEnum.RIGHT.animation;
                break;
        }
    }

    public boolean getAnimation(View itemView, int position) {
        return false;
    }

    /**
     * 取消动画
     */
    @Override
    public void cancelLoadAnimation() {
        mLoadAnimationEnabled = false;
        mLoadAnimation = null;
    }

    /**
     * Item动画是否只执行一次
     *
     * @param onlyOnce
     */
    @Override
    public void setOnlyOnce(boolean onlyOnce) {
        mOnlyOnce = onlyOnce;
    }

    /**
     * 添加动画
     */
    @Override
    public void addLoadAnimation(RecyclerView.ViewHolder holder, int position) {
        if (mLoadAnimationEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (!mOnlyOnce || /*holder.getLayoutPosition()*/position > mLastPosition) {//还没有加载的Item展示动画
                BaseAnimation animation;
                if (getAnimation(holder.itemView, position)) {
                    getAnimation(holder.itemView, position);
                } else {
                    animation = mLoadAnimation == null ? new AlphaInAnimation() : mLoadAnimation;
                    for (Animator anim : animation.getAnimators(holder.itemView)) {
                        anim.setInterpolator(mInterpolator);
                        anim.setDuration(mDuration).start();
                    }
                }
                mLastPosition = /*holder.getLayoutPosition()*/position;
            }
        }
    }


    @Override
    public void addHeader(View header) {
        if (!mHeaders.contains(header)) {
            mHeaders.add(header);
            notifyItemInserted(mHeaders.size() - 1);
        }
    }

    @Override
    public void addFooter(View footer) {
        if (!mFooters.contains(footer)) {
            mFooters.add(footer);
            notifyItemInserted(getHeaderSize() + (mDatas == null ? 0 : mDatas.size()) + getFootSize() - 1);
        }
    }

    @Override
    public void removeHeader(View header) {
        if (mHeaders.contains(header)) {
            notifyItemRemoved(mHeaders.indexOf(header));
            mHeaders.remove(header);
        }
    }

    @Override
    public void removeFooter(View footer) {
        if (mFooters.contains(footer)) {
            notifyItemRemoved(getHeaderSize() + (mDatas == null ? 0 : mDatas.size()) + mFooters.indexOf(footer));
            mFooters.remove(footer);
        }
    }

    @Override
    public int getHeaderSize() {
        return mHeaders.size();
    }

    @Override
    public int getFootSize() {
        return mFooters.size();
    }

    @Override
    public boolean isHeaderView(int position) {
        return getHeaderSize() > 0 && getHeaderSize() > position;
    }

    @Override
    public boolean isFooterView(int position) {
        return getFootSize() > 0 && position >= getDebugCount() + getHeaderSize() /* position - mDatas.size() - getHeaderSize() >= 0*/;
    }


}