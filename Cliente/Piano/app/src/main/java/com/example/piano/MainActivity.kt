package com.example.piano

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.util.concurrent.Executors
import android.view.View
import android.widget.Button
import android.widget.ImageButton

import android.widget.TextView




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val request = intent.extras
        val tv1 = findViewById<TextView>(R.id.entrada)
        if (request!=null){
            tv1.text = request.getString("pistanueva")
        }
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)
        val tv2 = findViewById<TextView>(R.id.respuesta)
        val btn = findViewById<Button>(R.id.enviar)
        val go = findViewById<ImageButton>(R.id.goPiano)
        go.setOnClickListener {
            val intent = Intent(this@MainActivity, Piano::class.java)
            startActivity(intent)
        }
        btn.setOnClickListener{
            val client = Cliente()
            Executors.newSingleThreadExecutor().execute {
                runOnUiThread {
                    putString(client.mandar(tv1.text.toString()))
                }
            }
        }
    }

    private fun putString(string: String){
        val tv2 = findViewById<TextView>(R.id.respuesta)
        tv2.text = string
    }

}