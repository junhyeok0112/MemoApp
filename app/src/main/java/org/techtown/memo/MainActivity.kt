package org.techtown.memo

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.loader.content.AsyncTaskLoader
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.memo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , OnDeleteListener {               //OnDeleteListener 인터페이스 구현해주기 -> 이걸 Adapter에서 사용

    lateinit var db : MemoDatabase
    var memoList = listOf<MemoEntity>()
    private lateinit var binding :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MemoDatabase.getInstance(this)!!   //Companion object로 만들어진 싱글톤 객체 가져오는 과정

        binding.btnAdd.setOnClickListener {
            val memo = MemoEntity(null, binding.edittextMemo.text.toString())   //Autogenerate 여서 null이여도 자동으로 생성
            binding.edittextMemo.setText("")
            insertMemo(memo)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        getAllMemos()                                                           //처음 실행했을 때부터 여태 내용 다 나오게함

    }

    //1. Insert Dat
    //2. Get Data
    //3. Delete Data

    fun insertMemo(memo:MemoEntity){
        //1. MainThread vs WorkerThread(Backgrond Thread)
        //UI관련이 아닌 데이터 관련작업은 BackGround에서 해야하는 데 이를 위해 코루틴 사용 -> BackGround에서 처리도 되고 UI에 적용도 가능
        val insertTask = object : AsyncTask<Unit , Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                //백그라운드에서 진행할 일 정의
                db.memoDAO().insert(memo)
            }

            @SuppressLint("StaticFieldLeak")
            override fun onPostExecute(result: Unit?) { //백그라운드에서 실행이 끝난 뒤에 해야할일
                super.onPostExecute(result)
                getAllMemos()                           //insert 했으니까 데이터들을 전부 다시 불러와라
            }
        }

        insertTask.execute()        //백그라운드에서 실행 ->insertTask 구현현
    }

    fun getAllMemos(){
        val getTask = (object : AsyncTask<Unit, Unit, Unit>(){   //AsyncTask는 비동기적 활동이나 BackGround에허 하는 작업을 도와주는 클래스
            override fun doInBackground(vararg params: Unit?) {
                memoList = db.memoDAO().getAll()                //메모리스트의 메모를 전부 가져와서 비어있는 메모리스트에 넣어줌
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                setRecyclerView(memoList)
            }

        }).execute()                                            //바로 실행


    }

    fun deleteMemo(memo:MemoEntity){                            //지우고 싶은 메모받음
        val deleteTast = object : AsyncTask<Unit, Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                db.memoDAO().delete(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }

        deleteTast.execute()

    }

    fun setRecyclerView(memoList : List<MemoEntity>){   //Adapter 사용해 RecyclerView에 어댑터 연결
        //insert누르면 getAllmemo()메소드에서 실행되면서 뜸
        binding.recyclerView.adapter = MyAdapter(this,memoList,this)           //Adapter에 여기서 구현한 리스너 넘겨줘서 Adapter에서 사용

    }

    override fun onDeleteListener(memo: MemoEntity) {   //이 액티비티에 있는 메소드를 다른 액티비티에서 사용하기 위해서 지정한 인터페이스 구현
        deleteMemo(memo)
    }
}