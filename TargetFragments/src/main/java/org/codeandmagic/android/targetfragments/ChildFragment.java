package org.codeandmagic.android.targetfragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.MessageFormat;

/**
 * Created by evelyne24 on 13/10/2013.
 */
public class ChildFragment extends Fragment {

    private static final int COLOR_OK = Color.parseColor("#1e7e62");
    private static final int COLOR_NOK = Color.parseColor("#e82741");
    private static final String SAVED_TARGET_FRAGMENT = "saved_target_fragment";

    public static final String EXPECTED_TARGET = ParentFragment.class.getName();

    private final static SpannableStringBuilder debug = new SpannableStringBuilder();
    private TextView debugView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printTargetFragment("onCreate()");

        if(savedInstanceState != null) {
            printTargetFragment("onCreate() - after rotation");
            Bundle bundle = savedInstanceState.getBundle(SAVED_TARGET_FRAGMENT);
            Fragment targetFragment = getFragmentManager().getFragment(bundle, SAVED_TARGET_FRAGMENT);

            printTargetFragment("onCreate() - restored from bundle",
                    targetFragment != null ? targetFragment.getClass().getName() : "null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        debugView = (TextView) view.findViewById(R.id.child_debug);
        debugView.setMovementMethod(new ScrollingMovementMethod());
        printTargetFragment("onViewCreated()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        printTargetFragment("onSaveInstanceState()");
        Bundle bundle = new Bundle();
        getFragmentManager().putFragment(bundle, SAVED_TARGET_FRAGMENT, getTargetFragment());
        outState.putBundle(SAVED_TARGET_FRAGMENT, bundle);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        printTargetFragment("onActivityCreated()");
    }

    private void printTargetFragment(String method) {
        final Fragment targetFragment = getTargetFragment();
        printTargetFragment(method, targetFragment != null ? targetFragment.getClass().getName() : "null");
    }

    private void printTargetFragment(String method, String targetFragment) {
        printDebug(MessageFormat.format("[{0}] Target Fragment:\n", method), targetFragment);
    }

    private void printDebug(String message, String targetFragment) {
        debug.append(message);
        if (EXPECTED_TARGET.equals(targetFragment)) {
            SpannableString span = new SpannableString(targetFragment);
            span.setSpan(new ForegroundColorSpan(COLOR_OK), 0, targetFragment.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            debug.append(span);
        } else {
            SpannableString span = new SpannableString(targetFragment);
            span.setSpan(new ForegroundColorSpan(COLOR_NOK), 0, targetFragment.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            debug.append(span);
        }
        debug.append("\n\n");

        if(debugView != null) {
            debugView.setText(debug);
        }
    }
}
