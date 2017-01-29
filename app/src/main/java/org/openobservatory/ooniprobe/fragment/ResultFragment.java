package org.openobservatory.ooniprobe.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;
import org.openobservatory.ooniprobe.R;
import org.openobservatory.ooniprobe.activity.ResultActivity;
import org.openobservatory.ooniprobe.utils.Alert;
import org.openobservatory.ooniprobe.utils.LogUtils;

import java.util.ArrayList;

public class ResultFragment extends Fragment {
    private ResultActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mActivity = (ResultActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onViewSelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result, container, false);

        int position = this.getArguments().getInt("position");
        mActivity.setTitle(this.getArguments().getString("title"));
        String json_file = getActivity().getIntent().getExtras().getString("json_file");
        final String parts = LogUtils.getLogParts(getActivity(), json_file, position);

        WebView wv = (WebView) v.findViewById(R.id.webview);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(new Alert.InjectedJSON(parts), "MeasurementJSON");
        wv.loadUrl("file:///android_asset/webui/index.html");

        return v;
    }

}
