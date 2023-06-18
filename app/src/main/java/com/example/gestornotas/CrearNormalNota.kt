package com.example.gestornotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.Modelo.NormalNota
import com.example.gestornotas.Api.*
import com.example.gestornotas.databinding.ActivityCrearNormalNotaBinding
import com.notes.Modelo.Nota
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class CrearNormalNota : AppCompatActivity() {
    lateinit var binding: ActivityCrearNormalNotaBinding
    lateinit var info: Nota

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearNormalNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra("id", -1)

        binding.btnGuardar.setOnClickListener {
            if(binding.titleText.text.isNotEmpty()){
                val calendar = Calendar.getInstance()
                val date: Date = calendar.time
                info = Nota(0, binding.titleText.text.toString(), 0, id, date.toString())
                guardarInfo(info)
                obtenerInfo()
                val cuerpo = NormalNota(0, binding.cuerpoText.text.toString(),info.idUser)
                guardarCuerpo(cuerpo)
            }
        }
    }
    private fun guardarCuerpo(cuerpo: NormalNota) {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.agregarNotasNormales(cuerpo)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (!response.isSuccessful) {
                    Toast.makeText(this@CrearNormalNota, "Algo ha fallado en la inserción del cuerpo", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@CrearNormalNota, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun obtenerInfo() {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUltimaNota()
        call.enqueue(object : Callback<Nota>{
            override fun onResponse(call: Call<Nota>, response: Response<Nota>){
                val post = response.body()
                if (post != null){
                    info = Nota(post.id, post.titulo, post.tipo, post.idUser, post.fecha)
                }else {
                    Toast.makeText(this@CrearNormalNota, "error al buscar la nota", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Nota>, t: Throwable) {
                Toast.makeText(this@CrearNormalNota, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun guardarInfo(info: Nota) {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.agregarNotas(info)

        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (!response.isSuccessful) {
                    Toast.makeText(this@CrearNormalNota, "Algo ha fallado en la inserción de la info", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@CrearNormalNota, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
            }
        })
    }
}