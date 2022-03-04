package com.bob.wanandroid.home.ui.fragment

import android.os.Handler
import androidx.fragment.app.viewModels
import com.bob.base.ui.BaseFragment
import com.bob.common.listloadmore.helper.PagingViewHelper
import com.bob.common.listloadmore.holder.WanLoadMoreFooter
import com.bob.wanandroid.R
import com.bob.wanandroid.home.ui.adapter.QAAdapter
import com.bob.wanandroid.home.vm.QAViewModel
import kotlinx.android.synthetic.main.fragment_q_a.*

/**
 * created by cly on 2022/1/25
 */
class QAFragment : BaseFragment() {

    private val viewModel: QAViewModel by viewModels()

    private val adapter: QAAdapter = QAAdapter()

    override val layoutId: Int = R.layout.fragment_q_a

    override fun initView() {
        val loadMoreFooter = WanLoadMoreFooter.create(rv_qa)
        PagingViewHelper.listen(this, viewModel, refresh_qa, loadMoreFooter, adapter)
        loadMoreFooter.addToRecyclerView(rv_qa)
        rv_qa.adapter = adapter
    }

    override fun subscribeUi() {
//        viewModel.refresh()
//        Handler().postDelayed({
        viewModel.loadCache()
//        }, 50)
    }
}