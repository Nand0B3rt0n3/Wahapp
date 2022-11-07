package com.example.wahapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.util.*

class SearchAdapter (val context: Context, private val searchList: ArrayList<User>): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){
    class SearchViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name  : TextView = view.findViewById(R.id.txtName)
        // val email --->
        val status: TextView = view.findViewById(R.id.txtStatus)
        val image : ImageView = view.findViewById(R.id.imgUserProfileImage)
        val addFriend : Button = view.findViewById(R.id.btAgree)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val contactView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_contacts, parent, false)
        return SearchViewHolder(contactView)
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val list = searchList[position]
        holder.name.text = list.profileName
//holder email--->
        holder.status.text = list.profileStatus
        holder.addFriend.visibility = View.VISIBLE
        Picasso.get().load(list.profilePicture).error(R.drawable.user).into(holder.image)
        holder.addFriend.setOnClickListener {
            val c = Calendar.getInstance(Locale.getDefault())
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            val timeStamp = "$hour:$minute"
            val obj = mutableMapOf<String, String>().also {
                it["time"] = timeStamp
            }
            val uid1 = FirebaseAuth.getInstance()
                .currentUser?.uid.toString()
            val uid2 = list.profileUid
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid1)
                .collection("friends").document(uid2).set(obj)
                .addOnSuccessListener {
                    Log.d("onSuccess", "Contacto añadido con exito ${list.profileUid}")
                }
            val obj1 = mutableMapOf<String, ArrayList<String>>().also {
                it["uids"] = arrayListOf(uid1, uid2)
            }
            FirebaseFirestore.getInstance()
                .collection("chats")
                .document()
                .set(obj1)
                .addOnSuccessListener {
                    Log.d("onSuccess","Conversacion con ${list.profileUid}")
                }
        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }
}
