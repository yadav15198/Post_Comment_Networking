package com.example.hp.post_comment_networking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class User_DetailActivity extends AppCompatActivity {
     commentAdapter adapter;
     ArrayList<comments> commentsArrayList=new ArrayList<>();
//     ArrayList<comments> arrayList=new ArrayList<>();
//     comments comments_object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__detail);
        adapter =new commentAdapter(this,commentsArrayList);
        final ListView listView = findViewById(R.id.list_item);
        final ProgressBar progressBar = findViewById(R.id.progressbar);
        listView.setAdapter(adapter);
//        comment_AsyncTask task = new comment_AsyncTask(new commentDownloadListener() {
//            @Override
//            public void ondownload(ArrayList<comments> comment) {
//            commentsArrayList.addAll(comment);
//             adapter.notifyDataSetChanged();
//
//            }
//        });
 //       task.execute("https://jsonplaceholder.typicode.com/posts/"+getIntent().getIntExtra("id",0)+"/comments");
        int a=  getIntent().getIntExtra("id",0);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/posts/"+a+"/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        comment_service service = retrofit.create(comment_service.class);
        Call<ArrayList<comments>>call = service.getcomments();
        call.enqueue(new Callback<ArrayList<comments>>() {
            @Override
            public void onResponse(Call<ArrayList<comments>> call, Response<ArrayList<comments>> response) {
                commentsArrayList.clear();
                 commentsArrayList.addAll(response.body());

                adapter.notifyDataSetChanged();
                listView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onFailure(Call<ArrayList<comments>> call, Throwable t) {

            }
        });

    }
}
