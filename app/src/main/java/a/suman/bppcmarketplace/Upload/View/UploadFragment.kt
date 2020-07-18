package a.suman.bppcmarketplace.Upload.View

import a.suman.bppcmarketplace.R
import a.suman.bppcmarketplace.Upload.ViewModel.UploadViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.isapanah.awesomespinner.AwesomeSpinner
import kotlinx.android.synthetic.main.fragment_upload_pictures.*


class UploadFragment : Fragment() {

    private lateinit var uploadViewModel: UploadViewModel
    private lateinit var priceList: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        uploadViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        ).get(UploadViewModel::class.java)
        priceList = ArrayList()
        return inflater.inflate(R.layout.fragment_upload_pictures, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initPricePricker()
        val categories = arrayOf("Sample Category 1", "Sample Category 2")
        val adapter = ArrayAdapter(
            activity!!.baseContext,
            android.R.layout.simple_spinner_item, categories
        )
        spinner.setAdapter(adapter)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner.setOnSpinnerItemClickListener(AwesomeSpinner.onSpinnerItemClickListener { position, itemAtPosition -> })

        val uploadAdapter = UploadAdapter()
        uploadImagesRecylerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        uploadImagesRecylerView.adapter = uploadAdapter
        postButton.setOnClickListener {

            if (isEmpty(productNameEditText) || isEmpty(
                    productdescriptionEditText
                ) || uploadRadioGroup.checkedRadioButtonId == -1
            ) Toast.makeText(context, "All fields are Necessary", Toast.LENGTH_LONG).show()
            else {

                var isNegotiable = true
                if (nonNegotiableRadioButton.id == uploadRadioGroup.checkedRadioButtonId)
                    isNegotiable = false

                uploadViewModel.addProduct(
                    productNameEditText.text.toString(),
                    productdescriptionEditText.text.toString(),
                    Integer.parseInt(priceList[pricePicker.value - 1].replace("₹", "")),
                    isNegotiable
                )

            }
        }

        uploadViewModel.isLoadingLiveData.observe(
            viewLifecycleOwner,
            Observer {
                if (it) uploadProgressbar.visibility = View.VISIBLE
                else uploadProgressbar.visibility = View.INVISIBLE
            })


        uploadViewModel.postResultLiveData.observe(viewLifecycleOwner, Observer {
            if (it == UploadViewModel.PostResult.Successful) {
                Toast.makeText(context, "Product Successfully Uploaded", Toast.LENGTH_SHORT).show()
                productNameEditText.setText("")
                productdescriptionEditText.setText("")
                uploadRadioGroup.clearCheck()
                spinner.clearSelection()
                pricePicker.value = 2
            } else if (it == UploadViewModel.PostResult.Unsuccessful)
                Toast.makeText(context, "Product Upload Unsuccessful", Toast.LENGTH_LONG).show()
            else {
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                productNameEditText.setText("")
                productdescriptionEditText.setText("")
                uploadRadioGroup.clearCheck()
                spinner.clearSelection()
                pricePicker.value = 2
            }

        })
    }

    private fun isEmpty(editText: EditText): Boolean {
        return editText.text.toString().trim { it <= ' ' }.length == 0
    }

    private fun initPricePricker() {
        var i = 1
        while (i <= 10000) {
            if (i < 500) {
                if (i % 10 == 0)
                    priceList.add("₹$i")

            } else if (i in 500..2499) {
                if (i % 50 == 0)
                    priceList.add("₹$i")
            } else {
                if (i % 100 == 0)
                    priceList.add("₹$i")
            }
            i++
        }

        pricePicker.minValue = 1
        pricePicker.maxValue = priceList.size
        pricePicker.displayedValues = priceList.toTypedArray()
        pricePicker.value = 2
    }
}
