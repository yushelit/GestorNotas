package com.notes.Modelo

data class Nota(var id: Int,
                var titulo:String,
                var tipo:Int,
                var idUser: Int,
                var fecha:String):java.io.Serializable
