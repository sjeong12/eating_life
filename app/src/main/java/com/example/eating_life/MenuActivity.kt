package com.example.eating_life

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*
import java.lang.Boolean.TRUE

class MenuActivity : AppCompatActivity() {
    var dbHelper : DBHelper? = null
    var menu_list = arrayListOf<menu_item>()
    lateinit var menuListAdapter: MenuListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        dbHelper = DBHelper(this)
        menu_list = dbHelper!!.selectAll()
        menuListAdapter = MenuListAdapter(this, menu_list)
        menuListview.adapter = menuListAdapter
        addButton.setOnClickListener {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.custom_dialog, null)

            val alertDialog = AlertDialog.Builder(this)
                .setTitle("추가하고 싶은 메뉴를 입력해주세요")
                .setPositiveButton("추가") { dialog, which ->
                    val textView: TextView = view.findViewById(R.id.addText)
                    if (textView.text.toString() == null || textView.text.toString() == "") {
                        Toast.makeText(this, "메뉴를 입력해주세요", Toast.LENGTH_LONG).show()
                    }
                    else if(dbHelper!!.select(textView.text.toString()).size != 0) {
                        Toast.makeText(this, "이미 존재하는 메뉴입니다", Toast.LENGTH_LONG).show()
                    }
                    else {
                        dbHelper!!.insertRecord(textView.text.toString())
                        menuListAdapter = MenuListAdapter(this, dbHelper!!.selectAll())
                        menuListview.adapter = menuListAdapter
                        menuListAdapter.notifyDataSetChanged()
                    }
                }
                .setNeutralButton("취소", null)
                .create()
            alertDialog.setCancelable(false)
            alertDialog.setView(view)
            alertDialog.show()
        }
        searchButton.setOnClickListener {
            menuListAdapter = MenuListAdapter(this, dbHelper!!.search(searchText.getText().toString()))
            menuListview.adapter = menuListAdapter
            menuListAdapter.notifyDataSetChanged()
        }
        back_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}

class menu_item (var name: String, val isSelect: Int)

class MenuListAdapter (val context: Context, val menuList: ArrayList<menu_item>) : BaseAdapter() {
    var dbHelper : DBHelper? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.custom_list, null)
        val name_text = view.findViewById<TextView>(R.id.lstNameText)
        val is_select_button = view.findViewById<ToggleButton>(R.id.lstToggleButton)
        val delete_button = view.findViewById<ImageButton>(R.id.lstDeleteButton)

        dbHelper = DBHelper(context)
        val menu = menuList[position]
        name_text.text = menu.name
        if(menu.isSelect == 1) is_select_button.setSelected(true)

        delete_button.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_Dialog)
            alertDialog.setTitle("정말 삭제하시겠습니까?")
            alertDialog.setPositiveButton("삭제") { dialog, which ->
                    Toast.makeText(context, menu.name + " 삭제", Toast.LENGTH_SHORT).show()
                    dbHelper!!.deleteRecord(menu.name)
                    menuList.remove(menu)
                    notifyDataSetChanged()
                }
                .setNeutralButton("취소", null)
                .create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return menuList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return menuList.size
    }
}
