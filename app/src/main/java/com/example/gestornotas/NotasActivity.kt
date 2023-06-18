package com.example.gestornotas

import adaptadores.NotasAdaptadorRecycler
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Modelo.Usuario
import com.example.gestornotas.Api.*

import com.example.gestornotas.databinding.ActivityNotasBinding
import com.notes.Modelo.Nota
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotasActivity : AppCompatActivity() {
    var bloc: ArrayList<Nota> = ArrayList()
    lateinit var binding: ActivityNotasBinding
    lateinit var notasRecyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        obtencionNotas()

        binding.materialToolbar.title = "Notas"
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun obtencionNotas() {
        val user = intent.getSerializableExtra("us") as Usuario
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getNotas(user.id)
        call.enqueue(object : Callback<MutableList<Nota>> {
            override fun onResponse(call: Call<MutableList<Nota>>, response: Response<MutableList<Nota>>
            ) {
                if (response.isSuccessful) {
                    bloc = response.body() as ArrayList<Nota>
                    notasRecyclerView = binding.recyclerView
                    notasRecyclerView.setHasFixedSize(true)
                    notasRecyclerView.layoutManager = LinearLayoutManager(this@NotasActivity)
                    val notaAdapter = NotasAdaptadorRecycler(this@NotasActivity, bloc)
                    notasRecyclerView.adapter = notaAdapter
                }
            }
            override fun onFailure(call: Call<MutableList<Nota>>, t: Throwable) {
                Toast.makeText(this@NotasActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notas_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.newNota -> crearNotaNormal()
            R.id.editarPerfil -> editarPerfil()
            R.id.editarNota -> editarNota()
            R.id.borrarNota -> borrarNota()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editarNota() {
        if(NotasAdaptadorRecycler.seleccionado >= 0){
            val note = bloc[NotasAdaptadorRecycler.seleccionado]
            val editarNormalNota = Intent(this, EditarNotasNormales::class.java)
            editarNormalNota.putExtra("note", note)
            startActivity(editarNormalNota)
        }else{
            Toast.makeText(this@NotasActivity, "Selecciona una nota para editarla.",Toast.LENGTH_LONG).show()
        }
    }

    private fun borrarNota() {
        if(NotasAdaptadorRecycler.seleccionado >= 0){
            val note = bloc[NotasAdaptadorRecycler.seleccionado]

            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.delNormalNotes(note.id)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful){
                        Toast.makeText(this@NotasActivity, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@NotasActivity, "Algo ha fallado en la conexión.",Toast.LENGTH_LONG).show()
                }
            })
        }else{
            Toast.makeText(this@NotasActivity, "Selecciona una nota para poder borrarla.",Toast.LENGTH_LONG).show()
        }
    }

    private fun crearNotaNormal() {
        val crearNormalNota = Intent(this, CrearNormalNota::class.java)
        val user = intent.getSerializableExtra("us") as Usuario
        crearNormalNota.putExtra("id", user.id)
        startActivity(crearNormalNota)
    }

    private fun editarPerfil() {
        val user = intent.getSerializableExtra("us") as Usuario
        val editorPerfil = Intent(this, EditarPerfil::class.java)
        editorPerfil.putExtra("us", user)
        startActivity(editorPerfil)
    }
}