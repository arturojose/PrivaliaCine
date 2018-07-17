package com.privaliacine;

import android.app.Activity;
import android.content.Context;
import android.widget.AbsListView;

public abstract class OnScrollFinishListener extends Activity implements AbsListView.OnScrollListener {
    private int currentPage = 0;
    private boolean loading;
    Context context;

    public OnScrollFinishListener(Context context) {
        this.context = context;
    }

    @Override
    public void onScroll(AbsListView view,int firstVisibleItem,int visibleItemCount,int totalItemCount) {
        loading = firstVisibleItem + visibleItemCount >= totalItemCount;
        if(loading){
            onScrollFinished();
            ConnectionServer connectionServer = new ConnectionServer(context, visibleItemCount);
        }
    }

    protected abstract void onScrollFinished();

    @Override
    public void onScrollStateChanged(AbsListView arg0, int arg1) {
        // TODO Auto-generated method stub
    }
}