package com.madcamp.lionmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<FriendItem> friends;
    private int layout;

    private final Context mContext;

    public FriendAdapter(Context context, int layout, ArrayList<FriendItem> friends) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.friends = friends;
        this.layout = layout;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public String getItem(int position) {
        return friends.get(position).getFriendEmail();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }
        FriendItem frienditem = friends.get(position);

        TextView friendEmail = (TextView) convertView.findViewById(R.id.friendEmailText);
        friendEmail.setText(frienditem.getFriendEmail());
        TextView friendName = (TextView) convertView.findViewById(R.id.friendNicknameText);
        friendName.setText(frienditem.getFriendNickname());
        return convertView;
    }
}
