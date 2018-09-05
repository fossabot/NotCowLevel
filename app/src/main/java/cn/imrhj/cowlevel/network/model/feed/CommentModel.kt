package cn.imrhj.cowlevel.network.model.feed

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.ContentModel
import com.google.gson.annotations.SerializedName

data class CommentModel(

        @field:SerializedName("comment_count")
        val commentCount: Int? = null,

        @field:SerializedName("neat_content")
        val neatContent: ContentModel? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("content")
        val content: String? = null
) : BaseModel()