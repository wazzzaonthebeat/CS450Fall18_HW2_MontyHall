package apps.wazzzainvst.montyhall;


import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import apps.wazzzainvst.montyhall.MainFragment.OnFragmentInteractionListener;

public class MainActivity
        extends AppCompatActivity
        implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}

