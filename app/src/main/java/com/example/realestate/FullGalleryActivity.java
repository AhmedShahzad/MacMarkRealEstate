package com.example.realestate;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.realestate.databinding.ActivityFullGalleryBinding;
import com.example.util.BannerAds;
import com.example.util.Method;
import com.example.util.StatusBar;


public class FullGalleryActivity extends AppCompatActivity {

    ActivityFullGalleryBinding bindingGallery;
    Method method;
    String imageId, imageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.initLatestScreen(FullGalleryActivity.this);

        bindingGallery = ActivityFullGalleryBinding.inflate(getLayoutInflater());
        setContentView(bindingGallery.getRoot());

        method = new Method(FullGalleryActivity.this);
        method.forceRTLIfSupported();

        Intent intent = getIntent();
        imageId = intent.getStringExtra("imageId");
        imageType = intent.getStringExtra("imageType");

        if (imageType.equals("Floor")) {
            bindingGallery.toolbarMain.tvName.setText(getString(R.string.floor_plan));
        } else {
            bindingGallery.toolbarMain.tvName.setText(getString(R.string.property_gallery));
        }


        bindingGallery.toolbarMain.fabBack.setOnClickListener(v -> onBackPressed());

        if (!imageId.equals("")) {
            Glide.with(FullGalleryActivity.this).load(imageId)
                    .placeholder(R.drawable.property_placeholder)
                    .into(bindingGallery.ivFullGallery);
        }
        BannerAds.showBannerAds(FullGalleryActivity.this, bindingGallery.layoutAds);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}