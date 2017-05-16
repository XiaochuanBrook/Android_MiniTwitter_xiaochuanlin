package com.lin.app.Ui.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by brooklin on 2017-05-13.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent();
    }

    protected abstract void setupComponent();


}
