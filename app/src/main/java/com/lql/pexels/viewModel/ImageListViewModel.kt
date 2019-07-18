package com.lql.pexels.viewModel

import com.lql.pexels.base.ActionStatus
import com.lql.pexels.model.Photo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.*

fun List<Photo>.toViewModels(): List<ImageViewModel> {
    return mapNotNull {
        ImageViewModel(it)
    }
}

interface ImageListViewModel<T: ImageViewModel> {
    val columnCount: Int
    val emptyContextText: BehaviorSubject<String>
    val viewModels: BehaviorSubject<List<T>>
    val status: PublishSubject<ActionStatus>
    val selectedViewModel: PublishSubject<T>
    val perPage : Int
    var pageIndex : Int

    fun fetchAction(offset: Int): Observable<List<T>>?
    fun fetch(reset:Boolean = false): Observable<List<T>>? {
        val observable = fetchAction(pageIndex * perPage)?.subscribeOn(Schedulers.io())
        observable?.also {
            it.doOnSubscribe { status.onNext(ActionStatus.Start()) }
                .subscribe({
                    status.onNext(ActionStatus.Completed())
                    // publish a empty list to make the adapter to clear the RecyclerView
                    if (reset) {
                        viewModels.onNext(emptyList())
                    }
                    viewModels.onNext(it)
                }, {
                    it.printStackTrace()
                    status.onNext(ActionStatus.Failed(it))
                })
        }
        return observable
    }
}
