package com.example.userssp

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userssp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import java.text.FieldPosition

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var userAdapter: UserAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getPreferences(Context.MODE_PRIVATE)

        val isFirsTime = preferences.getBoolean(getString(R.string.sp_first_time),true)
        Log.i("SP","${getString(R.string.sp_first_time)}= $isFirsTime")
        Log.i("SP","${getString(R.string.sp_username)}= ${preferences.getString(getString(R.string.sp_username),"NA")}")

        if(isFirsTime) {
            val dialogView=layoutInflater.inflate(R.layout.dialog_register,null)
            /*MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_confirm, DialogInterface.OnClickListener{dialogInterface, i ->
                    val username = dialogView.findViewById<TextInputEditText>(R.id.etUsername)
                        .text.toString()
                    with(preferences.edit()){
                        putBoolean(getString(R.string.sp_first_time), false)
                        putString(getString(R.string.sp_username),username)
                            .apply()
                    }
                    Toast.makeText(this,R.string.register_success,Toast.LENGTH_SHORT)
                        .show()

                } )
                .show()*/

            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_confirm) { dialogInterface, i -> }
                .create()

            dialog.show()

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {

                val username = dialogView.findViewById<TextInputEditText>(R.id.etUsername)
                    .text.toString()
                if (username.isBlank()){
                    Toast.makeText(this,R.string.register_invalid,Toast.LENGTH_SHORT)
                        .show()

                }else{
                    with(preferences.edit()){
                        putBoolean(getString(R.string.sp_first_time), false)
                        putString(getString(R.string.sp_username),username)
                            .apply()
                    }
                    Toast.makeText(this,R.string.register_success,Toast.LENGTH_SHORT)
                        .show()
                    dialog.dismiss()
                }

            }





        }else{
            val username = preferences.getString(getString(R.string.sp_username),getString(R.string.hint_username))
            Toast.makeText(this,"Bienvenido $username", Toast.LENGTH_SHORT).show()
        }

        userAdapter = UserAdapter(getUsers(),this)
        linearLayoutManager = LinearLayoutManager(this)


        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager=linearLayoutManager
            adapter=userAdapter
        }

        val swipeHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder): Boolean =false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                userAdapter.remove(viewHolder.adapterPosition)

            }

        })

        swipeHelper.attachToRecyclerView(binding.recyclerView)


    }

    private fun getUsers():MutableList<User>{
        val users = mutableListOf<User>()

        val usuario1= User(1,"Carlos","Murillo","https://scontent.fntr6-1.fna.fbcdn.net/v/t39.30808-6/313175540_10160906968277240_1431576640846043519_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=09cbfe&_nc_eui2=AeGAG9K-XGO9aiLFrZqrgQP9BDE8m-pW-iYEMTyb6lb6JsE9qG-A1iKodi0XfhbuOJE&_nc_ohc=sH0gD7gO8EkAX8msrx4&tn=ocJI70WpOGvDGhiH&_nc_ht=scontent.fntr6-1.fna&oh=00_AfDlmzrMlwL2v5m1nZOoMqLl9YSsQFFT3c-BugVQRO1Myw&oe=63F7D463")
        val usuario2= User(2,"Miriam", "Castillo", "https://scontent.fntr6-2.fna.fbcdn.net/v/t39.30808-6/243592997_4953443871349604_2929065686275661687_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=174925&_nc_eui2=AeFPzNz79hpEiSb2zTWx3ugJ_da9bccRtTj91r1txxG1OD4AcI_DXXB07OGl_bOBvu4&_nc_ohc=VZMW9NEh3pIAX9ilnDk&_nc_ht=scontent.fntr6-2.fna&oh=00_AfD2gk1RYjY2ijR_boybQ2JOGv6cYIPetwYSi8kpuAfKZQ&oe=63F87EB6")
        val usuario3 = User(3,"Patricia","Murillo","https://scontent.fntr6-4.fna.fbcdn.net/v/t39.30808-6/310594864_10159898753620499_4573528712265791484_n.jpg?_nc_cat=104&ccb=1-7&_nc_sid=174925&_nc_eui2=AeF5gfYKMsAn2Y5z2sZPXOh67UEMLbxHFA3tQQwtvEcUDedZCyaAQSXOvI_0XaSne3k&_nc_ohc=m3VlQEXmaaoAX8iOQEB&_nc_ht=scontent.fntr6-4.fna&oh=00_AfBSpQ2YhcZU7fEb4jJNipvxn9Tj-_d5AwsmvuO46lX3-Q&oe=63F82196")
        val usuario4=User(4,"Bertha","Valdes","https://scontent.fntr6-4.fna.fbcdn.net/v/t31.18172-8/334497_105084089611856_1791373051_o.jpg?_nc_cat=102&ccb=1-7&_nc_sid=09cbfe&_nc_eui2=AeGxfEJTMHZ3ObnYz2Zl3H5IHndyzyOrmdced3LPI6uZ16k-QX5LpakxQsYPBezkBiw&_nc_ohc=lrpVkUER8c4AX-WGskZ&tn=ocJI70WpOGvDGhiH&_nc_ht=scontent.fntr6-4.fna&oh=00_AfAPBZ1rIkIbW-qb5GdDrpChzntlFnNs1uZPo_1sDGsWGA&oe=641A849D")


        users.add(usuario1)
        users.add(usuario2)
        users.add(usuario3)
        users.add(usuario4)
        users.add(usuario1)
        users.add(usuario2)
        users.add(usuario3)
        users.add(usuario4)
        users.add(usuario1)
        users.add(usuario2)
        users.add(usuario3)
        users.add(usuario4)
        users.add(usuario1)
        users.add(usuario2)
        users.add(usuario3)
        users.add(usuario4)

        return users
    }

    override fun onClick(user: User,position:Int) {
        Toast.makeText(this,"$position: ${user.getFullName()}",Toast.LENGTH_SHORT).show()

    }
}