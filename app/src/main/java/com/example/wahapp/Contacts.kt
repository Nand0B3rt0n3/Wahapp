package com.example.wahapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private val contactInfo = arrayListOf<User>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contactos, container, false)
        contactsRecyclerView = view.findViewById(R.id.contactsRecyclerView)
        contactsLayoutManager= LinearLayoutManager(context as Activity)
        auth = FirebaseAuth.getInstance()
        fstore = FirebaseFirestore.getInstance()
        fstore.collection("users").get().addOnSuccessListener {
            if(!it.isEmpty)
            {
                val listContact = it.documents
                for(i in listContact)
                {
                    if(i.id==auth.currentUser?.uid){
                        Log.d("onFound","This is a Account")
                    }
                    else
                    {
                        val contact = User(i.getString("userName").toString(),
                            i.getString("userStatus").toString(),
                            i.getString("userEmail").toString(),
                            i.getString("userProfilePhoto").toString())

                        contactInfo.add(contact)
                        contactsAdapter = ContactsAdapter(context as Activity,contactInfo)
                        contactsRecyclerView.adapter = contactsAdapter
                        contactsRecyclerView.layoutManager = contactsLayoutManager
                        contactsRecyclerView.addItemDecoration(DividerItemDecoration(contactsRecyclerView.context,
                            (contactsLayoutManager as LinearLayoutManager).orientation))
                    }
                }
            }
        }
        return view
    }
}
