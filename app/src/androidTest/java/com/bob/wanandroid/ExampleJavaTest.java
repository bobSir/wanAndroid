package com.bob.wanandroid;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.viewpager2.widget.ViewPager2;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * created by cly on 2022/3/4
 */
@RunWith(AndroidJUnit4.class)
public class ExampleJavaTest {
    @Test
    public void useAppContext() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ViewPager2 viewPager2 = new ViewPager2(targetContext);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
}
