package com.bob.common.cache

import android.os.Environment
import com.bob.common.util.ContextUtil
import com.jakewharton.disklrucache.DiskLruCache
import java.io.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**
 * todo 添加LruCache 三级缓存
 * created by cly on 2022/2/7
 */
class WanDiskCache {
    private var diskLruCache: DiskLruCache? = null

    init {
        if (diskLruCache == null) {
            val filePath = if ((Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
                        || !Environment.isExternalStorageRemovable())
                && ContextUtil.getContext().externalCacheDir != null
            ) {
                ContextUtil.getContext().externalCacheDir?.absolutePath + File.separator + "wanAndroid"
            } else {
                ContextUtil.getContext().cacheDir.absolutePath + File.separator + "wanAndroid"
            }
            diskLruCache = DiskLruCache.open(File(filePath), 1, 1, maxSize)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: WanDiskCache? = null

        private const val maxSize: Long = 20 * 1024 * 1024

        fun getInstance(): WanDiskCache {
            return INSTANCE ?: synchronized(this) {
                val instance = WanDiskCache()
                INSTANCE = instance
                instance
            }
        }
    }

    fun put(key: String, value: Any) {
        var oos: ObjectOutputStream? = null
        var editor: DiskLruCache.Editor? = null
        var outputStream: OutputStream? = null
        try {
            editor = diskLruCache?.edit(toMd5Key(key))
            outputStream = editor?.newOutputStream(0)
            oos = ObjectOutputStream(outputStream)
            oos.writeObject(value)
            oos.flush()
            editor?.commit()
        } catch (e: IOException) {
            e.printStackTrace()
            try {
                editor?.abort()
            } catch (e1: IOException) {
                e1.printStackTrace()
            }
        } finally {
            try {
                oos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                outputStream?.close()
            } catch (e: IOException) {
            }
        }
    }

    fun <T> get(key: String): T? {
        val snapshot = diskLruCache?.get(toMd5Key(key))
        if (snapshot != null) {
            val inputStream = snapshot.getInputStream(0)
            var ois: ObjectInputStream? = null
            try {
                ois = ObjectInputStream(inputStream)
                return ois.readObject() as T?
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (ois != null) {
                    try {
                        ois.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }
        return null
    }

    /**
     * 把key用MD5加密
     * @param key
     * @return
     */
    private fun toMd5Key(key: String): String {
        val cacheKey: String = try {
            val mDigest = MessageDigest.getInstance("MD5")
            mDigest.update(key.toByteArray())
            bytesToHexString(mDigest.digest())
        } catch (e: NoSuchAlgorithmException) {
            key.hashCode().toString()
        }
        return cacheKey
    }

    private fun bytesToHexString(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (i in bytes.indices) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) {
                sb.append('0')
            }
            sb.append(hex)
        }
        return sb.toString()
    }

}