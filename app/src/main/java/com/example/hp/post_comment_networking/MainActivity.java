package com.example.hp.post_comment_networking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
   ArrayList<Posts> posts1;
   ArrayList<String> titles;
   ArrayAdapter adapter;
   ProgressBar progressBar;
   ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_item);
        progressBar = findViewById(R.id.progressbar);
        posts1 = new ArrayList<>();
        titles = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,titles);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

//       posts_AsyncTask post = new posts_AsyncTask(new PostDownloadListener() {
//           @Override
//           public void ondownload(ArrayList<Posts> post) {
//            posts.addAll(post);
//            for(Posts items: posts){
//                titles.add(items.getTitle());
//            }
//            adapter.notifyDataSetChanged();
//           }
//       });
//        post.execute("https://jsonplaceholder.typicode.com/posts/");
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        postService service = retrofit.create(postService.class);
        Call<ArrayList<Posts>>call = service.getPosts();
        call.enqueue(new Callback<ArrayList<Posts>>() {
            @Override
            public void onResponse(Call<ArrayList<Posts>> call, Response<ArrayList<Posts>> response) {
                posts1 = response.body();
                titles.clear();
                for(int i =0;i<posts1.size();i++){
                    titles.add(posts1.get(i).getTitle());
                }
                adapter.notifyDataSetChanged();
                listView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<Posts>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Posts post = posts1.get(i);
        int id = post.getId();
        int userid = post.getUserID();
        String postTitle = post.getTitle();
        String postbody = post.getBody();
        Intent intent1 = new Intent(this,User_DetailActivity.class);
        intent1.putExtra("id",id);
        intent1.putExtra("userid",userid);
        intent1.putExtra("title",postTitle);
        intent1.putExtra("body",postbody);
        startActivity(intent1);
    }
}
