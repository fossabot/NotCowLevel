package cn.imrhj.cowlevel.network.adapter

import cn.imrhj.cowlevel.network.model.OuterUserModel
import cn.imrhj.cowlevel.network.model.UserModel
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class OuterUserAdapter : JsonDeserializer<OuterUserModel> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): OuterUserModel {
        val userObject = json?.asJsonObject
        if (userObject?.get("user_site")?.asString == "[]") {
            userObject.remove("user_site")
        }
        return OuterUserModel(Gson().fromJson<UserModel>(userObject, UserModel::class.java))
    }
}