package com.example.wahapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import com.example.wahapp.*

class MenuActivity : AppCompatActivity() {

    private lateinit var toolbarMenu  : androidx.appcompat.widget.Toolbar
    private lateinit var frameLayout  : FrameLayout
    private lateinit var optionValue: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        toolbarMenu = findViewById(R.id.toolbarMenu)
        frameLayout = findViewById(R.id.frameLayout)
        if(intent!=null)
        {
            optionValue = intent.getStringExtra("OptionName").toString()
            when(optionValue)
            {
                "profile" ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, Profile()).commit()
                    toolbarMenu.title = "Perfil"
                }
                "ajustes" ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, Ajustes()).commit()
                    toolbarMenu.title = "Ajustes"

                }
                "about" ->{
                    supportFragmentManager.beginTransaction().replace(  R.id.frameLayout, About()).commit()
                    toolbarMenu.title = "Sobre Waha"

                }
            }
        }
    }
}