package cl.cutiko.recorderdemo.views.main.tracks;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.cutiko.recorderdemo.R;
import cl.cutiko.recorderdemo.adapters.TracksAdapter;
import cl.cutiko.recorderdemo.adapters.TracksListener;
import cl.cutiko.recorderdemo.models.Track;
import cl.cutiko.recorderdemo.services.PlayerService;

/**
 * A placeholder fragment containing a simple view.
 */
public class TracksFragment extends Fragment implements TracksListener {

    private TracksAdapter tracksAdapter;
    private ServiceConnection serviceConnection;
    private PlayerService playerService;
    private boolean isBind = false;

    public TracksFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Intent intent = new Intent(getContext(), PlayerService.class);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                PlayerService.LocalBinder binder = (PlayerService.LocalBinder) iBinder;
                playerService = binder.getService();
                isBind = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tracks, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        tracksAdapter = new TracksAdapter(this);

        recyclerView.setAdapter(tracksAdapter);
    }


    public void addTrack(Track track) {
        tracksAdapter.addTrack(track);
    }

    @Override
    public void clicked(String path) {
        playerService.play(path);
    }
}
