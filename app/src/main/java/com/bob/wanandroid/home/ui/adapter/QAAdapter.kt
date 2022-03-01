package com.bob.wanandroid.home.ui.adapter

import android.text.Html
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bob.lnetwork.entity.Article
import com.bob.wanandroid.R

/**
 * created by cly on 2022/2/8
 */
class QAAdapter : ListAdapter<Article, QAAdapter.ViewHolder>(diffCallback) {

    companion object {
        private val diffCallback: DiffUtil.ItemCallback<Article> = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_q_a, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binds(getItem(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val rootView: View = view.findViewById(R.id.root_view)
        private val ivAvatar: ImageView = view.findViewById(R.id.iv_avatar)
        private val tvName: TextView = view.findViewById(R.id.tv_Name)
        private val tvTime: TextView = view.findViewById(R.id.tv_time)
        private val ivLike: ImageView = view.findViewById(R.id.iv_like)
        private val tvTitle: TextView = view.findViewById(R.id.tv_title)
        private val tvContent: TextView = view.findViewById(R.id.tv_content)
        private val tvTag: TextView = view.findViewById(R.id.tv_tag)

        fun binds(article: Article) {
            rootView.setOnClickListener {

            }
            tvName.text = article.author
            tvTime.text = DateUtils.getRelativeTimeSpanString(article.publishTime)
            tvTitle.text = article.title
            if (article.desc.isNotEmpty()) {
                tvContent.visibility = View.VISIBLE
                tvContent.text = Html.fromHtml(article.desc)
            } else {
                tvContent.visibility = View.GONE
            }
            if (article.tags.isNotEmpty()) {
                tvTag.visibility = View.VISIBLE
                tvTag.text = article.tags[0].name
            } else {
                tvTag.visibility = View.GONE
            }
        }
    }
}