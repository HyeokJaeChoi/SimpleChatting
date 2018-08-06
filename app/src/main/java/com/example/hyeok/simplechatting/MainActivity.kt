package com.example.hyeok.simplechatting

import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.hyeok.simplechatting.adapter.ChatAdapter
import com.example.hyeok.simplechatting.data.ChatItemKeyedDataSource
import com.example.hyeok.simplechatting.model.Chat
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val userName : String = "user" + (5000..6000).shuffled().last()
    private val mChatDatabase = FirebaseDatabase.getInstance().reference
    private lateinit var adapter : ChatAdapter
    private lateinit var pref : SharedPreferences
    private lateinit var prefEditor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        prefEditor = pref.edit()

        if(!pref.contains("chatOrder")){
            prefEditor.putInt("chatOrder", 0)
            prefEditor.apply()
        }

        val config = PagedList.Config.Builder()
                .setPageSize(10)
                .setEnablePlaceholders(false)
                .build()

        val builder = LivePagedListBuilder<Int, Chat>(object : DataSource.Factory<Int, Chat>(){
            override fun create(): DataSource<Int, Chat> {
                return ChatItemKeyedDataSource(mChatDatabase)
            }
        }, config)

        adapter = ChatAdapter(userName)

        chatting_room.adapter = adapter

        builder.build().observe(this, android.arch.lifecycle.Observer {
            adapter.submitList(it)
        })
    }

    fun sendMessage(view : View){
        prefEditor.putInt("chatOrder", pref.getInt("chatOrder", 0) + 1)
        prefEditor.apply()
        val chat = Chat(userName, message_box.text.toString(), System.currentTimeMillis(), pref.getInt("chatOrder", 0))
        mChatDatabase.child("chatList").push().setValue(chat)
        message_box.setText("")
        (chatting_room.adapter as ChatAdapter).currentList?.dataSource?.invalidate()
    }
}
