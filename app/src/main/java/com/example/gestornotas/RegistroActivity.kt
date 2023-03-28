package com.example.gestornotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.example.Modelo.Usuario
import com.example.gestornotas.Api.ServiceBuilder
import com.example.gestornotas.Api.UserAPI
import com.example.gestornotas.databinding.ActivityRegistroBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolBarRegistro.title = "    Registrar"
        binding.toolBarRegistro.setLogo(R.drawable.bloc_de_notas)

        setSupportActionBar(binding.toolBarRegistro)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnConfirmar.setOnClickListener {
            if(binding.editTextNombre.text.isNotEmpty() && binding.editTextPasswordReg.text.isNotEmpty()){
                val u = Usuario(0, binding.editTextNombre.text.toString(),binding.editTextPasswordReg.text.toString())
                agregarUsuario(u)
                finish()
            }else{
                Toast.makeText(this, "Faltan campos por rellenar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun agregarUsuario(u: Usuario) {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.addUsuario(u)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (!response.isSuccessful) {
                    Toast.makeText(this@RegistroActivity, "Algo ha fallado en la inserción: clave duplicada", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@RegistroActivity, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_registro, menu)
        return super.onCreateOptionsMenu(menu)
    }
}