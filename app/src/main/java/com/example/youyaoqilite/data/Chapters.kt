package com.example.youyaoqilite.data

data class Chapters(
    val `data`: ChapterData
)

data class ChapterData(
    val returnData: ChapterReturnData
)

data class ChapterReturnData(
    val chapter_list: List<Chapter>
)

data class Chapter(
    val image_total: String,
    val name: String,
    val smallPlaceCover: String,
    val chapter_id: String
)