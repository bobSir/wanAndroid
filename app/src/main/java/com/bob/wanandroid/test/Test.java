package com.bob.wanandroid.test;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * created by cly on 2022/7/5
 */
class Test {
    private void test() {
        InputFilter inputFilter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                if (" ".contentEquals(charSequence)) {
                    return "";
                }
                return null;
            }
        };
    }
}
