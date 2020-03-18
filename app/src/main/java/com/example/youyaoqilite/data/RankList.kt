package com.example.youyaoqilite.data

data class RankList(
    val code: Int,
    val `data`: Data,
    val msg: String
)

data class Data(
    val message: String,
    val returnData: ReturnData,
    val stateCode: Int
)

data class ReturnData(
    val comics: List<Comic>,
    val defaultParameter: DefaultParameter,
    val hasMore: Boolean,
    val moreRankOptionList: List<MoreRankOption>,
    val page: Int,
    val rankTab: List<RankTab>
)

data class Comic(
    val author: String,
    val comicId: String,
    val cover: String,
    val description: String,
    val name: String,
    val tags: List<String>,
    val updateType: Int,
    val updateTypeIcon: String
)

data class DefaultParameter(
    val defaultPeriod: String,
    val defaultType: Int
)

data class MoreRankOption(
    val argVal: String,
    val name: String
)

data class RankTab(
    val defaultValue: String,
    val rankOptionList: List<RankOption>,
    val title: String,
    val `val`: Int
)

data class RankOption(
    val argVal: String,
    val name: String
)