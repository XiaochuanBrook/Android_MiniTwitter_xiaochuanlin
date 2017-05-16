package com.lin.app.Ui.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lin.app.Presenters.PostPresenter;
import com.lin.app.R;
import com.lin.app.Twitter;
import com.lin.app.Ui.Views.PostView;
import com.lin.app.di.PostActivityModule;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brooklin on 2017-05-09.
 */

public class PostActivity  extends BaseActivity implements PostView {

    private static final int PICK_IMAGE = 101;

    @BindView(R.id.post_textView)
    EditText mPostTextView;
    @BindView(R.id.post_imageView)
    ImageView mPostImageView;
    @BindView(R.id.post_send_button)
    Button mPostSendButton;
    @BindView(R.id.post_image_button)
    View mAddImageButton;
    @BindView(R.id.progress)
    View mProgressBar;

    @Inject
    public PostPresenter presenter;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

        mAddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent picker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picker, PICK_IMAGE);
            }
        });
        mPostSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null) {
                    presenter.postPicture(mPostTextView.getText().toString(), imageUri);
                } else {
                    presenter.post(mPostTextView.getText().toString());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE ) {
            imageUri = getImageUri(data);
            presenter.addPicture(imageUri);
        }
    }

    /**
     * Helper method to get the image uri from image picker
     * @param data intent includes image data
     * @return the usable image uri
     */
    public Uri getImageUri(final Intent data) {
        if (data == null || data.getData() == null) {
            return null;
        } else {
            return data.getData();
        }
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void onImageAdd(final Uri uri) {
        if (mPostImageView != null) {
            if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
                Picasso.with(this).load(uri).into(mPostImageView);
                mPostImageView.setVisibility(View.VISIBLE);
            } else {
                mPostImageView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onSent() {
        presenter.onDestroy();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setupComponent() {
        ((Twitter) Twitter.getAppContext())
                .getAppComponent()
                .getPostActivityComponent(new PostActivityModule(this))
                .inject(this);
    }
}
