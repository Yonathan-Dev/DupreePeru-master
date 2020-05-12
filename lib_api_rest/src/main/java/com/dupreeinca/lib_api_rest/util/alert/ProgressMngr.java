package com.dupreeinca.lib_api_rest.util.alert;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.dupreeinca.lib_api_rest.R;
import com.dupreeinca.lib_api_rest.util.helpers.WindowHelpers;


/**
 * Created by steveparrish on 3/6/17.
 */
public class ProgressMngr {
    private String TAG = ProgressMngr.class.getName();

    private static ProgressMngr singleton;
    private Activity activity = null;
    public final Downloads downloads;
    public final Busy busy;

    private ProgressMngr(final Activity activity) {
        this.activity = activity;
        downloads = new Downloads();
        busy = new Busy();
    }

    static public synchronized ProgressMngr getInstance(final Activity activity) {
        if (singleton == null) {
            singleton = new ProgressMngr(activity);
        }
        return singleton;
    }

    public void destroy() {
        singleton = null;
    }

    public class Downloads {

        private int progressRate = 0;
        private ProgressDialog dialog = null;

        Downloads() {
            dialog = createDialog(activity);
        }

        private ProgressDialog createDialog(final Activity activity) {
            ProgressDialog p = new ProgressDialog(activity);
            p.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            p.setTitle(activity.getString(R.string.progress_manual_download_title));
            p.setMessage(activity.getString(R.string.progress_manual_download_message));
            p.setCancelable(false);
            return p;
        }

        public void show() {
            if (!dialog.isShowing()) {
                WindowHelpers.lockCurrentOrientation(activity);
                dialog.show();
            }
        }

        public void dismiss() {
            dialog.setProgress(0);
            progressRate = 0;
            if (dialog.isShowing()) {
                WindowHelpers.unlockCurrentOrientation(activity);
                dialog.dismiss();
            }
        }

        public void updateProgress() {
            dialog.incrementProgressBy(progressRate);
        }

        public void setProgressRate(int progressRate) {
            this.progressRate = progressRate;
        }

        public void setProgress(int progress) {
            dialog.setProgress(progress);
        }

        public ProgressDialog getDialog() {
            return dialog;
        }
    }

    public class Busy {
        private final ProgressDialog progress;

        private Busy() {
            progress = createDialog(activity);
        }

        private ProgressDialog createDialog(final Activity activity) {
            ProgressDialog p = new ProgressDialog(activity, R.style.MyProgressDialogTheme);
            p.setIndeterminate(true);
            p.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
            p.setCancelable(false);
            return p;
        }

        public void show() {
//            WindowHelpers.lockCurrentOrientation(activity);
            Log.e(TAG, "show()");
            progress.show();
        }

        public void dismiss() {
            if (!activity.isFinishing() && !activity.isDestroyed() && progress.isShowing()) {
                progress.dismiss();
            }
            WindowHelpers.unlockCurrentOrientation(activity);
        }

        public void showNoLock() {
            progress.show();
        }

        public void dismissNoUnlock() {
            if (progress.isShowing()) {
                progress.dismiss();
            }
        }

        public ProgressDialog getDialog() {
            return progress;
        }
    }
}
