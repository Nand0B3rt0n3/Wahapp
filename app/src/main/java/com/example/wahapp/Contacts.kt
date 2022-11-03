package com.example.wahapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Contacts : Fragment() {
    private lateinit var contactsRecyclerView: RecyclerView
    private lateinit var contactsLayoutManager: RecyclerView.LayoutManager
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var fstore : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var toolbar: Toolbar
    private val contactInfo = arrayListOf<User>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contactos, container, false)
        contactsRecyclerView = view.findViewById(R.id.contactsRecyclerView)
        contactsLayoutManager = LinearLayoutManager(context as Activity)
        auth = FirebaseAuth.getInstance()
        fstore = FirebaseFirestore.getInstance()
        fstore.collection("users").get().addOnSuccessListener {
            if (!it.isEmpty) {
                contactInfo.clear()
                val list = it.documents
                for (doc in list) {
                    val obj = User(

                        doc.getString("userName").toString(),
                        doc.getString("userStatus").toString(),
                        doc.id,
                        doc.getString("userProfilePhoto").toString(),
                        doc.getString("prorileUid").toString()
                    )
                    contactInfo.add(obj)
                    contactsAdapter = ContactsAdapter(context as Activity, contactInfo)
                    contactsRecyclerView.adapter = contactsAdapter
                    contactsRecyclerView.layoutManager = contactsLayoutManager

                }
            }
        }
        return view
    }
}
