/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.mainfragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.gson.Gson;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.adapters.countsGridAdapter;
import com.soshoplus.timeline.adapters.infoListAdapter;
import com.soshoplus.timeline.databinding.FragmentProfileBinding;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.userprofile.countsGrid;
import com.soshoplus.timeline.models.userprofile.details;
import com.soshoplus.timeline.models.userprofile.infoList;
import com.soshoplus.timeline.models.userprofile.userData;
import com.soshoplus.timeline.models.userprofile.userInfo;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitCalls;
import com.soshoplus.timeline.utils.retrofitInstance;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {
    
    public profileFragment () {
        // Required empty public constructor
    }

    private static String TAG = "profileFragment ";
    private static String verified_tooltip, level_tooltip;
    private FragmentProfileBinding profileBinding;
    private queries profileQueries;
    private Observable<userInfo> userInfoCall;
    private apiErrors apiErrors;
    //
    String userId, timezone, accessToken;

    private ArrayList<infoList> infoList;
    private ArrayList<countsGrid> countsList;

    String bla;
    ProgressDialog progressDialog;
    /*initializing a view and inflate it */
    @Override
    public View onCreateView (@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false);
//
        SharedPreferences pref = requireContext().getSharedPreferences("userCred", 0); // 0 - for private mode

        if (pref.contains("userId")) {
            userId = pref.getString("userId", "0");
            timezone = pref.getString("timezone", "0");
            accessToken = pref.getString("accessToken", "0");
        }

        //Initializing Retrofit Instance for profile
        profileQueries = retrofitInstance.getInstRxJava().create(queries.class);
        userInfoCall = profileQueries.getUserData(accessToken, retrofitCalls.serverKey,
                getResources().getString(R.string.fetch_profile),
                userId);

        //call profile method
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run () {
                loadProfile();
            }
        });
        executorService.shutdown();
        
        return profileBinding.getRoot();
    }
    
    private void loadProfile () {

        userInfoCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<userInfo>() {
            @Override
            public void onSubscribe (@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext (@NonNull userInfo userInfo) {
                if (userInfo.getApiStatus() == 200) {
    
                    /*upper section*/
                    profileBinding.fullName.setText(userInfo.getUserData().getName());
                    profileBinding.email.setText(userInfo.getUserData().getEmail());
                    profileBinding.username.setText("@ " + userInfo.getUserData().getUsername());
    
                    /*upper counts*/
                    profileBinding.numberOfFollowers.setText(userInfo.getUserData().getDetails().getFollowersCount());
                    profileBinding.numberOfFollowing.setText(userInfo.getUserData().getDetails().getFollowingCount());
                    profileBinding.noOfPosts.setText(userInfo.getUserData().getDetails().getPostCount());
    
                    //profile pic
                    ShapeableImageView profilePic = profileBinding.profilePic;
                    profilePic.setShapeAppearanceModel(profilePic
                            .getShapeAppearanceModel()
                            .toBuilder()
                            .setAllCorners(CornerFamily.ROUNDED, 20)
                            .build());

                    Glide.with(requireContext()).load(userInfo.getUserData().getAvatar()).placeholder(R.drawable.ic_image_placeholder).thumbnail(0.5f).into(profileBinding.profilePic);

                    //get verified and user level badge
                    badges(userInfo.getUserData());
                    //updating social icons links
                    socialLinks(userInfo.getUserData());
                    //getting about user
                    aboutUser(userInfo.getUserData());
                    /*get counts*/
                    counts(userInfo.getUserData(), userInfo.getUserData().getDetails());
                }
                else {
                    apiErrors = userInfo.getErrors();
                    Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                    Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                }
            }

            @Override
            public void onError (@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete () {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    private void socialLinks (userData userData) {
        //updating social icons
        if (userData.getFacebook().isEmpty()) {
            profileBinding.facebookLink.setChipIconResource(R.drawable.ic_no_facebook);

        } else {
            profileBinding.facebookLink.setChipIconResource(R.drawable.ic_facebook);
        }

        if (userData.getTwitter().isEmpty()) {
            profileBinding.twitterLink.setChipIconResource(R.drawable.ic_no_twitter);

        } else {
            profileBinding.twitterLink.setChipIconResource(R.drawable.ic_twitter);
        }

        if (userData.getInstagram().isEmpty()) {
            profileBinding.instagramLink.setChipIconResource(R.drawable.ic_no_instagram);

        } else {
            profileBinding.instagramLink.setChipIconResource(R.drawable.ic_instagram);
        }

        if (userData.getLinkedin().isEmpty()) {
            profileBinding.linkedinLink.setChipIconResource(R.drawable.ic_no_linkedin);

        } else {
            profileBinding.linkedinLink.setChipIconResource(R.drawable.ic_linkedin);
        }

        if (userData.getYoutube().isEmpty()) {
            profileBinding.youtubeLink.setChipIconResource(R.drawable.ic_no_youtube);

        } else {
            profileBinding.youtubeLink.setChipIconResource(R.drawable.ic_youtube);
        }

        //toolTips
        socialToolTips(userData);
    }

    private void socialToolTips (userData userData) {
        //facebook tooltip
        profileBinding.facebookLink.setOnClickListener(v -> {
            if (userData.getFacebook().isEmpty()) {
                Balloon balloon = new Balloon.Builder(requireContext())
                        .setArrowVisible(false)
                        .setPadding(10)
                        .setText("You have not updated your facebook link")
                        .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                        .setAutoDismissDuration(2500L)
                        .setLifecycleOwner(() -> null)
                        .build();
                balloon.show(profileBinding.facebookLink);
            } else {
                Balloon balloon = new Balloon.Builder(requireContext())
                        .setArrowVisible(false)
                        .setLayout(R.layout.social_links_popup)
                        .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                        .setLifecycleOwner(() -> null)
                        .build();

                //custom popup resources ids
                TextView link =
                        balloon.getContentView().findViewById(R.id.link);
                Chip share = balloon.getContentView().findViewById(R.id.share);
                Chip open = balloon.getContentView().findViewById(R.id.open);
                link.setText(userData.getFacebook());
                link.setCompoundDrawablePadding(5);
                link.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_facebook, 0,0,0);

                share.setOnClickListener(v_ -> {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, userData.getFacebook());
                    startActivity(Intent.createChooser(intent, "Share with"));
                });
                open.setOnClickListener(v_ -> {

                    Uri webpage = Uri.parse(userData.getFacebook());
                    if (!userData.getFacebook().startsWith("http://") && !userData.getFacebook().startsWith(
                            "https" +
                            "://")) {
                        webpage = Uri.parse("http://" + userData.getFacebook());
                    }

                    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent =
                            intentBuilder.setShowTitle(true).addDefaultShareMenuItem().build();
                    customTabsIntent.launchUrl(requireContext(), webpage);
                });

                balloon.show(profileBinding.facebookLink);
            }
        });

        //twitter tooltip
        profileBinding.twitterLink.setOnClickListener(v -> {
            if (userData.getTwitter().isEmpty()) {
                Balloon balloon = new Balloon.Builder(requireContext())
                        .setArrowVisible(false)
                        .setPadding(10)
                        .setText("You have not updated your twitter link")
                        .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                        .setAutoDismissDuration(2500L)
                        .setLifecycleOwner(() -> null)
                        .build();
                balloon.show(profileBinding.twitterLink);
            } else {
                Balloon balloon = new Balloon.Builder(requireContext())
                        .setArrowVisible(false)
                        .setLayout(R.layout.social_links_popup)
                        .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                        .setLifecycleOwner(() -> null)
                        .build();

                //custom popup resources ids
                TextView link =
                        balloon.getContentView().findViewById(R.id.link);
                Chip share = balloon.getContentView().findViewById(R.id.share);
                Chip open = balloon.getContentView().findViewById(R.id.open);
                link.setText(userData.getTwitter());
                link.setCompoundDrawablePadding(5);
                link.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_twitter, 0,0,0);

                share.setOnClickListener(v_ -> {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, userData.getTwitter());
                    startActivity(Intent.createChooser(intent, "Share with"));
                });
                open.setOnClickListener(v_ -> {

                    Uri webpage = Uri.parse(userData.getTwitter());
                    if (!userData.getTwitter().startsWith("http://") && !userData.getTwitter().startsWith(
                            "https" +
                            "://")) {
                        webpage = Uri.parse("http://" + userData.getTwitter());
                    }

                    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent =
                            intentBuilder.setShowTitle(true).addDefaultShareMenuItem().build();
                    customTabsIntent.launchUrl(requireContext(), webpage);
                });

                balloon.show(profileBinding.twitterLink);
            }
        });

        //instagram tooltip
        profileBinding.instagramLink.setOnClickListener(v -> {
            if (userData.getInstagram().isEmpty()) {
                Balloon balloon = new Balloon.Builder(requireContext())
                        .setArrowVisible(false)
                        .setPadding(10)
                        .setText("You have not updated your instagram link")
                        .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                        .setAutoDismissDuration(2500L)
                        .setLifecycleOwner(() -> null)
                        .build();
                balloon.show(profileBinding.instagramLink);
            } else {
                Balloon balloon = new Balloon.Builder(requireContext())
                        .setArrowVisible(false)
                        .setLayout(R.layout.social_links_popup)
                        .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                        .setLifecycleOwner(() -> null)
                        .build();

                //custom popup resources ids
                TextView link =
                        balloon.getContentView().findViewById(R.id.link);
                Chip share = balloon.getContentView().findViewById(R.id.share);
                Chip open = balloon.getContentView().findViewById(R.id.open);
                link.setText(userData.getInstagram());
                link.setCompoundDrawablePadding(5);
                link.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_instagram, 0,0
                        ,0);

                share.setOnClickListener(v_ -> {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, userData.getInstagram());
                    startActivity(Intent.createChooser(intent, "Share with"));
                });
                open.setOnClickListener(v_ -> {

                    Uri webpage = Uri.parse(userData.getInstagram());
                    if (!userData.getInstagram().startsWith("http://") && !userData.getInstagram().startsWith(
                            "https" +
                                    "://")) {
                        webpage = Uri.parse("http://" + userData.getInstagram());
                    }

                    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent =
                            intentBuilder.setShowTitle(true).addDefaultShareMenuItem().build();
                    customTabsIntent.launchUrl(requireContext(), webpage);
                });

                balloon.show(profileBinding.instagramLink);
            }
        });

        //linkedin tooltip
        profileBinding.linkedinLink.setOnClickListener(v -> {
            if (userData.getLinkedin().isEmpty()) {
                Balloon balloon = new Balloon.Builder(requireContext())
                        .setArrowVisible(false)
                        .setPadding(10)
                        .setText("You have not updated your twitter link")
                        .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                        .setAutoDismissDuration(2500L)
                        .setLifecycleOwner(() -> null)
                        .build();
                balloon.show(profileBinding.linkedinLink);
            } else {
                Balloon balloon = new Balloon.Builder(requireContext())
                        .setArrowVisible(false)
                        .setLayout(R.layout.social_links_popup)
                        .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                        .setLifecycleOwner(() -> null)
                        .build();

                //custom popup resources ids
                TextView link =
                        balloon.getContentView().findViewById(R.id.link);
                Chip share = balloon.getContentView().findViewById(R.id.share);
                Chip open = balloon.getContentView().findViewById(R.id.open);
                link.setText(userData.getLinkedin());
                link.setCompoundDrawablePadding(5);
                link.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_linkedin, 0,0,0);

                share.setOnClickListener(v_ -> {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, userData.getLinkedin());
                    startActivity(Intent.createChooser(intent, "Share with"));
                });
                open.setOnClickListener(v_ -> {

                    Uri webpage = Uri.parse(userData.getLinkedin());
                    if (!userData.getLinkedin().startsWith("http://") && !userData.getLinkedin().startsWith("https" +
                            "://")) {
                        webpage = Uri.parse("http://" + userData.getLinkedin());
                    }

                    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent =
                            intentBuilder.setShowTitle(true).addDefaultShareMenuItem().build();
                    customTabsIntent.launchUrl(requireContext(), webpage);
                });

                balloon.show(profileBinding.linkedinLink);
            }
        });

        //youtube tooltip
        profileBinding.youtubeLink.setOnClickListener(v -> {
            if (userData.getYoutube().isEmpty()) {
                Balloon balloon = new Balloon.Builder(requireContext())
                        .setArrowVisible(false)
                        .setPadding(10)
                        .setText("You have not updated your youtube link")
                        .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                        .setAutoDismissDuration(2500L)
                        .setLifecycleOwner(() -> null)
                        .build();
                balloon.show(profileBinding.youtubeLink);
            } else {
                Balloon balloon = new Balloon.Builder(requireContext())
                        .setArrowVisible(false)
                        .setLayout(R.layout.social_links_popup)
                        .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                        .setLifecycleOwner(() -> null)
                        .build();

                //custom popup resources ids
                TextView link =
                        balloon.getContentView().findViewById(R.id.link);
                Chip share = balloon.getContentView().findViewById(R.id.share);
                Chip open = balloon.getContentView().findViewById(R.id.open);
                link.setText(userData.getYoutube());
                link.setCompoundDrawablePadding(5);
                link.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_youtube, 0,0
                        ,0);

                share.setOnClickListener(v_ -> {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, userData.getYoutube());
                    startActivity(Intent.createChooser(intent, "Share with"));
                });
                open.setOnClickListener(v_ -> {

                    Uri webpage = Uri.parse(userData.getYoutube());
                    if (!userData.getYoutube().startsWith("http://") && !userData.getYoutube().startsWith("https" +
                            "://")) {
                        webpage = Uri.parse("http://" + userData.getYoutube());
                    }

                    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent =
                            intentBuilder.setShowTitle(true).addDefaultShareMenuItem().build();
                    customTabsIntent.launchUrl(requireContext(), webpage);
                });

                balloon.show(profileBinding.youtubeLink);
            }
        });
    }

    private void badges (userData userData) {
        //user level
        switch (userData.getProType()) {
            case "0":
                profileBinding.userLevel.setText("FREE");
                profileBinding.userLevel.setChipBackgroundColorResource(R.color.gray);
                break;
            case "1":
                profileBinding.userLevel.setText("STAR");
                profileBinding.userLevel.setChipBackgroundColorResource(R.color.green);
                break;
            case "2":
                profileBinding.userLevel.setText("HOT");
                profileBinding.userLevel.setChipBackgroundColorResource(R.color.red);
                break;
            case "3":
                profileBinding.userLevel.setText("ULTIMATE");
                profileBinding.userLevel.setChipBackgroundColorResource(R.color.yellow);
                break;
            case "4":
                profileBinding.userLevel.setText("PRO");
                profileBinding.userLevel.setChipBackgroundColorResource(R.color.colorAccent);
                break;
        }

        //verification status
        if (userData.getVerified().equals("1")) {
            profileBinding.verifiedBadge.setText("VERIFIED");
            profileBinding.verifiedBadge.setChipBackgroundColorResource(R.color.green);
        }
        else {
            profileBinding.verifiedBadge.setText("NOT VERIFIED");
        }
    }

    private void aboutUser (userData userData) {

        if (userData == null) {
            userData = new userData();
        }

        /**/
        //about data
        if(userData.getAbout() == null) {
            profileBinding.aboutMe.setText("Please update your info here");
            profileBinding.aboutMe.setTextColor(getResources().getColor(R.color.indian_red));
        }
        else {
            profileBinding.aboutMe.setText(Html.fromHtml(userData.getAbout()));
        }

        if (userData.getPhoneNumber().isEmpty()) {
            profileBinding.phone.setText("Empty");
            profileBinding.phone.setTextColor(getResources().getColor(R.color.indian_red));
        }else {
            profileBinding.phone.setText(userData.getPhoneNumber());
        }

        /*Initializing List*/
        infoList = new ArrayList<>();

        if (userData.getBirthday().isEmpty()) {
            Log.d(TAG, "Birthday: Empty");
        }else {
            infoList.add(new infoList(R.drawable.ic_birthday, "Birthday", userData.getBirthday()));
        }

        if (userData.getGenderText().isEmpty()) {
            Log.d(TAG, "Gender: Empty");
        } else {
           infoList.add(new infoList(R.drawable.ic_gender, "Gender", userData.getGenderText()));
        }

        if (userData.getWorking().isEmpty()) {
            Log.d(TAG, "Working: Empty");
        }else {
            infoList.add(new infoList(R.drawable.ic_working, "Working at", userData.getWorking()));
        }

        if (userData.getWebsite().isEmpty()) {
            Log.d(TAG, "Website: Empty");
        }else {
            infoList.add(new infoList(R.drawable.ic_website, "Website", userData.getWebsite()));
        }

        if (userData.getCity().isEmpty()) {
            Log.d(TAG, "City: Empty");
        } else {
           infoList.add(new infoList(R.drawable.ic_city, "City", userData.getCity()));
        }

        if(userData.getSchool().isEmpty()) {
            Log.d(TAG, "School: Empty");
        }
        else {
            infoList.add(new infoList(R.drawable.ic_school, "School", userData.getSchool()));
        }

        if (userData.getLanguage().isEmpty()) {
            Log.d(TAG, "Language: Empty");
        }
        else {
           infoList.add(new infoList(R.drawable.ic_languages, "Languages", userData.getLanguage()));
        }

        addInfoList();
    }

    private void addInfoList () {
        /*Initializing Adapter*/
        infoListAdapter infoListAdapter = new infoListAdapter(requireContext(), infoList);
        /*Setting Layout*/
        profileBinding.infoList.setLayoutManager(new LinearLayoutManager(requireContext()));
        profileBinding.infoList.setItemAnimator(new DefaultItemAnimator());
        profileBinding.infoList.addItemDecoration(new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL));
        /*Setting Adapter*/
        profileBinding.infoList.setAdapter(infoListAdapter);
    }

    private void counts (userData userData, details details) {
        /*initializing list*/
        countsList = new ArrayList<>();
        /*adding data to a list*/
        countsList.add(new countsGrid(R.drawable.ic_gallery, details.getAlbumCount(), "Album"));
        countsList.add(new countsGrid(R.drawable.ic_wallet, userData.getWallet(), "Wallet"));
        countsList.add(new countsGrid(R.drawable.ic_points, userData.getPoints(), "Points"));
        countsList.add(new countsGrid(R.drawable.ic_group, details.getGroupsCount(), "Groups"));
        countsList.add(new countsGrid(R.drawable.ic_likes, details.getLikesCount(), "Likes"));

        /*Initializing Adapter*/
        countsGridAdapter countsGridAdapter = new countsGridAdapter(requireContext(), countsList);
        /*Setting Layout*/
        profileBinding.countsGrid.setLayoutManager(new GridLayoutManager(getContext(), 3));
        profileBinding.countsGrid.setItemAnimator(new DefaultItemAnimator());
        /*Setting Adapter*/
        profileBinding.countsGrid.setAdapter(countsGridAdapter);
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        profileBinding = null;
    }
}
