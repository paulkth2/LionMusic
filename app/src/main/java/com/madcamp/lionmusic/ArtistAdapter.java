package com.madcamp.lionmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class ArtistAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ArtistItem> artists;
    private int layout;
    private boolean liked;

    private final Context mContext;

    public ArtistAdapter(Context context, int layout, ArrayList<ArtistItem> artists, boolean liked) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.artists = artists;
        this.layout = layout;
        this.mContext = context;
        this.liked = liked;
    }

    @Override
    public int getCount() {
        return artists.size();
    }

    @Override
    public String getItem(int position) {
        return artists.get(position).getArtist();
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
        ArtistItem artistitem = artists.get(position);

        TextView artist = (TextView) convertView.findViewById(R.id.artistName);
        artist.setText(artistitem.getArtist());
        ToggleButton likedButton = (ToggleButton) convertView.findViewById(R.id.likeButton);
        likedButton.setChecked(liked);
        return convertView;
    }
}
