package com.example.hyeok.simplechatting.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.hyeok.simplechatting.R
import com.example.hyeok.simplechatting.model.Chat
import java.text.SimpleDateFormat
import java.util.*

class MeChatViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.chat_me, parent, false)){

    private val userNameView = itemView.findViewById<TextView>(R.id.user_name)
    private val messageView = itemView.findViewById<TextView>(R.id.message)
    private val timeStampView = itemView.findViewById<TextView>(R.id.send_time)

    private val simpleDateFormat = SimpleDateFormat("HH:mm:ss")

    fun bindTo(chat : Chat?){
        val date = Date(chat!!.timestamp)
        userNameView.text = chat?.userName
        messageView.text = chat?.message
        timeStampView.text = simpleDateFormat.format(date)
    }
}