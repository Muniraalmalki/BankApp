package com.example.muniraalmalki

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

     lateinit var currentBalance:TextView
     lateinit var recyclerView: RecyclerView
     lateinit var etDeposits:EditText
     lateinit var depositButton:Button
     lateinit var etWithdrawals:EditText
     lateinit var withdrawalButton:Button
     lateinit var displayList:ArrayList<String>
     lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // initialize view
        currentBalance = findViewById(R.id.tvBalance)
        recyclerView = findViewById(R.id.recyclerView)
        etDeposits = findViewById(R.id.etDeposits)
        depositButton = findViewById(R.id.depositButton)
        etWithdrawals = findViewById(R.id.etWithdrawals)
        withdrawalButton = findViewById(R.id.withdrawalButton)
        displayList = arrayListOf()

        // recyclerview linking
        recyclerView.adapter = RecyclerViewAdapter(displayList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // shared Preferences
        sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        setBalanceView(sharedPreferences.getFloat("balance", 0f))

        etDeposits.setOnClickListener {
           deposits()
            recyclerView.adapter!!.notifyDataSetChanged()
            recyclerView.scrollToPosition(displayList.size - 1)
        }
        withdrawalButton.setOnClickListener {
            withdrew()
            recyclerView.adapter!!.notifyDataSetChanged()
            recyclerView.scrollToPosition(displayList.size - 1)
        }

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putFloat("currentBalance",getBalance())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setBalanceView(savedInstanceState.getFloat("currentBalance", 0f))
    }

    fun getBalance(): Float {
        return currentBalance.text.toString().removePrefix("Current Balance: ").toFloat()
    }


    fun deposits(){
        if (etDeposits.text.isEmpty()){
           toast()
            return
        }
        val depositAoumnt = etDeposits.text.toString().toFloat()
        displayList.add("Deposit: $depositAoumnt")
        etDeposits.setText("")

        val currentBalances = getBalance() + depositAoumnt
        setBalanceView(currentBalances)
        with(sharedPreferences.edit()){
            putFloat("balance:",currentBalances)
            apply()
        }
    }
   fun toast(){
       Toast.makeText(applicationContext, "Enter a number", Toast.LENGTH_LONG).show()
       val toast = Toast.makeText(applicationContext, "Hello Javatpoint", Toast.LENGTH_SHORT)
       toast.show()

       val myToast = Toast.makeText(applicationContext,"Enter a number",Toast.LENGTH_SHORT)
       myToast.setGravity(Gravity.LEFT,200,200)
       myToast.show()

   }
    fun withdrew(){
        if (etWithdrawals.text.isEmpty()) {
          toast()

        return
        }
        if (getBalance() < 0){
            Toast.makeText(applicationContext, "Insufficient Found", Toast.LENGTH_LONG).show()
            return
        }
        val withdrawAmount = etWithdrawals.text.toString().toFloat()
        var currentBalances = getBalance() - withdrawAmount
        displayList.add("withdrawal: $withdrawAmount")
        etWithdrawals.setText("")

        if (currentBalances < 0 ){
            displayList.add("Negative Balance Fee: 20")
            currentBalances -= 20
        }
        setBalanceView(currentBalances)
        with(sharedPreferences.edit()){
            putFloat("balance", currentBalances)
            apply()
        }
    }

    fun setBalanceView(balance: Float){
        currentBalance.text = "Current Balance: $balance"
        when{
            balance == 0f -> currentBalance.setTextColor(Color.WHITE)
            balance > 0f -> currentBalance.setTextColor(Color.BLACK)
            balance < 0f -> currentBalance.setTextColor(Color.RED)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return  true
    }

    // Ledger clear

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.clear ->{
                displayList.clear()
                recyclerView.adapter!!.notifyDataSetChanged()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}


