package tjk.programs.richestmaninbabylon.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import tjk.programs.richestmaninbabylon.ClickListener;
import tjk.programs.richestmaninbabylon.MainActivity;
import tjk.programs.richestmaninbabylon.R;
import tjk.programs.richestmaninbabylon.adapter.Adapter;
import tjk.programs.richestmaninbabylon.model.Root;

public class ContentFragment extends Fragment implements ClickListener, Runnable {
    public int position = 0;
    Context context;
    ImageView btnClose, btnPlayPause, btnNextSong, btnRepeat, btnPreviousSong, btnPlaySpeed;
    RecyclerView recyclerView;
    FragmentManager fragmentManager;


    Thread t;

    int currentPositionSeekBar = 0;
    ConstraintLayout media_player;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;

    View rootView;

    int Speed = 1;
    private TextView nameSong, txtStart, txtEnd;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_content, container, false);
        context = getContext();
        recyclerView = rootView.findViewById(R.id.recyclerView);
        nameSong = rootView.findViewById(R.id.nameSong);
        media_player = rootView.findViewById(R.id.media_player);
        btnPlayPause = rootView.findViewById(R.id.btnPlayPause);
        seekBar = rootView.findViewById(R.id.progressBar);
        btnRepeat = rootView.findViewById(R.id.btnRepeat);
        btnClose = rootView.findViewById(R.id.btnClose);
        btnPlaySpeed = rootView.findViewById(R.id.btnPlaySpeed);
        btnNextSong = rootView.findViewById(R.id.btnNextSong);
        btnPreviousSong = rootView.findViewById(R.id.btnPreviousSong);
        txtStart = rootView.findViewById(R.id.txtStart);
        txtEnd = rootView.findViewById(R.id.txtEnd);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new Adapter(context, MainActivity.items, this));
        media_player.setVisibility(View.GONE);
        btnPlaySpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {

                    if (Speed <= 3) {
                        Speed = Speed + 1;
                    } else {
                        Speed = 1;
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PlaybackParams playbackParams = new PlaybackParams();

                        playbackParams.setSpeed(Speed);
                        playbackParams.setPitch(1);
                        playbackParams.setAudioFallbackMode(PlaybackParams.AUDIO_FALLBACK_MODE_DEFAULT);
                        mediaPlayer.setPlaybackParams(playbackParams);
                    }

                }
            }
        });
        btnPreviousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = 0;
                position = ContentFragment.this.position - 1;
                if (isIndex(MainActivity.items, position)) {
                    ContentFragment.this.position = position;
                    clearMediaPlayer();
                    click(MainActivity.items.get(position), position);
                }
            }
        });
        btnNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = 0;
                position = ContentFragment.this.position + 1;
                if (isIndex(MainActivity.items, position)) {
                    ContentFragment.this.position = position;
                    clearMediaPlayer();
                    click(MainActivity.items.get(position), position);
                }

            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    Thread.interrupted();
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    media_player.setVisibility(View.GONE);
                }
            }
        });
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread.interrupted();
                clearMediaPlayer();
                click(MainActivity.items.get(position), position);

            }
        });
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        if (t != null) Thread.interrupted();
                        btnPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                    } else {
                        mediaPlayer.start();
                        t = new Thread(ContentFragment.this);
                        t.start();
                        btnPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));

                    }
                } else {
                    click(MainActivity.items.get(position), position);
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
//                seekBar.setVisibility(View.VISIBLE);
//                int x = (int) Math.ceil(progress / 1000f);

//                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
//                    clearMediaPlayer();
//                    btnPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
//                    seekBar.setProgress(0);
//                }


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                    if (t != null) Thread.interrupted();
                    t = new Thread(ContentFragment.this);
                    t.start();
                }
                currentPositionSeekBar = seekBar.getProgress();

            }
        });

        return rootView;
    }

    public boolean isIndex(ArrayList<Root> arrayList, int index) {
        return index < arrayList.size() && index >= 0;
    }

    private void clearMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        this.currentPositionSeekBar = 0;
        Speed = 1;
    }

    @Override
    public void click(Root root, int position) {
        this.position = position;
        if (root.getLink() != null && !root.getLink().equals("")) {

            media_player.setVisibility(View.VISIBLE);
            nameSong.setText(root.getTitle());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    } else {
                        if (mediaPlayer != null) {
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                        mediaPlayer = new MediaPlayer();
                    }

                    try {
                        mediaPlayer.setDataSource(root.getLink());
                        mediaPlayer.prepare();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // below line is use to prepare
                    // and start our media player.

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            seekBar.setMax(mediaPlayer.getDuration());
                            txtEnd.setText(formatDuration(mediaPlayer.getDuration()));
                            btnPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    Thread.interrupted();
                                    if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                                        btnPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                                        clearMediaPlayer();
                                        txtStart.setText("00:00");
                                    }
                                }

                            });

                        }
                    });
                    t = new Thread(ContentFragment.this);
                    t.start();


                }
            }).start();

            // below line is use to set the audio
            // stream type for our media player.


            // below line is use to set our
            // url to our media player.


        }
    }

    @Override
    public void run() {
        int currentPosition = 0;

        int total = mediaPlayer.getDuration();

        while (mediaPlayer != null && mediaPlayer.isPlaying() && currentPosition < total) {
            try {
                Thread.sleep(1000);
                if (this.currentPositionSeekBar != 0) {
                    currentPosition = this.currentPositionSeekBar;
                    this.currentPositionSeekBar = 0;
                    mediaPlayer.seekTo(currentPosition);
                } else {
                    currentPosition = mediaPlayer.getCurrentPosition();
                }
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }


            int finalCurrentPosition = currentPosition;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtStart.setText(formatDuration(finalCurrentPosition));
                    seekBar.setProgress(finalCurrentPosition);
                }
            });
        }
    }

    private String formatDuration(long duration) {
        long minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS);
        long seconds = TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) - minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES);

        return String.format("%02d:%02d", minutes, seconds);
    }
}