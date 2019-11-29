package com.example.chatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chatapp.Data.User;
import com.example.chatapp.R;

import java.util.ArrayList;

public class FriendAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<User> mItems = new ArrayList<>();


    public FriendAdapter(Context context,ArrayList<User> mItems) {
        this.mItems = mItems;
        this.inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public User getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_friend, parent, false);
        }

        TextView userName = (TextView) convertView.findViewById(R.id.username_textView);
        User friend = getItem(position);
        userName.setText(friend.getUserName());

        return convertView;
    }
}
