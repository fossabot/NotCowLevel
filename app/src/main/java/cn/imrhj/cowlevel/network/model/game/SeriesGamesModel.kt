package cn.imrhj.cowlevel.network.model.game

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class SeriesGamesModel(
        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("games")
        val games: List<GamesItem?>? = null,

        @field:SerializedName("id")
        val id: Int? = null
) : BaseModel()