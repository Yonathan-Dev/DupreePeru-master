package com.dupreincaperu.dupree.view.activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

public interface BaseActivityListener {
    Fragment addFragmentWithBackStack(Class myNewFragmentClass, Boolean withBackstack);
    Fragment replaceFragmentWithBackStack(Class myNewFragmentClass, Boolean withBackstack, Bundle bundle);
    Fragment replaceFragmentWithBackStack(Fragment myNewFragment, Boolean withBackstack, Bundle bundle);
    Fragment replaceFragmentWithBackStackAnimate(Fragment myNewFragment, Boolean withBackstack, Bundle bundle);
    Fragment replaceFragmentWithBackStackAnimation(Class myNewFragmentClass, Boolean withBackstack);
}
