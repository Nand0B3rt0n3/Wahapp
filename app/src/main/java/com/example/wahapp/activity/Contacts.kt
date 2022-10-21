package com.example.wahapp.activity

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wahapp.ContactsAdapter
import com.example.wahapp.R
import com.example.wahapp.User

class Contacts : Fragment() {
    private lateinit var contactsRecyclerView: RecyclerView
    private lateinit var contactsLayoutManager: RecyclerView.LayoutManager
    private lateinit var contactsAdapter: ContactsAdapter
    private val contactInfo = arrayListOf<User>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contactos, container, false)
        contactsRecyclerView = view.findViewById(R.id.contactsRecyclerView)
        contactsLayoutManager= LinearLayoutManager(context as Activity)
        val contact = User("Jaime Soto","jaime@gmail.com","A dormir","https://contentstatic.techgig.com/thumb/msid-84438897,width-460,resizemode-4/7-Things-about-programming-that-every-coder-hates.jpg?214822")
        contactInfo.add(contact)
        contactsAdapter = ContactsAdapter(context as Activity,contactInfo)
        contactsRecyclerView.adapter = contactsAdapter
        contactsRecyclerView.layoutManager = contactsLayoutManager

        return view
    }
}
