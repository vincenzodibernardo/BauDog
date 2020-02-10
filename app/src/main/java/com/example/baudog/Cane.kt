package com.example.baudog

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cane(val id              : String    =   "",
                val razza           : String    =   "",
                val sesso           : String    =   "",
                val colore          : String    =   "",
                val chip            : Boolean   =   false,
                val collare         : Boolean   =   false,
                var colore_collare  : String    =   "",
                var nome_collare    : String    =   "",
                var info            : String    =   "",
                val profileImageUrl : String?   =   "",
                val rit_smarr       : String    =   "") : Parcelable