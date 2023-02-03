package androidx.banner.sample.list

import com.google.gson.annotations.SerializedName

class ListModel(@SerializedName("top_stories") val topStories: List<DataModel>)

class DataModel(val title: String = "", val image: String = "")
