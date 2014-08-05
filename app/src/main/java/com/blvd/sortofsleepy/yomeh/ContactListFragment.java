package com.blvd.sortofsleepy.yomeh;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class ContactListFragment extends ListFragment implements LoaderCallbacks<Cursor> {

    private CursorAdapter mAdapter;
    private Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create adapter once
        Context context = getActivity();
        int layout = android.R.layout.simple_list_item_1;
        Cursor c = null; // there is no cursor yet
        int flags = 0; // no auto-requery! Loader requeries.
        mAdapter = new SimpleCursorAdapter(context, layout, c, FROM, TO, flags);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // each time we are started use our listadapter
        setListAdapter(mAdapter);
        // and tell loader manager to start loading
        getLoaderManager().initLoader(0, null, this);
    }

    // columns requested from the database
    private static final String[] PROJECTION = {
            Contacts._ID, // _ID is always required
            Contacts.DISPLAY_NAME_PRIMARY // that's what we want to display
    };

    // and name should be displayed in the text1 textview in item layout
    private static final String[] FROM = {Contacts.DISPLAY_NAME_PRIMARY};
    private static final int[] TO = {android.R.id.text1};

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // load from the "Contacts table"
        Uri contentUri = Contacts.CONTENT_URI;

        // no sub-selection, no sort order, simply every row
        // projection says we want just the _id and the name column
        return new CursorLoader(getActivity(),
                contentUri,
                PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Once cursor is loaded, give it to adapter
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // on reset take any old cursor away
        mAdapter.swapCursor(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor selectedFromList =(Cursor) (l.getItemAtPosition(position));
        Log.i("NAME SELECTED FROM LIST WAS", selectedFromList.getString(1));
        getDetails(selectedFromList.getString(1));

    }

    /**
     * Retrieves the phone number for a user.
     * @param NAME{STRING} the name of the user
     */
    public void getDetails(String NAME){
        Log.i("The name of the user picked is :", NAME);


        String ret = null;
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" like'%" + NAME +"%'";
        String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor c = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, selection, null, null);
        if (c.moveToFirst()) {
            ret = c.getString(0);
            Log.i(NAME + "PHONE NUMBER IS :",ret);
        }


        c.close();
        if(ret==null){

            CharSequence sequence = "Cannot load details or user doesn't have phone number";
            int duration = 2;
            Toast toast = Toast.makeText(context,sequence,Toast.LENGTH_SHORT);
            toast.show();
        }else{
            //show send screen.
            String test = ret.replaceAll("(?s).","");
            Log.d("TESTSTRING",test);

            SmsManager sms = SmsManager.getDefault();
            String number = ret;
            sms.sendTextMessage(number,null,"YoYoYo \n\n- from the YoYoYo app",null,null);

            //start the intent to send you home
            Intent intent = new Intent(context,Home.class);
            intent.putExtra("SMS_SENT",true);
            intent.putExtra("SMS_NUMBER",number);
            startActivity(intent);

        }
        Log.d("END USER FETCH","Got all Contacts");

    }

    /**
     * Sets the current application context
     * @param ctx
     */
    public void setContext(Context ctx){
        context = ctx;
    }
}