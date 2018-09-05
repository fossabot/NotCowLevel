package cn.imrhj.cowlevel.network.exception

class AuthException(val msg: String = "认证失败") : Exception(msg)