package com.example.hp.post_comment_networking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class commentAdapter extends ArrayAdapter {
     ArrayList<comments> commentsArrayList ;
     LayoutInflater inflater;

    public commentAdapter(@NonNull Context context, ArrayList<comments> comments) {
        super(context,0,comments);
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.commentsArrayList = comments;
    }


    @Override
    public long getItemId(int position) {
        return commentsArrayList.get(position).getId();
    }

    @Override
    public int getCount() {
        return commentsArrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View output = convertView;
       if(output == null){
        output = inflater.inflate(R.layout.comment_layout,parent,false);
           TextView postid = output.findViewById(R.id.comment_textview1);
           TextView id = output.findViewById(R.id.comment_textview2);
           TextView name = output.findViewById(R.id.comment_textview3);
           TextView email = output.findViewById(R.id.comment_textview4);
           TextView body = output.findViewById(R.id.comment_textview5);
           commentViewHolder holder = new commentViewHolder();
           holder.postId = postid;
           holder.id = id;
           holder.name = name;
           holder.email = email;
           holder.body = body;
           output.setTag(holder);
       }
          commentViewHolder holder = (commentViewHolder) output.getTag();
          comments comments1 =commentsArrayList.get(position);
          holder.postId.setText(""+comments1.postId);
          holder.id.setText(""+comments1.id);
          holder.name.setText(comments1.getName());
          holder.email.setText(comments1.getEmail());
          holder.body.setText(comments1.getBody());
        return output;
    }
}
