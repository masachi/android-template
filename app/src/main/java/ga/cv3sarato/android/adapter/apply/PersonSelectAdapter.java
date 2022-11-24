package ga.cv3sarato.android.adapter.apply;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.entity.response.contact.ContactEntity;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PersonSelectAdapter extends BaseRecyclerAdapter<ContactEntity.ContactItem> {

    private Context context;
    private List<ContactEntity.ContactItem> data;
    private SparseBooleanArray selectedPositions = new SparseBooleanArray();

    public PersonSelectAdapter(Context context, List<ContactEntity.ContactItem> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, ContactEntity.ContactItem itemData, int position) {
        ImageView personSelectAvatarImageView = holder.getView(R.id.person_select_avatar_imageView);
        TextView personSelectNameTextView = holder.getView(R.id.person_select_name_textView);
        CheckBox personSelectCheckBox = holder.getView(R.id.person_select_checkBox);

        GlideUtils.newInstance(context)
                .setImageView(personSelectAvatarImageView)
                .setPath(itemData.getAvatar())
                .withDefaultHeaders()
                .loadImage();

        personSelectNameTextView.setText(itemData.getName());
        personSelectCheckBox.setChecked(isItemChecked(position));

        personSelectCheckBox.setOnClickListener(view -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });

        holder.getRootView().setOnClickListener(view -> {
            if(onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }

    public void setItemChecked(int position, boolean isChecked) {
        selectedPositions.put(position, isChecked);
    }

    public boolean isItemChecked(int position) {
        return selectedPositions.get(position);
    }

    public ArrayList<ContactEntity.ContactItem> getSelecedItem() {
        ArrayList<ContactEntity.ContactItem> selectedItems = new ArrayList<>();
        for(int i =0; i< data.size(); i++) {
            if(isItemChecked(i)) {
                selectedItems.add(data.get(i));
            }
        }
        return selectedItems;
    }
}
