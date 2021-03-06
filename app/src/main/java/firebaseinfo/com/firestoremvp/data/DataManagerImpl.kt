package firebaseinfo.com.firestoremvp.data

import firebaseinfo.com.firestoremvp.data.local.DbHelperImpl
import firebaseinfo.com.firestoremvp.data.model.firestore.Social
import firebaseinfo.com.firestoremvp.data.model.firestore.User
import firebaseinfo.com.firestoremvp.data.network.ApiHelperImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.DisposableSubscriber
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class DataManagerImpl @Inject constructor(
    private val apiHelper: ApiHelperImpl,
    private val dbHelper: DbHelperImpl
) : DataManager {

    override fun addUser(handler: AppCallback<User>): Disposable {
        return apiHelper.uploadUser(
            createTestUserWithSocialList()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSubscriber<User>() {
                override fun onComplete() {
                    // do nothing
                }

                override fun onNext(t: User?) {
                    handler.onSuccess(t as User)
                    dbHelper.insertOrUpdateUser(t)
                }

                override fun onError(e: Throwable?) {
                    if (e is Exception) {
                        handler.onFailure(e.message, e)
                    }
                }

            })
    }

    override fun getUserFromDatabase(handler: AppCallback<User>): Disposable {
        return dbHelper.getUserFlowable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSubscriber<User>() {
                override fun onComplete() {
                    // do nothing
                }

                override fun onNext(t: User?) {
                    handler.onSuccess(t as User)
                }

                override fun onError(e: Throwable?) {
                    if (e is Exception) {
                        handler.onFailure(e.message, e)
                    }
                }

            })
    }

    override fun getUserListFromDatabase(handler: AppCallback<List<User>>): Disposable {
        return dbHelper.getUserListFlowable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSubscriber<List<User>>() {
                override fun onComplete() {
                    // do nothing
                }

                override fun onNext(t: List<User>?) {
                    handler.onSuccess(t as List<User>)
                }

                override fun onError(e: Throwable?) {
                    if (e is Exception) {
                        handler.onFailure(e.message, e)
                    }
                }

            })
    }

    override fun syncUsersWithQuery(handler: AppCallback<List<User>>): Disposable {
        return apiHelper.getUsersFromServerWithQuery()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSubscriber<List<User>>() {
                override fun onComplete() {
                    // do nothing
                }

                override fun onNext(t: List<User>?) {
                    handler.onSuccess(t as List<User>)
                    dbHelper.syncUsersWithDatabase(t)
                }

                override fun onError(e: Throwable?) {
                    if (e is Exception) {
                        handler.onFailure(e.message, e)
                    }
                }

            })

    }


    override fun syncUsersWithListener(handler: AppCallback<List<User>>): Disposable {
        return apiHelper.getUsersFromServerWithListener()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSubscriber<List<User>>() {
                override fun onComplete() {
                    // do nothing
                }

                override fun onNext(t: List<User>?) {
                    handler.onSuccess(t as List<User>)
                    dbHelper.syncUsersWithDatabase(t)
                }

                override fun onError(e: Throwable?) {
                    if (e is Exception) {
                        handler.onFailure(e.message, e)
                    }
                }
            })
    }

    override fun deleteAll(handler: AppCallback<String>): Disposable {
        return apiHelper.deleteEverythingFromServer()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSubscriber<String>() {
                override fun onComplete() {
                    handler.onSuccess("")
                    dbHelper.deleteAllFromDatabase()
                }

                override fun onNext(t: String?) {
                    // do nothing
                }

                override fun onError(e: Throwable?) {
                    if (e is Exception) {
                        handler.onFailure(e.message, e)
                    }
                }

            })
    }

    // test method
    private fun createTestUserWithSocialList(): User {
        val social = Social(UUID.randomUUID().toString())
        val social2 = Social(UUID.randomUUID().toString())
        val socialList = ArrayList<Social>()
        socialList.add(social)
        socialList.add(social2)
        val user = User(
            UUID.randomUUID().toString()
        )
        user.socials = socialList
        return user
    }


}


