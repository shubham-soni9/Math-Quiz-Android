package eu;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.View;

import eu.thedarken.myo.R;


public class InfoFragment extends PreferenceFragment  {
    private String           mAppVersion = "unknown";
    private SlideAddActivity mSlideAddActivity;

    public static Fragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        mSlideAddActivity = (SlideAddActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs_info);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.setFitsSystemWindows(true);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            mAppVersion = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        Preference appVersion = findPreference("app.version");
        if (appVersion != null) {
            appVersion.setSummary(mAppVersion);
        }
    }


    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference != null && preference.getKey() != null) {
            if (preference.getKey().equals("myo.info")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.thalmic.com/en/myo/")));
            } else if (preference.getKey().equals("myo.purchase")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.thalmic.com/en/myo/preorder/")));
            } else if (preference.getKey().equals("help.mail")) {
                createSupportEmail();
            } else if (preference.getKey().equals("help.twitter")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/d4rken")));
            } else if (preference.getKey().equals("help.gplus")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/116634499773478773276")));
            } else if (preference.getKey().equals("source.github")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/d4rken/myo-android-2048")));
            } else if (preference.getKey().equals("source.base")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/tpcstld/2048")));
            } else if (preference.getKey().equals("source.orig")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/gabrielecirulli/2048")));
            } else if (preference.getKey().equals("app.version")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=eu.thedarken.myo.twothousandfortyeight")));
            }

        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void createSupportEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@thedarken.eu"});

        String subject = "[2048 Myo edition] Question/Suggestion/Feedback";


        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "\n\n" + "(I'm using app version v" + mAppVersion + ")");
        startActivity(Intent.createChooser(intent, ""));
    }

}
