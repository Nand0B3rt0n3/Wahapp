package com.example.wahapp

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class Messaging : Fragment() {
    private lateinit var  messageRecyclerView : RecyclerView
    private lateinit var sendMessageEditText  : EditText
    private lateinit var sendMessageButton    : FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_messaging, container, false)
        messageRecyclerView = view.findViewById(R.id.messageRecyclerView)
        sendMessageButton = view.findViewById(R.id.bt_send)
        sendMessageEditText = view.findViewById(R.id.etSendMessage)
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
            val messageObject = mutableMapOf<String,String>().apply{

            }
        }
    }
}
