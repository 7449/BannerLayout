package com.example.list

import com.google.gson.annotations.SerializedName

/**
 * by y on 2016/8/7.
 */
class ListModel(@SerializedName("top_stories") val topStories: List<DataModel>)

class DataModel(val title: String = "", val image: String = "")
