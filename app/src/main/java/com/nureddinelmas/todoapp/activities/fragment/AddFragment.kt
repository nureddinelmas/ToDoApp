package com.nureddinelmas.todoapp.activities.fragment

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nureddinelmas.todoapp.NotifierAlarm
import com.nureddinelmas.todoapp.R
import com.nureddinelmas.todoapp.model.ToDo
import com.nureddinelmas.todoapp.viewModel.FragmentSharedViewModel
import com.nureddinelmas.todoapp.viewModel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {

    private val dToDoViewModel : ToDoViewModel by viewModels()
    private val dFragmentSharedViewModel: FragmentSharedViewModel by viewModels()



   // private var dateFormat = SimpleDateFormat("dd MMM, YYYY", Locale.US)
    // private var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
    private lateinit var lastDate: Date
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_add, container, false)
        activity?.title = "Add New Todo"
        setHasOptionsMenu(true)

        view.spinnerType.onItemSelectedListener = dFragmentSharedViewModel.listener



        return view
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonTimeDate.setOnClickListener {
            getDate()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_menu){
         insertDataToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun insertDataToDatabase() {
        val date = Date()
       val dTitle = titleText.text.toString()
        val dPriority = spinnerType.selectedItem.toString()
        val dDescription = descriptionText.text.toString()
        Log.d("!!!", Date().toString())
        val validation = dFragmentSharedViewModel.isDataOk(dTitle, dDescription)

        if(validation){
            val newData = ToDo(
                0,
                dTitle,
                dFragmentSharedViewModel.parsePriority(dPriority),
                lastDate,
                dDescription
            )
            dToDoViewModel.insertData(newData)

            dFragmentSharedViewModel.setAlarm(newData)

            Toast.makeText(requireContext(), "Succesfully added", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "UnSuccesfully!", Toast.LENGTH_LONG).show()
        }
    }




    fun getDate (){
        val newCalender = Calendar.getInstance()
        val dialog = DatePickerDialog(requireContext(), { view, year, month, dayOfMonth ->
            val newDate = Calendar.getInstance()
            val newTime = Calendar.getInstance()
            val time = TimePickerDialog(requireContext(), { view, hourOfDay, minute ->
                newDate[year, month, dayOfMonth, hourOfDay, minute] = 0
                val tem = Calendar.getInstance()
                Log.w("TIME", System.currentTimeMillis().toString() + "")
                if (newDate.timeInMillis - tem.timeInMillis > 0){
                    buttonTimeDate.setText(newDate.time.toString())
                    lastDate = newDate.time
                    Toast.makeText(requireContext(), newDate.time.toString(), Toast.LENGTH_SHORT).show()
                }

                else {
                    Toast.makeText(requireContext(), "invalid Time", Toast.LENGTH_SHORT).show()
                }
            }, newTime[Calendar.HOUR_OF_DAY], newTime[Calendar.MINUTE], true
            )
            time.show()
        }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(
            Calendar.DAY_OF_MONTH
        )
        )

        dialog.datePicker.minDate = System.currentTimeMillis()
        dialog.show()
    }



}