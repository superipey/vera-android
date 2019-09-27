package id.vera.model

import java.io.Serializable

data class SiswaModel(
    val nis: String,
    val nisn: String,
    val biodata: BiodataModel,
    val detailKelas: DetailKelasModel
) : Serializable