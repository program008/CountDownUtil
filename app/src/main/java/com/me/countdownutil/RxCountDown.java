package com.me.countdownutil;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class RxCountDown {

    public static Observable<Integer> countdown(int time) {
        if (time < 0) time = 0;

        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {
                        @Override
                        public Integer apply(@NonNull Long increaseTime) throws Exception {
                                Log.d("RxCountDown", "increaseTime.intValue():" + increaseTime.intValue());
                                return countTime - increaseTime.intValue();
                        }
                })
                .take(countTime + 1);

    }
}