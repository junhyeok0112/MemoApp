package org.techtown.memo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query


@Dao
interface MemoDAO {

    @Insert(onConflict = REPLACE)
    fun insert(memo : MemoEntity)

    @Query("SELECT * FROM memo")
    fun getAll() : List<MemoEntity>         //인터페이스이므로 내부 구현할 필요 없음 -> 리턴을 List<MemoEntity>로

    @Delete
    fun delete(memo: MemoEntity)

}