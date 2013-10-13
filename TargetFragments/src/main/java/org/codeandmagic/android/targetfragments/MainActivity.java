package org.codeandmagic.android.targetfragments;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, new ParentFragment(), "parent").commit();
        }
    }
}
