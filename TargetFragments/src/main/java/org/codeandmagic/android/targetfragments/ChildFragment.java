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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private static final String EXPECTED_TARGET_FRAGMENT = ParentFragment.class.getName();
    private final static SpannableStringBuilder log = new SpannableStringBuilder();
    private TextView logView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        log("onCreate()");

        if(savedInstanceState != null) {
            log("onCreate() - after rotation");

            final Bundle bundle = savedInstanceState.getBundle(SAVED_TARGET_FRAGMENT);
            final Fragment targetFragment = getFragmentManager().getFragment(bundle, SAVED_TARGET_FRAGMENT);

            log("onCreate() - restored from bundle", targetFragment != null ? targetFragment.getClass().getName() : "null");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.child_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(R.id.action_clear == item.getItemId()) {
            clearLog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logView = (TextView) view.findViewById(R.id.child_debug);
        logView.setMovementMethod(new ScrollingMovementMethod());
        log("onViewCreated()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        final Bundle bundle = new Bundle();
        getFragmentManager().putFragment(bundle, SAVED_TARGET_FRAGMENT, getTargetFragment());
        outState.putBundle(SAVED_TARGET_FRAGMENT, bundle);

        log("onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        log("onActivityCreated()");
    }

    private void log(String method) {
        final Fragment targetFragment = getTargetFragment();
        log(method, targetFragment != null ? targetFragment.getClass().getName() : "null");
    }

    private void log(String method, String targetFragment) {
        appendLog(MessageFormat.format("[{0}] Target Fragment:\n", method), targetFragment);
    }

    private void appendLog(String message, String targetFragment) {
        log.append(message);

        if (EXPECTED_TARGET_FRAGMENT.equals(targetFragment)) {
            SpannableString span = new SpannableString(targetFragment);
            span.setSpan(new ForegroundColorSpan(COLOR_OK), 0, targetFragment.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            log.append(span);
        } else {
            SpannableString span = new SpannableString(targetFragment);
            span.setSpan(new ForegroundColorSpan(COLOR_NOK), 0, targetFragment.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            log.append(span);
        }
        log.append("\n\n");

        if(logView != null) {
            logView.setText(log);
        }
    }

    private void clearLog() {
        log.clear();
        if(logView != null) {
            logView.setText("");
        }
    }
}
