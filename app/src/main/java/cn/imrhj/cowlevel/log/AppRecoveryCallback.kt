package cn.imrhj.cowlevel.log

import com.elvishew.xlog.XLog
import com.zxy.recovery.callback.RecoveryCallback

class AppRecoveryCallback : RecoveryCallback {
    override fun throwable(throwable: Throwable?) {
    }

    override fun stackTrace(stackTrace: String?) {
        XLog.t().b().e("app crash -> stackTrace:\n$stackTrace")
    }

    override fun cause(cause: String?) {}

    override fun exception(throwExceptionType: String?, throwClassName: String?,
                           throwMethodName: String?, throwLineNumber: Int) {
    }
}