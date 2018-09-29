package cn.imrhj.cowlevel.ui.base

import cn.imrhj.cowlevel.App
import com.elvishew.xlog.XLog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlin.math.log

interface BasePageInterface {
    fun <T> getObserver(onNext: (t: T) -> Unit): Observer<T> {
        return this.getObserver(onNext, null, null)
    }

    fun <T> getObserver(onNext: (t: T) -> Unit, onError: ((e: Throwable) -> Unit)? = null,
                        onComplete: (() -> Unit)? = null): Observer<T> {
        return object : Observer<T> {
            private lateinit var mDisposable: Disposable
            override fun onComplete() {
                App.app.getCompositeDisposable(this@BasePageInterface).remove(mDisposable)
                if (onComplete != null) {
                    onComplete()
                } else {
                    this@BasePageInterface.onComplete()
                }
            }

            override fun onSubscribe(d: Disposable) {
                App.app.getCompositeDisposable(this@BasePageInterface).add(d)
                mDisposable = d
            }

            override fun onNext(t: T) {
                onNext(t)
            }

            override fun onError(e: Throwable) {
                App.app.getCompositeDisposable(this@BasePageInterface).remove(mDisposable)
                if (onError != null) {
                    onError(e)
                } else {
                    this@BasePageInterface.onError(e)
                }
            }
        }
    }

    fun onDestroyCallback() {
        App.app.removeCompositeDisposable(this)
    }

    fun shouldCallOnDestroy(): Boolean

    fun onComplete() {
    }

    fun onError(e: Throwable) {
        XLog.e(":$e")
    }
}