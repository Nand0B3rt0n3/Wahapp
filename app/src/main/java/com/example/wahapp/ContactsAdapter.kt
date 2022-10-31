package com.example.wahapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class ContactsAdapter(val context : Context, private val contactList : ArrayList<User>)

    :RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>(){
        class ContactsViewHolder(view: View):RecyclerView.ViewHolder(view) {
            val name  : TextView  = view.findViewById(R.id.txtName)
            val status: TextView  = view.findViewById(R.id.txtStatus)
            val image : ImageView = view.findViewById(R.id.imgUserProfileImage)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val contactView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_contacts,parent,false)
        return ContactsViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val list = contactList[position]
        holder.name.text = list.profileName

        holder.status.text = list.profileStatus
        Picasso.get().load(list.profilePicture).error(R.drawable.user).into(holder.image)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}