package com.blvd.sortofsleepy.yomeh;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

import com.blvd.sortofsleepy.yomeh.R;

public class Home extends Activity {

    public static String SMS_KEY = "SMS_SENT";
    public static String SMS_NUMBER = "SMS_NUMBER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            HomeMainFragment fragment = new HomeMainFragment();
            fragment.setContext(this);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }

        /**
         * If we're hitting home from a sent activity,
         * display a toast indicating success or failure.
         */
        if(getIntent().getStringExtra(SMS_KEY) != ""){
            CharSequence message = "Your SMS to the number " + getIntent().getStringExtra(SMS_NUMBER) + " was sent successfully";
            Toast toast = Toast.makeText(this,message, Toast.LENGTH_SHORT);
            toast.show();
        }

        Log.i("HOME","HOME IS LAUNCHED");
    }

    /**
     * Loads a contacts list
     * @param menu
     * @return
     */
    public void loadContacts(View view){
        Intent intent = new Intent(this,Contacts.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class HomeMainFragment extends Fragment {
        Typeface gotham;
        Context ctx;

        public HomeMainFragment(){}

        void setContext(Context context){
            ctx = context;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);

          /*  gotham = Typeface.createFromAsset(ctx.getAssets(), "fonts/GothamBold.ttf");
            TextView title = (TextView)rootView.findViewById(R.id.send_yo);
            TextView title2 = (TextView)rootView.findViewById(R.id.about);
            TextView title3 = (TextView)rootView.findViewById(R.id.set_phrases);

            title.setTypeface(gotham);
            title2.setTypeface(gotham);
            title3.setTypeface(gotham);*/

            return rootView;
        }
    }
}
