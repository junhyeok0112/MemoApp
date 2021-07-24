package org.techtown.memo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(MemoEntity::class), version = 1)              //테이블을 어떤걸 사용할 것인지
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDAO() : MemoDAO//만든 memodao를 반환하는 추상화 메소드 만들어야함

    companion object{       //싱글톤 패턴 -> 전체 프로세스 안에서 객체 한번만 생성 : 이유 DB만드는게 리소스를 많이 잡아먹어서

        var INSTANCE : MemoDatabase? = null

        fun getInstance(context: Context) : MemoDatabase? {
            if(INSTANCE == null){
                synchronized(MemoDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    MemoDatabase::class.java, "memo.db")
                        .fallbackToDestructiveMigration().build()

                    //데이터 베이스 생성 후 그 schema를 계속 쓰는게 아니라 수정이 일어날 수 있음 ->수정하면 버전 변경해줘야함
                    //이때 버전 변경이 되면 과거의 DB를 그대로 옮길것인가(MIGRATION) 아니면 전부 삭제하고 새로운 데이터로 시작할것인가(fallbackToDes~ )
                    //우리가 하는건 전부 삭제하는 방법
                }
            }

            return INSTANCE
        }

    }
}