package com.example.realestate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.adapter.RecentAdapter;
import com.example.database.DatabaseHelperRecent;
import com.example.item.Property;
import com.example.realestate.databinding.ActivityAdvanceSearchBinding;
import com.example.util.BannerAds;
import com.example.util.Method;
import com.example.util.StatusBar;

import java.util.ArrayList;
import java.util.List;


public class RecentActivity extends AppCompatActivity {

    ActivityAdvanceSearchBinding bindingAdvSearch;
    Method method;
    List<Property> propertyListRecent;
    RecentAdapter recentAdapter;
    DatabaseHelperRecent databaseHelperRecent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.initLatestScreen(RecentActivity.this);

        bindingAdvSearch = ActivityAdvanceSearchBinding.inflate(getLayoutInflater());
        setContentView(bindingAdvSearch.getRoot());

        method = new Method(RecentActivity.this);
        method.forceRTLIfSupported();
        propertyListRecent = new ArrayList<>();
        databaseHelperRecent = new DatabaseHelperRecent(RecentActivity.this);

        bindingAdvSearch.toolbarMain.tvName.setText(getString(R.string.recent_property));

        bindingAdvSearch.toolbarMain.fabBack.setOnClickListener(v -> onBackPressed());
        bindingAdvSearch.layRecycle.rvRecycle.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(RecentActivity.this, 1);
        bindingAdvSearch.layRecycle.rvRecycle.setLayoutManager(layoutManager);
        bindingAdvSearch.layRecycle.rvRecycle.setFocusable(false);
        bindingAdvSearch.layRecycle.progressHome.setVisibility(View.GONE);
        BannerAds.showBannerAds(RecentActivity.this,bindingAdvSearch.layoutAds);

    }

    @Override
    public void onResume() {
        super.onResume();
        propertyListRecent = databaseHelperRecent.getRecent();
        displayDataRecent();
    }

    @SuppressLint("SetTextI18n")
    private void displayDataRecent() {
        recentAdapter = new RecentAdapter(RecentActivity.this, propertyListRecent);
        bindingAdvSearch.layRecycle.rvRecycle.setAdapter(recentAdapter);
        recentAdapter.setOnItemClickListener(position -> {
            Intent intentDetail = new Intent(RecentActivity.this, DetailActivity.class);
            intentDetail.putExtra("ID", propertyListRecent.get(position).getPropertyId());
            startActivity(intentDetail);
        });
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