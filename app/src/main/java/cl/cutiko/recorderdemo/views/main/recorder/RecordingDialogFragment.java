package cl.cutiko.recorderdemo.views.main.recorder;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import cl.cutiko.recorderdemo.R;
import cl.cutiko.recorderdemo.models.Track;
import cl.cutiko.recorderdemo.views.main.RecordCoordinator;

/**
 * Created by cutiko on 26-06-17.
 */

public class RecordingDialogFragment extends DialogFragment implements RecordingCallback, Runnable {

    private static final long ROUNDING_TIME = 800;
    private MediaRecorder mediaRecorder;
    private String fileName;
    private RecordCoordinator coordinator;

    public static RecordingDialogFragment newInstance() {
        return new RecordingDialogFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        coordinator = (RecordCoordinator) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recording_dialogfragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = (Button) view.findViewById(R.id.recorBtn);
        final TextView textView = (TextView) view.findViewById(R.id.recordingTv);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Saving...");
                new Handler().postDelayed(RecordingDialogFragment.this, ROUNDING_TIME);
            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        setCancelable(false);
        getDialog().getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        startRecording();
    }


    private void startRecording(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        fileName = getActivity().getExternalCacheDir().getAbsolutePath() + "/" +  String.valueOf(System.currentTimeMillis()) + ".3gp";
        //EXAMPLE /storage/emulated/0/Android/data/cl.cutiko.recorderdemo/cache/1498521161863.3gp
        Log.d("FILE", fileName);
        mediaRecorder.setOutputFile(fileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            Toast.makeText(getContext(), "Unexpected error please try again", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }


    @Override
    public void done(Track track) {
        coordinator.addTrack(track);
        dismiss();
    }

    @Override
    public void run() {
        new CreateTrack(RecordingDialogFragment.this).saveToDb(fileName);
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }
}
