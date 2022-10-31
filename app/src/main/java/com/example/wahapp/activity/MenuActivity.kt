package com.example.wahapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wahapp.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration


class MenuActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var toolbarMenu        : Toolbar
    private lateinit var frameLayout        : FrameLayout
    private lateinit var optionValue        : String
    private lateinit var queryTerm          : String
    private lateinit var searchRecyclerView : RecyclerView
    private lateinit var searchLayoutManager: RecyclerView.LayoutManager
    private lateinit var searchAdapter      : SearchAdapter
    private var register : ListenerRegistration? = null
    private val searchInfo = arrayListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        toolbarMenu = findViewById(R.id.toolbarMenu)
        frameLayout = findViewById(R.id.frameLayout)
        if(intent != null) {
            optionValue = intent.getStringExtra("OptionName").toString()
            when(optionValue) {
                "profile" ->{
                    frameLayout.visibility = View.VISIBLE
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, Profile()).commit()
                    toolbarMenu.title = "Perfil"
                }
                "ajustes" ->{
                    frameLayout.visibility = View.VISIBLE
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, Ajustes()).commit()
                    toolbarMenu.title = "Ajustes"

                }
                "about" ->{
                    frameLayout.visibility = View.VISIBLE
                    supportFragmentManager.beginTransaction().replace(  R.id.frameLayout, About()).commit()
                    toolbarMenu.title = "Sobre Waha"

                }
                "contacts" ->{
                    frameLayout.visibility = View.VISIBLE
                    searchRecyclerView = findViewById(R.id.RecyclerViewSearch)
                    supportFragmentManager.beginTransaction().replace(  R.id.frameLayout, Contacts()).commit()
                    searchLayoutManager = LinearLayoutManager(this)
                    searchRecyclerView.visibility = View.VISIBLE
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
        register = FirebaseFirestore.getInstance()
            .collection("users")
            .orderBy("userName").startAt(queryTerm).limit(5)
            .addSnapshotListener{ snapshot,exception->
                if(exception != null) {
                    Log.e("onError", "Some Error 0ccured")
                } else {
                    if (snapshot?.isEmpty!!)
                    {
                        searchInfo.clear()
                        val searchList = snapshot.documents

                        for (doc in searchList)
                        {
                            val obj = User(

                                doc.getString("userName").toString(),
                                doc.getString("userStatus").toString(),
                                doc.id,
                                doc.getString("userProfilePhoto").toString()
                            )
                            searchInfo.add(obj)
                            searchAdapter = SearchAdapter(this, searchInfo)
                            searchRecyclerView.adapter = searchAdapter
                            searchRecyclerView.layoutManager = searchLayoutManager
                            searchRecyclerView.addItemDecoration(
                                DividerItemDecoration(
                                    searchRecyclerView.context,
                                    (searchLayoutManager as LinearLayoutManager).orientation)
                            )
                        }
                    }
                }
            }
        }

    override fun onDestroy() {
            register?.remove()

            super.onDestroy()
        }
    }