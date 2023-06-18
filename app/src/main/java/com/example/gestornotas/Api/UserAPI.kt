package com.example.gestornotas.Api



import com.example.Modelo.*
import com.notes.Modelo.Nota
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {
    //login
    @Headers("Content-Type:application/json")
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

    //Modificacion de datos
    @Headers("Content-Type:application/json")
    @PUT("usuarios/{id}")
    fun modUsuario(@Body info: Usuario, @Path("nombre") id:Int) : Call<ResponseBody>

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

    //Borrar notas normales de la base de datos
    @DELETE("normalNotes/{id}")
    fun delNormalNotes(@Path("id") id:Int) : Call<ResponseBody>

//    Obtencion del cuerpo de notas normales
    @GET("normalNotes/cuerpo/{id}")
    fun getCuerpo(@Path("id") id:Int): Call<NormalNota>

    //Modificacion de datos
    @Headers("Content-Type:application/json")
    @PUT("notes/{id}")
    fun modInfo(@Body info: Nota, @Path("id") id:Int) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("normalNotes/cuerpo/{id}")
    fun modNormalNota(@Body info: NormalNota, @Path("id") id:Int) : Call<ResponseBody>

    //Notas
    @GET("listas/{id}")
    fun getElementos(@Path("id") id:Int): Call<MutableList<ListaNota>>
}