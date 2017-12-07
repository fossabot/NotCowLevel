package cn.imrhj.cowlevel.utils

/**
 * Created by rhj on 2017/12/6.
 */

/**
 * 针对长宽进行图片裁剪，定死宽高 居中裁剪
 */
fun cdnImageForSize(imageUrl: String?, width: Int? = 0, height: Int? = 0): String? {
    if (imageUrl == null) {
        return imageUrl
    }

    if (!imageUrl.contains("pic1.cdncl.net")) {
        return imageUrl
    }

    val result = StringBuilder(imageUrl).append("?imageView2")
    if (width != null && height != null) {
        if (width > 0 && height > 0) {
            result.append("/1")
                    .append("/w/$width")
                    .append("/h/$height")
        } else {
            if (width > 0) {
                result.append("/2/w/$width")
            } else if (height > 0) {
                result.append("/2/h/$height")
            }
        }
    } else {
        if (width != null && width > 0) {
            result.append("/2/w/$width")
        }
        if (height != null && height > 0) {
            result.append("/2/h/$height")
        }
    }

    if (result.endsWith("imageView2")) {
        result.append("/0")
    }

    result.append("/format/webp")
    return result.toString()
}

/**
 * 裁剪一个方形图片
 */
fun cdnImageForSquare(imageUrl: String?, size: Int? = 0) :String? {
    return cdnImageForSize(imageUrl, size, size)
}