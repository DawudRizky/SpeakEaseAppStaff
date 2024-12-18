package com.dra.speakeaseappstaff.model

data class Profile(
    val name: String = "",
    val gender: String = "",
    val age: Int = 0,
    val diagnoses: List<Diagnoses> = emptyList(),
    val actionsByStaff: List<ActionByStaff> = emptyList(),
    val medicinesTaken: List<Medicine> = emptyList(),
    val checkups: List<Checkup> = emptyList()
)