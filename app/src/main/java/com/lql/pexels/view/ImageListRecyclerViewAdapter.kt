package com.lql.pexels.view

import android.graphics.Color
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.lql.pexels.R
import com.lql.pexels.viewModel.ImageListViewModel
import com.lql.pexels.viewModel.ImageViewModel
import com.lql.pexels.viewModel.SearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_image_list_item.view.*

class ImageListRecyclerViewAdapter(
    private val mViewModel: ImageListViewModel<ImageViewModel>
)
    : RecyclerView.Adapter<ImageListRecyclerViewAdapter.ImageViewHolder>() {
    private val disposes = CompositeDisposable()
    private var viewModels: MutableList<ImageViewModel> = arrayListOf()
    init {
        mViewModel.viewModels.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                // clear the RecyclerView if the result of new query is coming
                if (it.isEmpty()) {
                    viewModels.clear()
                }
                viewModels.addAll(it)
                notifyDataSetChanged()
            }
            .addTo(disposes)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        disposes.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_image_list_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        if (position == viewModels.size - 1) {
            mViewModel.pageIndex++
            mViewModel.fetch(false)
        }
        else {
            val viewModel = viewModels[position]
            holder.bind(viewModel = viewModel, listViewModel = mViewModel)
        }
    }

    override fun getItemCount(): Int = viewModels.size

    inner class ImageViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        private val mTitleView: TextView = mView.media_title
        private val mThumbnailView: SimpleDraweeView = mView.media_thumbnail as SimpleDraweeView
        fun bind(viewModel: ImageViewModel, listViewModel: ImageListViewModel<ImageViewModel>) {
            mTitleView.setTextColor(Color.WHITE)
            mTitleView.text = viewModel.image.photographer
            mThumbnailView.setImageURI(viewModel.image.src.small)

            mView.setOnClickListener {}
        }
    }
}
