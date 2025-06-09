package simple.github.sponsor

import kotlinx.serialization.Serializable

@Serializable
abstract class ListWrapper<out T> : List<T>

@Serializable
data class Result(
    val download_url: String
)
