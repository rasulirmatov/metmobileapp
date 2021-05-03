package com.example.metproject.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavController

interface SuccessResponse {
    fun gettingParentFragment(): Fragment?
    fun getNavcontroller()
    fun setParent(parentfr : Fragment)
}