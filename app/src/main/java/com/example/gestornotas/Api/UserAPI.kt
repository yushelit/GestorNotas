package com.example.gestornotas.Api



import com.example.Modelo.*
import com.notes.Modelo.Nota
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {
    //login
    @POST("login")
    fun getLogin(@Body user:Usuario): Call<Usuario>

    //Obtener datos
    @GET("usuarios")
    fun getUsuarios(): Call<MutableList<Usuario>>

    @GET("usuarios/{nombre}")
    fun getUsuario(@Path("nombre") nombre:String): Call<Usuario>

    @Headers("Content-Type:application/json")
    @POST("usuarios")
    fun addUsuario(@Body info: Usuario) : Call<ResponseBody>

    //Notas
    @GET("notes/{id}")
    fun getNotas(@Path("id") id:Int): Call<MutableList<Nota>>

    @GET("normalNotes/ultima")
    fun getUltimaNota(): Call<Nota>

    @Headers("Content-Type:application/json")
    @POST("notes")
    fun agregarNotas(@Body info: Nota): Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("normalNotes")
    fun agregarNotasNormales(@Body info: NormalNota): Call<ResponseBody>
}