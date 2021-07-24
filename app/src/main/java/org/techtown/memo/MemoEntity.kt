package org.techtown.memo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo")
data class MemoEntity(    //테이블이라 생각
    @PrimaryKey(autoGenerate = true)        //따로 지정하지 않아도 기본적으로 1 2 3 .. 으로 들어감
    var id : Long? ,
    var memo:String = "")