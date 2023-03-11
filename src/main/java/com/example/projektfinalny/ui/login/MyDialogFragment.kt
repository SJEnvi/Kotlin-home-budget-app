import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import androidx.fragment.app.DialogFragment
import com.example.projektfinalny.data.model.Transaction
import java.io.File
import java.time.LocalDate
import java.util.*

class MyDialogFragment : DialogFragment() {

    val FILE_NAME = "transactions.txt"
    val items = arrayOf("Wpływy","Rachunki", "Rozrywka i wypoczynek", "Wydatki bierzące", "Zdrowie")
    var selectedItem : String? = null
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDate = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create a new AlertDialog.Builder object
        val builder = AlertDialog.Builder(activity)

        // Set the dialog title
        builder.setTitle("Add new transaction")

        val layout = LinearLayout(activity)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50,50,50,50)

        // Set the dialog message and input field
        val title = EditText(activity)
        title.hint = "Enter title"
        layout.addView(title)

        val amount = EditText(activity)
        amount.hint = "Enter amount (separate with dot)"
        layout.addView(amount)

        val categoryText = TextView(activity)
        categoryText.text = "Select category"
        categoryText.setPadding(20)
        layout.addView(categoryText)

        val category = Spinner(activity)
        val adapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, items) }
        category.adapter = adapter
        layout.addView(category)

        builder.setView(layout)

        category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItem = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                selectedItem = "No category"
            }
        }

        // Set the positive button text and click listener
        builder.setPositiveButton("OK") { _, _ ->
            // Get the input text and do something with it
            // Checks if the transactions file already exists
            val fileExists = context?.getFileStreamPath(FILE_NAME)?.exists() ?: false
            var id = if (fileExists) {File(context?.filesDir, FILE_NAME).readLines().size+1}else{1}
            val amountRounded = roundTo2Dec(amount.text.toString().toDouble()).toString()
            val data = id.toString()+","+title.text.toString()+","+amountRounded+","+selectedItem +","+currentDate
            val fileContents = if (fileExists) {"\n$data"}else{data}


            context?.openFileOutput(FILE_NAME, Context.MODE_APPEND).use {
                it?.write(fileContents.toByteArray())
            }


            }




        // Set the negative button text and click listener
        builder.setNegativeButton("Cancel") { _, _ ->
            // Do nothing
        }

        // Return the created dialog
        return builder.create()
    }

    private fun roundTo2Dec(number: Double) : Double{return (kotlin.math.round(number*100)/100)}

}