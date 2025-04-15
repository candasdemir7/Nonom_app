package com.mustafacandasdemir.candasdemir_hw2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mustafacandasdemir.candasdemir_hw2.util.Utils

@Entity(tableName = Utils.TABLENAME_OWNER)
class Owner (
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    @SerializedName("name") //name match with the key of JSON object, GSON according to that name will map the object to data members
    val name: String = "",
    @SerializedName("date")
    val date: String = "")
{

    override fun toString(): String {
        return "Owner\nName: $name\nDate=$date\n"
    }
}
