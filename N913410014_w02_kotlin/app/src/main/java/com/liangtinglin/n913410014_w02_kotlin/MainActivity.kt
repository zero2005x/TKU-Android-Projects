package com.liangtinglin.n913410014_w02_kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat




class MainActivity : AppCompatActivity() {
    var rr: TextView? = null
    var re: TextView? = null
    var fontSize: Int = 25
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById<View?>(R.id.main),
            OnApplyWindowInsetsListener { v: View?, insets: WindowInsetsCompat? ->
                val systemBars = insets!!.getInsets(WindowInsetsCompat.Type.systemBars())
                v!!.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            })
        rr = findViewById<View?>(R.id.res) as TextView
        rr!!.setText("今天天氣")
        re = findViewById<View?>(R.id.Textview) as TextView
        re!!.setText("測試")
    }

    @SuppressLint("SetTextI18n")
    fun big(view: View?) {
        if (fontSize < 38) fontSize++
        re!!.setTextSize(fontSize.toFloat())
        rr!!.setTextSize(fontSize.toFloat())
        re!!.setText(getString(R.string.prm) + "," + getString(R.string.size) + fontSize)
        rr!!.setText(getString(R.string.prm2) + "," + getString(R.string.size) + fontSize)
    }

    @SuppressLint("SetTextI18n")
    fun small(view: View?) {
        if (fontSize > 25) fontSize--
        re!!.setTextSize(fontSize.toFloat())
        rr!!.setTextSize(fontSize.toFloat())
        re!!.setText(getString(R.string.prm) + "," + getString(R.string.size) + fontSize)
        rr!!.setText(getString(R.string.prm2) + "," + getString(R.string.size) + fontSize)
    }
}