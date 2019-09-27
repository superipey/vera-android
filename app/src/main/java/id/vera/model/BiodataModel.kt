package id.vera.model

import java.io.Serializable
import java.util.*

data class BiodataModel(
    val nama_lengkap: String,
    val jenis_kelamin: String,
    val tempat_lahir: String,
    val tanggal_lahir: String,
    val agama: String,
    val alamat_jalan: String,
    val alamat_rt: String,
    val alamat_rw: String,
    val alamat_desa: String,
    val alamat_kecamatan: String,
    val alamat_kota: String,
    val alamat_pos: Integer,
    val telp_mobile: String,
    val email: String
) : Serializable