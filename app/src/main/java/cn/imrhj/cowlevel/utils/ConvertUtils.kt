package cn.imrhj.cowlevel.utils

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import cn.imrhj.cowlevel.App
import java.io.ByteArrayOutputStream


/**
 * Created by rhj on 12/12/2017.
 */

object ConvertUtils {

    /**
     * bitmap 转 byteArr
     *
     * @param bitmap bitmap 对象
     * @param format 格式
     * @return 字节数组
     */
    fun bitmap2Bytes(bitmap: Bitmap?, format: Bitmap.CompressFormat): ByteArray? {
        if (bitmap == null) return null
        val baos = ByteArrayOutputStream()
        bitmap.compress(format, 100, baos)
        return baos.toByteArray()
    }

    /**
     * byteArr 转 bitmap
     *
     * @param bytes 字节数组
     * @return bitmap
     */
    fun bytes2Bitmap(bytes: ByteArray?): Bitmap? {
        return if (bytes == null || bytes.isEmpty()) null else BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    /**
     * drawable 转 bitmap
     *
     * @param drawable drawable 对象
     * @return bitmap
     */
    fun drawable2Bitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }
        val bitmap: Bitmap
        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565)
        } else {
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight,
                    if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565)
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * bitmap 转 drawable
     *
     * @param bitmap bitmap 对象
     * @return drawable
     */
    fun bitmap2Drawable(bitmap: Bitmap?): Drawable? {
        return if (bitmap == null) null else BitmapDrawable(App.getAppContext().resources, bitmap)
    }

    /**
     * drawable 转 byteArr
     *
     * @param drawable drawable 对象
     * @param format   格式
     * @return 字节数组
     */
    fun drawable2Bytes(drawable: Drawable?, format: Bitmap.CompressFormat): ByteArray? {
        return if (drawable == null) null else bitmap2Bytes(drawable2Bitmap(drawable), format)
    }

    /**
     * byteArr 转 drawable
     *
     * @param bytes 字节数组
     * @return drawable
     */
    fun bytes2Drawable(bytes: ByteArray?): Drawable? {
        return if (bytes == null) null else bitmap2Drawable(bytes2Bitmap(bytes))
    }

    /**
     * view 转 Bitmap
     *
     * @param view 视图
     * @return bitmap
     */
    fun view2Bitmap(view: View?): Bitmap? {
        if (view == null) return null
        val ret = Bitmap.createBitmap(view!!.getWidth(), view!!.getHeight(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(ret)
        val bgDrawable = view!!.getBackground()
        if (bgDrawable != null) {
            bgDrawable!!.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view!!.draw(canvas)
        return ret
    }


}