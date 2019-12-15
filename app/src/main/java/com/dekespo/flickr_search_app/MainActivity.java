package com.dekespo.flickr_search_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;

import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.ToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleDrawerItem toggleDrawerItem = new ToggleDrawerItem().withIdentifier(1).withName("Toggle button").withOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked)
            {
                Log.e(TAG, "Running toggle on checkedChanged");
            }
        });
        SwitchDrawerItem switchDrawerItem = new SwitchDrawerItem().withIdentifier(2).withName("Swtich button").withOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked)
            {
                Log.e(TAG, "Running switch on checkedChanged");
            }
        });
        DrawerBuilder drawerBuilder = new DrawerBuilder();
        drawerBuilder
                .withActivity(this)
                .addDrawerItems(
                        toggleDrawerItem,
                        switchDrawerItem
                )
                .build();
    }
}
