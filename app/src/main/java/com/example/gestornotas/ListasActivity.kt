package com.example.gestornotas

import adaptadores.ListasAdaptadorRecycler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Modelo.ListaNota
import com.example.Modelo.Usuario
import com.example.gestornotas.Api.ServiceBuilder
import com.example.gestornotas.Api.UserAPI
import com.example.gestornotas.databinding.ActivityListasBinding
import com.example.gestornotas.databinding.ActivityNotasBinding
import com.notes.Modelo.Nota
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListasActivity : AppCompatActivity() {
    var lista: ArrayList<ListaNota> = ArrayList()
    lateinit var binding: ActivityListasBinding
    lateinit var elementosRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        obtencionElementos()

        binding.materialToolbarListas.title = "Listas"
        setSupportActionBar(binding.materialToolbarListas)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.materialToolbarListas.setNavigationOnClickListener {
            finish()
        }
    }

    private fun obtencionElementos() {
        val info = intent.getSerializableExtra("note") as Nota
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getElementos(info.id)
        call.enqueue(object : Callback<MutableList<ListaNota>> {
            override fun onResponse(call: Call<MutableList<ListaNota>>, response: Response<MutableList<ListaNota>>
            ) {
                if (response.isSuccessful) {
                    lista = response.body() as ArrayList<ListaNota>
                    elementosRecyclerView = binding.listasRecyclerView
                    elementosRecyclerView.setHasFixedSize(true)
                    elementosRecyclerView.layoutManager = LinearLayoutManager(this@ListasActivity)
                    val notaAdapter = ListasAdaptadorRecycler(this@ListasActivity, lista)
                    elementosRecyclerView.adapter = notaAdapter
                }
            }
            override fun onFailure(call: Call<MutableList<ListaNota>>, t: Throwable) {
                Toast.makeText(this@ListasActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}