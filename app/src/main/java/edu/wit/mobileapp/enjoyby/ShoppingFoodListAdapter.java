package edu.wit.mobileapp.enjoyby;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ShoppingFoodListAdapter extends ArrayAdapter<Comment> {

    public ShoppingFoodListAdapter(Context context, List<Comment> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shopping_list_item,parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }

        Comment tweet = getItem(position);
        if(tweet.getImportant().equals("y")) {
            viewHolder.text.setTextColor(Color.parseColor("#FFFFFF"));
        }

        if(tweet.getImportant().equals("n")) {
            viewHolder.text.setTextColor(Color.parseColor("blue"));
        }

        viewHolder.pseudo.setText(tweet.getPseudo());
        viewHolder.text.setText(tweet.getText());

        return convertView;
    }

    private class TweetViewHolder{
        public TextView pseudo;
        public TextView text;

    }
}

class Comment {
    private int color;
    private String pseudo;
    private String text;
    private String important;


    public Comment(int color, String pseudo, String text, String important) {
        this.color = color;
        this.pseudo = pseudo;
        this.text = text;
        this.important = important;
    }

    public String getPseudo() {
        return this.pseudo;
    }
    public String getText() {
        return this.text;
    }


    public int getColor() {
        return this.color;
    }
    public String getImportant() { return this.important; }
}



