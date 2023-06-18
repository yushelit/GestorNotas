package com.example.gestornotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.Modelo.Usuario
import com.example.gestornotas.Api.*
import com.example.gestornotas.databinding.ActivityEditarPerfilBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarPerfil : AppCompatActivity() {
    lateinit var binding: ActivityEditarPerfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAceptar.setOnClickListener {
            val user = intent.getSerializableExtra("us") as Usuario
            if(binding.etNombre.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()){
                val nuevoUsuario = Usuario(0, binding.etNombre.text.toString(), binding.etPassword.text.toString())
                modificarUsuario(nuevoUsuario, user.id)
                finish()
            }else{
                Toast.makeText(this, "Hay campos vacios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun modificarUsuario(nuevoUsuario: Usuario, id: Int) {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.modUsuario(nuevoUsuario, id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarPerfil, "Modificacion Realizada", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@EditarPerfil, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
            }
        })
    }
}