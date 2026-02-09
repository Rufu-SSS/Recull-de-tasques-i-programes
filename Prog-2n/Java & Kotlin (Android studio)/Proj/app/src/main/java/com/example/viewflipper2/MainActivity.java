package com.example.viewflipper2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ViewFlipper carregarImg;
    String[] imgUrl ={
            "https://cards.scryfall.io/large/front/8/6/86b45e3e-8460-4678-87d1-d74479936c83.jpg?1710673445",
            "https://cards.scryfall.io/large/front/2/3/23eb3cf7-c90d-4bfa-b125-4fbcb5614468.jpg?1710673416",
            "https://cards.scryfall.io/large/front/2/d/2da11337-ceb2-4744-9696-c06fec2f2daa.jpg?1710673427",
            "https://cards.scryfall.io/large/front/5/b/5b0497da-670a-405c-a39c-97cae7942836.jpg?1710673422",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        carregarImg=findViewById(R.id.carregarImg);
        carregarImg.setFlipInterval(3000);
        carregarImg.startFlipping();
    }

    private void loadIMG(){
        for(String url : imgUrl){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.get()
                    .load(url)
                    .into(imageView);
            carregarImg.addView(imageView);
        }
    }

}