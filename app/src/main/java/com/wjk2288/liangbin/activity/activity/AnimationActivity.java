package com.wjk2288.liangbin.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wjk2288.liangbin.R;
import com.wjk2288.liangbin.activity.utils.SpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

public class AnimationActivity extends AppCompatActivity {


    @Bind(R.id.gif_animation)
    GifImageView gifAnimation;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);

//        //存储启动状态
//        SpUtils.getInstance(this).save("istart", true);
//        SpUtils.getInstance(this).getBooleanValues("istart");

        countDownTimer = new CountDownTimer(4650, 1000) {
            /**
             * 固定间隔被调用
             * @param millisUntilFinished 倒计时剩余时间
             */
            @Override
            public void onTick(long millisUntilFinished) {

            }

            //定时执行完成的时候回调
            @Override
            public void onFinish() {
                gifAnimation.setVisibility(View.GONE);

                if (isOneStart()) {
                    Intent intent = new Intent(AnimationActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    finish();

                } else {

                    Intent intent = new Intent(AnimationActivity.this, GuideActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    finish();


                }


            }
        }.start();


//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(AnimationActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        }, 5000);


    }

    public boolean isOneStart() {

        return SpUtils.getInstance(this).getBooleanValues("istart");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        countDownTimer.cancel();
    }
}
