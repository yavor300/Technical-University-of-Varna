package bg.tuvarna.sit.data

import android.os.Parcel
import android.os.Parcelable

class Profile(val name: String, var email: String, var phone: String) :
  Parcelable {

  private constructor(parcel: Parcel) : this(
    name = parcel.readString() ?: throw IllegalStateException("Name is null"),
    email = parcel.readString() ?: throw IllegalStateException("Email is null"),
    phone = parcel.readString() ?: throw IllegalStateException("Phone is null")
  )

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(name)
    parcel.writeString(email)
    parcel.writeString(phone)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Profile> {
    override fun createFromParcel(parcel: Parcel): Profile = Profile(parcel)
    override fun newArray(size: Int): Array<Profile?> = arrayOfNulls(size)
  }
}
