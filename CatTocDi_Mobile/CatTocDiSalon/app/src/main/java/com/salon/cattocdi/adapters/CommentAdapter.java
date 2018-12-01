package com.salon.cattocdi.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.salon.cattocdi.R;
import com.salon.cattocdi.models.Comment;
import com.salon.cattocdi.utils.MyContants;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private List<Comment> reviews;

    public CommentAdapter(List<Comment> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_card_view, viewGroup, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int i) {
        Comment comment = reviews.get(i);
        holder.rbRating.setRating(comment.getRating());
        holder.tvName.setText(comment.getCustomerName());
        holder.tvContent.setText(comment.getContent());
        if(comment.getDate() != null){
            holder.tvDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(comment.getDate()));
        }
    }

    @Override
    public int getItemCount() {
        return reviews != null ? reviews.size() : 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        public CardView item;
        public TextView tvName, tvContent, tvDate;
        public RatingBar rbRating;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            this.item = (CardView) itemView;
            this.tvName = itemView.findViewById(R.id.comment_name);
            this.tvContent = itemView.findViewById(R.id.comment_content);
            this.tvDate = itemView.findViewById(R.id.comment_date);
            this.rbRating = itemView.findViewById(R.id.comment_rb);
        }
    }
}
