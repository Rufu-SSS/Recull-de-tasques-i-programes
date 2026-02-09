package com.example.pph_sliderimg;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private SeekBar seekBar;
    private TextView textView;
    private Button btnPlay, btnPause;
    private Handler handler;
    private Runnable runnable;
    private boolean isPlaying = false;
    private int delayMillis = 2000;

    private String[] imageUrls = {
            "https://assets.moxfield.net/cards/card-zaVb4-normal.webp?267504238",
            "https://assets.moxfield.net/cards/card-yj60N-normal.webp?267503082",
            "https://assets.moxfield.net/cards/card-w7Bo9-normal.webp?267000758",
            "https://assets.moxfield.net/cards/card-vrp48-normal.webp?266431428",
            "https://assets.moxfield.net/cards/card-vr1KZ-normal.webp?267493545",
            "https://assets.moxfield.net/cards/card-vr0WV-normal.webp?263300533",
            "https://assets.moxfield.net/cards/card-r8Gmb-normal.webp?267535879",
            "https://assets.moxfield.net/cards/card-q4gQz-normal.webp?262922646",
            "https://assets.moxfield.net/cards/card-pRppj-normal.webp?266723158",
            "https://assets.moxfield.net/cards/card-pDzA1-normal.webp?267503843",
            "https://assets.moxfield.net/cards/card-oWz9J-normal.webp?267114565",
            "https://assets.moxfield.net/cards/card-nawwA-normal.webp?267596064",
            "https://assets.moxfield.net/cards/card-naVeR-normal.webp?231976979",
            "https://assets.moxfield.net/cards/card-mOjW0-normal.webp?267503837",
            "https://assets.moxfield.net/cards/card-lo4Ml-normal.webp?267494339",
            "https://assets.moxfield.net/cards/card-kyOwo-normal.webp?267523168",
            "https://assets.moxfield.net/cards/card-kqK03-normal.webp?267518288",
            "https://assets.moxfield.net/cards/card-kq1Rx-normal.webp?262567390",
            "https://assets.moxfield.net/cards/card-kp2JO-normal.webp?266444630",
            "https://assets.moxfield.net/cards/card-kXKJM-normal.webp?267588109",
            "https://assets.moxfield.net/cards/card-kObDw-normal.webp?266438178",
            "https://assets.moxfield.net/cards/card-kAz1x-normal.webp?267593116",
            "https://assets.moxfield.net/cards/card-k9vWa-normal.webp?267474173",
            "https://assets.moxfield.net/cards/card-g39X9-normal.webp?267511880",
            "https://assets.moxfield.net/cards/card-g3Pyx-normal.webp?263306750",
            "https://assets.moxfield.net/cards/card-dogKG-normal.webp?267504255",
            "https://assets.moxfield.net/cards/card-dXmee-normal.webp?267601603",
            "https://assets.moxfield.net/cards/card-bRrV7-normal.webp?262797451",
            "https://assets.moxfield.net/cards/card-bRGZa-normal.webp?267016869",
            "https://assets.moxfield.net/cards/card-bRAP7-normal.webp?234501550",
            "https://assets.moxfield.net/cards/card-a2R02-normal.webp?267322742",
            "https://assets.moxfield.net/cards/card-YxaVg-normal.webp?264409242",
            "https://assets.moxfield.net/cards/card-YxX9Q-normal.webp?267030003",
            "https://assets.moxfield.net/cards/card-Yx4v9-normal.webp?264659984",
            "https://assets.moxfield.net/cards/card-YjW1Z-normal.webp?263618727",
            "https://assets.moxfield.net/cards/card-YjBWl-normal.webp?267017706",
            "https://assets.moxfield.net/cards/card-YeJR1-normal.webp?267594163",
            "https://assets.moxfield.net/cards/card-Ye352-normal.webp?262847203",
            "https://assets.moxfield.net/cards/card-Ydraa-normal.webp?267599283",
            "https://assets.moxfield.net/cards/card-Yd8g8-normal.webp?267498533",
            "https://assets.moxfield.net/cards/card-YNjmJ-normal.webp?267538182",
            "https://assets.moxfield.net/cards/card-YM5bd-normal.webp?203530427",
            "https://assets.moxfield.net/cards/card-Y813A-normal.webp?266781121",
            "https://assets.moxfield.net/cards/card-XJbjG-normal.webp?267503940",
            "https://assets.moxfield.net/cards/card-WarJ1-normal.webp?266977174",
            "https://assets.moxfield.net/cards/card-V2vdl-normal.webp?267520517",
            "https://assets.moxfield.net/cards/card-V2jgB-normal.webp?263306734",
            "https://assets.moxfield.net/cards/card-R9GQl-normal.webp?267496734",
            "https://assets.moxfield.net/cards/card-O9ram-normal.webp?267546906",
            "https://assets.moxfield.net/cards/card-O9ogX-normal.webp?267043844",
            "https://assets.moxfield.net/cards/card-N9r4M-normal.webp?265622524",
            "https://assets.moxfield.net/cards/card-LzRn6-normal.webp?267413988",
            "https://assets.moxfield.net/cards/card-LzGvN-normal.webp?266756317",
            "https://assets.moxfield.net/cards/card-LwDod-normal.webp?262921789",
            "https://assets.moxfield.net/cards/card-LVqoK-normal.webp?266456935",
            "https://assets.moxfield.net/cards/card-LVaV8-normal.webp?267591738",
            "https://assets.moxfield.net/cards/card-LV6xR-normal.webp?266463832",
            "https://assets.moxfield.net/cards/card-LRdXl-normal.webp?267088956",
            "https://assets.moxfield.net/cards/card-LDlAM-normal.webp?218141027",
            "https://assets.moxfield.net/cards/card-LDKnb-normal.webp?267323592",
            "https://assets.moxfield.net/cards/card-LD68N-normal.webp?265707080",
            "https://assets.moxfield.net/cards/card-L49NR-normal.webp?266769695",
            "https://assets.moxfield.net/cards/card-L4mMP-normal.webp?267537483",
            "https://assets.moxfield.net/cards/card-J99n4-normal.webp?267516910",
            "https://assets.moxfield.net/cards/card-J9Jqm-normal.webp?264633663",
            "https://assets.moxfield.net/cards/card-J7dNg-normal.webp?267597514",
            "https://assets.moxfield.net/cards/card-J7WoN-normal.webp?262855425",
            "https://assets.moxfield.net/cards/card-G4pBZ-normal.webp?263278305",
            "https://assets.moxfield.net/cards/card-G4O45-normal.webp?263643504",
            "https://assets.moxfield.net/cards/card-Eg526-normal.webp?261840757",
            "https://assets.moxfield.net/cards/card-Eb2Oa-normal.webp?203545557",
            "https://assets.moxfield.net/cards/card-EZXWV-normal.webp?249607456",
            "https://assets.moxfield.net/cards/card-EWzPG-normal.webp?203571311",
            "https://assets.moxfield.net/cards/card-EQw3A-normal.webp?249377981",
            "https://assets.moxfield.net/cards/card-EQoNA-normal.webp?267603435",
            "https://assets.moxfield.net/cards/card-EQly2-normal.webp?263615110",
            "https://assets.moxfield.net/cards/card-EQ8l5-normal.webp?267013254",
            "https://assets.moxfield.net/cards/card-DjXRX-normal.webp?267566313",
            "https://assets.moxfield.net/cards/card-DjN7M-normal.webp?267532691",
            "https://assets.moxfield.net/cards/card-Dj9oN-normal.webp?267506592",
            "https://assets.moxfield.net/cards/card-AWoDR-normal.webp?266976678",
            "https://assets.moxfield.net/cards/card-508ra-normal.webp?264907346",
            "https://assets.moxfield.net/cards/card-43NvR-normal.webp?267541547",
            "https://assets.moxfield.net/cards/card-9mKPa-normal.webp?267493110",
            "https://assets.moxfield.net/cards/card-3Gbj3-normal.webp?267000804",
            "https://assets.moxfield.net/cards/card-1v2AB-normal.webp?267586264"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFlipper = findViewById(R.id.carregarImg);
        seekBar = findViewById(R.id.imageSlider);
        textView = findViewById(R.id.textView);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);

        carregarImatges();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewFlipper.setDisplayedChild(progress);
                textView.setText("Imatge: " + (progress + 1) + " / " + imageUrls.length);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pauseAutoPlay();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAutoPlay();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAutoPlay();
            }
        });
    }

    private void startAutoPlay() {
        if (isPlaying) return;

        isPlaying = true;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isPlaying) {
                    int currentPosition = seekBar.getProgress();
                    int nextPosition = (currentPosition + 1) % imageUrls.length;

                    seekBar.setProgress(nextPosition);

                    handler.postDelayed(this, delayMillis);
                }
            }
        };
        handler.postDelayed(runnable, delayMillis);
    }

    private void pauseAutoPlay() {
        isPlaying = false;
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pauseAutoPlay();
    }

    private void carregarImatges() {
        for (String imageUrl : imageUrls) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setLayoutParams(new ViewFlipper.LayoutParams(
                    ViewFlipper.LayoutParams.MATCH_PARENT,
                    ViewFlipper.LayoutParams.MATCH_PARENT
            ));

            // Carregar la imatge amb Picasso
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(android.R.drawable.ic_menu_gallery) // Imatge mentre carrega
                    .error(android.R.drawable.ic_menu_report_image) // Imatge si hi ha error
                    .into(imageView);

            viewFlipper.addView(imageView);
        }
    }
}
