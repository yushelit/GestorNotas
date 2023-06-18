package com.example.Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Usuario( @SerializedName("id")
                    var id:Int,
                    @SerializedName("nombre")
                    var nombre:String,
                    @SerializedName("password")
                    var password:String): Serializable
