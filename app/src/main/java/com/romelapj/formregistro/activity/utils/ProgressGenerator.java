package com.romelapj.formregistro.activity.utils;

import android.os.Handler;

import com.dd.processbutton.ProcessButton;

import java.util.Random;

/**
 * Created by romelapj on 7/05/15.
 */
public class ProgressGenerator {
    Handler handler;
    ProcessButton btnSignIn;
    public interface OnCompleteListener {

        public void onComplete();
        public void onError();
    }

    private OnCompleteListener mListener;
    private int mProgress;

    public ProgressGenerator(OnCompleteListener listener) {
        mListener = listener;
    }

    public void start(final ProcessButton button) {
        btnSignIn=button;
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                if(btnSignIn.getError()!="Cancelado ...") {
                    btnSignIn.setProgress(mProgress);
                    if (mProgress < 100) {
                        handler.postDelayed(this, generateDelay());
                    } else {
                        mListener.onComplete();
                    }
                }else{
                    btnSignIn.setProgress(0);
                    mListener.onError();
                }
            }
        }, generateDelay());
    }
    public void error() {
        btnSignIn.setError("Cancelado ...");
    }

    private Random random = new Random();

    private int generateDelay() {
        return random.nextInt(1000);
    }
}
