package cn.imrhj.cowlevel.deeplink

import com.airbnb.deeplinkdispatch.DeepLinkSpec


@DeepLinkSpec(prefix = ["http://cowlevel.net", "https://cowlevel.net"])
@Retention(AnnotationRetention.BINARY)
annotation class WebDeepLink(vararg val value: String)
