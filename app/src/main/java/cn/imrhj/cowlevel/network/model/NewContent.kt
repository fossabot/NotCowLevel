package cn.imrhj.cowlevel.network.model

import com.google.gson.annotations.SerializedName

data class NewContent(

        @field:SerializedName("question_count")
        val questionCount: Int? = null,

        @field:SerializedName("article_count")
        val articleCount: Int? = null,

        @field:SerializedName("video_count")
        val videoCount: Int? = null,

        @field:SerializedName("review_count")
        val reviewCount: Int? = null
) : BaseModel()