package com.mellie.melliesimpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks= mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onlongClickListener= object: TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                listOfTasks.removeAt(position)
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

        //findViewById<Button>(R.id.button).setOnClickListener{
        //    Log.i("Mellie","User clicked on button")
       // }
        loadItems()

        val recyclerView= findViewById<RecyclerView>(R.id.recyclerView)
        adapter= TaskItemAdapter(listOfTasks, onlongClickListener)
        recyclerView.adapter=adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val inputTextField=findViewById<EditText>(R.id.addTaskField)
        findViewById<Button>(R.id.button).setOnClickListener{
            val userInputtedTask=inputTextField.text.toString()
            listOfTasks.add(userInputtedTask)
            adapter.notifyItemInserted(listOfTasks.size-1)
            inputTextField.setText("")

            saveItems()
        }
    }
    //Save the data the user has inputed
    fun getDatafile(): File {
        return File(filesDir,"data.txt")
    }
    fun loadItems(){
        try {
    listOfTasks= FileUtils.readLines(getDatafile(), Charset.defaultCharset())
    }catch(ioException:IOException){
        ioException.printStackTrace()
        }
    }
    fun saveItems(){
    try{
        FileUtils.writeLines(getDatafile(), listOfTasks)
    }catch (ioException: IOException){
            ioException.printStackTrace()
         }
    }
}