import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.setPadding
import androidx.fragment.app.DialogFragment

class MyDialogFragment : DialogFragment() {

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
        amount.hint = "Enter amount"
        layout.addView(amount)

        val category = EditText(activity)
        category.hint = "Select category"
        layout.addView(category)

        builder.setView(layout)

        // Set the positive button text and click listener
        builder.setPositiveButton("OK") { _, _ ->
            // Get the input text and do something with it
            val data = title.text.toString() + amount.text.toString() + category.text.toString()
            Toast.makeText(activity, "Hello, $data!", Toast.LENGTH_SHORT).show()
        }

        // Set the negative button text and click listener
        builder.setNegativeButton("Cancel") { _, _ ->
            // Do nothing
        }

        // Return the created dialog
        return builder.create()
    }
}