package com.example.gestornotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gestornotas.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirmar.setOnClickListener {

        }
    }
}