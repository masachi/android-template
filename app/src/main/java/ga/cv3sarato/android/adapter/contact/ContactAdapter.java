package ga.cv3sarato.android.adapter.contact;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.entity.response.contact.ContactEntity;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.viethoa.RecyclerViewFastScroller;

import java.util.List;

public class ContactAdapter extends BaseRecyclerAdapter<ContactEntity.ContactItem>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>, RecyclerViewFastScroller.BubbleTextGetter {

    private List<ContactEntity.ContactItem> data;
    private Context context;

    public ContactAdapter(Context context, List<ContactEntity.ContactItem> data, int layoutId) {
        super(context, data, layoutId);
        this.data = data;
        this.context = context;
    }


    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, ContactEntity.ContactItem itemData, int position) {
        ImageView contactAvatar = holder.getView(R.id.contact_avatar_image);
        TextView contactName = holder.getView(R.id.contact_name_textView);
        contactName.setText(itemData.getName());
        GlideUtils.newInstance(context)
                .setImageView(contactAvatar)
                .setPath(itemData.getAvatar())
                .withDefaultHeaders()
                .loadImage();

        holder.getRootView().setOnClickListener(view -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }

    @Override
    public long getHeaderId(int position) {
            return data.get(position).getAlphabet().charAt(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.header_contact, parent, false)) {};
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        textView.setText(String.valueOf(data.get(position).getAlphabet().charAt(0)));
    }

    @Override
    public String getTextToShowInBubble(int pos) {
        if (pos < 0 || pos >= data.size())
            return null;

        String name = data.get(pos).getAlphabet();
        if (name == null || name.length() < 1)
            return null;

        return data.get(pos).getAlphabet();
    }
}
