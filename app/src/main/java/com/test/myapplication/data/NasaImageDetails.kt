package com.test.myapplication.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NasaImageDetails(
    val title: String,
    val center: String,
    val description: String,
    val dateCreated: String,
    val href: String
    ) : Parcelable