package com.example.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.adapter.GridAdapter;
import com.example.item.Property;
import com.example.realestate.DetailActivity;
import com.example.realestate.R;
import com.example.realestate.databinding.RowRecyclerviewBinding;
import com.example.response.LatestRP;
import com.example.rest.ApiClient;
import com.example.rest.ApiInterface;
import com.example.util.API;
import com.example.util.Constant;
import com.example.util.EndlessRecyclerViewScrollListener;
import com.example.util.Events;
import com.example.util.GlobalBus;
import com.example.util.Method;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PropertyGridFragment extends Fragment {

    RowRecyclerviewBinding bindingList;
    Method method;
    String strPostId, strPostName, strPostType;
    GridAdapter latestAdapter;
    private int pageIndex = 1;
    private boolean isFirst = true, isLoadMore = false;
    List<Property> propertyList;
    int j = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        bindingList = RowRecyclerviewBinding.inflate(inflater, container, false);
        GlobalBus.getBus().register(this);
        method = new Method(requireActivity());
        if (getArguments() != null) {
            strPostId = getArguments().getString("postId");
            strPostName = getArguments().getString("postName");
            strPostType = getArguments().getString("postType");
        }
        propertyList = new ArrayList<>();

        bindingList.rvRecycle.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 2);
        bindingList.rvRecycle.setLayoutManager(layoutManager);
        bindingList.rvRecycle.setFocusable(false);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (latestAdapter.getItemViewType(position) != 1) {
                    return 2;
                }
                return 1;
            }
        });

        onRequest();
        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (isLoadMore) {
                    new Handler().postDelayed(() -> {
                        pageIndex++;
                        isFirst = false;
                        onRequest();
                    }, 1000);
                } else {
                    latestAdapter.hideHeader();
                }
            }
        };
        bindingList.rvRecycle.addOnScrollListener(endlessRecyclerViewScrollListener);

        return bindingList.getRoot();
    }

    private void onRequest() {
        if (method.isNetworkAvailable()) {
            latestData(method.getUserId());
        } else {
            onState(1);
        }
    }

    private void latestData(String userId) {

        Activity activity = getActivity();
        if (isAdded() && activity != null) {
            if (isFirst)
                showProgress(true);
            Call<LatestRP> call;
            JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(getActivity()));
            switch (strPostType) {
                case "LatestHighPrice": {
                    jsObj.addProperty("user_id", userId);
                    jsObj.addProperty("filter_by", "price_high");
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    call = apiService.getLatestData(API.toBase64(jsObj.toString()));
                    break;
                }
                case "LatestLowPrice": {
                    jsObj.addProperty("user_id", userId);
                    jsObj.addProperty("filter_by", "price_low");
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    call = apiService.getLatestData(API.toBase64(jsObj.toString()));
                    break;
                }
                case "LatestDistance": {
                    jsObj.addProperty("user_id", userId);
                    jsObj.addProperty("filter_by", "distance");
                    jsObj.addProperty("lat", Constant.USER_LATITUDE);
                    jsObj.addProperty("long", Constant.USER_LONGITUDE);
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    call = apiService.getLatestData(API.toBase64(jsObj.toString()));
                    break;
                }
                case "CatList": {
                    jsObj.addProperty("user_id", userId);
                    jsObj.addProperty("type_id", strPostId);
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    call = apiService.getCatByData(API.toBase64(jsObj.toString()),pageIndex);
                    break;
                }
                default: {
                    jsObj.addProperty("user_id", userId);
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    call = apiService.getLatestData(API.toBase64(jsObj.toString()));
                    break;
                }
            }
            call.enqueue(new Callback<LatestRP>() {
                @Override
                public void onResponse(@NotNull Call<LatestRP> call, @NotNull Response<LatestRP> response) {

                    LatestRP latestRP = response.body();
                    if (latestRP != null) {
                        if (isFirst)
                            showProgress(false);
                        isLoadMore = latestRP.isLoadMore();
                        if (latestRP.getLatestPropertyList().isEmpty()) {
                            if (isFirst) {
                                onState(2);//no data
                            }
                        } else {
                            if (latestRP.getLatestPropertyList().size() != 0) {
                                for (int i = 0; i < latestRP.getLatestPropertyList().size(); i++) {
                                    if (Constant.isNative) {
                                        if (j % Constant.nativePosition == 0) {
                                            propertyList.add(null);
                                            j++;
                                        }
                                    }
                                    propertyList.add(latestRP.getLatestPropertyList().get(i));
                                    j++;
                                }
                                if (isFirst) {
                                    isFirst = false;
                                    latestAdapter = new GridAdapter(activity, propertyList);
                                    bindingList.rvRecycle.setAdapter(latestAdapter);
                                    latestAdapter.setOnItemClickListener(position -> {
                                        Intent intentDetail = new Intent(activity, DetailActivity.class);
                                        intentDetail.putExtra("ID", propertyList.get(position).getPropertyId());
                                        startActivity(intentDetail);
                                    });
                                } else {
                                    latestAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                    } else {
                        onState(3);//error
                    }
                }

                @Override
                public void onFailure(@NotNull Call<LatestRP> call, @NotNull Throwable t) {
                    Log.e("fail", t.toString());
                    onState(3);
                }
            });
        }
    }

    private void onState(int state) {
        Activity activity = getActivity();
        if (isAdded() && activity != null) {
            bindingList.layState.getRoot().setVisibility(View.VISIBLE);
            bindingList.rvRecycle.setVisibility(View.GONE);
            bindingList.progressHome.setVisibility(View.GONE);
            String title, desc;
            int image;
            switch (state) {
                case 1:
                default:
                    title = activity.getString(R.string.no_internet);
                    desc = activity.getString(R.string.no_internet_msg);
                    image = R.drawable.img_no_internet;
                    break;
                case 2:
                    title = activity.getString(R.string.no_data);
                    desc = activity.getString(R.string.no_data_msg);
                    image = R.drawable.img_no_data;
                    break;
                case 3:
                    title = activity.getString(R.string.no_error);
                    desc = activity.getString(R.string.no_error_msg);
                    image = R.drawable.img_no_server;
                    break;
            }
            bindingList.layState.ivState.setImageResource(image);
            bindingList.layState.tvState.setText(title);
            bindingList.layState.tvStateMsg.setText(desc);

            bindingList.layState.btnRefreshNow.setOnClickListener(view -> {
                bindingList.layState.getRoot().setVisibility(View.GONE);
                onRequest();
            });
        }
    }

    private void showProgress(boolean isProgress) {
        if (isProgress) {
            bindingList.progressHome.setVisibility(View.VISIBLE);
            bindingList.rvRecycle.setVisibility(View.GONE);
        } else {
            bindingList.progressHome.setVisibility(View.GONE);
            bindingList.rvRecycle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GlobalBus.getBus().unregister(this);
    }

    @Subscribe
    public void getFav(Events.FavProperty favProperty) {
        for (int i = 0; i < propertyList.size(); i++) {
            if (propertyList.get(i) != null) {
                if (propertyList.get(i).getPropertyId().equals(favProperty.getPropertyId())) {
                    propertyList.get(i).setPropertyFavorite(Boolean.parseBoolean(favProperty.getIsFav()));
                    latestAdapter.notifyItemChanged(i);
                }
            }
        }
    }
}
