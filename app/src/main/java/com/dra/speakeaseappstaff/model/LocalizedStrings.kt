package com.dra.speakeaseappstaff.model

import com.dra.speakeaseappstaff.R
import java.util.Locale

object LocalizedStrings {
    fun getEmergencyText(locale: Locale): List<String> {
        return when (locale) {
            Locale("id") -> listOf(
                "DARURAT",
                "TENAGA MEDIS DIPERLUKAN",
                "Segera Bertindak",
            )
            else -> listOf(
                "EMERGENCY",
                "MEDICAL STAFF NEEDED",
                "Immediate Action Needed",
            )
        }
    }

    fun getNavLabels(locale: Locale): List<BottomNavItem> {
        return when (locale) {
            Locale("id") -> listOf(
                BottomNavItem("history", "Riwayat", R.drawable.nav_history),
                BottomNavItem("scan", "Pindai", R.drawable.nav_scan),
                BottomNavItem("profile", "profil", R.drawable.nav_profile)
            )
            else -> listOf(
                BottomNavItem("history", "History", R.drawable.nav_history),
                BottomNavItem("scan", "Scan", R.drawable.nav_scan),
                BottomNavItem("profile", "Profile", R.drawable.nav_profile)
            )
        }
    }
}
