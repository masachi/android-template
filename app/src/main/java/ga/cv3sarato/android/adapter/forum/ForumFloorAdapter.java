package ga.cv3sarato.android.adapter.forum;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.Router;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.common.CommonImageAdapter;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseRecyclerViewHolder;
import ga.cv3sarato.android.entity.file.FileEntity;
import ga.cv3sarato.android.entity.response.forum.ForumFloorsEntity;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class ForumFloorAdapter extends BaseRecyclerAdapter<ForumFloorsEntity.Floor> {

    private Context context;
    private List<ForumFloorsEntity.Floor> floors;

    private CommonImageAdapter imageAdapter;
    private FloorCommentAdapter commentAdapter;

    public ForumFloorAdapter(Context context, List<ForumFloorsEntity.Floor> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.floors = data;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, ForumFloorsEntity.Floor itemData, int position) {
        ImageView floorAvatarImageView = holder.getView(R.id.floor_avatar_imageView);
        TextView floorCreatorTextView = holder.getView(R.id.floor_creator_textView);
        TextView floorCreateTimeTextView = holder.getView(R.id.floor_create_time_textView);
        TextView floorContentTextView = holder.getView(R.id.floor_content_textView);
        RecyclerView floorImageRecyclerView = holder.getView(R.id.floor_image_recyclerView);
        RecyclerView floorCommentRecyclerView = holder.getView(R.id.floor_comment_recyclerView);
        Button floorCommentMoreBtn = holder.getView(R.id.floor_comment_more_btn);

        GlideUtils.newInstance(context)
                .setImageView(floorAvatarImageView)
                .setPath(itemData.getCreator().getAvatar())
                .withDefaultHeaders()
                .loadImage();

        floorCreatorTextView.setText(itemData.getCreator().getName());
        floorCreateTimeTextView.setText(itemData.getCreateTime());
        floorContentTextView.setText(itemData.getContent());

        imageAdapter = new CommonImageAdapter(context, itemData.getFiles(), R.layout.item_image_forum_detail);
        floorImageRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        floorImageRecyclerView.setAdapter(imageAdapter);

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

        commentAdapter = new FloorCommentAdapter(context, itemData.getFloorComments(), R.layout.item_forum_floor_comment);
        floorCommentRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        floorCommentRecyclerView.setAdapter(commentAdapter);
        commentAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int pos) {
                Router.build("gtedx://forumComment")
                        .with("id", floors.get(position).getId())
                        .with("type", "COMMENT")
                        .with("pid", itemData.getFloorComments().get(pos).getId())
                        .go(context);
            }
        });

        if(itemData.getPagination().getRemain() > 0) {
            floorCommentMoreBtn.setVisibility(View.VISIBLE);
            floorCommentMoreBtn.setText("查看剩余" + String.valueOf(itemData.getPagination().getRemain()) + "条评论");
        }
        else {
            floorCommentMoreBtn.setVisibility(View.GONE);
        }

        floorCommentMoreBtn.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });

        holder.getRootView().setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }
}
