package com.disney.framework.utils

import com.google.gson.Gson
import java.io.InputStream

/**
 * Fetch corresponding model object [T] via [gson] conversion for a given [FileName].
 *
 * @return Model object mapped from [FileName].
 */
inline fun <reified T> FileName.getDataFromGson(gson: Gson = Gson()): T {
    return gson.fromJson(this.getResourceAsString(), T::class.java)
}

/**
 * Fetch a file available on the `resources` directory as a [String].
 *
 * @return File from `resources` as a [String].
 */
fun FileName.getResourceAsString(): String = getString(ClassLoader.getSystemResourceAsStream(this))

private fun getString(stream: InputStream?): String = stream?.bufferedReader().use {
    it?.readText()
}.orEmpty()

typealias FileName = String
