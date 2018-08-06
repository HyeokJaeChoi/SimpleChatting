package com.example.hyeok.simplechatting.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.hyeok.simplechatting.R
import com.example.hyeok.simplechatting.model.Chat

class OtherChatViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.chat_other, parent, false)){

    private val userNameView = itemView.findViewById<TextView>(R.id.user_name)
    private val messageView = itemView.findViewById<TextView>(R.id.message)
    private val timeStampView = itemView.findViewById<TextView>(R.id.send_time)

    fun bindTo(chat : Chat?){
        userNameView.text = chat?.userName
        messageView.text = chat?.message
        timeStampView.text = chat?.timestamp.toString()
    }
}