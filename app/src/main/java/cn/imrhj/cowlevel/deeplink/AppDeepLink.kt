package cn.imrhj.cowlevel.deeplink

import com.airbnb.deeplinkdispatch.DeepLinkSpec


@DeepLinkSpec(prefix = ["cow://level"])
@Retention(AnnotationRetention.BINARY)
annotation class AppDeepLink(vararg val value: String)
