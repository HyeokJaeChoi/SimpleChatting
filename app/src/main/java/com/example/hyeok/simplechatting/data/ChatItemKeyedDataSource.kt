package com.example.hyeok.simplechatting.data

import android.arch.paging.ItemKeyedDataSource
import android.util.Log
import com.example.hyeok.simplechatting.model.Chat
import com.google.firebase.database.*

class ChatItemKeyedDataSource(private val mChatDatabase : DatabaseReference) : ItemKeyedDataSource<Long, Chat>(){

    private var chatList = ArrayList<Chat>()

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Chat>) {
        Log.d("ChatItemKeyed", params.requestedInitialKey.toString() + " " + params.requestedLoadSize)
        mChatDatabase.child("chatList").orderByChild("timestamp").limitToFirst(params.requestedLoadSize)
                .addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        Log.d("ChatListInitial", p0.toString())
                        for(dataSnapShot in p0.children){
                            val chat = dataSnapShot.getValue(Chat::class.java)
                            chatList.add(chat!!)
                        }
                        callback.onResult(chatList)
                    }
                })
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Chat>) {
        Log.d("ChatItemKeyed", params.key.toString())
        mChatDatabase.child("chatList").orderByChild("timestamp").startAt(params.key.toDouble()).limitToFirst(params.requestedLoadSize)
                .addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        for(dataSnapShot in p0.children){
                            Log.d("ChatListAfter", dataSnapShot.getValue(Chat::class.java).toString())
                            val chat = dataSnapShot.getValue(Chat::class.java)
                            chatList.add(chat!!)
                        }
                        callback.onResult(chatList)
                    }
                })
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Chat>) {

    }

    override fun getKey(item: Chat): Long {
        return item.timestamp
    }
}