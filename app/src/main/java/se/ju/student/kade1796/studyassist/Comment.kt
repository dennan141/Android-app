package se.ju.student.kade1796.studyassist

import android.os.Parcel
import android.os.Parcelable

data class Comment(

        val category: String? = "",
        val content: String? = "-Empty Content-",
        val threadId: String? = "",
        var likes: Int? = 0,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
    ) {
    }


        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(category)
                parcel.writeString(content)
                parcel.writeString(threadId)
                parcel.writeValue(likes)
        }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }
}