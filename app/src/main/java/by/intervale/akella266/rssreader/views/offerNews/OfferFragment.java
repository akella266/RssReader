package by.intervale.akella266.rssreader.views.offerNews;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import by.intervale.akella266.rssreader.R;

import static android.app.Activity.RESULT_OK;

public class OfferFragment extends Fragment
        implements OfferContract.View{

    private static final int SERVICE_LATENCY_IN_MILLIS = 2000;

    Unbinder unbinder;

    @BindView(R.id.tabs_offer)
    TabLayout mTabs;
    @BindView(R.id.edit_text_news)
    EditText mEditTextNews;
    @BindView(R.id.edit_text_phone)
    EditText mEditTextPhone;
    @BindView(R.id.edit_text_email)
    EditText mEditTextEmail;
    @BindView(R.id.image_button_add_photo)
    ImageView mImageView;

    @Inject
    OfferContract.Presenter mPresenter;
    @Inject
    LoadingDialogFragment mDialog;

    private Bitmap mSelectedImage;

    @Inject
    public OfferFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer, container, false);
        unbinder = ButterKnife.bind(this, view);
        mImageView.setOnClickListener(view1 -> {
            if (mSelectedImage == null) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 200);
            }
            else{
                mSelectedImage = null;
                mImageView.setImageResource(R.drawable.ic_photo);
                showMessage(getString(R.string.image_deleted));
            }
        });

        mEditTextPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.dropView();
    }

    @OnClick(R.id.button_send)
    void buttonSendClick() {
        if (mEditTextNews.getText().length() == 0){
            showMessage(getString(R.string.error_news_empty));
            return;
        }
        if (mEditTextEmail.getText().length() == 0 &&
                mEditTextPhone.getText().length() == 0) {
            showMessage(getString(R.string.error_email_phone_empty));
            return;
        }
        if ((mEditTextPhone.getText().length() != 0 &&
                !mPresenter.isValidePhoneNumber(mEditTextPhone.getText().toString()))) {
            mEditTextPhone.setError(getString(R.string.error_invalid_phone_number));
            return;
        }
        if (mEditTextEmail.getText().length() != 0 &&
                !mPresenter.isValideEmail(mEditTextEmail.getText().toString())) {
            mEditTextEmail.setError(getString(R.string.error_invalid_email));
            return;
        }
        mPresenter.openSending();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 200:{
                if (resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = data.getData();
                        if (imageUri != null) {
                            final InputStream imageStream;
                            imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                            mSelectedImage = BitmapFactory.decodeStream(imageStream);
                            mImageView.setImageResource(R.drawable.ic_done);
                        }
                        else{
                            showMessage(getString(R.string.error_image_not_attached));
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void showSending() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null)
            mDialog.show(activity.getSupportFragmentManager(), "Group");
        Handler handler = new Handler();
        handler.postDelayed(() -> mPresenter.closeSending(), SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void closeSending() {
        mDialog.dismiss();
        mPresenter.openComplete();
    }

    @Override
    public void showComplete() {
        showMessage(getString(R.string.sending_completed));
        mPresenter.initReset();
    }

    @Override
    public void resetFields() {
        mEditTextNews.setText("");
        mEditTextEmail.setText("");
        mEditTextPhone.setText("");
        mSelectedImage = null;
        mImageView.setImageResource(R.drawable.ic_photo);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mTabs, message, Snackbar.LENGTH_SHORT).show();
    }
}
