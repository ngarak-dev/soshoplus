/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.gson.Gson;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.databinding.ActivityMyProfileBinding;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.userprofile.details;
import com.soshoplus.timeline.models.userprofile.userData;
import com.soshoplus.timeline.models.userprofile.userInfo;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class myProfile extends AppCompatActivity {
    
    private static String server_key = "a41ab77c99ab5c9f46b66a894d97cce9";
    private static   String fetch = "user_data,family,liked_pages,joined_groups";
    private static String TAG = "profile Activity ";
    private static String verified_tooltip, level_tooltip;
    private queries profileQueries;
    private Call<userInfo> userInfoCall;
    private String access_token, user_id;
    private userData userData = new userData();
    private ActivityMyProfileBinding myProfileBinding;
    private String facebook, twitter, instagram, youtube, linkedln;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myProfileBinding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        View view = myProfileBinding.getRoot();
        setContentView(view);
        
        //get data bundle from splash activity
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            access_token = bundle.getString("access_token");
            user_id = bundle.getString("user_id");
        }
        
        //Initializing Retrofit Instance for profile
        profileQueries = retrofitInstance.getRetrofitInst().create(queries.class);
        userInfoCall = profileQueries.getUserData(access_token, server_key, fetch, user_id);
        
        userInfoCall.enqueue(new Callback<userInfo>() {
            @Override
            public void onResponse (@NonNull Call<userInfo> call, @NonNull Response<userInfo> response) {
                if (response.body() != null) {
                    
                    if (response.body().getApiStatus() == 200) {
                        userData = response.body().getUserData();
                        details details = response.body().getUserData().getDetails();
                        userInfo userInfo = response.body();
                        
                        //upper section
                        String no_followers =details.getFollowersCount();
                        String no_following = details.getFollowingCount();
                        String email = userData.getEmail();
                        String pic_url = userData.getAvatar();
                        String full_name = userData.getName();
                        String profile_url = userData.getUrl();
                        
                        //badges
                        String user_level = userData.getProType();
                        String is_verified = userData.getVerified();
                        
                        //about me
                        String about_me = userData.getAbout();
    
                        //personal info
                        String gender = userData.getGenderText();
                        String registered = userData.getRegistered();
                        String city = userData.getCity();
                        String working = userData.getWorking();
                        String website = userData.getWebsite();
                        String phone = userData.getPhoneNumber();
                        String birthday = userData.getBirthday();
                        
                        //get social links
                        facebook = userData.getFacebook();
                        twitter =  userData.getTwitter();
                        instagram = userData.getInstagram();
                        youtube = userData.getYoutube();
                        linkedln = userData.getLinkedin();
                        
                        //counts
                        String no_posts = details.getPostCount();
                        String no_groups = details.getGroupsCount();
                        String no_points = userData.getPoints();
                        String no_wallet = userData.getWallet();
                        String no_album = details.getAlbumCount();
                        String no_likes = details.getLikesCount();
                        
                        //upper section
                        myProfileBinding.fullName.setText(full_name);
                        myProfileBinding.email.setText(email);
                        myProfileBinding.numberOfFollowers.setText(no_followers);
                        myProfileBinding.numberOfFollowing.setText(no_following);
                        myProfileBinding.aboutMe.setText(about_me);
                        myProfileBinding.shareProfile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick (View v) {
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, profile_url);
                                startActivity(Intent.createChooser(intent, "Share with"));
                            }
                        });
                        
                        //counts
                        myProfileBinding.noOfPosts.setText(no_posts);
                        myProfileBinding.noOfGroups.setText(no_groups);
                        myProfileBinding.noOfPoints.setText(no_points);
                        myProfileBinding.noOfAlbum.setText(no_album);
                        myProfileBinding.noOfLikes.setText(no_likes);
                        myProfileBinding.wallet.setText(no_wallet);
                        
                        //about data
                        if(userData.getAbout().isEmpty()) {
                            myProfileBinding.aboutMe.setText("Please update your info here");
                            myProfileBinding.aboutMe.setTextColor(getResources().getColor(R.color.indian_red));
                            myProfileBinding.aboutMe.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_edit,0);
                        }
                        
                        //personal info
                        if (userData.getGenderText().isEmpty()) {
                            myProfileBinding.gender.setText("Update gender");
                            myProfileBinding.gender.setTextColor(getResources().getColor(R.color.indian_red));
                        } else {
                            myProfileBinding.gender.setText("Gender: " + gender);
                        }
                        myProfileBinding.registeredOn.setText("Registered on : " + registered);
                        if (userData.getCity().isEmpty()) {
                            myProfileBinding.city.setText("City : " + "Update city");
                            myProfileBinding.city.setTextColor(getResources().getColor(R.color.indian_red));
                        } else {
                            myProfileBinding.city.setText("City : " + city);
                        }
                        if (userData.getWorking().isEmpty()) {
                            myProfileBinding.working.setText("Working at : " + "none");
                            myProfileBinding.working.setTextColor(getResources().getColor(R.color.indian_red));;
                        }else {
                            myProfileBinding.working.setText("Working at : " + working);
                        }
                        if (userData.getBirthday().isEmpty()) {
                            myProfileBinding.birthday.setText("Birthday : " + "none");
                            myProfileBinding.birthday.setTextColor(getResources().getColor(R.color.indian_red));
                        }else {
                            myProfileBinding.birthday.setText("Birthday : " + birthday);
                        }
                        if (userData.getWebsite().isEmpty()) {
                            myProfileBinding.website.setText("Website : " + "none");
                            myProfileBinding.website.setTextColor(getResources().getColor(R.color.indian_red));
                        }else {
                            myProfileBinding.website.setText("Website : " + website);
                        }
                        if (userData.getPhoneNumber().isEmpty()) {
                            myProfileBinding.phone.setText("Phone : " + "none");
                            myProfileBinding.phone.setTextColor(getResources().getColor(R.color.indian_red));
                        }else {
                            myProfileBinding.phone.setText("Phone : " + phone);
                        }
                        
                        //user level
                        switch (user_level) {
                            case "0":
                                myProfileBinding.userLevel.setChipIconResource(R.drawable.ic_free_badge);
                                level_tooltip = "You have a free user badge";
                                break;
                            case "1":
                                myProfileBinding.userLevel.setChipIconResource(R.drawable.ic_star_badge);
                                level_tooltip = "You have a star user badge";
                                break;
                            case "2":
                                myProfileBinding.userLevel.setChipIconResource(R.drawable.ic_hot_badge);
                                level_tooltip = "You have a hot user badge";
                                break;
                            case "3":
                                myProfileBinding.userLevel.setChipIconResource(R.drawable.ic_ultimate_badge);
                                level_tooltip = "You have an ultimate user badge";
                                break;
                            case "4":
                                myProfileBinding.userLevel.setChipIconResource(R.drawable.ic_pro_badge);
                                level_tooltip = "You have a PRO user badge";
                                break;
                        }
                        
                        //verification status
                        if (is_verified.equals("1")) {
                            myProfileBinding.verifiedBadge.setChipIconResource(R.drawable.ic_verified_badge);
                            verified_tooltip = "You are a verified user";
                        }
                        else {
                            myProfileBinding.verifiedBadge.setChipIconResource(R.drawable.ic_not_verified_badge);
                            verified_tooltip = "You are  not a verified user";
                        }
                        
                        //profile pic
                        ShapeableImageView profilePic = myProfileBinding.profilePic;
                        profilePic.setShapeAppearanceModel(profilePic
                                .getShapeAppearanceModel()
                                .toBuilder()
                                .setAllCorners(CornerFamily.ROUNDED, 50)
                                .build());
                        Picasso.get().load(pic_url).into(profilePic);
                        
                        //level_tooltip
                        myProfileBinding.userLevel.setOnClickListener(v -> {
                            Balloon balloon = new Balloon.Builder(myProfile.this)
                                    .setArrowSize(10)
                                    .setArrowOrientation(ArrowOrientation.BOTTOM)
                                    .setArrowVisible(true)
                                    .setPadding(10)
                                    .setText(level_tooltip)
                                    .setTextColor(ContextCompat.getColor(myProfile.this,
                                            R.color.white))
                                    .setBackgroundColor(ContextCompat.getColor(myProfile.this,
                                            R.color.colorPrimaryDark))
                                    .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                                    .setAutoDismissDuration(2500L)
                                    .setLifecycleOwner(new LifecycleOwner() {
                                        @NonNull
                                        @Override
                                        public Lifecycle getLifecycle () {
                                            return null;
                                        }
                                    })
                                    .build();
                            balloon.show(myProfileBinding.userLevel);
                        });
                        
                        //verified_tooltip
                        myProfileBinding.verifiedBadge.setOnClickListener(v -> {
                            Balloon balloon = new Balloon.Builder(myProfile.this)
                                    .setArrowSize(10)
                                    .setArrowOrientation(ArrowOrientation.BOTTOM)
                                    .setArrowVisible(true)
                                    .setPadding(10)
                                    .setText(verified_tooltip)
                                    .setTextColor(ContextCompat.getColor(myProfile.this,
                                            R.color.white))
                                    .setBackgroundColor(ContextCompat.getColor(myProfile.this,
                                            R.color.colorPrimaryDark))
                                    .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                                    .setAutoDismissDuration(2500L)
                                    .setLifecycleOwner(new LifecycleOwner() {
                                        @NonNull
                                        @Override
                                        public Lifecycle getLifecycle () {
                                            return null;
                                        }
                                    })
                                    .build();
                            balloon.show(myProfileBinding.verifiedBadge);
                        });
                        
                        //facebook tooltip
                        myProfileBinding.facebookLink.setOnClickListener(v -> {
                            if (facebook.isEmpty()) {
                                Balloon balloon = new Balloon.Builder(myProfile.this)
                                        .setArrowVisible(false)
                                        .setPadding(10)
                                        .setText("You have not updated your facebook link")
                                        .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                                        .setAutoDismissDuration(2500L)
                                        .setLifecycleOwner(() -> null)
                                        .build();
                                balloon.show(myProfileBinding.facebookLink);
                            } else {
                                Balloon balloon = new Balloon.Builder(myProfile.this)
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
                                link.setText(facebook);
                                link.setCompoundDrawablePadding(5);
                                link.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_facebook, 0,0,0);
                                
                                share.setOnClickListener(v_ -> {
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("text/plain");
                                    intent.putExtra(Intent.EXTRA_TEXT, facebook);
                                    startActivity(Intent.createChooser(intent, "Share with"));
                                });
                                open.setOnClickListener(v_ -> {
    
                                    Uri webpage = Uri.parse(facebook);
                                    if (!facebook.startsWith("http://") && !facebook.startsWith("https" +
                                            "://")) {
                                        webpage = Uri.parse("http://" + facebook);
                                    }
                                    
                                    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                                    CustomTabsIntent customTabsIntent =
                                            intentBuilder.setShowTitle(true).addDefaultShareMenuItem().build();
                                    customTabsIntent.launchUrl(myProfile.this, webpage);
                                });
                                
                                balloon.show(myProfileBinding.facebookLink);
                            }
                        });
                        
                        //twitter tooltip
                        myProfileBinding.twitterLink.setOnClickListener(v -> {
                            if (twitter.isEmpty()) {
                                Balloon balloon = new Balloon.Builder(myProfile.this)
                                        .setArrowVisible(false)
                                        .setPadding(10)
                                        .setText("You have not updated your twitter link")
                                        .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                                        .setAutoDismissDuration(2500L)
                                        .setLifecycleOwner(() -> null)
                                        .build();
                                balloon.show(myProfileBinding.twitterLink);
                            } else {
                                Balloon balloon = new Balloon.Builder(myProfile.this)
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
                                link.setText(twitter);
                                link.setCompoundDrawablePadding(5);
                                link.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_twitter, 0,0,0);
    
                                share.setOnClickListener(v_ -> {
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("text/plain");
                                    intent.putExtra(Intent.EXTRA_TEXT, twitter);
                                    startActivity(Intent.createChooser(intent, "Share with"));
                                });
                                open.setOnClickListener(v_ -> {
        
                                    Uri webpage = Uri.parse(twitter);
                                    if (!twitter.startsWith("http://") && !twitter.startsWith("https" +
                                            "://")) {
                                        webpage = Uri.parse("http://" + twitter);
                                    }
        
                                    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                                    CustomTabsIntent customTabsIntent =
                                            intentBuilder.setShowTitle(true).addDefaultShareMenuItem().build();
                                    customTabsIntent.launchUrl(myProfile.this, webpage);
                                });
                                
                                balloon.show(myProfileBinding.twitterLink);
                            }
                        });
                        
                        //instagram tooltip
                        myProfileBinding.instagramLink.setOnClickListener(v -> {
                            if (instagram.isEmpty()) {
                                Balloon balloon = new Balloon.Builder(myProfile.this)
                                        .setArrowVisible(false)
                                        .setPadding(10)
                                        .setText("You have not updated your instagram link")
                                        .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                                        .setAutoDismissDuration(2500L)
                                        .setLifecycleOwner(() -> null)
                                        .build();
                                balloon.show(myProfileBinding.instagramLink);
                            } else {
                                Balloon balloon = new Balloon.Builder(myProfile.this)
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
                                link.setText(instagram);
                                link.setCompoundDrawablePadding(5);
                                link.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_instagram, 0,0
                                        ,0);
            
                                share.setOnClickListener(v_ -> {
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("text/plain");
                                    intent.putExtra(Intent.EXTRA_TEXT, instagram);
                                    startActivity(Intent.createChooser(intent, "Share with"));
                                });
                                open.setOnClickListener(v_ -> {
                
                                    Uri webpage = Uri.parse(instagram);
                                    if (!instagram.startsWith("http://") && !instagram.startsWith(
                                            "https" +
                                            "://")) {
                                        webpage = Uri.parse("http://" + instagram);
                                    }
                
                                    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                                    CustomTabsIntent customTabsIntent =
                                            intentBuilder.setShowTitle(true).addDefaultShareMenuItem().build();
                                    customTabsIntent.launchUrl(myProfile.this, webpage);
                                });
            
                                balloon.show(myProfileBinding.instagramLink);
                            }
                        });
                        
                        //linkedin tooltip
                        myProfileBinding.linkedinLink.setOnClickListener(v -> {
                            if (linkedln.isEmpty()) {
                                Balloon balloon = new Balloon.Builder(myProfile.this)
                                        .setArrowVisible(false)
                                        .setPadding(10)
                                        .setText("You have not updated your twitter link")
                                        .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                                        .setAutoDismissDuration(2500L)
                                        .setLifecycleOwner(() -> null)
                                        .build();
                                balloon.show(myProfileBinding.linkedinLink);
                            } else {
                                Balloon balloon = new Balloon.Builder(myProfile.this)
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
                                link.setText(linkedln);
                                link.setCompoundDrawablePadding(5);
                                link.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_linkedin, 0,0,0);
            
                                share.setOnClickListener(v_ -> {
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("text/plain");
                                    intent.putExtra(Intent.EXTRA_TEXT, linkedln);
                                    startActivity(Intent.createChooser(intent, "Share with"));
                                });
                                open.setOnClickListener(v_ -> {
                
                                    Uri webpage = Uri.parse(linkedln);
                                    if (!linkedln.startsWith("http://") && !linkedln.startsWith("https" +
                                            "://")) {
                                        webpage = Uri.parse("http://" + linkedln);
                                    }
                
                                    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                                    CustomTabsIntent customTabsIntent =
                                            intentBuilder.setShowTitle(true).addDefaultShareMenuItem().build();
                                    customTabsIntent.launchUrl(myProfile.this, webpage);
                                });
            
                                balloon.show(myProfileBinding.linkedinLink);
                            }
                        });
                        
                        //youtube tooltip
                        myProfileBinding.youtubeLink.setOnClickListener(v -> {
                            if (youtube.isEmpty()) {
                                Balloon balloon = new Balloon.Builder(myProfile.this)
                                        .setArrowVisible(false)
                                        .setPadding(10)
                                        .setText("You have not updated your youtube link")
                                        .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                                        .setAutoDismissDuration(2500L)
                                        .setLifecycleOwner(() -> null)
                                        .build();
                                balloon.show(myProfileBinding.youtubeLink);
                            } else {
                                Balloon balloon = new Balloon.Builder(myProfile.this)
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
                                link.setText(youtube);
                                link.setCompoundDrawablePadding(5);
                                link.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_youtube, 0,0
                                        ,0);
            
                                share.setOnClickListener(v_ -> {
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("text/plain");
                                    intent.putExtra(Intent.EXTRA_TEXT, youtube);
                                    startActivity(Intent.createChooser(intent, "Share with"));
                                });
                                open.setOnClickListener(v_ -> {
                
                                    Uri webpage = Uri.parse(youtube);
                                    if (!youtube.startsWith("http://") && !youtube.startsWith("https" +
                                            "://")) {
                                        webpage = Uri.parse("http://" + youtube);
                                    }
                
                                    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                                    CustomTabsIntent customTabsIntent =
                                            intentBuilder.setShowTitle(true).addDefaultShareMenuItem().build();
                                    customTabsIntent.launchUrl(myProfile.this, webpage);
                                });
            
                                balloon.show(myProfileBinding.youtubeLink);
                            }
                        });
    
                        //updating social icons
                        if (userData.getFacebook().isEmpty()) {
                            myProfileBinding.facebookLink.setChipIconResource(R.drawable.ic_no_facebook);

                        } else {
                            myProfileBinding.facebookLink.setChipIconResource(R.drawable.ic_facebook);
                        }

                        if (userData.getTwitter().isEmpty()) {
                            myProfileBinding.twitterLink.setChipIconResource(R.drawable.ic_no_twitter);

                        } else {
                            myProfileBinding.twitterLink.setChipIconResource(R.drawable.ic_twitter);
                        }

                        if (userData.getInstagram().isEmpty()) {
                            myProfileBinding.instagramLink.setChipIconResource(R.drawable.ic_no_instagram);

                        } else {
                            myProfileBinding.instagramLink.setChipIconResource(R.drawable.ic_instagram);
                        }

                        if (userData.getLinkedin().isEmpty()) {
                            myProfileBinding.linkedinLink.setChipIconResource(R.drawable.ic_no_linkedin);

                        } else {
                            myProfileBinding.linkedinLink.setChipIconResource(R.drawable.ic_linkedin);
                        }

                        if (userData.getYoutube().isEmpty()) {
                            myProfileBinding.youtubeLink.setChipIconResource(R.drawable.ic_no_youtube);

                        } else {
                            myProfileBinding.youtubeLink.setChipIconResource(R.drawable.ic_youtube);
                        }
                        
                        Gson gson = new Gson();
                        String userdata = gson.toJson(userData);
                        String userdetails = gson.toJson(details);
                        String userinfo = gson.toJson(userInfo);
    
                        Log.d(TAG, "onResponse: " + userdata);
                    }
                    else {
                        apiErrors apiErrors = response.body().getErrors();
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                    }
                }
                else {
                    //response is null
                    Log.d(TAG, "onResponse: " + "Response is Null");
                }
            }
    
            @Override
            public void onFailure (@NonNull Call<userInfo> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
