package by.intervale.akella266.rssreader.views.main;

import android.app.Dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.rssreader.R;
import by.intervale.akella266.rssreader.data.NewsRepository;
import by.intervale.akella266.rssreader.data.Source;
import by.intervale.akella266.rssreader.data.callbacks.SourceSavedCallback;
import by.intervale.akella266.rssreader.util.events.SourceAddedEvent;

public class AddSourceDialogFragment extends DialogFragment {

    private Unbinder unbinder;
    @BindView(R.id.edit_text_url_source)
    EditText mEditText;
    @Inject
    NewsRepository mNewsRepository;

    @Inject
    public AddSourceDialogFragment() {}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_fragment_add_source, null ,false);
        unbinder = ButterKnife.bind(this, view);
        builder.setView(view);
        builder.setPositiveButton("OK", (dialogInterface, i) -> {});
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null){
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view ->{
                if(!Patterns.WEB_URL.matcher(mEditText.getText().toString()).matches() ||
                        !mEditText.getText().toString().matches("^(http|https)://.*")) {
                    Log.e("Dialog", "invalidUrl");
                    mEditText.setError(getString(R.string.error_invalid_url));
                } else{
                    Log.d("Dialog", "valid url");
                    String url = mEditText.getText().toString();
                    if (!url.endsWith("/")) url = url.concat("/");
                    Source source = new Source(url);
                    mNewsRepository.addSource(source, new SourceSavedCallback() {
                        @Override
                        public void onSaved() {
                            EventBus.getDefault().postSticky(new SourceAddedEvent());
                        }

                        @Override
                        public void onError() {}
                    });
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
