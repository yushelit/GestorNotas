package com.example.gestornotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.Modelo.NormalNota
import com.example.gestornotas.Api.ServiceBuilder
import com.example.gestornotas.Api.UserAPI
import com.example.gestornotas.databinding.ActivityEditarNotasNormalesBinding
import com.notes.Modelo.Nota
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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