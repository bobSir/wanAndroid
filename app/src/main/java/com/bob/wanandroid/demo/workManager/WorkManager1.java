package com.bob.wanandroid.demo.workManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.aware.PublishConfig;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.bob.wanandroid.R;
import com.bob.wanandroid.databinding.ActivityDatabindingDemoBinding;
import com.bob.wanandroid.demo.databinding.User;

/**
 * 特除服务
 * created by cly on 2020/8/22
 */
public class WorkManager1 extends Worker {
    private Context mContext;
    private WorkerParameters mWorkerParameters;

    public WorkManager1(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Result doWork() {

        //接受数据
        mWorkerParameters.getInputData().getString("key");

        //反馈数据
//        Data build = new Data.Builder().putString("key", "value").build();
//        new Result.Success(build);


        new Result.Retry();//本次执行任务 重试
        new Result.Failure();//失败
        new Result.Success();//成功
        return null;
    }
}
