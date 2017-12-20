package cn.imrhj.cowlevel.network.model

/**
 * Created by rhj on 2017/12/4.
 */
data class MergedModel(
        val pic_count: Int = 0,
        val video_count: Int = 0,
        val voters: List<UserModel>? = null,
        val followers: List<UserModel>? = null,
        val games: List<GameModel>? = null,
        val tags: List<MergedTagModel>? = null,
        val pics: List<*>? = null,
        val videos: List<*>? = null
) : BaseModel()