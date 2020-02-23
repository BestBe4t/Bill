package com.example.bill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bill.repository.State
import com.example.bill.ui.bill.AccountFragment
import com.example.bill.ui.bill.BillFragment
import com.example.bill.ui.main.MainFragment
import com.example.bill.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(R.layout.main_activity)

        viewModel.State.observe(this, Observer { state ->
            when(state){
                is State.MainMenu -> {
                    showFragment(MainFragment::class.java){
                        MainFragment()
                    }
                }
                is State.Cammera -> {
                    showFragment(BillFragment::class.java) {
                        BillFragment()
                    }
                }
                is State.ConnectedServer -> {
                    showFragment(AccountFragment::class.java) {
                        AccountFragment()
                    }
                }
            }
        })
    }

    private fun showFragment(clazz: Class<out Fragment>, create: () -> Fragment) {
        val manager = supportFragmentManager
        if (!clazz.isInstance(manager.findFragmentById(R.id.container))) {
            manager.beginTransaction().replace(R.id.container, create()).commit()
        }
    }

}
