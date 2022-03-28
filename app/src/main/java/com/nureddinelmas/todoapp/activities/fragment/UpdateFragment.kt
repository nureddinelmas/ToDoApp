package com.nureddinelmas.todoapp.activities.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nureddinelmas.todoapp.R
import com.nureddinelmas.todoapp.model.Priority
import com.nureddinelmas.todoapp.model.ToDo
import com.nureddinelmas.todoapp.viewModel.FragmentSharedViewModel
import com.nureddinelmas.todoapp.viewModel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.util.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val dSharedViewModel : FragmentSharedViewModel by viewModels()
    private val dToDoViewModel: ToDoViewModel by viewModels()
    private val dFragmentSharedViewModel: FragmentSharedViewModel by viewModels()

    private lateinit var lastDate: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      val view = inflater.inflate(R.layout.fragment_update, container, false)
        setHasOptionsMenu(true)

        lastDate = Date(args.currentItem.reminderDate.toString())
        view.updateTitleText.setText(args.currentItem.title)
        view.updateButtonTimeDate.text = args.currentItem.reminderDate.toString()
        view.updateDescriptionText.setText(args.currentItem.description)
        view.updateSpinnerType.setSelection(dSharedViewModel.parsePriority(args.currentItem.period))
        view.updateSpinnerType.onItemSelectedListener = dSharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.update_menu){
            updateItem()
            Log.d("!!!", "tiklandi")
        }

        if(item.itemId == R.id.update_delete){
            deleteItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteItem() {
       dToDoViewModel.deleteData(args.currentItem)
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }

    private fun updateItem() {
       val title = updateTitleText.text.toString()
        val description = updateDescriptionText.text.toString()
        val getPeriod = updateSpinnerType.selectedItem.toString()
        val updateDate = lastDate

        if (dSharedViewModel.isDataOk(title, description)){
            val updateItem = ToDo(args.currentItem.id,title, dSharedViewModel.parsePriority(getPeriod),updateDate,description)
            dToDoViewModel.updateData(updateItem)
            dFragmentSharedViewModel.setAlarm(updateItem)
            view?.findNavController()?.navigate(R.id.action_updateFragment_to_listFragment)

        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateButtonTimeDate.setOnClickListener {
            updateNewDate()
        }
    }


    private fun updateNewDate (){
        val newCalender = Calendar.getInstance()
        val dialog = DatePickerDialog(requireContext(), { view, year, month, dayOfMonth ->
            val newDate = Calendar.getInstance()
            val newTime = Calendar.getInstance()
            val time = TimePickerDialog(requireContext(), { view, hourOfDay, minute ->
                newDate[year, month, dayOfMonth, hourOfDay, minute] = 0
                val tem = Calendar.getInstance()

                if (newDate.timeInMillis - tem.timeInMillis > 0){
                    Log.d("!!!", newDate.time.toString())
                  //  buttonTimeDate.setText(newDate.time.toString())
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