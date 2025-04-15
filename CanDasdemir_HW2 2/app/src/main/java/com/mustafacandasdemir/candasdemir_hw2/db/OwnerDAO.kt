package com.mustafacandasdemir.candasdemir_hw2.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mustafacandasdemir.candasdemir_hw2.model.Owner
import com.mustafacandasdemir.candasdemir_hw2.util.Utils


@Dao
interface OwnerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOwner(owner: Owner) // suspend is written because it will be used with coroutine

    @Update
    fun updateOwner(owner: Owner)

    @Delete
    fun deleteOwner(customer: Owner)

    @Query("SELECT * FROM ${Utils.TABLENAME_OWNER}")
    fun getAllOwners(): LiveData<List<Owner>>
}