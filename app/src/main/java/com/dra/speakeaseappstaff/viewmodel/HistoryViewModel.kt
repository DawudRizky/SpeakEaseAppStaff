package com.dra.speakeaseappstaff.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HistoryViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance("https://speakease-eb1ab-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val userRef = database.getReference("profile/history")

    private val _history = MutableStateFlow<List<HistoryItem>>(emptyList())
    val history: StateFlow<List<HistoryItem>> = _history

    init {
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val historyList = snapshot.children.mapNotNull { dataSnapshot ->
                    val text = dataSnapshot.child("text").getValue(String::class.java)
                    val timestamp = dataSnapshot.child("timestamp").getValue(String::class.java)
                    if (text != null && timestamp != null) {
                        HistoryItem(text, timestamp)
                    } else {
                        null
                    }
                }
                _history.value = historyList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HistoryViewModel", "Database error: ${error.message}")
            }
        })
    }
}

data class HistoryItem(val text: String, val timestamp: String)
