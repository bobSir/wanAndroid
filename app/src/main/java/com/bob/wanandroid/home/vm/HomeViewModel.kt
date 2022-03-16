package com.bob.wanandroid.home.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.bob.base.vm.BaseViewModel
import com.bob.wanandroid.home.repository.HomeLocalDataSource
import com.bob.wanandroid.home.repository.HomeRemoteDataSource
import com.bob.wanandroid.home.repository.HomeRepository
import com.bob.wanandroid.home.ui.bean.UiModel
import kotlinx.coroutines.flow.map

/**
 * created by cly on 2022/1/26
 */
class HomeViewModel : BaseViewModel() {
    private val homeRepository: HomeRepository = HomeRepository(HomeRemoteDataSource(), HomeLocalDataSource())

//    val hotArticle: LiveData<PagingData<Article>> =
//        homeRepository.fetchHotArticle().flow.cachedIn(viewModelScope).asLiveData()

    val newResult: LiveData<PagingData<UiModel>> =
        homeRepository.fetchHotArticle().flow
            .map { article -> article.map { UiModel.ArticleItem(it) } }
            .map { pagingData: PagingData<UiModel.ArticleItem> ->
                pagingData.insertSeparators { before, after ->
                    if (after == null) {
                        //footer
                        return@insertSeparators null
                    }
                    if (before == null) {
                        //header
                        return@insertSeparators UiModel.SeparatorItem("header")
                    } else {
                        return@insertSeparators null
                    }
                }
            }
            .cachedIn(viewModelScope)
            .asLiveData()
}