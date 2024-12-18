package com.dra.speakeaseappstaff.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dra.speakeaseappstaff.model.Profile
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance("https://speakease-eb1ab-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val _profile = MutableStateFlow(Profile())
    val profile: StateFlow<Profile> get() = _profile

    private val profileRef = database.getReference("profile")
    private val emergencyRef = profileRef.child("emergency")

    private val _isEmergency = MutableStateFlow(false)
    val isEmergency: StateFlow<Boolean> get() = _isEmergency

    init {
        fetchProfile()
        fetchEmergencyStatus()
    }

    fun fetchProfile() {
        profileRef.get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val data = snapshot.getValue(Profile::class.java)
                    if (data != null) {
                        _profile.value = data
                    } else {
                        Log.e("ProfileViewModel", "Data is null")
                    }
                } else {
                    Log.e("ProfileViewModel", "Snapshot does not exist")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ProfileViewModel", "Failed to fetch data", exception)
            }
    }

    private fun fetchEmergencyStatus() {
        emergencyRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val emergencyValue = snapshot.getValue(Int::class.java) ?: 0
                _isEmergency.value = emergencyValue == 1
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ProfileViewModel", "Failed to fetch emergency status: ${error.message}")
            }
        })
    }

    fun setEmergencyStatus(isEmergency: Boolean) {
        emergencyRef.setValue(if (isEmergency) 1 else 0)
            .addOnFailureListener { exception ->
                Log.e("ProfileViewModel", "Failed to set emergency status", exception)
            }
    }
}
