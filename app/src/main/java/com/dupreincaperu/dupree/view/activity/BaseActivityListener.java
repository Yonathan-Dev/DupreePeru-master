package com.dupreincaperu.dupree.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public interface BaseActivityListener {
    Fragment addFragmentWithBackStack(Class myNewFragmentClass, Boolean withBackstack);
    Fragment replaceFragmentWithBackStack(Class myNewFragmentClass, Boolean withBackstack, Bundle bundle);
    Fragment replaceFragmentWithBackStack(Fragment myNewFragment, Boolean withBackstack, Bundle bundle);
    Fragment replaceFragmentWithBackStackAnimate(Fragment myNewFragment, Boolean withBackstack, Bundle bundle);
    Fragment replaceFragmentWithBackStackAnimation(Class myNewFragmentClass, Boolean withBackstack);
}
