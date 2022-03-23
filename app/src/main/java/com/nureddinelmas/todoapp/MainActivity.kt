package com.nureddinelmas.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nureddinelmas.todoapp.databinding.ActivityMainBinding
import com.nureddinelmas.todoapp.model.Post
import com.nureddinelmas.todoapp.repository.Repository
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository =  Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]

        binding.signInButton.setOnClickListener {
            signInClicked()
        }
    }


    fun signInClicked (){
        if (!isValidString(binding.emailNameText.text.toString())){
            binding.emailNameText.error = "Not correct Email"
        } else {

            val myPost = Post(binding.emailNameText.text.toString(), binding.passwordText.text.toString(), token = "")
            // val myPost = Post("eve.holt@reqres.in", "cityslicka","")
            viewModel.pushPost(myPost)
            viewModel.myResponse.observe(this, Observer { response ->
                if(response.isSuccessful){
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    Log.d("!!!", response.body().toString())
                    Log.d("!!!", response.code().toString())
                    Log.d("!!!", response.message())

                }else {
                    Log.d("!!!", "UnSuccess")
                }
            })
        }
    }

    val validation = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    fun isValidString(str: String): Boolean{
        return validation.matcher(str).matches()
    }
}