package a.suman.bppcmarketplace.Upload.View

import a.suman.bppcmarketplace.R
import a.suman.bppcmarketplace.Upload.ViewModel.UploadViewModel
import android.content.Intent
import android.os.Build
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
import com.bumptech.glide.Glide
import com.nguyenhoanglam.imagepicker.model.Config
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.fragment_upload_pictures.*


class UploadFragment : Fragment() {

    private lateinit var uploadViewModel: UploadViewModel
    private lateinit var priceList: ArrayList<String>
    private lateinit var images: ArrayList<Image>
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
        images = ArrayList()

        uploadCard1.setOnClickListener {
            val imagePicker: ImagePicker.Builder = ImagePicker.with(this)
                .setFolderMode(true)
                .setFolderTitle("Pick upto 3 images")
                .setRootDirectoryName(Config.ROOT_DIR_DCIM)
                .setDirectoryName("BPPC MarketPlace")
                .setMultipleMode(true)
                .setShowNumberIndicator(true)
                .setMaxSize(3)
                .setLimitMessage("You can select up to 3 images")
                .setSelectedImages(images)
                .setRequestCode(0)
            if (addImageImageView1.drawable == null)
                imagePicker.start()
            else
                imagePicker.setMultipleMode(false).setFolderTitle("Replace 1st image with:")
                    .setRequestCode(1).start()
        }
        uploadCard2.setOnClickListener {
            val imagePicker: ImagePicker.Builder = ImagePicker.with(this)
                .setFolderMode(true)
                .setFolderTitle("Pick upto 3 images")
                .setRootDirectoryName(Config.ROOT_DIR_DCIM)
                .setDirectoryName("BPPC MarketPlace")
                .setMultipleMode(true)
                .setShowNumberIndicator(true)
                .setMaxSize(3)
                .setLimitMessage("You can select up to 3 images")
                .setSelectedImages(images)
                .setRequestCode(0)
            if (addImageImageView2.drawable == null && addImageImageView1.drawable != null && addImageImageView3.drawable == null)
                imagePicker.setMultipleMode(false).setFolderTitle("Select 2nd image")
                    .setRequestCode(2).start()
            else if (addImageImageView2.drawable == null && addImageImageView1.drawable == null && addImageImageView3.drawable == null)
                imagePicker.start()
            else
                imagePicker.setMultipleMode(false).setFolderTitle("Replace 2nd image with:")
                    .setRequestCode(2).start()
        }
        uploadCard3.setOnClickListener {
            val imagePicker: ImagePicker.Builder = ImagePicker.with(this)
                .setFolderMode(true)
                .setFolderTitle("Pick upto 3 images")
                .setRootDirectoryName(Config.ROOT_DIR_DCIM)
                .setDirectoryName("BPPC MarketPlace")
                .setMultipleMode(true)
                .setShowNumberIndicator(true)
                .setMaxSize(3)
                .setLimitMessage("You can select up to 3 images")
                .setSelectedImages(images)
                .setRequestCode(0)

            if (addImageImageView3.drawable == null && addImageImageView2.drawable != null && addImageImageView1.drawable != null)
                imagePicker.setMultipleMode(false).setFolderTitle("Select 3rd image")
                    .setRequestCode(3).start()
            else if (addImageImageView3.drawable == null && addImageImageView2.drawable == null && addImageImageView1.drawable == null)
                imagePicker.start()
            else if (addImageImageView3.drawable == null && addImageImageView2.drawable == null && addImageImageView1.drawable != null)
                imagePicker.setMultipleMode(false).setFolderTitle("Select 2nd image")
                    .setRequestCode(2).start()
            else
                imagePicker.setMultipleMode(false).setFolderTitle("Replace 3rd image with:")
                    .setRequestCode(3).start()
        }
        initPricePricker()
        val categories = arrayOf("Sample Category 1", "Sample Category 2")
        val adapter = ArrayAdapter(
            activity!!.baseContext,
            android.R.layout.simple_spinner_item, categories
        )
        spinner.setAdapter(adapter)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner.setOnSpinnerItemClickListener({ position, itemAtPosition -> })


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
                scrollView.smoothScrollTo(0, 0)
                Toast.makeText(context, "Product Successfully Uploaded", Toast.LENGTH_SHORT).show()
                addImageImageView1.setImageDrawable(null)
                addImageImageView2.setImageDrawable(null)
                addImageImageView3.setImageDrawable(null)
                images.clear()
                productNameEditText.setText("")
                productdescriptionEditText.setText("")
                uploadRadioGroup.clearCheck()
                spinner.clearSelection()
                pricePicker.smoothScrollToPosition(2)
            } else if (it == UploadViewModel.PostResult.Unsuccessful)
                Toast.makeText(context, "Product Upload Unsuccessful", Toast.LENGTH_LONG).show()
            else {
                scrollView.smoothScrollTo(0, 0)
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                addImageImageView1.setImageDrawable(null)
                addImageImageView2.setImageDrawable(null)
                addImageImageView3.setImageDrawable(null)
                images.clear()
                productNameEditText.setText("")
                productdescriptionEditText.setText("")
                uploadRadioGroup.clearCheck()
                spinner.clearSelection()
                pricePicker.smoothScrollToPosition(2)

            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandleResult(requestCode, resultCode, data, 0)) {
            images = ImagePicker.getImages(data)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (images.size == 1) {
                    Glide.with(this)
                        .load(images[0].uri).centerCrop()
                        .into(addImageImageView1)
                } else if (images.size == 2) {
                    Glide.with(this)
                        .load(images[0].uri).centerCrop()
                        .into(addImageImageView1)
                    Glide.with(this)
                        .load(images[1].uri).centerCrop()
                        .into(addImageImageView2)
                } else if (images.size == 3) {
                    Glide.with(this)
                        .load(images[0].uri).centerCrop()
                        .into(addImageImageView1)
                    Glide.with(this)
                        .load(images[1].uri).centerCrop()
                        .into(addImageImageView2)
                    Glide.with(this)
                        .load(images[2].uri).centerCrop()
                        .into(addImageImageView3)
                }


            } else {
                if (images.size == 1) {
                    Glide.with(this)
                        .load(images[0].uri).centerCrop()
                        .into(addImageImageView1)
                } else if (images.size == 2) {
                    Glide.with(this)
                        .load(images[0].uri).centerCrop()
                        .into(addImageImageView1)
                    Glide.with(this)
                        .load(images[1].uri).centerCrop()
                        .into(addImageImageView2)
                } else if (images.size == 3) {
                    Glide.with(this)
                        .load(images[0].uri).centerCrop()
                        .into(addImageImageView1)
                    Glide.with(this)
                        .load(images[1].uri).centerCrop()
                        .into(addImageImageView2)
                    Glide.with(this)
                        .load(images[2].uri).centerCrop()
                        .into(addImageImageView3)
                }
            }

        } else if (ImagePicker.shouldHandleResult(requestCode, resultCode, data, 1)) {
            val image = ImagePicker.getImages(data)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Glide.with(this)
                    .load(image[0].uri).centerCrop()
                    .into(addImageImageView1)
            } else {
                Glide.with(this)
                    .load(image[0].path).centerCrop()
                    .into(addImageImageView1)
            }

        } else if (ImagePicker.shouldHandleResult(requestCode, resultCode, data, 2)) {
            val image = ImagePicker.getImages(data)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Glide.with(this)
                    .load(image[0].uri).centerCrop()
                    .into(addImageImageView2)
            } else {
                Glide.with(this)
                    .load(image[0].path).centerCrop()
                    .into(addImageImageView2)
            }

        } else if (ImagePicker.shouldHandleResult(requestCode, resultCode, data, 3)) {
            val image = ImagePicker.getImages(data)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Glide.with(this)
                    .load(image[0].uri).centerCrop()
                    .into(addImageImageView3)
            } else {
                Glide.with(this)
                    .load(image[0].path).centerCrop()
                    .into(addImageImageView3)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
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
