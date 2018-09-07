package cn.imrhj.cowlevel.network.adapter

import cn.imrhj.cowlevel.network.model.element.QuestionModel
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class QuestionAdapter : JsonDeserializer<QuestionModel> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): QuestionModel {
        val myAnswer = json?.asJsonObject?.get("my_answer")
        if (myAnswer?.toString() == "[]") {
            json.asJsonObject.remove("my_answer")
        }
        return Gson().fromJson<QuestionModel>(json, QuestionModel::class.java)
    }
}