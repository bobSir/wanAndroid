package com.bob.common.customView

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import com.bob.lutil.log.BobLog
import com.google.android.material.tabs.TabLayout
import java.lang.reflect.Field

/**
 * fix tabLayout划出一屏选中抖动问题
 * created by cly on 2022/3/16
 */
class FixedTabLayout(
    context: Context,
    attrs: AttributeSet?, defStyleAttr: Int
) : TabLayout(
    context,
    attrs,
    defStyleAttr
) {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private fun initView() {
        try {
            BobLog.d(1)
            val field: Field = TabLayout::class.java.getDeclaredField("pageChangeListener")
            field.isAccessible = true
            field.set(this, FixedTabLayoutOnPageChangeListener(this))
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    class FixedTabLayoutOnPageChangeListener(tabLayout: TabLayout?) : TabLayoutOnPageChangeListener(tabLayout) {
        private var isTouchState = false
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            BobLog.d(11)
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                BobLog.d(2)
                isTouchState = true
            } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                BobLog.d(3)
                isTouchState = false
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            if (isTouchState) {
                BobLog.d(4)
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        }
    }

    init {
        initView()
    }
}