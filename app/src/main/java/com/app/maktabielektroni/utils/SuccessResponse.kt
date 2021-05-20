package com.app.maktabielektroni.utils

import androidx.fragment.app.Fragment

interface SuccessResponse {
    fun gettingParentFragment(): Fragment?
    fun getNavcontroller()
    fun setParent(parentfr : Fragment)
}