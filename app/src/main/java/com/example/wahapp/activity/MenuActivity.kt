package com.example.wahapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.SearchView
import com.example.wahapp.*
import com.google.firebase.firestore.FirebaseFirestore

class MenuActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var toolbarMenu  : Toolbar
    private lateinit var frameLayout  : FrameLayout
    private lateinit var optionValue  : String
    private lateinit var queryTerm    : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        toolbarMenu = findViewById(R.id.toolbarMenu)
        frameLayout = findViewById(R.id.frameLayout)
        if(intent!=null) {
            optionValue = intent.getStringExtra("OptionName").toString()
            when(optionValue) {
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
                "contacts" ->{
                    supportFragmentManager.beginTransaction().replace(  R.id.frameLayout, Contacts()).commit()
                    toolbarMenu.title = "Contactos"
                    setSupportActionBar(toolbarMenu)

                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search,menu)
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query!=null)
        {
            queryTerm = query
            if(queryTerm.isNotEmpty())
            {
                searchUsers()
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText!=null)
        {
            queryTerm = newText
            if(queryTerm.isNotEmpty())
            {
                searchUsers()
            }
        }
        return true
    }

    private fun searchUsers() {
        FirebaseFirestore.getInstance()
            .collection("user")
            .whereEqualTo("useName",queryTerm).addSnapshotListener { snapshot, exception ->
                if (exception!=null)
                {
                    Log.e("onError","Some error occured")
                }
            }
        Toast.makeText(this,"Contacto $queryTerm",Toast.LENGTH_LONG).show()
    }
}