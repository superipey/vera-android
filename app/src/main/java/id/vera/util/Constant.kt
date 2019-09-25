package id.vera.util

import id.vera.service.VeraRetrofit
import id.vera.service.VeraService

object Constant {
    val APIURL = "BASEURL"

    val APISERVICE = VeraRetrofit.veraRetrofit.create(VeraService::class.java)
}