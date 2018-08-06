package com.example.hyeok.simplechatting.data

import android.arch.paging.ItemKeyedDataSource
import android.util.Log
import com.example.hyeok.simplechatting.model.Chat
import com.google.firebase.database.*

class ChatItemKeyedDataSource(private val mChatDatabase : DatabaseReference) : ItemKeyedDataSource<Int, Chat>(){

    private var chatList = ArrayList<Chat>()
    private var endOfTimeStamp = 0

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Chat>) {
        Log.d("ChatItemKeyed", params.requestedInitialKey.toString() + " " + params.requestedLoadSize)
            mChatDatabase.child("chatList").orderByChild("chatOrder").limitToFirst(params.requestedLoadSize)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            Log.d("ChatListInitial", p0.children.toString())

                            for (dataSnapShot in p0.children) {
                                val chat = dataSnapShot.getValue(Chat::class.java)
                                chatList.add(chat!!)
                            }
                            callback.onResult(chatList)
                        }
                    })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Chat>) {
        Log.d("ChatItemKeyed", params.key.toString())
        /*
            mChatDatabase.child("chatList").orderByChild("chatOrder").startAt((params.key + 1).toDouble()).limitToLast(params.requestedLoadSize)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            Log.d("ChatListAfter", p0.children.toString())
                            for (dataSnapShot in p0.children) {
                                val chat = dataSnapShot.getValue(Chat::class.java)
                                chatList.add(chat!!)
                            }
                            callback.onResult(chatList)
                        }
                    })*/
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Chat>) {

    }

    override fun getKey(item: Chat): Int {
        return item.chatOrder
    }
}