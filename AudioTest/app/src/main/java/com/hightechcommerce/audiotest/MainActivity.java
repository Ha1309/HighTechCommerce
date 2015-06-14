package com.hightechcommerce.audiotest;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    int m_samplerate = 11025;
    int m_numberOfBytes = AudioRecord.getMinBufferSize(11025, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
    AudioRecord m_recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, m_samplerate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, m_numberOfBytes);
    AudioTrack m_audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 11025, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, m_numberOfBytes, AudioTrack.MODE_STREAM);
    boolean m_isRecording = false;
    Thread recordingThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void RecordAndPlay() {
        Log.d("Status Audio:", String.valueOf(m_recorder.getState()));
        if (m_recorder.getState() == AudioRecord.STATE_INITIALIZED) {
            recordingThread = new Thread(new Runnable() {
                public void run() {
                    short[] data = new short[m_numberOfBytes / 2];

                    m_isRecording = true;
                    int result;
                    m_recorder.startRecording();
                    m_audioTrack.play();
                    while (m_isRecording) {
                        result = m_recorder.read(data, 0, m_numberOfBytes / 2);

                        if (result > 0) {

                            for (int i = 0; i < data.length; i++) {
                                Log.d("AudioData:", String.valueOf(data[i]));
                            }
                            break;
                        } else if (result == AudioRecord.ERROR_INVALID_OPERATION) {
                            Log.d("Recording", "Invalid operation error");
                            break;
                        } else if (result == AudioRecord.ERROR_BAD_VALUE) {
                            Log.d("Recording", "Bad value error");
                            break;
                        } else if (result == AudioRecord.ERROR) {
                            Log.d("Recording", "Unknown error");
                            break;
                        }

                    }
                }
            }, "AudioRecorder Thread");
            recordingThread.start();
        }
    }
}
