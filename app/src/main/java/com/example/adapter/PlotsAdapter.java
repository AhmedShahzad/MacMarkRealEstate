package com.example.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.bumptech.glide.Glide;
import com.example.item.Category;
import com.example.item.Plots;
import com.example.realestate.BuildConfig;
import com.example.realestate.R;
import com.example.realestate.databinding.RowCatBinding;
import com.example.util.AdInterstitialAds;
import com.example.util.Constant;
import com.example.util.Method;
import com.example.util.OnClick;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.startapp.sdk.ads.nativead.NativeAdDetails;
import com.startapp.sdk.ads.nativead.NativeAdPreferences;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.wortise.ads.natives.GoogleNativeAd;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class PlotsAdapter extends ArrayAdapter<Plots>
{
    ArrayList<Plots> plots;
    Context context;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=LayoutInflater.from(context).inflate(R.layout.plotslayout,null
        );
        TextView title,desc;
        title=view.findViewById(R.id.tvCategoryTitle);
        desc=view.findViewById(R.id.tvdesc);
        title.setText(plots.get(position).getTitle());
        if(plots.get(position).getTitle().equals("Popular")||plots.get(position).getTitle().equals("Location")||
                plots.get(position).getTitle().equals("Type"))
        {

            desc.setVisibility(View.GONE);
        }
        else
        {
desc.setVisibility(View.VISIBLE);
            desc.setText(plots.get(position).getDesc());
        }
        desc.setText(plots.get(position).getDesc());
        return view;
    }

    public PlotsAdapter(@NonNull Context context, ArrayList<Plots> plots) {
        super(context, R.layout.plotslayout,plots);
        this.context=context;
        this.plots=plots;
    }
}
