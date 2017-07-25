package com.me.countdownutil;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

        private long mStartTime;

        private int index;

        private long startTime;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
//                countDown();
               // Logger.e("MainActivity", "System.currentTimeMillis():" + System.currentTimeMillis());

                countdown2();

        }

        private void countdown2() {
                RxCountDown.countdown(120)
                        .doOnSubscribe(new Consumer<Disposable>() {
                                @Override
                                public void accept(Disposable disposable) throws Exception {
                                        Log.d("MainActivity", "即时开始");
                                        startTime = System.currentTimeMillis();
                                }
                        })
                        .subscribe(new Observer<Integer>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {
                                        Log.d("MainActivity", "记时开始");
                                }

                                @Override
                                public void onNext(@NonNull Integer integer) {
                                        Log.d("MainActivity", "当前时间"+integer);
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                        Log.d("MainActivity", "计时结束");
                                        Log.d("MainActivity", "System.currentTimeMillis()-startTime:" + (System.currentTimeMillis() - startTime)/1000.0);
                                }
                        });
        }

        private void countDown() {
                mStartTime = System.currentTimeMillis();
                index = 120;

                new CountDownTimer(120*1000,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                                Log.d("MainActivity", "l:" + Math.round((millisUntilFinished/1000.0)));
                                Log.d("MainActivity", "index:" + (index--));
                        }

                        @Override
                        public void onFinish() {
                                Log.d("MainActivity", "index:" + 0);
                                long endTime = System.currentTimeMillis();
                                Log.d("MainActivity", "endTime-mStartTime:" + (endTime - mStartTime)/1000.0);
                        }
                }.start();
        }
}
