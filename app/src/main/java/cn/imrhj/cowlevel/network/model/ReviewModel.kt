package cn.imrhj.cowlevel.network.model

/**
 * Created by rhj on 2017/12/4.
 * id : 1888111
 * vote_count : 1
 * comment_count : 0
 * has_vote : 0
 * has_collect : 0
 * title :
 * brief_content : {"desc":"单纯作为一个喜欢体验剧情而非竞技RTS玩家对星际2剧情剧本部分说些个人感想。 手残玩家RTS一直玩不好，但星际的设定我小时候就非常喜欢。（最喜欢刺蛇这样的充满野性的 ...","thumb":""}
 * neat_content : {"desc":"单纯作为一个喜欢体验剧情而非竞技RTS玩家对星际2剧情剧本部分说些个人感想。 手残玩家RTS一直玩不好，但星际的设定我小时候就非常喜欢。（最喜欢刺蛇这样的充满野性的 ...","thumb":""}
 * play_time : 3
 * play_time_text : 玩过 10 ~ 30 小时
 * play_status : 2
 * star : 3
 * star_text : 值得一玩
 * hide_comment : 0
 * lock_comment : 0
 * is_wildrose : 0
 */
data class ReviewModel(
        val id: Int = 0,
        val vote_count: Int = 0,
        val comment_count: Int = 0,
        val has_vote: Int = 0,
        val has_collect: Int = 0,
        val title: String? = null,
        val brief_content: ContentModel? = null,
        val neat_content: ContentModel? = null,
        val play_time: Int = 0,
        val play_time_text: String? = null,
        val play_status: Int = 0,
        val star: Int = 0,
        val star_text: String? = null,
        val hide_comment: Int = 0,
        val lock_comment: Int = 0,
        val is_wildrose: Int = 0
) : BaseModel()