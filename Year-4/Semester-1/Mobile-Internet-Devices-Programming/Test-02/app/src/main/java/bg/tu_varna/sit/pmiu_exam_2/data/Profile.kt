package bg.tu_varna.sit.pmiu_exam_2.data

import android.os.Parcel
import android.os.Parcelable

class Profile(var name: String, var email: String, var phone: String, var webUrl: String) :
  Parcelable {

  constructor(parcel: Parcel) : this(
    parcel.readString().toString(),
    parcel.readString().toString(),
    parcel.readString().toString(),
    parcel.readString().toString()
  )

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(name)
    parcel.writeString(email)
    parcel.writeString(phone)
    parcel.writeString(webUrl)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Profile> {
    override fun createFromParcel(parcel: Parcel): Profile {
      return Profile(parcel)
    }

    override fun newArray(size: Int): Array<Profile?> {
      return arrayOfNulls(size)
    }
  }
}