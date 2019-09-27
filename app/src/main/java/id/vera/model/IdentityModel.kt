package id.vera.model

import java.io.Serializable

data class IdentityModel(
    val nis: String,
    val similarity: Float,
    val foto: String,
    val data: SiswaModel
) : Serializable