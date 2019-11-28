package com.example.databindingintent

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.databindingintent.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val myContact = Contact("See", "01234567")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.contact=myContact

        binding.buttonSave.setOnClickListener{
            with(binding){
                contact?.name = editTextName.text.toString()
                contact?.phone = editTextPhone.text.toString()
                invalidateAll() //refresh UI
            }
        }
        binding.buttonSend.setOnClickListener(){
            //Create an explicit intent
            val intent = Intent(this, SecondActivity::class.java)

            //Prepare extra data
            intent.putExtra(EXTRA_NAME, binding.contact?.name)
            intent.putExtra(EXTRA_PHONE, binding.contact?.phone)
           // startActivity(intent)  //Your parent expect nothing, they want you not exist, fuck me, it's kinda dark

            startActivityForResult(intent, REQUEST_REPLY)//PTPTN wants your money back, so try not to get ptptn.
        }
    }// end of the class

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_REPLY){
            if(resultCode == Activity.RESULT_OK){
                val reply = data?.getStringExtra(EXTRA_REPLY)
                textViewReply.text = String.format(getString(R.string.reply)+": %s", reply)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object{
        const val EXTRA_NAME = "com.example.databindingintent.NAME"
        const val EXTRA_PHONE = "com.example.databindingintent.PHONE"
        const val REQUEST_REPLY = 1
        const val EXTRA_REPLY = "com.example.databindingintent.REPLY"
    }
}
