package com.dupreincaperu.dupree.mh_fragments_main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_utilities.mPreferences;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    private final String TAG = "ContactFragment";

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact, container, false);
        WebView webView = (WebView) v.findViewById(R.id.webView);
        //webView.loadUrl("https://azzorti.bo");


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //progDailog.show();
                Log.e(TAG,"Loading");
                view.loadUrl(url);

                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                //progDailog.dismiss();
                Log.e(TAG,"dismiss");
            }
        });

        String url = mPreferences.getUrlEmbeddedChat(getActivity());
        webView.loadUrl(url != null && !url.isEmpty() ? url : "https://dupreincaperu.bo/dupreeWS/chatAPP/chat.html");



        return v;
    }

    public static ContactFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
