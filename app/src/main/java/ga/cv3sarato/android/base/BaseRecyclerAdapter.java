package ga.cv3sarato.android.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filterable;

import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    protected List<T> data;
    private int layoutId;
    protected OnItemClickListener onItemClickListener;
    protected OnItemLongClickListener onItemLongClickListener;
    private boolean flag = true;

    public BaseRecyclerAdapter(Context context, List<T> data, int layoutId) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder holder = new BaseRecyclerViewHolder(inflater.inflate(layoutId, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position) {
        bindHolder(holder, data.get(position), position);
    }

    protected abstract void bindHolder(BaseRecyclerViewHolder holder,T itemData, int position);

    public void add(List<T> items){
        int previousDataSize = this.data.size();
        this.data.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(View v, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClickListener(View v, int position);
    }
}
