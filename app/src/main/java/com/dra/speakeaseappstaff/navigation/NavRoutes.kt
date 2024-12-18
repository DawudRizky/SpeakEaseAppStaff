package com.dra.speakeaseappstaff.navigation

sealed class NavRoute(val route: String) {
    data object History : NavRoute("history")
    data object Scan : NavRoute("scan")
    data object Profile : NavRoute("profile")
    data object Emergency : NavRoute("emergency")
}