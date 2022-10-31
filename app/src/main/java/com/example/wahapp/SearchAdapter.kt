package com.example.wahapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class SearchAdapter (val context: Context,private val searchList: ArrayList<User>): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){
    class SearchViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name  : TextView = view.findViewById(R.id.txtName)
        // val email --->
        val status: TextView = view.findViewById(R.id.txtStatus)
        val image : ImageView = view.findViewById(R.id.imgUserProfileImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val contactView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_contacts, parent, false)
        return SearchAdapter.SearchViewHolder(contactView)
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val list = searchList[position]
        holder.name.text = list.profileName
//holder email--->
        holder.status.text = list.profileStatus
        Picasso.get().load(list.profilePicture).error(R.drawable.user).into(holder.image)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }
}
