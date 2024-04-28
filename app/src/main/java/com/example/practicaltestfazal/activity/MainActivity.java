package com.example.practicaltestfazal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.practicaltestfazal.R;
import com.example.practicaltestfazal.adapter.PostAdapter;
import com.example.practicaltestfazal.adapter.interfaces.RecyclerViewClickListener;
import com.example.practicaltestfazal.api.APIClient;
import com.example.practicaltestfazal.api.APIService;
import com.example.practicaltestfazal.model.PostModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {

    private int currentPage = 1;
    private final ArrayList<PostModel> posts = new ArrayList<>();
    private PostAdapter adapter;
    private boolean isLoading = false;
    private ProgressBar progressBar;
    private int totalPages = 10; // Set the total number of pages

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        adapter = new PostAdapter(posts);
        recyclerView.setAdapter(adapter);
        adapter.setItemClick(this);

        // Setup RecyclerView scroll listener for pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loadMoreItems();
                }
            }
        });

        // Initial data loading
        loadMoreItems();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void loadMoreItems() {
        if (isNetworkConnected() && !isLoading && currentPage <= totalPages) {
            showProgressBar();
            isLoading = true;
            APIService apiService = APIClient.getClient().create(APIService.class);
            // Adjust as needed
            int pageSize = 10;
            Call<ArrayList<PostModel>> call = apiService.getPosts(currentPage, pageSize);
            call.enqueue(new Callback<ArrayList<PostModel>>() {
                @Override
                public void onResponse(@NotNull Call<ArrayList<PostModel>> call, @NotNull Response<ArrayList<PostModel>> response) {
                    hideProgressBar();
                    if (response.isSuccessful()) {
                        ArrayList<PostModel> newPosts = response.body();
                        if (newPosts != null && newPosts.size() > 0) {
                            if (currentPage == 1) {
                                posts.clear();
                            }
                            posts.addAll(newPosts.subList(posts.size(), Math.min(posts.size() + pageSize, newPosts.size())));
                            adapter.notifyDataSetChanged();
                            currentPage++;

                            System.out.println(posts.size());
                        } else {
                            Toast.makeText(MainActivity.this, "No more data", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                    isLoading = false;
                }

                @Override
                public void onFailure(@NotNull Call<ArrayList<PostModel>> call, @NotNull Throwable t) {
                    hideProgressBar();
                    Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    isLoading = false;
                }
            });
        } else if (currentPage > totalPages) {
            Toast.makeText(MainActivity.this, "No more data available", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Internet...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        try {
            Intent intent = new Intent(MainActivity.this,SubActivity.class);
            intent.putExtra("ID",posts.get(position).getId());
            intent.putExtra("Title",posts.get(position).getTitle());
            intent.putExtra("Body",posts.get(position).getBody());
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}