package com.example.zxl.cloudmanager.Refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.zxl.cloudmanager.R;

/**
 * Created by ZQQ on 2016/8/12.
 */
public class RefreshRecycleView extends LinearLayout {
    /**
     * 垂直方向
     */
    static final int VERTICAL = LinearLayoutManager.VERTICAL;
    /**
     * 水平方向
     */
    static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    /**
     * 内容控件
     */
    private RecyclerView recyclerView;
    /**
     * 刷新布局控件
     */
    private SwipeRefreshLayout swipeRfl;
    private LinearLayoutManager layoutManager;
    /*
     * 刷新布局的监听
     */
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener;
    /**
     * 内容控件滚动监听
     */
    private RecyclerView.OnScrollListener mScrollListener;
    /**
     * 内容适配器
     */
    private RecyclerView.Adapter mAdapter;
    /*
     * 刷新加载监听事件
     */
    private RefreshLoadMoreListener mRefreshLoadMoreListner;
    /**
     * 是否可以加载更多
     */
    private boolean hasMore = true;
    /**
     * 是否正在刷新
     */
    private boolean isRefresh = false;
    /**
     * 是否正在加载更多
     */
    private boolean isLoadMore = false;

    public RefreshRecycleView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @SuppressWarnings("deprecation")
    public RefreshRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 导入布局
        LayoutInflater.from(context).inflate(
                R.layout.main_fragment_my_check, this, true);
        //swipeRfl = (SwipeRefreshLayout) findViewById(R.id.all_swipe);
        recyclerView = (RecyclerView) findViewById(R.id.check_recyclerview);
        // 加载颜色是循环播放的，只要没有完成刷新就会一直循环，color1>color2>color3>color4
        // swipeRfl.setColorScheme(Color.BLUE, Color.GREEN, Color.RED,
        // Color.YELLOW);

        /**
         * 监听上拉至底部滚动监听
         */
        mScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //最后显示的项
                int lastVisibleItem = layoutManager
                        .findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                // lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载
                // dy>0 表示向下滑动
                /*  if (hasMore && (lastVisibleItem >= totalItemCount - 1)
                        && dy > 0 && !isLoadMore) {
                    isLoadMore = true;
                    loadMore();
                }*/
                /**
                 * 无论水平还是垂直
                 */
                if (hasMore && (lastVisibleItem >= totalItemCount - 1)
                        && !isLoadMore) {
                    isLoadMore = true;
                    loadMore();
                }

            }
        };

        /**
         * 下拉至顶部刷新监听
         */
        mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if (!isRefresh) {
                    isRefresh = true;
                    refresh();
                }
            }
        };
        swipeRfl.setOnRefreshListener(mRefreshListener);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnScrollListener(mScrollListener);
    }

    /*public void setOrientation(int orientation) {
        if (orientation != 0 && orientation != 1) {
            layoutManager.setOrientation(VERTICAL);
        } else {
            layoutManager.setOrientation(HORIZONTAL);
        }
    }

    public int getOrientation() {
        return layoutManager.getOrientation();
    }*/

    public void setPullLoadMoreEnable(boolean enable) {
        this.hasMore = enable;
    }

    public boolean getPullLoadMoreEnable() {
        return hasMore;
    }

    public void setPullRefreshEnable(boolean enable) {
        swipeRfl.setEnabled(enable);
    }

    public boolean getPullRefreshEnable() {
        return swipeRfl.isEnabled();
    }

    public void setLoadMoreListener() {
        recyclerView.setOnScrollListener(mScrollListener);
    }

    public void loadMore() {
        if (mRefreshLoadMoreListner != null && hasMore) {
            mRefreshLoadMoreListner.onLoadMore();

        }
    }

    /**
     * 加载更多完毕,为防止频繁网络请求,isLoadMore为false才可再次请求更多数据
     */
    public void setLoadMoreCompleted(){
        isLoadMore = false;
    }

    public void stopRefresh() {
        isRefresh = false;
        swipeRfl.setRefreshing(false);
    }

    public void setRefreshLoadMoreListener(RefreshLoadMoreListener listener) {
        mRefreshLoadMoreListner = listener;
    }

    public void refresh() {
        if (mRefreshLoadMoreListner != null) {
            mRefreshLoadMoreListner.onRefresh();
        }
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null)
            recyclerView.setAdapter(adapter);
    }

    public interface RefreshLoadMoreListener {
        public void onRefresh();

        public void onLoadMore();
    }
}
