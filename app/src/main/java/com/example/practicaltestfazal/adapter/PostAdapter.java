package com.example.practicaltestfazal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaltestfazal.R;
import com.example.practicaltestfazal.adapter.interfaces.RecyclerViewClickListener;
import com.example.practicaltestfazal.model.PostModel;

import java.util.ArrayList;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private ArrayList<PostModel> posts;
    private RecyclerViewClickListener listener;

    public PostAdapter(ArrayList<PostModel> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostModel post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setItemClick(RecyclerViewClickListener mListener) {
        this.listener = mListener;
    }

    public  class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView idTextView;
        private TextView titleTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.tvID);
            titleTextView = itemView.findViewById(R.id.tvTitle);
            CardView lylClick = itemView.findViewById(R.id.lylClick);
            lylClick.setOnClickListener(view -> listener.onItemClick(view,getAdapterPosition()));
        }

        public void bind(PostModel post) {
            idTextView.setText(String.valueOf(post.getId()));
            titleTextView.setText(post.getTitle());
        }
    }
}

