package com.bob.wanandroid.topic.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bob.base.vm.BaseViewModel
import com.bob.lnetwork.entity.Chapter
import com.bob.lnetwork.entity.base.RespResult
import com.bob.wanandroid.topic.repository.TopicLocalRepository
import com.bob.wanandroid.topic.repository.TopicRemoteRepository
import com.bob.wanandroid.topic.repository.TopicRepository
import kotlinx.coroutines.launch

/**
 * created by cly on 2022/3/16
 */
class TopicViewModel : BaseViewModel() {
    private val repository: TopicRepository = TopicRepository(TopicRemoteRepository(), TopicLocalRepository())

    val chapters: MutableLiveData<List<Chapter>> = MutableLiveData<List<Chapter>>()

    fun fetchChapters() {
        viewModelScope.launch {
            when (val result = repository.fetchChapters()) {
                is RespResult.Success -> chapters.value = result.data
            }
        }
    }

    fun fetchChapterArticle() {

    }

}