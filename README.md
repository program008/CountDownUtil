# CountDownUtil
两种实现倒计时的方法
1，rxjava
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

  2、sdk25中的CountDownTimer
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

  以上两种倒计时的时间差都是毫秒级的，区别在于第二种不能取到最后一秒的，这个在onFinish处理就好
