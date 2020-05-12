package com.dupreincaperu.dupree.mh_dialogs;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_adapters.AuthenticatePagerAdapter;

/**
 * Created by cloudemotion on 6/8/17.
 */

public class MH_Dialogs_Login extends DialogFragment {

    String TAG = "MH_Dialogs_Login";
    public static final String BROACAST_LOGIN="MH_Dialogs_Login";
    public static final String BROACAST_LOGIN_DATA="reg_data";
    public static final String BROACAST_LOGIN_BTNENTRAR="MH_Dialogs_Login_btnentrar";
    public static final String BROACAST_LOGIN_BTNFORGOT="MH_Dialogs_Login_btnforgot";
    public static final String BROACAST_LOGIN_EXIT="MH_Dialogs_Login_exit";
    ViewPager vp;
    public MH_Dialogs_Login() {
    }


    public static MH_Dialogs_Login newInstance() {
        Bundle args = new Bundle();

        MH_Dialogs_Login fragment = new MH_Dialogs_Login();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        //window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setLayout((int) (size.x * 0.95), (int) (size.y * 0.75));
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }

    int style = DialogFragment.STYLE_NO_TITLE;
    int theme = R.style.MyDialogTransparent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(style, theme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mh_dialogo_pager_user, container);
    }

    ImageButton left_nav, right_nav;
    AuthenticatePagerAdapter mh_pagerAdapter_login;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Retrieve layout elements

        left_nav = (ImageButton) view.findViewById(R.id.left_nav);
        right_nav = (ImageButton) view.findViewById(R.id.right_nav);
        vp = (ViewPager) view.findViewById(R.id.pagerUser);

        mh_pagerAdapter_login = new AuthenticatePagerAdapter(getChildFragmentManager());//se usa chield cuando desde un fragment se llama a otro

        vp.setAdapter(mh_pagerAdapter_login);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case AuthenticatePagerAdapter.PAGE_LOGIN:
                        controlArrow(false, true);
                        break;
                    case AuthenticatePagerAdapter.PAGE_FORGOT:
                        controlArrow(true, false);
                        break;
                    case AuthenticatePagerAdapter.PAGE_CODE:
                        controlArrow(true, false);
                        break;
                    case AuthenticatePagerAdapter.PAGE_PASSWORD:
                        controlArrow(true, false);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        controlArrow(false, true);

    }

    private void controlArrow(Boolean left, Boolean right){
        left_nav.setVisibility(left ? View.VISIBLE : View.INVISIBLE);
        right_nav.setVisibility(right ? View.VISIBLE : View.INVISIBLE);
    }

    public void gotoPage(int posPage){
        vp.setCurrentItem(posPage);
    }


    /*
    public static final int NUM_PAGES=4;
    public static final int PAGE_LOGIN=0;
    public static final int PAGE_IDENTY=1;
    public static final int PAGE_CODE=2;
    public static final int PAGE_NEW_PWD=3;
    */
    /*
    View viewFlipper;
    public class PagerAdapter extends android.support.v4.view.PagerAdapter {

        int numView=0;

        public PagerAdapter(int numView) {
            this.numView = numView;
        }

        @Override
        public int getCount() {
            return numView;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            View page = null;

            switch(position){
                case PAGE_LOGIN:
                    viewFlipper = (ScrollView) LayoutInflater.from(getActivity()).inflate(R.layout.mh_dialog_login, null);
                    EditText txtUsername = (EditText) viewFlipper.findViewById(R.id.txtUsername);
                    EditText txtPwd = (EditText) viewFlipper.findViewById(R.id.txtPwd);
                    Button btnLogin = (Button) viewFlipper.findViewById(R.id.btnLogin);
                    TextView tvForgot = (TextView) viewFlipper.findViewById(R.id.tvForgot);

                    btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(), MenuActivity.class));
                        }
                    });
                    tvForgot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            gotoPage(PAGE_IDENTY);
                        }
                    });

                    break;
                case PAGE_IDENTY:
                    viewFlipper = (ScrollView) LayoutInflater.from(getActivity()).inflate(R.layout.mh_dialog_forgot, null);
                    EditText txtIdenty = (EditText) viewFlipper.findViewById(R.id.txtIdenty);
                    Button btnForgot = (Button) viewFlipper.findViewById(R.id.btnForgot);

                    btnForgot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            gotoPage(PAGE_CODE);
                        }
                    });
                    break;
                case PAGE_CODE:
                    viewFlipper = (ScrollView) LayoutInflater.from(getActivity()).inflate(R.layout.mh_dialog_code, null);
                    EditText txtCode = (EditText) viewFlipper.findViewById(R.id.txtCode);
                    Button btnCode = (Button) viewFlipper.findViewById(R.id.btnCode);

                    btnCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            gotoPage(PAGE_NEW_PWD);
                        }
                    });
                    break;
                case PAGE_NEW_PWD:
                    viewFlipper = (ScrollView) LayoutInflater.from(getActivity()).inflate(R.layout.mh_dialog_new_pwd, null);
                    EditText txtNewPwd = (EditText) viewFlipper.findViewById(R.id.txtNewPwd);
                    EditText txtRepeatNewPwd = (EditText) viewFlipper.findViewById(R.id.txtRepeatNewPwd);
                    Button btnNewPwd = (Button) viewFlipper.findViewById(R.id.btnNewPwd);
                    break;

            }


            page = viewFlipper;
            collection.addView(page, 0);
            return page;
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Log.e("destroyItem",String.valueOf(position));
        }


    }

*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(
                new Intent(BROACAST_LOGIN)
                        .putExtra(BROACAST_LOGIN, BROACAST_LOGIN_EXIT));

    }
}
