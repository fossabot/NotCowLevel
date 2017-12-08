package cn.imrhj.cowlevel.network.model

/**
 * Created by rhj on 2017/12/4.
 */
data class MergedModel(
        val pic_count: Int = 0,
        val video_count: Int = 0,
        val voters: List<*>? = null,
        val followers: List<*>? = null,
        val games: List<MergedGameModel>? = null,
        val tags: List<MergedTagModel>? = null,
        val pics: List<*>? = null,
        val videos: List<*>? = null)