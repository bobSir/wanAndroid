package com.bob.wanandroid.home.ui.adapter

import android.text.Html
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bob.lnetwork.entity.Article
import com.bob.wanandroid.R
import com.bob.wanandroid.home.vm.UiModel

/**
 * created by cly on 2022/1/26
 */
class HotArticlePagedAdapter : PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        private val diffCallback: DiffUtil.ItemCallback<UiModel> = object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return (oldItem is UiModel.ArticleItem && newItem is UiModel.ArticleItem &&
                        oldItem.article.id == newItem.article.id) ||
                        (oldItem is UiModel.SeparatorItem && newItem is UiModel.SeparatorItem &&
                                oldItem.description == newItem.description)
            }

            override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    private val itemEventObservable: MutableLiveData<String> = MutableLiveData()

    fun observeItemEvent(): LiveData<String> {
        return itemEventObservable
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.ArticleItem -> R.layout.item_hot_article
            is UiModel.SeparatorItem -> R.layout.item_separator_view_item
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item_hot_article) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hot_article, parent, false)
            HotArticleViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_separator_view_item, parent, false)
            SeparatorViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel.let {
            when (uiModel) {
                is UiModel.ArticleItem -> (holder as HotArticleViewHolder).binds(uiModel.article, position, itemEventObservable)
                is UiModel.SeparatorItem -> (holder as SeparatorViewHolder).bind(uiModel.description)
                else -> {
                }
            }
        }
    }
}

class HotArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val rootView: View = view.findViewById(R.id.root_view)
    private val ivAvatar: ImageView = view.findViewById(R.id.iv_avatar)
    private val tvName: TextView = view.findViewById(R.id.tv_Name)
    private val tvTime: TextView = view.findViewById(R.id.tv_time)
    private val ivLike: ImageView = view.findViewById(R.id.iv_like)
    private val tvTitle: TextView = view.findViewById(R.id.tv_title)
    private val tvContent: TextView = view.findViewById(R.id.tv_content)
    private val tvTag: TextView = view.findViewById(R.id.tv_tag)

    fun binds(data: Article, position: Int, liveData: MutableLiveData<String>) {
        rootView.setOnClickListener {
            liveData.postValue(data.link)
        }

        tvName.text = data.author
        tvTime.text = DateUtils.getRelativeTimeSpanString(data.publishTime)
        tvTitle.text = data.title
        if (data.desc.isNotEmpty()) {
            tvContent.visibility = View.VISIBLE
            tvContent.text = Html.fromHtml(data.desc)
        } else {
            tvContent.visibility = View.GONE
        }
        if (data.tags.isNotEmpty()) {
            tvTag.visibility = View.VISIBLE
            tvTag.text = data.tags[0].name
        } else {
            tvTag.visibility = View.GONE
        }
    }
}

class SeparatorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvSeparator: TextView = view.findViewById(R.id.tv_separator)

    fun bind(s: String) {
        tvSeparator.text = s
    }
}