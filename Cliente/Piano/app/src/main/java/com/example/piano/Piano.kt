package com.example.piano

import android.media.MediaPlayer
import android.media.midi.MidiDevice
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.provider.MediaStore
import android.view.GestureDetector

import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.View.SCROLLBARS_OUTSIDE_INSET
import android.widget.*
import androidx.core.text.isDigitsOnly

import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener

import android.widget.TextView

class Piano : AppCompatActivity(){

    var started : Long = 0
    var conteo: Long = 0
    var empezo : Boolean = false
    var lastDown: Long = 0
    var timeSong: Long = 0
    var keyPressedDuration: Long = 0
    var request : String = ""
    var theCanal : String = ""
    var octava : String = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.piano)
        val d1 = findViewById<View>(R.id.d1)
        val d2 = findViewById<View>(R.id.d2)
        val d3 = findViewById<View>(R.id.d3)
        val d4 = findViewById<View>(R.id.d4)
        val d5 = findViewById<View>(R.id.d5)
        val d6 = findViewById<View>(R.id.d6)
        val d7 = findViewById<View>(R.id.d7)
        val t1 = findViewById<View>(R.id.t1)
        val t2 = findViewById<View>(R.id.t2)
        val t3 = findViewById<View>(R.id.t3)
        val t4 = findViewById<View>(R.id.t4)
        val t5 = findViewById<View>(R.id.t5)
        val tv2 = findViewById<TextView>(R.id.canal)
        val tv3 = findViewById<TextView>(R.id.pista)
        val stop = findViewById<ImageButton>(R.id.parar)
        val play = findViewById<ImageButton>(R.id.reproducir)
        val record = findViewById<ImageButton>(R.id.grabar)
        val back = findViewById<ImageButton>(R.id.regresar)
        val spin = findViewById<Spinner>(R.id.octava)
        val octavas = arrayOf("0","1","2","3","4","5","6","7","8")
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, octavas
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = adapter

        spin.setOnItemSelectedListener(object : OnItemSelectedListener {
            var chunkSize : Int = 0
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                parent?.getItemAtPosition(position)?.toString()?.toInt()?.also { chunkSize = it }
                (view as TextView).setTextColor(Color.YELLOW) //Change selected text color
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        val md6 = MediaPlayer.create(this,R.raw.d6)
        val md1 = MediaPlayer.create(this,R.raw.d1)
        val md2 = MediaPlayer.create(this,R.raw.d2)
        val md3 = MediaPlayer.create(this,R.raw.d3)
        val md4 = MediaPlayer.create(this,R.raw.d4)
        val md5 = MediaPlayer.create(this,R.raw.d5)
        val md7 = MediaPlayer.create(this,R.raw.d7)
        val mdT1 = MediaPlayer.create(this,R.raw.t1)
        val mdT2 = MediaPlayer.create(this,R.raw.t2)
        val mdT3 = MediaPlayer.create(this,R.raw.t3)
        val mdT4 = MediaPlayer.create(this,R.raw.t4)
        val mdT5 = MediaPlayer.create(this,R.raw.t5)

        val avisar = Toast(this)
        back.setOnClickListener {
            val intent = Intent(this@Piano, MainActivity::class.java)
            startActivity(intent)
        }
        stop.setOnClickListener{
            avisar("Pista detenida, puedes cambiar el canal y la octava si lo deseas, si cambias el nombre, se sobreescribira")
            empezo = false
        }
        play.setOnClickListener {
            val str = tv3.text.toString().replace(" ","");
            var envio = "<solicitud>\n\t<tipo>pistanueva</tipo>\n\t<nombre>\"$str\"</nombre>$request\n</solicitud>"
            val intento = Intent(
                this@Piano,
                MainActivity::class.java
            )
            intento.putExtra("pistanueva",envio)
            startActivity(intento)
        }
        record.setOnClickListener {
            if (!empezo) {
                if (tv2.text.isNotEmpty() && tv3.text.isNotEmpty()) {
                    if (tv2.text.isDigitsOnly()) {
                        started = System.currentTimeMillis()
                        empezo = true
                        theCanal = tv2.text.toString()
                        octava = spin.selectedItem.toString()
                        avisar("Se empezo a grabar")
                    } else {
                        avisar("El canal debe de ser un número")
                    }
                } else {
                    avisar("El canal y la pista son aspectos que deben ser rellenados")
                }
            }
        }
        lastDown = 0
        d1.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(arg0: View, arg1: MotionEvent): Boolean {
                when (arg1.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastDown = System.currentTimeMillis()
                        md1.start()
                        d1.setBackgroundResource(R.color.app)
                        val note = "do"
                        if (isNoteNull(note))
                        {
                            saveNote(note)
                        }
                        else {
                            avisar("No reproduce sonido esta nota en esta octava")
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        keyPressedDuration = System.currentTimeMillis()-lastDown
                        println(keyPressedDuration)
                        d1.setBackgroundResource(R.color.white)
                        val note = "do"
                        if (isNoteNull(note))
                        {
                            saveNote2(note)
                        }
                    }
                }
                return true
            }
        })
        d2.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(arg0: View, arg1: MotionEvent): Boolean {
                when (arg1.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastDown = System.currentTimeMillis()
                        md2.start()
                        d2.setBackgroundResource(R.color.app)
                        val note = "re"
                        if (isNoteNull(note))
                        {
                            saveNote(note)
                        }
                        else {
                            avisar("No reproduce sonido esta nota en esta octava")
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        keyPressedDuration = System.currentTimeMillis()-lastDown
                        println(keyPressedDuration)
                        d2.setBackgroundResource(R.color.white)
                        val note = "re"
                        if (isNoteNull(note))
                        {
                            saveNote2(note)
                        }
                    }
                }
                return true
            }
        })
        d3.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(arg0: View, arg1: MotionEvent): Boolean {
                when (arg1.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastDown = System.currentTimeMillis()
                        md3.start()
                        d3.setBackgroundResource(R.color.app)
                        val note = "mi"
                        if (isNoteNull(note))
                        {
                            saveNote(note)
                        }
                        else {
                            avisar("No reproduce sonido esta nota en esta octava")
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        keyPressedDuration = System.currentTimeMillis()-lastDown
                        d3.setBackgroundResource(R.color.white)
                        val note = "mi"
                        if (isNoteNull(note))
                        {
                            saveNote2(note)
                        }
                    }
                }
                return true
            }
        })
        d4.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(arg0: View, arg1: MotionEvent): Boolean {
                when (arg1.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastDown = System.currentTimeMillis()
                        md4.start()
                        d4.setBackgroundResource(R.color.app)
                        val note = "fa"
                        if (isNoteNull(note))
                        {
                            saveNote(note)
                        }
                        else {
                            avisar("No reproduce sonido esta nota en esta octava")
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        keyPressedDuration = System.currentTimeMillis()-lastDown
                        d4.setBackgroundResource(R.color.white)
                        val note = "fa"
                        if (isNoteNull(note))
                        {
                            saveNote2(note)
                        }
                    }
                }
                return true
            }
        })
        d5.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(arg0: View, arg1: MotionEvent): Boolean {
                when (arg1.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastDown = System.currentTimeMillis()
                        md5.start()
                        d5.setBackgroundResource(R.color.app)
                        val note = "sol"
                        if (isNoteNull(note))
                        {
                            saveNote(note)
                        }
                        else {
                            avisar("No reproduce sonido esta nota en esta octava")
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        keyPressedDuration = System.currentTimeMillis()-lastDown
                        d5.setBackgroundResource(R.color.white)
                        val note = "sol"
                        if (isNoteNull(note))
                        {
                            saveNote2(note)
                        }
                    }
                }
                return true
            }
        })
        d6.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(arg0: View, arg1: MotionEvent): Boolean {
                when (arg1.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastDown = System.currentTimeMillis()
                        md6.start()
                        d6.setBackgroundResource(R.color.app)
                        val note = "la"
                        if (isNoteNull(note))
                        {
                            saveNote(note)
                        }
                        else {
                            avisar("No reproduce sonido esta nota en esta octava")
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        keyPressedDuration = System.currentTimeMillis()-lastDown
                        d6.setBackgroundResource(R.color.white)
                        val note = "la"
                        if (isNoteNull(note))
                        {
                            saveNote2(note)
                        }
                    }
                }
                return true
            }
        })
        d7.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(arg0: View, arg1: MotionEvent): Boolean {
                when (arg1.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastDown = System.currentTimeMillis()
                        md7.start()
                        d7.setBackgroundResource(R.color.app)
                        val note = "si"
                        if (isNoteNull(note))
                        {
                            saveNote(note)
                        }
                        else {
                            avisar("No reproduce sonido esta nota en esta octava")
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        keyPressedDuration = System.currentTimeMillis()-lastDown
                        d7.setBackgroundResource(R.color.white)
                        val note = "si"
                        if (isNoteNull(note))
                        {
                            saveNote2(note)
                        }
                    }
                }
                return true
            }
        })
        t1.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(arg0: View, arg1: MotionEvent): Boolean {
                when (arg1.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastDown = System.currentTimeMillis()
                        mdT1.start()
                        t1.setBackgroundResource(R.color.app)
                        val note = "do#"
                        if (isNoteNull(note))
                        {
                            saveNote(note)
                        }
                        else {
                            avisar("No reproduce sonido esta nota en esta octava")
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        keyPressedDuration = System.currentTimeMillis()-lastDown
                        t1.setBackgroundResource(R.color.black)
                        val note = "do#"
                        if (isNoteNull(note))
                        {
                            saveNote2(note)
                        }
                    }
                }
                return true
            }
        })
        t2.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(arg0: View, arg1: MotionEvent): Boolean {
                when (arg1.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastDown = System.currentTimeMillis()
                        mdT2.start()
                        t2.setBackgroundResource(R.color.app)
                        val note = "re#"
                        if (isNoteNull(note))
                        {
                            saveNote(note)
                        }
                        else {
                            avisar("No reproduce sonido esta nota en esta octava")
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        keyPressedDuration = System.currentTimeMillis()-lastDown
                        t2.setBackgroundResource(R.color.black)
                        val note = "re#"
                        if (isNoteNull(note))
                        {
                            saveNote2(note)
                        }
                    }
                }
                return true
            }
        })
        t3.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(arg0: View, arg1: MotionEvent): Boolean {
                when (arg1.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastDown = System.currentTimeMillis()
                        mdT3.start()
                        t3.setBackgroundResource(R.color.app)
                        val note = "fa#"
                        if (isNoteNull(note))
                        {
                            saveNote(note)
                        }
                        else {
                            avisar("No reproduce sonido esta nota en esta octava")
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        keyPressedDuration = System.currentTimeMillis()-lastDown
                        t3.setBackgroundResource(R.color.black)
                        val note = "fa#"
                        if (isNoteNull(note))
                        {
                            saveNote2(note)
                        }
                    }
                }
                return true
            }
        })
        t4.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(arg0: View, arg1: MotionEvent): Boolean {
                when (arg1.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastDown = System.currentTimeMillis()
                        mdT4.start()
                        t4.setBackgroundResource(R.color.app)
                        val note = "sol#"
                        if (isNoteNull(note))
                        {
                            saveNote(note)
                        }
                        else {
                            avisar("No reproduce sonido esta nota en esta octava")
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        keyPressedDuration = System.currentTimeMillis()-lastDown
                        t4.setBackgroundResource(R.color.black)
                        val note = "sol#"
                        if (isNoteNull(note))
                        {
                            saveNote2(note)
                        }
                    }
                }
                return true
            }
        })
        t5.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(arg0: View, arg1: MotionEvent): Boolean {
                when (arg1.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastDown = System.currentTimeMillis()
                        mdT5.start()
                        arg0.setBackgroundResource(R.color.app)
                        val note = "la#"
                        if (isNoteNull(note))
                        {
                            saveNote(note)
                        }
                        else {
                            avisar("No reproduce sonido esta nota en esta octava")
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        keyPressedDuration = System.currentTimeMillis()-lastDown
                        arg0.setBackgroundResource(R.color.black)
                        val note = "la#"
                        if (isNoteNull(note))
                        {
                            saveNote2(note)
                        }
                    }
                }
                return true
            }
        })
    }

    private fun avisar(aviso:String){
        val to = Toast(this)
        to.setText(aviso)
        to.show()
    }

    private fun saveNote(nota:String){
        var newNote : Double = 0.0
        val newTime : Long = System.currentTimeMillis()
        when(nota){
            "do"-> newNote = 32.70
            "do#"-> newNote = 34.65
            "re"-> newNote = 36.71
            "re#"-> newNote = 38.89
            "mi"-> newNote = 41.20
            "fa"-> newNote = 43.65
            "fa#"-> newNote = 46.25
            "sol"-> newNote = 49.00
            "sol#"-> newNote = 51.91
            "la"-> newNote = 55.00
            "la#"-> newNote = 58.27
            "si"-> newNote = 61.74
            else ->
                println("no sé que nota mandaste")
        }
        if (empezo){
            if ((newTime-started)>1000){
                val time = newTime - started
                timeSong += time
                started += time
                val uT = "\t"
                val dT = "\n\t\t"
                request += "\n$uT<datos>$dT<canal>$theCanal</canal>$dT<nota>sin</nota>$dT<octava>$octava</octava>$dT<duracion>$time</duracion>\n$uT</datos>"
            }
        }
    }

    private fun isNoteNull(note:String) : Boolean{
        var newNote = 0.0
        when(note){
            "do"-> newNote = 32.70
            "do#"-> newNote = 34.65
            "re"-> newNote = 36.71
            "re#"-> newNote = 38.89
            "mi"-> newNote = 41.20
            "fa"-> newNote = 43.65
            "fa#"-> newNote = 46.25
            "sol"-> newNote = 49.00
            "sol#"-> newNote = 51.91
            "la"-> newNote = 55.00
            "la#"-> newNote = 58.27
            "si"-> newNote = 61.74
            else ->
                println("no sé que nota mandaste")
        }
        if (octava == "0"){
            return newNote >= 55.00
        } else if (octava == "8"){
            return newNote == 32.70
        } else {
            return true
        }
    }

    private fun saveNote2(note:String){
        var newNote = 0.0
        val newTime : Long = System.currentTimeMillis()
        when(note){
            "do"-> newNote = 32.70
            "do#"-> newNote = 34.65
            "re"-> newNote = 36.71
            "re#"-> newNote = 38.89
            "mi"-> newNote = 41.20
            "fa"-> newNote = 43.65
            "fa#"-> newNote = 46.25
            "sol"-> newNote = 49.00
            "sol#"-> newNote = 51.91
            "la"-> newNote = 55.00
            "la#"-> newNote = 58.27
            "si"-> newNote = 61.74
            else ->
                println("no sé que nota mandaste")
        }
        if (empezo){
            writeNote(newTime,note)
        }
    }

    private fun writeNote(newTime:Long, note: String){
        val time = newTime - started
        started += time
        timeSong += time
        val uT = "\t"
        val dT = "\n\t\t"
        request += "\n$uT<datos>$dT<canal>$theCanal</canal>$dT<nota>$note</nota>$dT<octava>$octava</octava>$dT<duracion>$time</duracion>\n$uT</datos>"
    }
}