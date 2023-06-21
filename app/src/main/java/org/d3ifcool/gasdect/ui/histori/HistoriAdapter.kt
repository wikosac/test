package org.d3ifcool.gasdect.ui.histori

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.d3ifcool.gasdect.R
import org.d3ifcool.gasdect.model.Histori

class HistoriAdapter(var context: Context) : RecyclerView.Adapter<HistoriAdapter.MyViewHolder>() {

    var historiList: List<Histori> = listOf()
    var historiListFiltered: List<Histori> = listOf()

    fun setHistoriList(context: Context, historiList: List<Histori>) {
        this.context = context
        if (historiList == null) {
            this.historiList = historiList
            this.historiListFiltered = historiList
            notifyItemChanged(0, historiListFiltered.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@HistoriAdapter.historiList.size
                }

                override fun getNewListSize(): Int {
                    return historiList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@HistoriAdapter.historiList.get(oldItemPosition)
                        .gasvalue === historiList[newItemPosition].gasvalue
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val newHistori: Histori = this@HistoriAdapter.historiList.get(oldItemPosition)
                    val oldHistori: Histori = historiList[newItemPosition]
                    return newHistori.gasvalue === oldHistori.gasvalue
                }
            })
            this.historiList = historiList
            this.historiListFiltered = historiList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_histori, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.gasvalue!!.text = historiListFiltered[position].gasvalue.toString()

        val histori: Histori = historiListFiltered[position]

        val id: String = histori.token
        val time: String = histori.time
        val gasvalue: String = histori.gasvalue

        // Bind
        holder.token!!.text = id
        holder.time!!.text = time
        holder.gasvalue!!.text = gasvalue

        holder.setItemClickListener(object : ItemClickListener {
            override fun onItemClick(pos: Int) {
                // toast
            }
        })
    }

    override fun getItemCount(): Int {
        return if (historiList != null) {
            historiListFiltered.size
        } else {
            0
        }
    }

    interface ItemClickListener {
        fun onItemClick(pos: Int)
    }

    inner class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!),
        View.OnClickListener {

        private lateinit var itemClickListener: ItemClickListener

        var token: TextView? = null
        var time: TextView? = null
        var gasvalue: TextView? = null

        init {
            token = itemView!!.findViewById<View>(R.id.tokenTextView) as TextView
            time = itemView.findViewById<View>(R.id.timeTextView) as TextView
            gasvalue = itemView.findViewById<View>(R.id.gasvalueTextView) as TextView
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            this.itemClickListener.onItemClick(this.layoutPosition)
        }

        fun setItemClickListener(itemClickListener: ItemClickListener) {
            this.itemClickListener = itemClickListener
        }
    }
}