package com.sm.navigationdrawerone;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActDashboard extends AppCompatActivity {
    //|------------------------------------------------------------|
    private Activity activity;
    private Context context;
    //|------------------------------------------------------------|
    private Toolbar sysToolBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout sysDrawerLayout;
    private RelativeLayout sysIdDrawerContainer;
    private ListView sysDrawerList;
    private DynamicArrayAdapter adapterLstDrawer;
    private ArrayList<DynamicModel> modelDrawerItems = new ArrayList<DynamicModel>();
    //|------------------------------------------------------------|

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //|------------------------------------------------------------|
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dashboard);
        //|------------------------------------------------------------|
        activity = this;
        context = this;
        //|------------------------------------------------------------|
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        sysToolBar = (Toolbar) findViewById(R.id.sysToolBar);
        //sysToolBar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(sysToolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
        //|------------------------------------------------------------|
        /*Drawable upArrow = ContextCompat.getDrawable(context, R.drawable.ic_drawer);
        upArrow.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);*/
        //sysToolBar.setNavigationIcon(R.drawable.ic_drawer);
        //|------------------------------------------------------------|
        //SetUserTheme.setStatusBarColor(activity, Color.parseColor("#00793A"));
        //|------------------------------------------------------------|
        //|------------------------------------------------------------|
        sysIdDrawerContainer = (RelativeLayout) findViewById(R.id.sysIdDrawerContainer);
        sysDrawerLayout = (DrawerLayout) findViewById(R.id.sysDrawerLayout);
        sysDrawerList = (ListView) findViewById(R.id.sysDrawerList);
        //|------------------------------------------------------------|
        modelDrawerItems.add(onGetSetDynamicModelData("Title-001", "Description-001"));
        modelDrawerItems.add(onGetSetDynamicModelData("Title-002", "Description-002"));
        modelDrawerItems.add(onGetSetDynamicModelData("Title-003", "Description-003"));
        adapterLstDrawer = new DynamicArrayAdapter(context, R.layout.lay_row_grid, modelDrawerItems);
        ArrayList<DynamicArrayAdapter.ModelRowViewHolder> listRowViewFields = null;
        listRowViewFields = new ArrayList<DynamicArrayAdapter.ModelRowViewHolder>();
        listRowViewFields.add(adapterLstDrawer.onGetSetModelRowViewData(new TextView(context), "sysTvRowTitle", ""));
        listRowViewFields.add(adapterLstDrawer.onGetSetModelRowViewData(new TextView(context), "sysTvRowDesc", ""));
        adapterLstDrawer.onSetListRowViewFields(new DynamicArrayAdapter.OnFieldListenerHandler() {
            @Override
            public void onSetFieldValue(ArrayList<DynamicArrayAdapter.ModelRowViewHolder> argListRowViewFields, Object argObject) {
                if (argObject instanceof DynamicModel) {
                    //System.out.println("|----|------------|GET_POSITION|----|");
                    DynamicModel item = (DynamicModel) argObject;
                    TextView rowField = null;
                    if (argListRowViewFields.size() > 0) {
                        rowField = (TextView) argListRowViewFields.get(0).getFieldObject();
                        rowField.setText(item.getTitle());
                        rowField = (TextView) argListRowViewFields.get(1).getFieldObject();
                        rowField.setText(item.getDescription());
                    }
                }
            }
        }, listRowViewFields);
        sysDrawerList.setAdapter(adapterLstDrawer);
        //|------------------------------------------------------------|
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, sysDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            /* Called when drawer is closed */
            public void onDrawerClosed(View view) {
                //Put your code here
                invalidateOptionsMenu();
            }

            /* Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
                //Put your code here
                invalidateOptionsMenu();
            }
        };
        /*actionBarDrawerToggle.setDrawerIndicatorEnabled(false); //disable "hamburger to arrow" drawable
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_drawer); //set your own*/
        actionBarDrawerToggle.syncState();
        sysDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        /*actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sysDrawerLayout.openDrawer(GravityCompat.START);
            }
        });*/
        //|------------------------------------------------------------|
        sysDrawerList.setOnItemClickListener(new NavigationDrawerClickListener());
        //|------------------------------------------------------------|
        //|------------------------------------------------------------|
    }

    //|------------------------------------------------------------|
    private class NavigationDrawerClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> argParent, View argView, int argPosition, long argId) {
            Toast.makeText(context, "NAVIGATION_DRAWER_POSITION: " + argPosition, Toast.LENGTH_LONG).show();
            sysDrawerList.setItemChecked(argPosition, true);
            sysDrawerLayout.closeDrawer(sysIdDrawerContainer);
        }
    }

    //|------------------------------------------------------------|
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    //|------------------------------------------------------------|
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        //Toast.makeText(context, "Pressed", Toast.LENGTH_LONG).show();
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    //|------------------------------------------------------------|
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    //|------------------------------------------------------------|
    public DynamicModel onGetSetDynamicModelData(String argTitle, String argDescription) {
        return new DynamicModel(argTitle, argDescription);
    }
    //|------------------------------------------------------------|
}
