package com.example.cai.longstopevent;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

public class LongClickStopActivity extends AppCompatActivity {


    private TasksCompleteView mTasksView;

    private int mTotalProgress;
    private int mCurrentProgress;
    private Handler mHandler = new Handler();
    private boolean isClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_long_click_stop);
        initVariable();
        initView();

        // new Thread(new ProgressRunable()).start();
    }


    private void initVariable() {
        mTotalProgress = 100;
        mCurrentProgress = 0;
    }

    private void initView() {
        mTasksView = (TasksCompleteView) findViewById(R.id.tasks_view);
        mTasksView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {// 返回true的话，单击事件、长按事件不可以被触发
                    case MotionEvent.ACTION_DOWN:
                        isClick = true;
                        mCurrentProgress = 0;
                        startplay();
                        return true;
                    case MotionEvent.ACTION_UP:
                        isClick = false;

                        break;

                    default:
                        break;
                }

                return false;
            }

        });

    }

    private void startplay() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mCurrentProgress < mTotalProgress) {

                    if (isClick) {// 一直长按
                        mCurrentProgress += 1;
                        mHandler.postDelayed(this, 50);
                        mTasksView.setProgress(mCurrentProgress);
                    } else {// 中途放弃长按
                        if (mCurrentProgress >= 50) {// 进度超过50%直接走到100%，
                            mCurrentProgress += 1;
                            mHandler.postDelayed(this, 10);
                            mTasksView.setProgress(mCurrentProgress);
                        } else {// 进度没到50%重置为0
                            mCurrentProgress = 0;
                            mTasksView.setProgress(mCurrentProgress);
                        }
                    }
                }
            }
        });

    }

    boolean isFmActive() {
        final AudioManager am = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        if (am == null) {
            Log.w("", "isFmActive: couldn't get AudioManager reference");
            return false;
        }
        return am.isFmActive();
    }








}
