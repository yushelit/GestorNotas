package com.example.Modelo

import kotlinx.serialization.Serializable

@Serializable
data class Usuario( var id:Int,
                    var nombre:String,
                    var password:String)
