package com.example.hyeok.simplechatting.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.example.hyeok.simplechatting.model.Chat

class ChatAdapter() : PagedListAdapter<Chat, ChatViewHolder>(diffCallback){

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Chat>(){
            override fun areItemsTheSame(oldItem: Chat?, newItem: Chat?): Boolean {
                return oldItem?.message == newItem?.message
            }

            override fun areContentsTheSame(oldItem: Chat?, newItem: Chat?): Boolean {
                return (oldItem?.message == newItem?.message) && (oldItem?.userName == newItem?.userName)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ChatViewHolder{
        return ChatViewHolder(parent);
    }

    override fun onBindViewHolder(holder : ChatViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }
}