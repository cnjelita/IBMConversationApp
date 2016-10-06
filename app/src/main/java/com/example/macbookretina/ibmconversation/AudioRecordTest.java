package com.example.macbookretina.ibmconversation;
import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Environment;

/**
 * @author <a href="http://www.benmccann.com">Ben McCann</a>
 */
public class AudioRecordTest {

    private MediaRecorder recorder = new MediaRecorder();
    final String path;

    /**
     * Creates a new audio recording at the given path (relative to root of SD card).
     */
    public AudioRecordTest(String path) {
        this.path = path;
    }

    /**
     * Starts a new recording.
     */
    public void start() throws IOException {
        File output = new File(path);
        if (output != null) {
            output.delete();
        }

        String state = android.os.Environment.getExternalStorageState();
        if(!state.equals(android.os.Environment.MEDIA_MOUNTED))  {
            throw new IOException("SD Card is not mounted.  It is " + state + ".");
        }

        // make sure the directory we plan to store the recording in exists
        File directory = new File(path).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Path to file could not be created.");
        }

        recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(path);
        recorder.prepare();
        recorder.start();
    }

    /**
     * Stops a recording that has been previously started.
     */
    public void stop() throws IOException {
        recorder.stop();
        recorder.release();
    }

}