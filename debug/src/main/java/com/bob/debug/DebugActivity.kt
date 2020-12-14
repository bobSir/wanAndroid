package com.bob.debug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bob.debug.databinding.DebugActivityDebugBinding

class DebugActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<DebugActivityDebugBinding>(this, R.layout.debug_activity_debug);
    }
}