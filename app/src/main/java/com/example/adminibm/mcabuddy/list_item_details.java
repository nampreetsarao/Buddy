package com.example.adminibm.mcabuddy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class list_item_details extends ActionBarActivity {

    Bundle getItemDetails;

    String strHeaderValue;
    String strDetailsValue;

    TextView lblTextItemHeader;
    TextView lblListItemDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_details);

        getItemDetails = getIntent().getExtras();

        //strHeaderValue = getItemDetails.getString("strHeader");
        strDetailsValue = getItemDetails.getString("strDetails");

        lblListItemDetails = (TextView)findViewById(R.id.lblListItemDetails);

        //lblTextItemHeader.setText(strHeaderValue);
        lblListItemDetails.setText(strDetailsValue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_item_details, menu);
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
