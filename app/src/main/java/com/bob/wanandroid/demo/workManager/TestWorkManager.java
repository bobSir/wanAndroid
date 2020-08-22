package com.bob.wanandroid.demo.workManager;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

/**
 * workManager test
 * created by cly on 2020/8/22
 */
public class TestWorkManager {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void test1(Context context) {
        //约束条件
        Constraints builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .setRequiresDeviceIdle(true)
                .build();

        Data sendData = new Data.Builder().putString("key", "value").build();

        //任务对象
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(WorkManager1.class)
//                .setConstraints()
                .setInputData(sendData)
                .build();

        WorkManager.getInstance(context).getWorkInfoByIdLiveData(request.getId())
                .observe((LifecycleOwner) context, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {

                    }
                });


        WorkManager.getInstance(context)
                .enqueue(request);

        //顺序执行
//        WorkManager.getInstance(context).beginWith()
//                .then()
//                .then()
//                .enqueue();

        //并发执行
//        WorkManager.getInstance(context).beginWith(list)


        //重复任务
        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(WorkManager1.class, 15, TimeUnit.SECONDS).build();
        WorkManager.getInstance(context).getWorkInfoByIdLiveData(periodicWorkRequest.getId())
                .observe((LifecycleOwner) context, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo.getState().isFinished()) {

                        }
                    }
                });

        WorkManager.getInstance().enqueue(periodicWorkRequest);
    }
}
