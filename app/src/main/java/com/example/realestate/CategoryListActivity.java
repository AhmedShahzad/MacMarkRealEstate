package com.example.realestate;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.fragment.AdvanceSearchFragment;
import com.example.fragment.PropertyGridFragment;
import com.example.fragment.PropertyListFragment;
import com.example.realestate.databinding.ActivityCatListBinding;
import com.example.util.BannerAds;
import com.example.util.Method;
import com.example.util.StatusBar;


public class CategoryListActivity extends AppCompatActivity {

    ActivityCatListBinding bindingCatList;
    Method method;
    FragmentManager fragmentManager;
    String catId,catName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.initLatestScreen(CategoryListActivity.this);
        bindingCatList = ActivityCatListBinding.inflate(getLayoutInflater());
        setContentView(bindingCatList.getRoot());

        method = new Method(CategoryListActivity.this);
        method.forceRTLIfSupported();
        fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        catId = intent.getStringExtra("CatId");
        catName = intent.getStringExtra("CatName");

        bindingCatList.toolbarMain.tvName.setText(catName);

        bindingCatList.toolbarMain.fabBack.setOnClickListener(v -> onBackPressed());

        bindingCatList.ibGrid.setOnClickListener(view -> {
            bindingCatList.ibList.setColorFilter(getResources().getColor(R.color.img_normal), PorterDuff.Mode.SRC_IN);
            bindingCatList.ibGrid.setColorFilter(getResources().getColor(R.color.img_select), PorterDuff.Mode.SRC_IN);
            bindingCatList.ibGrid.setBackgroundResource(R.drawable.icon_purple_bg);
            bindingCatList.ibList.setBackgroundResource(R.drawable.icon_light_purple_bg);
            goGrid();
        });

        bindingCatList.ibList.setOnClickListener(view -> {
            bindingCatList.ibList.setColorFilter(getResources().getColor(R.color.img_select), PorterDuff.Mode.SRC_IN);
            bindingCatList.ibGrid.setColorFilter(getResources().getColor(R.color.img_normal), PorterDuff.Mode.SRC_IN);
            bindingCatList.ibGrid.setBackgroundResource(R.drawable.icon_light_purple_bg);
            bindingCatList.ibList.setBackgroundResource(R.drawable.icon_purple_bg);
            goList();
        });
        goGrid();

         bindingCatList.toolbarMain.ibFilter.setOnClickListener(view -> {
             Bundle bundle = new Bundle();
             bundle.putString("postId", "");
             bundle.putString("userId", method.getUserId());
             AdvanceSearchFragment advanceSearchFragment = new AdvanceSearchFragment();
             advanceSearchFragment.setArguments(bundle);
             advanceSearchFragment.show(getSupportFragmentManager(), advanceSearchFragment.getTag());
         });
        BannerAds.showBannerAds(CategoryListActivity.this, bindingCatList.layoutAds);
    }
    private void goGrid() {
        Bundle bundle = new Bundle();
        bundle.putString("postId", catId);
        bundle.putString("postName", catName);
        bundle.putString("postType", "CatList");
        PropertyGridFragment propertyGridFragment = new PropertyGridFragment();
        propertyGridFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.frameCATList, propertyGridFragment, "")
                .commitAllowingStateLoss();
    }

    private void goList() {
        Bundle bundle = new Bundle();
        bundle.putString("postId", catId);
        bundle.putString("postName", catName);
        bundle.putString("postType", "CatList");
        PropertyListFragment propertyListFragment = new PropertyListFragment();
        propertyListFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.frameCATList, propertyListFragment, "")
                .commitAllowingStateLoss();
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