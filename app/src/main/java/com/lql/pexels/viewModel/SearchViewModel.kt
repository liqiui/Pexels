package com.lql.pexels.viewModel

import android.os.Parcelable
import com.lql.pexels.base.ActionStatus
import com.lql.pexels.network.ContentApiService
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class SearchViewModel(override val columnCount: Int)
    : ImageListViewModel<ImageViewModel>, Parcelable {
    @IgnoredOnParcel
    override var pageIndex: Int = 0

    @IgnoredOnParcel
    override var perPage: Int = 30

    @IgnoredOnParcel
    private var currentSearchObservable: Observable<List<ImageViewModel>> = Observable.just(listOf())

    @IgnoredOnParcel
    override val viewModels: BehaviorSubject<List<ImageViewModel>> = BehaviorSubject.create()

    @IgnoredOnParcel
    override val status: PublishSubject<ActionStatus> = PublishSubject.create()

    @IgnoredOnParcel
    override val selectedViewModel: PublishSubject<ImageViewModel> = PublishSubject.create()

    @IgnoredOnParcel
    override val emptyContextText: BehaviorSubject<String> = BehaviorSubject.create()

    init {
    }

    @IgnoredOnParcel
    var query: String? = null
        set(value) {
            field = value
            pageIndex = 1
            fetch(reset = true)
        }

    @IgnoredOnParcel
    private val repository = ContentApiService.create(mapOf(
        "per_page" to 30
    ))

    override fun fetchAction(offset:Int): Observable<List<ImageViewModel>>? {

        currentSearchObservable.unsubscribeOn(Schedulers.io())
        currentSearchObservable = query?.let {
            repository.search(query = it,
                pageIndex = pageIndex)
                .map { it.photos.toViewModels() }
            .doOnError {}
            .onErrorReturn { listOf() }
                .subscribeOn(Schedulers.single())
        } ?: Observable.just(listOf())

        return currentSearchObservable
    }
}
