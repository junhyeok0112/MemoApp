package org.techtown.memo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.memo.databinding.ItemMemoBinding

class MyAdapter(val context : Context, var list : List<MemoEntity> , var onDeleteListener: OnDeleteListener) :RecyclerView.Adapter<MyAdapter.MyViewHolder> (){


    inner class MyViewHolder(binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root){ //뷰 홀더에는 root로
        val memo = binding.textviewMemo
        val root = binding.root

    }


    override fun getItemCount(): Int {      //아이템이 몇개 있는지 리턴 , 매개변수의 list에 item을 넣음
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {   //보여줄 뷰를 만드는 메소드
        //ViewBinding 사용해서 할때는 onCreateViewHolder에서 binding객체 생성
        //binding은 사용할 XML의 이름을 거꾸로 뒤집은 것
        val binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context),parent, false)    //parent는 매개변수로 받은 ViewGroup
        return MyViewHolder(binding)   //ViewHolder를 onCreateViewHolder를 통해서 만들어냄
        //생성된 객체가 BindViewHolder로 넘어감
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {    //만들어진 틀과 내욜을 결합해줌
        val memo = list[position]                                           //posiong 번째에 있는 것과 MyViewHolder와 binding
        holder.memo.text = memo.memo                                        //ViewHolder에서 셋팅한 textView에 list의 memo값 설정

        //기능 추가하려면 onBindViewHolder 안에 구현
        holder.root.setOnLongClickListener(object :View.OnLongClickListener{//길게 눌렀을 때 메모 삭제  ->root로 지정한 건 그 칸 전체를 눌러도된다는 뜻
            override fun onLongClick(v: View?): Boolean {                   //Main에 있는 delet 실행해야함
                onDeleteListener.onDeleteListener(memo)                     //33번째 줄에 있는 memo를 넘겨줘서 삭제
                                                                            //onDeleteListener에 있는 onDeleteLisener 트리거

                return true
            }
        })

    }




}