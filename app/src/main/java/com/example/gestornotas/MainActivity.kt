package com.example.gestornotas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.Modelo.Usuario
import com.example.gestornotas.Api.ServiceBuilder
import com.example.gestornotas.Api.UserAPI
import com.example.gestornotas.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            if (binding.textName.text.trim().isNotEmpty() && binding.textPassword.text.trim().isNotEmpty()){
                getBuscarUnUsuario(binding.textName.text.toString(), binding.textPassword.text.toString())
            }else{
                Toast.makeText(this@MainActivity, "Campos Vacios", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnRegistrar.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegistroActivity::class.java))
        }
    }

    private fun getBuscarUnUsuario(name: String, pwd: String) {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getLogin(name, pwd)
        call.enqueue(object : Callback<Usuario>{
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>){
                val post = response.body()
                if (post != null){
                    Toast.makeText(this@MainActivity, "Bienvenido ${post.nombre}", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this@MainActivity, "Login no encontrado", Toast.LENGTH_SHORT).show()
                }

            }
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}