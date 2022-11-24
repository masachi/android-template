package ga.cv3sarato.android.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
    }

    public <T> T getView(Integer viewId) {
        View view = views.get(viewId);
        if(view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }

        return (T) view;
    }

    public View getRootView() {
        return itemView;
    }
}
