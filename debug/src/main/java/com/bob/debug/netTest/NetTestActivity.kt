package com.bob.debug.netTest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bob.debug.R
import com.bob.debug.databinding.DebugActivityNetTestBinding
import kotlinx.android.synthetic.main.debug_activity_net_test.*

class NetTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<DebugActivityNetTestBinding>(this, R.layout.debug_activity_net_test)
        test1.setOnClickListener {
        }
    }
}