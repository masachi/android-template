package ga.cv3sarato.android.adapter.forum;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.Router;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.common.CommonImageAdapter;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.entity.file.FileEntity;
import ga.cv3sarato.android.entity.response.forum.ForumPostsEntity;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class ForumAllAdapter extends BaseRecyclerAdapter<ForumPostsEntity.ForumItem> {

    private Context context;
    private List<ForumPostsEntity.ForumItem> data;

    private CommonImageAdapter imageAdapter;

    public ForumAllAdapter(Context context, List<ForumPostsEntity.ForumItem> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, ForumPostsEntity.ForumItem itemData, int position) {
        TextView forumItemTitleTextView = holder.getView(R.id.forum_item_title_textView);
        ImageView forumItemAvatarImageView = holder.getView(R.id.forum_item_avatar_imageView);
        TextView forumItemNameTextView = holder.getView(R.id.forum_item_name_textView);
        TextView forumItemTimeTextView = holder.getView(R.id.forum_item_time_textView);
        Button forumItemTopBtn = holder.getView(R.id.forum_item_top_btn);
        Button forumItemDeleteBtn = holder.getView(R.id.forum_item_delete_btn);
        TextView forumItemContentTextView = holder.getView(R.id.forum_item_content_textView);
        ImageButton forumItemFollowBtn = holder.getView(R.id.forum_item_follow_btn);
        TextView forumItemFollowNumber = holder.getView(R.id.forum_item_follow_number);
        ImageButton forumItemCommentBtn = holder.getView(R.id.forum_item_comment_btn);
        TextView forumItemCommentNumber = holder.getView(R.id.forum_item_comment_number);
        ImageButton forumItemLikeBtn = holder.getView(R.id.forum_item_like_btn);
        TextView forumItemLikeNumber = holder.getView(R.id.forum_item_like_number);
        RecyclerView forumItemImageRecyclerView = holder.getView(R.id.forum_item_image_recyclerView);

        GlideUtils.newInstance(context)
                .setPath(itemData.getCreator().getAvatar())
                .setImageView(forumItemAvatarImageView)
                .withDefaultHeaders()
                .loadImage();

        forumItemTitleTextView.setText(itemData.getTitle());
        forumItemNameTextView.setText(itemData.getCreator().getName() == null ? "匿名" : itemData.getCreator().getName());
        forumItemTimeTextView.setText(itemData.getCreateTime());
        forumItemContentTextView.setText(itemData.getContent());
        forumItemFollowNumber.setText(String.valueOf(itemData.getFollowCount()));
        forumItemCommentNumber.setText(String.valueOf(itemData.getFloorCount()));
        forumItemLikeNumber.setText(String.valueOf(itemData.getLikeCount()));

        imageAdapter = new CommonImageAdapter(context, itemData.getFiles(), R.layout.item_image_forum_posts);
        forumItemImageRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        forumItemImageRecyclerView.setAdapter(imageAdapter);
        ((SimpleItemAnimator) forumItemImageRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        imageAdapter.setOnItemClickListener((view, pos) -> {
            ArrayList<String> images = new ArrayList<>();
            for (FileEntity file : itemData.getFiles()) {
                images.add(file.getViewUrl());
            }

            Bundle bundle = new Bundle();
            bundle.putStringArrayList("uri", images);
            Router.build("gtedx://bigImage")
                    .with(bundle)
                    .with("index", pos)
                    .go(context);
        });

        holder.getRootView().setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });

        forumItemTopBtn.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });

        forumItemDeleteBtn.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });

        forumItemFollowBtn.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });

        forumItemLikeBtn.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });

        forumItemCommentBtn.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });

    }
}
