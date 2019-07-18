package com.lql.pexels.view

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.lql.pexels.R
import com.lql.pexels.viewModel.ImageListViewModel
import com.lql.pexels.viewModel.ImageViewModel
import com.lql.pexels.viewModel.SearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_image_list.*

class ImageListFragment : Fragment() {
    private lateinit var viewModel: ImageListViewModel<ImageViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNCHECKED_CAST")
        viewModel = arguments?.getParcelable<Parcelable>(ARG_VIEW_MODEL)
            ?.let { it as? ImageListViewModel<ImageViewModel> } ?: return

        viewModel.selectedViewModel.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                displayMediaDetail(it)
            }.addTo(disposes)

        viewModel.fetch(reset = true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image_list?.run {
            adapter = ImageListRecyclerViewAdapter(viewModel)
            this.layoutManager = GridLayoutManager(context, 3)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_list, container, false)
    }

    private val disposes = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposes.clear()
    }

    private fun displayMediaDetail(viewModel: ImageViewModel) {
    }

    companion object {
        private const val ARG_VIEW_MODEL = "view-model"

        @JvmStatic
        fun newInstance(viewModel: SearchViewModel) = ImageListFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_VIEW_MODEL, viewModel)
            }
        }
    }
}
