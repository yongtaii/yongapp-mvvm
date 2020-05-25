package com.rnd.jworld.livedatabasic

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var itemList : List<Item>
    lateinit var itemAdapter : ItemAdapter
    lateinit var viewModel : ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Layoutmanager : 리사이클러뷰가 아이템을 화면에 표시할 때, 아이템 뷰들이 리아시클러뷰 내부에서 배치되는 형태를 관리하는 요소.
        // 리스트뷰 vs 리사이클러뷰 : 리스트뷰는 수직 스크롤만 가능하지만, 리사이클러뷰는 데이터목록을 수직,수평,Grid 등의 형태로 배치할 수 있다
        // recyclerview는 데이터목록을 수직,수평,Grid 등의 형태로 배치할 수 있는데, 이를 관리하는 요소가 Layout manager이다.
        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(this, ItemViewModel.Factory(application)).get(ItemViewModel::class.java)
        itemList = ArrayList<Item>()
        itemAdapter = ItemAdapter()
        recyclerView.adapter = itemAdapter

        // viewModel의 livedate를 observe
        viewModel.getItems().observe(this, Observer {updatedList ->

            // DiffUtil : 두 목록간의 차이점을 찾고 업데이트 되어야 할 목록을 반환해준다.
            //getOldListSize : 이전 목록 갯수 반환
            // getNewListSize : 새로운 목록 갯수 반환
            // areItemsTheSame : 두 객체가 같은 항목인지 여부 반환
            // areContentsTheSame : 두 항목의 데이터가 같은지 여부를 결정
            // 리사이클러뷰에서 아이템 리스트가 변경되었을때 수동으로 notifyDataSetChanged()를 사용하는 것보다 DiffUtil쓰는것이 효율적이다.
           val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return itemList[oldItemPosition].id == updatedList[newItemPosition].id
                }
                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return itemList[oldItemPosition] == updatedList[newItemPosition]
                }
                override fun getOldListSize() = itemList.size
                override fun getNewListSize() = updatedList.size
            })
            // dispactUpdatesTo 를 호출하여 업데이트할 Adapter를 설정.
            // diff 계산에서 반환된 DiffResult 객체가 변경사항을 Adapter에 전달하고 어댑터가 변경사항에 대해 알림을 받음.
            diff.dispatchUpdatesTo(itemAdapter)
            // 아이템 목록 수정해줌.
            itemList = updatedList
        })

        btnAdd.setOnClickListener {
            val inUrl = EditText(this)
            val builder = AlertDialog.Builder(this)

            val positiveButtonClick = { dialog: DialogInterface, which: Int ->
                val title = inUrl.text.toString()
                val date = Date().time
                viewModel.addItem(title,date)
            }
            // Scope Function
            // with : 블록에 리시버 객체를 전달. 수행된 결과를 반환. this로 객체 접근 가능.(this 생략도 가능)
            with(builder)
            {
                setMessage("내용을 입력해 주세요")
                setView(inUrl)
                setPositiveButton("Add", DialogInterface.OnClickListener(function = positiveButtonClick))
                setNegativeButton(android.R.string.no, null)
                show()
            }

        }

    }


    // 리사이클러뷰 vs 리스트뷰 : 레이아웃매니저, ViewHolder 패턴 의무사용, Item에 대한 뷰의 변형이나 애니메이션 할수있는 개념추가가
    // 리사이클러뷰 : 데이터를 리스트 형태로 화면에 표시하는 컨테이너 역할 수행
    // 레이아웃매니저 : 어떤 혀앹로 배치될 아이템 뷰를 만들것인지 결정
    // 어댑터 : 리사이클러뷰에 표시될 아이템뷰를 생성하는 역할. 사용자 데이터 리스트로부터 아이템 뷰를 생성.
    // ItemDecoration : 아이템 항목에서 서브 뷰에대 한 처리, 쉽게 아이템들을 divide 할 수있다
    // ItemAnimation  : 아이템 항목이 추가/제거 되거나 정렬될때 애니메이션 처리
    inner class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

        // onCreateViewHolder : 뷰 홀더를 생성하고 뷰를 붙여주는 부분
        // 리사이클러뷰 vs 리스트뷰 : 리스트 뷰가 사용했던 getView() 메서드는 매번 호출되면서 null처리를 해야했다면, onCreateViewHolder는 새롭게 생성될때만 불린다
        // 리사이클러뷰 vs 리스트뷰 : UI 수정할때마다 부르는 findVIewById()를 뷰홀더 패턴을 이용해 한번만 함으로서 리스트뷰의 지연을 초래하는 무거운 연산을 줄였다
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
            val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_row,parent,false)
            return ViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        // onBindViewHolder : 재활용 되는 뷰가 호출하여 실행되는 메서드, 뷰 홀더를 전달하고 어댑터는 position의 데이터를 결합시킨다
        override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
            val item = itemList[position]
            holder.tvTitle.setText(item.title)
            val date = SimpleDateFormat("yyyy년 MM년 dd일 E요일 HH:mm").format(item.date)
            holder.tvDate.setText(date)
        }


        // 뷰홀더 : 화면에 표시될 아이템 뷰를 저자앟는 객체
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
            val tvTitle : TextView
            val tvDate : TextView

            init {
                tvTitle = view.findViewById(R.id.tvTitle)
                tvDate = view.findViewById(R.id.tvDate)
                val btnDelete : ImageButton = view.findViewById(R.id.btnDelete)
                btnDelete.setOnClickListener {
                    adapterPosition
                    val item = itemList[adapterPosition]
                    viewModel.removeItem(item.id)
                }
            }

        }

    }
}
