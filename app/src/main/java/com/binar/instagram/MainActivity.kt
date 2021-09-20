package com.binar.instagram

import android.content.res.AssetManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    //private val gridView: GridView by lazy { findViewById(R.id.gv_post) }
    private val rvColor: RecyclerView by lazy { findViewById(R.id.rv_post) }

    private val TAG = "MAIN ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //gridView.adapter

        val itemColors = listOf(
            ItemColor("red", Color.RED),
            ItemColor("green", Color.GREEN),
            ItemColor("yellow", Color.YELLOW),
            ItemColor("black", Color.BLACK),
            ItemColor("blue", Color.BLUE),
            ItemColor("cyan", Color.CYAN),
            ItemColor("magenta", Color.MAGENTA),
        )

        val rvAdapter = RvAdapter()
        rvColor.layoutManager = GridLayoutManager(this, 3)
        rvColor.adapter = rvAdapter

        rvAdapter.addList(itemColors)

        getFileFromAsset("note.txt")
    }

    private fun getFileFromAsset(path: String) {
        val inputStream = assets.open(path)

        val r = BufferedReader(InputStreamReader(inputStream))
        val total = StringBuilder()
        var line: String?
        while (r.readLine().also { line = it } != null) {
            total.append(line).append('\n')
        }

        Log.d(TAG, "getFileFromAsset: $total")
    }

    class RvAdapter : RecyclerView.Adapter<RvViewHolder>() {
        private val listItemColor: MutableList<ItemColor> = mutableListOf()

        fun addList(itemColors: List<ItemColor>) {
            listItemColor.addAll(itemColors)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
            return RvViewHolder(view)
        }

        override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
            val item = listItemColor[position]
            holder.bindItem(item)
        }

        override fun getItemCount(): Int {
            return listItemColor.size
        }

    }

    class RvViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(itemColor: ItemColor) = itemView.run {
            val viewColor: View = findViewById(R.id.view_color)
            val textViewColor: TextView = findViewById(R.id.tv_color)

            viewColor.setBackgroundColor(itemColor.color)
            textViewColor.text = itemColor.itemName
        }
    }

    data class ItemColor(
        val itemName: String,
        val color: Int
    )
}