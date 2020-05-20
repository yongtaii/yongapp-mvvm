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
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var itemList : List<Item>
    lateinit var itemAdapter : ItemAdapter
    lateinit var viewModel : ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(this, ItemViewModel.Factory(application)).get(ItemViewModel::class.java)
        itemList = ArrayList<Item>()

        viewModel.getItems().observe(this, Observer {updatedList ->
            itemAdapter = ItemAdapter()
            recyclerView.adapter = itemAdapter

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
            diff.dispatchUpdatesTo(itemAdapter)
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

            with(builder)
            {
                setTitle("Add Item")
                setMessage("Add title")
                setView(inUrl)
                setPositiveButton("Add", DialogInterface.OnClickListener(function = positiveButtonClick))
                setNegativeButton(android.R.string.no, null)
                show()
            }

        }

    }


    inner class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
            val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_row,parent,false)
            return ViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
            val item = itemList[position]
            holder.tvTitle.setText(item.title)
            val date = Date(item.date).toString()
            holder.tvDate.setText(date)
        }

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
