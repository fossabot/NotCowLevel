package cn.imrhj.cowlevel.network.adapter

import cn.imrhj.cowlevel.network.model.feed.FeedApiModel
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class FeedApiAdapter : JsonDeserializer<FeedApiModel> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): FeedApiModel {
        val lastId = json?.asJsonObject?.get("last_id")
        if (lastId?.toString() == "") {
            json.asJsonObject.remove("last_id")
        }
        return Gson().fromJson<FeedApiModel>(json, FeedApiModel::class.java)
    }
}