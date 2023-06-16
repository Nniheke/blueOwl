package com.iheke.ispy.challenges.presentation.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.iheke.ispy.R


/**

A binding adapter for setting a TextView's text with a plural string resource

based on a double value.

This binding adapter sets the text of the given TextView with a formatted plural string,

using the provided double value. The double value is converted to an integer if necessary,

and the formatted value is used to populate the plural string resource.

@param view The TextView to set the text on.

@param value The double value to use for populating the plural string resource.

@see BindingAdapter
 */
object PluralsBindingAdapter {

    /**

    Sets the text of the given TextView with a formatted plural string,
    using the provided double value.
    @param view The TextView to set the text on.
    @param value The double value to use for populating the plural string resource.
     */
    @JvmStatic
    @BindingAdapter("app:pluralsWithDouble")
    fun setPluralsWithDouble(view: TextView, value: Double) {
        val context = view.context
        val resourceId = R.plurals.average_challenge_rating
        val quantity = value.toInt()
        val formattedValue = String.format("%.2f", value)
        val formattedString =
            context.resources.getQuantityString(resourceId, quantity, formattedValue)
        view.text = formattedString
    }
}
