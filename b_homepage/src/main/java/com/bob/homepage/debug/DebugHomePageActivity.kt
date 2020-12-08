package com.bob.homepage.debug

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bob.homepage.R

/**
 * module独立运行 页面入口
 */
class DebugHomePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_ac_debug_page)
    }
}