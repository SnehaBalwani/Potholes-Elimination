package com.example.hackofpi.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentReference
import java.util.ArrayList

data class User(
    val id : String = "",
    val name : String = "",
    val email : String = "",
    val contact : Long = 0,
    val image : String = "",
    val fcmTocken : String = "",
    val complaintList: ArrayList<DocumentReference> = ArrayList(),


    ) : Parcelable
{

    constructor(parcel: Parcel) : this
    (
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readArrayList(DocumentReference::class.java.classLoader)!! as ArrayList<DocumentReference>


    ){

    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeLong(contact)
        parcel.writeString(image)
        parcel.writeString(fcmTocken)
        parcel.writeList(complaintList)

    }


    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
    fun User(){

    }

}

