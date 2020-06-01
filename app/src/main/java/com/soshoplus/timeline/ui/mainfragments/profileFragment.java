/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.mainfragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.databinding.FragmentProfileBinding;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.userprofile.details;
import com.soshoplus.timeline.models.userprofile.userData;
import com.soshoplus.timeline.models.userprofile.userInfo;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitCalls;
import com.soshoplus.timeline.utils.retrofitInstance;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private Call<userInfo> userInfoCall;
    private details details;
    private userData userData;
    private apiErrors apiErrors;
    //
    String userId, timezone, accessToken;
    
    @Override
    public View onCreateView (@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false);
    
        SharedPreferences pref = requireContext().getSharedPreferences("userCred", 0); // 0 - for private mode
    
        if (pref.contains("userId")) {
            userId = pref.getString("userId", "0");
            timezone = pref.getString("timezone", "0");
            accessToken = pref.getString("accessToken", "0");
        }
    
        //Initializing Retrofit Instance for profile
        profileQueries = retrofitInstance.getRetrofitInst().create(queries.class);
        userInfoCall = profileQueries.getUserData(accessToken, retrofitCalls.serverKey,
                getResources().getString(R.string.fetch_profile),
                userId);
        
        //call profile method
        loadProfile();
        
        return profileBinding.getRoot();
    }
    
    private void loadProfile () {
        userInfoCall.enqueue(new Callback<userInfo>() {
            @Override
            public void onResponse (@NotNull Call<userInfo> call, @NotNull Response<userInfo> response) {
                if (response.body() != null) {
                    if (response.body().getApiStatus() == 200) {
                        userData = response.body().getUserData();
                        details = response.body().getUserData().getDetails();
    
                        //upper section
                        profileBinding.fullName.setText(userData.getName());
                        profileBinding.email.setText(userData.getEmail());
                        profileBinding.username.setText("@ " + userData.getUsername());
                        profileBinding.numberOfFollowers.setText(details.getFollowersCount());
                        profileBinding.numberOfFollowing.setText(details.getFollowingCount());
                        profileBinding.aboutMe.setText(userData.getAbout());
    
                        //counts
                        profileBinding.noOfPosts.setText(details.getPostCount());
//                        profileBinding.noOfGroups.setText(details.getGroupsCount());
//                        profileBinding.noOfPoints.setText(details.getPostCount());
//                        profileBinding.noOfAlbum.setText(details.getAlbumCount());
//                        profileBinding.noOfLikes.setText(details.getLikesCount());
//                        profileBinding.wallet.setText(userData.getWallet());
    
                        //profile pic
                        ShapeableImageView profilePic = profileBinding.profilePic;
                        profilePic.setShapeAppearanceModel(profilePic
                                .getShapeAppearanceModel()
                                .toBuilder()
                                .setAllCorners(CornerFamily.ROUNDED, 50)
                                .build());
                        Picasso.get().load(userData.getAvatar()).into(profilePic);
                        
                        //getting about user
                        aboutUser(userData);
                        //get verified and user level badge
                        badges(userData);
                        //updating social icons links
                        socialLinks(userData);
                    }
                    
                    else {
                        apiErrors = response.body().getErrors();
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                        
                        /*TODO*/
                        /*Get error code from server and display a message*/
                    }
                }
                
                else {
                    //response is null
                    Log.d(TAG, "onResponse: " + "Response is Null");
                    
                    /*TODO*/
                    /*Display appropriate Message*/
                }
            }
    
            @Override
            public void onFailure (@NotNull Call<userInfo> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                
                /*TODO*/
                /*Display Error Message*/
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
    
        //about data
        if(userData.getAbout().isEmpty()) {
            profileBinding.aboutMe.setText("Please update your info here");
            profileBinding.aboutMe.setTextColor(getResources().getColor(R.color.indian_red));
        }
    
        //personal info
//        if (userData.getGenderText().isEmpty()) {
//            profileBinding.gender.setText("Update gender");
//            profileBinding.gender.setTextColor(getResources().getColor(R.color.indian_red));
//        } else {
//            profileBinding.gender.setText("Gender: " + userData.getGenderText());
//        }
//        profileBinding.registeredOn.setText("Registered on : " + userData.getRegistered());
//        if (userData.getCity().isEmpty()) {
//            profileBinding.city.setText("City : " + "Update city");
//            profileBinding.city.setTextColor(getResources().getColor(R.color.indian_red));
//        } else {
//            profileBinding.city.setText("City : " + userData.getCity());
//        }
//        if (userData.getWorking().isEmpty()) {
//            profileBinding.working.setText("Working at : " + "none");
//            profileBinding.working.setTextColor(getResources().getColor(R.color.indian_red));;
//        }else {
//            profileBinding.working.setText("Working at : " + userData.getWorking());
//        }
//        if (userData.getBirthday().isEmpty()) {
//            profileBinding.birthday.setText("Birthday : " + "none");
//            profileBinding.birthday.setTextColor(getResources().getColor(R.color.indian_red));
//        }else {
//            profileBinding.birthday.setText("Birthday : " + userData.getBirthday());
//        }
//        if (userData.getWebsite().isEmpty()) {
//            profileBinding.website.setText("Website : " + "none");
//            profileBinding.website.setTextColor(getResources().getColor(R.color.indian_red));
//        }else {
//            profileBinding.website.setText("Website : " + userData.getWebsite());
//        }
        if (userData.getPhoneNumber().isEmpty()) {
            profileBinding.phone.setText("Empty");
            profileBinding.phone.setTextColor(getResources().getColor(R.color.indian_red));
        }else {
            profileBinding.phone.setText(userData.getPhoneNumber());
        }
    }
    
    @Override
    public void onDestroy () {
        super.onDestroy();
        profileBinding = null;
    }
}
