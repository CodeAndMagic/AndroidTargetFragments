package org.codeandmagic.android.targetfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * Created by evelyne24 on 13/10/2013.
 */
public class ParentFragment extends Fragment {

    public static final int PARENT = 100;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.press_me).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showChildFragment();
            }
        });
    }

    private void showChildFragment() {
        ChildFragment child = new ChildFragment();
        child.setTargetFragment(this, PARENT);

        // If getFragmentManager() is used instead, the behaviour is the correct one.

        // Then what's the purpose of getChildFragmentManager() ?
        // From documentation:
        // getChildFragmentManager() Return a private FragmentManager for placing and managing Fragments inside of this Fragment.
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.child_placeholder, child, "child")
                .commit();
    }
}
