package com.example.weatherforecast.data

class DataorEception<T,Boolean,E:Exception>(
    val data: T?=null,
    var loading:kotlin.Boolean?=null,
    val e:Exception?=null)
