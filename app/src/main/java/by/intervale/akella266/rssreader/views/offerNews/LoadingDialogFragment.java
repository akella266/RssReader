package by.intervale.akella266.rssreader.views.offerNews;

import android.app.Dialog;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import javax.inject.Inject;

import by.intervale.akella266.rssreader.R;

public class LoadingDialogFragment extends DialogFragment {

    @Inject
    public LoadingDialogFragment() {}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle(R.string.dialog_title);
        pd.setMessage(getString(R.string.dialog_message));
        return pd;
    }
}
