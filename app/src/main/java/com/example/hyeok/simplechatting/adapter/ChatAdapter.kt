package com.example.hyeok.simplechatting.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.hyeok.simplechatting.model.Chat

class ChatAdapter(private val userName : String) : PagedListAdapter<Chat, RecyclerView.ViewHolder>(diffCallback){

    private val CHAT_OF_ME = 1;
    private val CHAT_OF_OTHER = 2;

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

    override fun getItemViewType(position: Int): Int {
        if(getItem(position)?.userName == userName)
            return CHAT_OF_ME
        else
            return CHAT_OF_OTHER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder{
        when(viewType){
            CHAT_OF_ME -> {
                return MeChatViewHolder(parent)
            }
            CHAT_OF_OTHER -> {
                return OtherChatViewHolder(parent)
            }
        }
    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            CHAT_OF_ME -> {
                (holder as MeChatViewHolder).bindTo(getItem(position))
            }
            CHAT_OF_OTHER -> {
                (holder as OtherChatViewHolder).bindTo(getItem(position))
            }
        }
    }
}