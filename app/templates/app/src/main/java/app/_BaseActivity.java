package <%= appPackage %>.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import <%= appPackage %>.repository.JsonSharedPreferencesRepository;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity  {
    @Inject
    MixpanelAPI mixpanel;

    @Inject
    JsonSharedPreferencesRepository jsonSharedPreferencesRepository;
    @Inject

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
