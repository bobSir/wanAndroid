package com.bob.debug

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bob.debug.databinding.DebugActivityDebugBinding
import com.bob.debug.netTest.NetTestActivity
import kotlinx.android.synthetic.main.debug_activity_debug.*

class DebugActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<DebugActivityDebugBinding>(this, R.layout.debug_activity_debug);
        btn1.setOnClickListener {
            startActivity(Intent(this, NetTestActivity::class.java))
        }
    }
}