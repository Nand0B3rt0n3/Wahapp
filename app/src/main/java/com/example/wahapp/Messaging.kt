package com.example.wahapp

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*

class Messaging : Fragment() {
    private lateinit var  messageRecyclerView : RecyclerView
    private lateinit var sendMessageEditText  : EditText
    private lateinit var sendMessageButton    : FloatingActionButton
    private lateinit var fstore               : FirebaseFirestore
    private lateinit var fauth                : FirebaseAuth
    private lateinit var messageLayoutManager : RecyclerView.LayoutManager
    private lateinit var messageAdapter       : MessageAdapter
    private lateinit var db                   : DocumentReference
    private lateinit var userid               : String
    private val messageInfo = arrayListOf<MessageModal>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_messaging, container, false)
        fstore              = FirebaseFirestore.getInstance()
        fauth               = FirebaseAuth.getInstance()
        userid              = fauth.currentUser?.uid.toString()
        messageRecyclerView = view.findViewById(R.id.messageRecyclerView)
        sendMessageButton   = view.findViewById(R.id.bt_send)
        sendMessageEditText = view.findViewById(R.id.etSendMessage)
        messageLayoutManager= LinearLayoutManager(context)
        fstore.collection("chats").whereArrayContains("uids",userid).addSnapshotListener{
            snapshot,exception->
            if (exception!=null)
            {
                Log.d("onError","Error in fetching Data")
            }
            else
            {
                val list = snapshot?.documents
               if(list != null) {
                    for(doc in list) {
                        db = fstore.collection("chats").document(doc.id).collection("message")
                            .document()
                        fstore.collection("chats").document(doc.id).collection("message")
                            .orderBy("id",Query.Direction.ASCENDING)
                            .addSnapshotListener{ snapshot,exception->
                            if (snapshot != null) {
                                if (!snapshot.isEmpty)
                                {
                                    messageInfo.clear()
                                    val list = snapshot.documents
                                    for (document in list)
                                    {
                                        val obj = MessageModal(document.getString("messageSender").toString(),
                                        document.getString("message").toString(),
                                        document.getString("messageTime").toString())
                                        messageInfo.add(obj)
                                        messageAdapter = MessageAdapter(context as Activity,messageInfo)
                                        messageRecyclerView.adapter = messageAdapter
                                        messageRecyclerView.layoutManager = messageLayoutManager
                                        messageRecyclerView.scrollToPosition(list.size-1)
                                        messageRecyclerView.adapter!!.notifyDataSetChanged()

                                    }
                                }
                            }
                        }
                    }
               }
            }
        }

        messageRecyclerView = view.findViewById(R.id.messageRecyclerView)
        sendMessageButton   = view.findViewById(R.id.bt_send)
        sendMessageEditText = view.findViewById(R.id.etSendMessage)
        messageLayoutManager= LinearLayoutManager(context)
        sendMessageButton.setOnClickListener{
            sendMessage()

        }
        return view
    }

    private fun sendMessage() {
        val message = sendMessageEditText.text.toString()
        if(TextUtils.isEmpty(message)){
            sendMessageEditText.error = "Introduce un mesaje para enviar"
        }
        else
        {
            val c             = Calendar.getInstance()
            val hour          = c.get(Calendar.HOUR_OF_DAY)
            val minute        = c.get(Calendar.MINUTE)
            val timeStamp     = "$hour:$minute"
            val messageObject = mutableMapOf<String,Any>().also {

                it["message"] = message
                it["messageId"] = 1
                it["messageSender"] = userid
                it["message"] = message
                it["messageReciever"] = ""
                it["messageTime"] = timeStamp

            }
            db.set(messageObject).addOnSuccessListener {
                Log.d("onSuccess","Mensaje enviado")
            }
        }
    }
}
