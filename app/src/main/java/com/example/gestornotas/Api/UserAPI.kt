package com.example.gestornotas.Api



import com.example.Modelo.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {
    //login
    @GET("login/{nombre}/{password}")
    fun getLogin(@Path("nombre") nombre:String, @Path("password") pwd:String): Call<Usuario>

    //Obtener datos
    @GET("usuarios")
    fun getUsuarios(): Call<MutableList<Usuario>>

    @GET("usuarios/{nombre}")
    fun getUsuario(@Path("nombre") nombre:String): Call<Usuario>

    @Headers("Content-Type:application/json")
    @POST("usuarios")
    fun addUsuario(@Body info: Usuario) : Call<ResponseBody>

    //Notas

}