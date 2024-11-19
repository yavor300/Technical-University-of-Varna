package bg.tu_varna.sit.lab_07.data

import android.os.Parcel
import android.os.Parcelable

class Person(val name: String, var email: String = "", var phone: String = "") : Parcelable {
  constructor(parcel: Parcel) : this(
    parcel.readString().toString(),
    parcel.readString().toString(),
    parcel.readString().toString()
  )

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(name)
    parcel.writeString(email)
    parcel.writeString(phone)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Person> {
    override fun createFromParcel(parcel: Parcel): Person {
      return Person(parcel)
    }

    override fun newArray(size: Int): Array<Person?> {
      return arrayOfNulls(size)
    }
  }
}