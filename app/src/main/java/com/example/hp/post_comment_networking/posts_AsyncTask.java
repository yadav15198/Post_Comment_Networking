package com.example.hp.post_comment_networking;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class posts_AsyncTask extends AsyncTask<String,Void,ArrayList<Posts>> {
      PostDownloadListener postDownloadListener;

    public posts_AsyncTask(PostDownloadListener postDownloadListener) {
        this.postDownloadListener = postDownloadListener;
    }

    @Override
    protected ArrayList<Posts> doInBackground(String... strings) {
        ArrayList<Posts> posts = new ArrayList<>();
          String urlstring = strings[0];
        try {
            URL url = new URL(urlstring);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner =new Scanner(inputStream);
            String result = "";
            while(scanner.hasNext()){
                result+=scanner.next();
            }
            Gson gson = new Gson();
            posts = gson.fromJson(result,new TypeToken<List<Posts>>(){}.getType());
//            for(int i = 0; i< post.size();i++){
//                JSONObject postsobject = post.getJSONObject(i);
//                int id = postsobject.getInt("id");
//                int userid = postsobject.getInt("userId");
//                String title = postsobject.getString("title");
//                String body = postsobject.getString("body");
//                Posts posts1 = new Posts(id,userid,title,body);
//                posts.add(posts1);
//
//            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }

        return posts;
    }

    @Override
    protected void onPostExecute(ArrayList<Posts> posts) {
        super.onPostExecute(posts);
       postDownloadListener.ondownload(posts);
    }
}
