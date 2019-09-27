package id.vera.util

import id.vera.service.VeraRetrofit
import id.vera.service.VeraService

object Constant {
    val APIURL = "https://vera.smkn4bdg.sch.id/"
    val APISERVICE = VeraRetrofit.veraRetrofit.create(VeraService::class.java)
}