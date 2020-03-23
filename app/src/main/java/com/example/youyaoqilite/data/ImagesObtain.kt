package com.example.youyaoqilite.data

data class ImagesObtain(
    val code: Int,
    val `data`: ImageData
)

data class ImageData(
    val message: String,
    val returnData: ImageReturnData,
    val stateCode: Int
)

data class ImageReturnData(
    val chapter_id: String,
    val image_list: List<ImageItem>,
    val type: String,
    val unlock_image: List<Any>,
    val zip_file_high: String
)

data class ImageItem(
    val height: String,
    val image_id: String,
    val images: List<ImageX>,
    val img05: String,
    val img50: String,
    val location: String,
    val total_tucao: String,
    val type: String,
    val webp: String,
    val width: String
)

data class ImageX(
    val height: String,
    val id: String,
    val img05: String,
    val img50: String,
    val sort: String,
    val width: String
)