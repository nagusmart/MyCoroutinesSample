package com.example.mycoroutinessample.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
                 @Expose
                 @SerializedName("id")
                 var id: String,
                 @Expose
                 @SerializedName("name")
                 var name: String,
                 @Expose
                 @SerializedName("title")
                 var title: String,
                 @Expose
                 @SerializedName("message")
                 var message: String
) : Parcelable