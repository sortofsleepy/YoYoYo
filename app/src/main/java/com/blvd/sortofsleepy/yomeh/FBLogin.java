package com.blvd.sortofsleepy.yomeh;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

import org.w3c.dom.Text;

import java.util.Arrays;

/**
 * Created by sortofsleepy on 8/1/14.
 */
public class FBLogin extends Fragment {

    Typeface gotham = null;
    private Context ctx;

    private static final String TAG ="FBLogin";
    private UiLifecycleHelper uiHelper;


    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    public FBLogin(){}
    void setCtx(Context activity){
        ctx = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        //need to mod the log-in button a bit
        LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
        authButton.setFragment(this);

        authButton.setReadPermissions(Arrays.asList(
                "user_friends"
        ));


       /* //setup typeface
        if(gotham == null){
            gotham = Typeface.createFromAsset(ctx.getAssets(),"fonts/GothamBold.ttf");
        }
        TextView title = (TextView)view.findViewById(R.id.titleText);
        title.setTypeface(gotham);*/
        return view;
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            //open up the home activity
            Intent intent = new Intent(ctx,Home.class);
            startActivity(intent);
            Log.i(TAG, "Logged In.. divierting to home");
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }

        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
}
