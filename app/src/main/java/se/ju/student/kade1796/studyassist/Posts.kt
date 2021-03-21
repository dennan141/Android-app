package se.ju.student.kade1796.studyassist

import android.os.Parcel
import android.os.Parcelable

data class Posts(
        val content: String? = "-Empty Content-",
        val id: String? = "",
        var likes: Int? = 0,
) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readValue(Int::class.java.classLoader) as? Int
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(content)
                parcel.writeString(id)
                parcel.writeValue(likes)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Posts> {
                override fun createFromParcel(parcel: Parcel): Posts {
                        return Posts(parcel)
                }

                override fun newArray(size: Int): Array<Posts?> {
                        return arrayOfNulls(size)
                }
        }
}