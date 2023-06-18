package com.example.gestornotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.Modelo.NormalNota
import com.example.gestornotas.Api.ServiceBuilder
import com.example.gestornotas.Api.UserAPI
import com.example.gestornotas.databinding.ActivityEditarNotasNormalesBinding
import com.notes.Modelo.Nota
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class EditarNotasNormales : AppCompatActivity() {
    lateinit var binding: ActivityEditarNotasNormalesBinding
    lateinit var nota:Nota
    lateinit var cuerpo:NormalNota

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarNotasNormalesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolBarEditNormal.title = "Editar Nota"
        setSupportActionBar(binding.toolBarEditNormal)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolBarEditNormal.setNavigationOnClickListener {
            finish()
        }

        nota = intent.getSerializableExtra("note") as Nota

        binding.titleText.append(nota.titulo)

        buscarCuerpo(nota.id)

        binding.btnGuardar.setOnClickListener {
            if(binding.titleText.text.isNotEmpty()){
                val calendar = Calendar.getInstance()
                val date: Date = calendar.time
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val formattedDate = dateFormat.format(date)

                val nuevaInfo = Nota(0, binding.titleText.text.toString(),0,0, formattedDate)
                modificarInfo(nuevaInfo)

                val nuevoBody = NormalNota(0, binding.cuerpoText.text.toString(), nota.id)
                modificarCuerpo(nuevoBody)

            }else{
                Toast.makeText(this, "Hay campos vacios", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun modificarInfo(nuevaInfo: Nota) {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.modInfo(nuevaInfo, nota.id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarNotasNormales, "Nota modificada con éxito", Toast.LENGTH_LONG).show()
                    setResult(RESULT_OK)
                    finish()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@EditarNotasNormales, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun modificarCuerpo(nuevoBody: NormalNota) {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.modNormalNota(nuevoBody, nota.id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarNotasNormales, "Modificacion Realizada", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@EditarNotasNormales, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun buscarCuerpo(id: Int) {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getCuerpo(id)
        call.enqueue(object : Callback<NormalNota> {
            override fun onResponse(call: Call<NormalNota>, response: Response<NormalNota>) {
                val post = response.body()
                if(post != null){
                    binding.cuerpoText.append(post.cuerpo)
                    cuerpo = post
                }
            }
            override fun onFailure(call: Call<NormalNota>, t: Throwable) {
                Toast.makeText(this@EditarNotasNormales, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}