package com.bob.wanandroid.demo.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bob.wanandroid.R
import com.bob.wanandroid.databinding.ActivityDatabindingDemoBinding

/**
 * created by cly on 2020/11/30
 */
class DataBindingDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDatabindingDemoBinding = DataBindingUtil.setContentView(this, R.layout.activity_databinding_demo)
        val user = User("jake", 1)
        binding.user = user
    }
}