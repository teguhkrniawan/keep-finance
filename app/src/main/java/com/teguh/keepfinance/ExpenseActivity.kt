package com.teguh.keepfinance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.teguh.keepfinance.adapter.ExpenseCategoriesAdapter
import com.teguh.keepfinance.model.ResponseExpenseByCategories
import com.teguh.keepfinance.networking.ConfigNetwork
import kotlinx.android.synthetic.main.activity_expense.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        generateExpenseData()

    }

    private fun generateExpenseData() {
        val dataExpenseResponse = ConfigNetwork.transaksiService().getExpenseByCategories()
        dataExpenseResponse.enqueue(object: Callback<ResponseExpenseByCategories>{
            override fun onFailure(call: Call<ResponseExpenseByCategories>, t: Throwable) {
                Toast.makeText(this@ExpenseActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ResponseExpenseByCategories>,
                response: Response<ResponseExpenseByCategories>
            ) {
               if (response.isSuccessful){
                   val data = response.body()?.data
                   val expenseAdapter = ExpenseCategoriesAdapter(data)
                   rv_expense_by_categories.adapter = expenseAdapter
               }
            }

        })
    }
}