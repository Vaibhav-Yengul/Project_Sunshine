package com.example.vaibhav.projectsunshine;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    static SharedPreferences SP;
    ShareActionProvider mShareActionProvider;
    PlaceholderFragment pf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        SP = PreferenceManager.getDefaultSharedPreferences(this);
        pf = new PlaceholderFragment();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, pf).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        MenuItem share = menu.findItem(R.id.menu_item_share);
        mShareActionProvider =  (ShareActionProvider) MenuItemCompat.getActionProvider(share);

        Intent shareIntent;
        shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        shareIntent.putExtra(Intent.EXTRA_TEXT, pf.getTextViewValue() + "#SunshineApp");
        shareIntent.setType("text/plain");

        mShareActionProvider.setShareIntent(shareIntent);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings){
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment{
        public PlaceholderFragment(){
        }

        TextView tv;
/*
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }*/

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView =  inflater.inflate(R.layout.fragment_details, container, false);
            String tvValue = getActivity().getIntent().getStringExtra("forecastString");
            tv = (TextView) rootView.findViewById(R.id.fragment_textView);
            tv.setText(tvValue);

            String units = SP.getString(getResources().getString(R.string.units_preferenceKey),"def");
            String location = SP.getString(getResources().getString(R.string.location_preferenceKey),"no loc");
            //tv.setText(tv.getText() + "\n" + units + "\n" + location);

            return rootView;
        }

        public String getTextViewValue(){
            return (String) tv.getText();
        }


    }
}
