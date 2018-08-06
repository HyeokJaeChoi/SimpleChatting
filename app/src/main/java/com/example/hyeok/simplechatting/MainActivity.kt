package com.example.hyeok.simplechatting

import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val config = PagedList.Config.Builder()
                .setPageSize(10)
                .setPrefetchDistance(5)
                .setEnablePlaceholders(false)
                .build()

        val builder = LivePagedListBuilder<Long, Chat>(object : DataSource.Factory<Long, Chat>(){
            override fun create(): DataSource<Long, Chat> {
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
        val chat = Chat(userName, message_box.text.toString(), System.currentTimeMillis())
        mChatDatabase.child("chatList").push().setValue(chat)
        message_box.setText("")
        (chatting_room.adapter as ChatAdapter).currentList?.dataSource?.invalidate()
    }
}
