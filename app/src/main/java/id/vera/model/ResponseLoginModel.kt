package id.vera.model

import java.io.Serializable

data class ResponseLoginModel(
    val token: String,
    val message: String
) : Serializable