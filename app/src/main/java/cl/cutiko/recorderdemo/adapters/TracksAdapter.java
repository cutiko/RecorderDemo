package cl.cutiko.recorderdemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cl.cutiko.recorderdemo.data.Queries;
import cl.cutiko.recorderdemo.models.Track;

/**
 * Created by cutiko on 26-06-17.
 */

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.ViewHolder> {

    private List<Track> tracks = new Queries().tracks();
    private TracksListener listener;

    public TracksAdapter(TracksListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        Track track = tracks.get(position);
        textView.setText(track.getName());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Track auxTrack = tracks.get(holder.getAdapterPosition());
                listener.clicked(auxTrack.getPath());
            }
        });
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public void addTrack(Track track) {
        tracks.add(track);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
