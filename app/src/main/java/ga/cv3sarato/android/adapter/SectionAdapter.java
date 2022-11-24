package ga.cv3sarato.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.viethoa.RecyclerViewFastScroller;

import java.util.ArrayList;
import java.util.List;

public class SectionAdapter extends BaseRecyclerAdapter<String>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>, RecyclerViewFastScroller.BubbleTextGetter {

    private ArrayList<Integer> mSectionPositions;
    private List<String> data;
    private Context context;
    private List<String> sections;

    public SectionAdapter(Context context, List<String> data, int layoutId) {
        super(context, data, layoutId);
        this.data = data;
        this.context = context;
    }


    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, String itemData, int position) {
//        TextView contactText = (TextView) holder.getView(R.id.contactTitle);
//        contactText.setText(itemData);
    }

    @Override
    public long getHeaderId(int position) {
        return data.get(position).charAt(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.header_contact, parent, false)) {};
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        textView.setText(String.valueOf(data.get(position).charAt(0)));
    }

    @Override
    public String getTextToShowInBubble(int pos) {
        if (pos < 0 || pos >= data.size())
            return null;

        String name = data.get(pos);
        if (name == null || name.length() < 1)
            return null;

        return data.get(pos).substring(0, 1);
    }
}

