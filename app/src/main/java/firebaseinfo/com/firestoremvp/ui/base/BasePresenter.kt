package firebaseinfo.com.firestoremvp.ui.base

import javax.inject.Inject

open class BasePresenter<T : MvpView> @Inject constructor():
    MvpPresenter<T> {

    var mvpView: T? = null
        private set

    val isViewAttached: Boolean
        get() = mvpView != null

    override fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    override fun detachView() {
        mvpView = null
    }

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException :
        RuntimeException("Please call MvpPresenter.attachView(MvpView) before" + " requesting data to the MvpPresenter")
}
