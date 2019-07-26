package com.learning.topdownloadedapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FeedAdapter extends ArrayAdapter {
    private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<Entry> songs;

    public FeedAdapter(Context context, int resource, List<Entry> songs) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.songs = songs;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Entry currentSong = songs.get(position);

        viewHolder.tvName.setText(currentSong.getName());
        viewHolder.tvAlbum.setText(currentSong.getAlbum());
        viewHolder.tvArtist.setText(currentSong.getArtist());
        viewHolder.tvGenre.setText(currentSong.getGenre());

        return convertView;
    }

    private class ViewHolder {
        final TextView tvName;
        final TextView tvAlbum;
        final TextView tvArtist;
        final TextView tvGenre;

        ViewHolder (View v) {
            this.tvName = v.findViewById(R.id.tvName);
            this.tvAlbum = v.findViewById(R.id.tvAlbum);
            this.tvArtist = v.findViewById(R.id.tvArtist);
            this.tvGenre = v.findViewById(R.id.tvGenre);
        }
    }
}