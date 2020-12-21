package com.bob.debug

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bob.debug.databinding.DebugActivityDebugBinding
import com.bob.debug.netTest.NetTestActivity
import com.bob.debug.threadTest.ThreadTest
import kotlinx.android.synthetic.main.debug_activity_debug.*

class DebugActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<DebugActivityDebugBinding>(this, R.layout.debug_activity_debug);

        threadTest();

        btn1.setOnClickListener {
            startActivity(Intent(this, NetTestActivity::class.java))
        }
    }

    private fun threadTest() {
//        val threadTest = ThreadTest()
//        Thread {
//            ThreadTest.test1()
//        }.start()
//
//        Thread {
//            threadTest.test2()
//        }.start()
    }
}