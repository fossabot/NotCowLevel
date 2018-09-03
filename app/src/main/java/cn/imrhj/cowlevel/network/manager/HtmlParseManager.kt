package cn.imrhj.cowlevel.network.manager

import cn.imrhj.cowlevel.network.model.ElementModel
import cn.imrhj.cowlevel.network.model.home.FeedHomeModel
import cn.imrhj.cowlevel.network.parse.parseElementJSString
import cn.imrhj.cowlevel.network.parse.parseHomeJSString
import io.reactivex.Observable

const val SCRIPT_INDEX_START = "<script src=\""
const val SCRIPT_INDEX_END = "\"></script>"

const val SCRIPT_REGEX = "<script src=\"(https://cowlevel\\.net/(\\w+/)*_\\.\\w+\\.jo\\..{10}\\.js)\"></script>"

object HtmlParseManager {
    private val mScriptRegex by lazy { Regex(SCRIPT_REGEX) }

    private fun getJSData(url: String): Observable<String> {
        return OkHttpManager.getServerData(COW_LEVEL_URL + url)
                .map {
                    it.substring(
                            it.lastIndexOf(SCRIPT_INDEX_START) + SCRIPT_INDEX_START.length,
                            it.lastIndexOf(SCRIPT_INDEX_END)
                    )
                }
                .flatMap { OkHttpManager.getServerData(it) }

    }

    private fun getJSData(url: String, index: Int, reverse: Boolean = true): Observable<String> {
        return OkHttpManager.getServerData(COW_LEVEL_URL + url)
                .map {
                    val result = mScriptRegex.findAll(it, it.indexOf("<body")).toList()
                    if (index > result.size) {
                        throw Exception("数据源错误!")
                    }
                    result[result.size - index].groups[1]?.value
                }
                .flatMap { OkHttpManager.getServerData(it) }
    }

    fun getHomeHtml(): Observable<FeedHomeModel> {
        return getJSData("feed", 1).map(parseHomeJSString)
    }

    fun getElement(id: Int): Observable<ElementModel> {
        return getJSData("element/$id", 2).map(parseElementJSString)
    }
}