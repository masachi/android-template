package ga.cv3sarato.android.adapter.contact;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.entity.response.contact.ContactEntity;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class ContactFilterAdapter extends BaseRecyclerAdapter<ContactEntity.ContactItem> implements Filterable {

    private Context context;
    private List<ContactEntity.ContactItem> data;
    private List<ContactEntity.ContactItem> filteredData;

    public ContactFilterAdapter(Context context, List<ContactEntity.ContactItem> data, int layoutId, List<ContactEntity.ContactItem> filteredData) {
        super(context, filteredData, layoutId);
        this.context = context;
        this.data = data;
        this.filteredData = filteredData;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, ContactEntity.ContactItem itemData, int position) {
        ImageView contactFilterAvatarImageView = holder.getView(R.id.contact_filter_avatar_imageView);
        TextView contactFilterNameTextView = holder.getView(R.id.contact_filter_name_textView);
        TextView contactFilterPhoneTextView = holder.getView(R.id.contact_filter_phone_textView);

        GlideUtils.newInstance(context)
                .setPath(itemData.getAvatar())
                .setImageView(contactFilterAvatarImageView)
                .withDefaultHeaders()
                .loadImage();

        contactFilterNameTextView.setText(itemData.getName());
        contactFilterPhoneTextView.setText(itemData.getMobile());
        holder.getRootView().setOnClickListener(view -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String keyword = charSequence.toString();
                if(keyword.isEmpty()) {
                    filteredData = new ArrayList<>();
                }
                else {
                    List<ContactEntity.ContactItem> filteredList = new ArrayList<>();
                    for(ContactEntity.ContactItem item : data) {
                        if(item.getName().contains(keyword) || item.getMobile().contains(keyword) || item.getEmail().contains(keyword)) {
                            filteredList.add(item);
                        }
                    }

                    filteredData = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ContactFilterAdapter.super.data = (ArrayList<ContactEntity.ContactItem>) filterResults.values;
                ContactFilterAdapter.super.notifyDataSetChanged();
            }
        };
    }

    public List<ContactEntity.ContactItem> getFilteredData() {
        return filteredData;
    }
}
