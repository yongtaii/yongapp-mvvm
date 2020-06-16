package com.rnd.jworld.rxjavaretrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rnd.jworld.rxjavaretrofit.pojo.Currency
import java.text.SimpleDateFormat

// 리사이클러뷰 vs 리스트뷰 : 레이아웃매니저, ViewHolder 패턴 의무사용, Item에 대한 뷰의 변형이나 애니메이션 할수있는 개념추가가
// 리사이클러뷰 : 데이터를 리스트 형태로 화면에 표시하는 컨테이너 역할 수행
// 레이아웃매니저 : 어떤 혀앹로 배치될 아이템 뷰를 만들것인지 결정
// 어댑터 : 리사이클러뷰에 표시될 아이템뷰를 생성하는 역할. 사용자 데이터 리스트로부터 아이템 뷰를 생성.
// ItemDecoration : 아이템 항목에서 서브 뷰에대 한 처리, 쉽게 아이템들을 divide 할 수있다
// ItemAnimation  : 아이템 항목이 추가/제거 되거나 정렬될때 애니메이션 처리

class RecyclerViewAdapter(private val items:ArrayList<Currency>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    // onCreateViewHolder : 뷰 홀더를 생성하고 뷰를 붙여주는 부분
    // 리사이클러뷰 vs 리스트뷰 : 리스트 뷰가 사용했던 getView() 메서드는 매번 호출되면서 null처리를 해야했다면, onCreateViewHolder는 새롭게 생성될때만 불린다
    // 리사이클러뷰 vs 리스트뷰 : UI 수정할때마다 부르는 findVIewById()를 뷰홀더 패턴을 이용해 한번만 함으로서 리스트뷰의 지연을 초래하는 무거운 연산을 줄였다
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_row,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // onBindViewHolder : 재활용 되는 뷰가 호출하여 실행되는 메서드, 뷰 홀더를 전달하고 어댑터는 position의 데이터를 결합시킨다
    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.setText(item.nation)
        holder.tvCur.setText(item.rate)
    }

    // item list 재할당
    fun setData(updatedItems:ArrayList<Currency>) {
        items.clear()
        items.addAll(updatedItems)
        notifyDataSetChanged()
    }

    // 뷰홀더 : 화면에 표시될 아이템 뷰를 저장하는 객체
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvName : TextView
        val tvCur : TextView

        init {
            tvName = view.findViewById(R.id.tvName)
            tvCur = view.findViewById(R.id.tvCur)
        }
    }

}