package cn.imrhj.cowlevel.manager

import cn.imrhj.cowlevel.network.model.UserModel
import cn.imrhj.cowlevel.utils.SecurityUtils
import cn.imrhj.cowlevel.utils.StringUtils
import cn.imrhj.cowlevel.utils.ThreadUtils
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by rhj on 13/12/2017.
 */
class UserManager {


    // 单例实现
    companion object {
        fun getInstance(): UserManager {
            return Inner.mSingleInstance
        }

        fun getUserModel(): UserModel {
            return Inner.mUserModel
        }

        private fun saveUserModel() {
            SecurityUtils.putValue(Inner.KEY, Inner.mUserModel.toJsonString())
        }

        private fun clearUserModel() {
            SecurityUtils.putValue(Inner.KEY, "")
        }

        fun logout() {
            val func = {
                Inner.mUserModel = UserModel()
                clearUserModel()
                LinkUtils.openLogin()
            }
            if (ThreadUtils.isMainThread()) {
                func()
            } else {
                Observable.just(1)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe { func() }
            }
        }

        fun setToken(token: String) {
            Inner.mUserModel.token = token
            saveUserModel()
        }

    }

    private object Inner {
        val KEY = "UserLoginInfo"
        val mSingleInstance = UserManager()
        var mUserModel: UserModel

        init {
            val modelStr = SecurityUtils.getValue(KEY)
            mUserModel = if (StringUtils.isNotBlank(modelStr)) {
                Gson().fromJson(modelStr, UserModel::class.java)
            } else {
                UserModel()
            }

        }
    }
}