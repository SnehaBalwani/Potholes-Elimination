package com.example.hackofpi.model

import android.os.Parcel
import android.os.Parcelable
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation

data class Complaint(
    val id: String = "",
    val comments: String = "",
    val image1: String = "",
    val image2: String = "",
    val image3: String = "",
    val image4: String = "",
    val image5: String = "",
    val count: Int = 0,
    val latLng: GeoLocation = GeoLocation(0.0,0.0),
    val hash: String = GeoFireUtils.getGeoHashForLocation(latLng as GeoLocation).toString(),
    val name: String = "",
    val status: Long = 0

    ) : Parcelable
{

    constructor(parcel: Parcel) : this
        (
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readValue(GeoLocation::class.java.classLoader)!! as GeoLocation,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong()!!


        ){

    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(comments)
        parcel.writeString(image1)
        parcel.writeString(image2)
        parcel.writeString(image3)
        parcel.writeString(image4)
        parcel.writeString(image5)
        parcel.writeInt(count)
        parcel.writeValue(latLng)
        parcel.writeString(hash)
        parcel.writeString(name)
        parcel.writeLong(status)

    }


    companion object CREATOR : Parcelable.Creator<Complaint> {
        override fun createFromParcel(parcel: Parcel): Complaint {
            return Complaint(parcel)
        }

        override fun newArray(size: Int): Array<Complaint?> {
            return arrayOfNulls(size)
        }
    }
    fun Complaint(){

    }

}

