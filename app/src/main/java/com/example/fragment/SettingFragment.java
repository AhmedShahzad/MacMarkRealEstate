package com.example.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.adapter.SettingAdapter;
import com.example.realestate.AboutUsActivity;
import com.example.realestate.LoginActivity;
import com.example.realestate.PagesActivity;
import com.example.realestate.R;
import com.example.realestate.databinding.FragmentSettingBinding;
import com.example.util.Constant;
import com.example.util.Events;
import com.example.util.GlobalBus;
import com.example.util.Method;
import com.example.util.MyApplication;
import com.example.util.NotificationTiramisu;
import com.example.util.StatusBar;
import com.onesignal.OneSignal;

import org.greenrobot.eventbus.Subscribe;


public class SettingFragment extends Fragment {

    FragmentSettingBinding bindingSetting;
    Method method;
    SettingAdapter settingAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        StatusBar.initLatestScreen(requireActivity());
        bindingSetting = FragmentSettingBinding.inflate(inflater, container, false);
        GlobalBus.getBus().register(this);
        method = new Method(requireActivity());
        method.forceRTLIfSupported();

        bindingSetting.rvSettingList.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 1);
        bindingSetting.rvSettingList.setLayoutManager(layoutManager);
        bindingSetting.rvSettingList.setFocusable(false);

        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked},
        };

        int[] thumbColors = new int[]{
                ContextCompat.getColor(requireActivity(), R.color.switch_thumb_disable),
                ContextCompat.getColor(requireActivity(), R.color.switch_thumb_enable),
        };

        int[] trackColors = new int[]{
                ContextCompat.getColor(requireActivity(), R.color.switch_track_disable),
                ContextCompat.getColor(requireActivity(), R.color.switch_track),
        };
        DrawableCompat.setTintList(DrawableCompat.wrap(bindingSetting.scNotification.getThumbDrawable()), new ColorStateList(states, thumbColors));
        DrawableCompat.setTintList(DrawableCompat.wrap(bindingSetting.scNotification.getTrackDrawable()), new ColorStateList(states, trackColors));

        bindingSetting.scNotification.setChecked(NotificationTiramisu.isNotificationChecked(requireContext()));//myApplication.getNotification()
//        bindingSetting.scNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    NotificationTiramisu.takePermissionSettings(requireActivity(), bindingSetting.scNotification, activityResultLauncher);
//                } else {
//                    OneSignal.disablePush(false);
//                    MyApplication.getInstance().saveIsNotification(false);
//                    OneSignal.unsubscribeWhenNotificationsAreDisabled(false);
//                }
//            }
//        });

        if (method.getIsLogin()) {
            bindingSetting.tvSettingLogOut.setText(getString(R.string.logout));
        } else {
            bindingSetting.tvSettingLogOut.setText(getString(R.string.lbl_log_in));
        }

//        bindingSetting.rlLogOut.setOnClickListener(v -> {
//            if (method.getIsLogin()) {
//                method.logoutDialog();
//            } else {
//                Toast.makeText(requireActivity(), getString(R.string.login_require), Toast.LENGTH_SHORT).show();
//                Intent intentLogin = new Intent(requireActivity(), LoginActivity.class);
//                intentLogin.putExtra("isFromDetail", true);
//                startActivity(intentLogin);
//            }
//
//        });

//        bindingSetting.rlMoreApp.setOnClickListener(v -> {
//            if (Constant.appListData.getGooglePlayLink().isEmpty()) {
//                Toast.makeText(requireActivity(), getString(R.string.no_link_found), Toast.LENGTH_SHORT).show();
//            } else {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.appListData.getGooglePlayLink())));
//            }
//        });
//
//        bindingSetting.rlRateApp.setOnClickListener(v -> {
//            final String appName = requireActivity().getPackageName();//your application package name i.e play store application url
//            try {
//                startActivity(new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("market://details?id="
//                                + appName)));
//            } catch (android.content.ActivityNotFoundException anfe) {
//                startActivity(new Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse("http://play.google.com/store/apps/details?id="
//                                + appName)));
//            }
//        });

//        bindingSetting.rlShareApp.setOnClickListener(v -> {
//            Intent intentShare = new Intent(Intent.ACTION_SEND);
//            intentShare.setType("text/plain");
//            intentShare.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_msg) + "\n" + "http://play.google.com/store/apps/details?id=" + requireActivity().getPackageName());
//            startActivity(intentShare);
//        });

     //   settingAdapter = new SettingAdapter(requireActivity(), Constant.appListData.getPageList());
     //   bindingSetting.rvSettingList.setAdapter(settingAdapter);
//        settingAdapter.setOnItemClickListener(position -> {
//            if (Constant.appListData.getPageList().get(position).getPageId().equals("1")) {
//                Intent intentAbout = new Intent(requireActivity(), AboutUsActivity.class);
//                intentAbout.putExtra("ABOUT", Constant.appListData.getPageList().get(position).getPageContent());
//                startActivity(intentAbout);
//            } else {
//                Intent intentPage = new Intent(requireActivity(), PagesActivity.class);
//                intentPage.putExtra("PAGE_TITLE", Constant.appListData.getPageList().get(position).getPageTitle());
//                intentPage.putExtra("PAGE_DESC", Constant.appListData.getPageList().get(position).getPageContent());
//                startActivity(intentPage);
//            }
//
//        });

        return bindingSetting.getRoot();
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        NotificationTiramisu.setCheckedFromSetting(requireActivity(), bindingSetting.scNotification);
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
        GlobalBus.getBus().unregister(this);
    }

    @Subscribe
    public void onEvent(Events.ProfileUpdate profileUpdate) {
        bindingSetting.tvSettingLogOut.setText(getString(R.string.logout));
    }
}
