package com.example.adminibm.mcabuddy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MCABuddy_FlashScreen extends ActionBarActivity {

    public TextView MCABuddy_textview;
    Handler handle_MCABuddyFlash = new Handler();
    public Intent MCABuddylogin_Intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcabuddy__flash_screen);

        //assigning TextView object to layout TextView control
        MCABuddy_textview = (TextView) findViewById(R.id.MCABuddy_Title);

        //creating Intent for Profile activity
        MCABuddylogin_Intent = new Intent(MCABuddy_FlashScreen.this, MCABuddy_Login.class);

        //Handler object for redirect after delay
        handle_MCABuddyFlash.postDelayed(MCABuddyRedirect, 1000);
    }

    Runnable MCABuddyRedirect = new Runnable() {
        @Override
        public void run() {
            MoveToLogin();
        }
    };

    public void MoveToLogin(){
        startActivity(MCABuddylogin_Intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mcabuddy__flash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
