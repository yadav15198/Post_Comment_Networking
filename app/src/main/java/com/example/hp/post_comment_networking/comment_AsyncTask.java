package com.example.hp.post_comment_networking;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class comment_AsyncTask extends AsyncTask<String,Void,ArrayList<comments>> {
         commentDownloadListener commentDownloadListener;

    public comment_AsyncTask(com.example.hp.post_comment_networking.commentDownloadListener commentDownloadListener) {
        this.commentDownloadListener = commentDownloadListener;
    }

    @Override
    protected ArrayList<comments> doInBackground(String... strings) {
        ArrayList<comments> comment = new ArrayList<>();
        String urlString = strings[0];
        try {
            URL url = new URL(urlString);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.connect();
            urlConnection.setRequestMethod("get");
            InputStream inputStream = urlConnection.getInputStream();
            String result = "";
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()){
                result+=scanner.next();
            }
            JSONArray commentArray = new JSONArray(result);
            for(int i =0;i<commentArray.length();i++){
                JSONObject commentObject = commentArray.getJSONObject(i);
                int postid = commentObject.getInt("postId");
                int id = commentObject.getInt("id");
                String name = commentObject.getString("name");
                String email = commentObject.getString(" email");
                String body = commentObject.getString("body");
                comments comments_content = new comments(postid,id,name,email,body);
                comment.add(comments_content);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return comment;
    }

    @Override
    protected void onPostExecute(ArrayList<comments> comments) {
        super.onPostExecute(comments);
        commentDownloadListener.ondownload(comments);
    }
}
