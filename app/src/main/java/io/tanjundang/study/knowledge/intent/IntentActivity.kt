package io.tanjundang.study.knowledge.intent

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.ContactsContract
import android.view.View
import android.widget.ImageView
import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity
import io.tanjundang.study.common.tools.Functions
import io.tanjundang.study.common.tools.LogTool
import kotlinx.android.synthetic.main.activity_intent.view.*
import java.io.FileNotFoundException

/**
 * @Author: TanJunDang
 * @Date: 2019/5/15
 * @Description:
 */
class IntentActivity : BaseActivity() {

    val IMAGE_CODE: Int = 666;
    val CONTACT_CODE: Int = 777;


    override fun initView() {
        setContentView(R.layout.activity_intent)
    }

    override fun initData() {

    }

    fun sendToQQ(v: View) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/*"
        intent.putExtra(Intent.EXTRA_TEXT, "This is share text")
        startActivity(intent)
    }

    fun sendToEmail(v: View) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("TanJunDang@126.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Email subject")
        intent.putExtra(Intent.EXTRA_TEXT, "Email message text")
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"))
        startActivity(intent)
    }

    fun dialPhone(v: View) {
        val intent = Intent()
        intent.action = Intent.ACTION_DIAL
        val uri = Uri.parse("tel:10000000")
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun dialPhone120(v: View) {
        val intent = Intent()
        intent.action = Intent.ACTION_DIAL
        val uri = Uri.parse("tel:120")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.data = uri
        startActivity(intent)
    }

    fun webView(v: View) {
        val uri = Uri.parse("http://www.baidu.com")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    fun sendImage(v: View) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "选择"), IMAGE_CODE)

    }

    fun selectContact(v: View) {
        val intent = Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"))
        intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE // Show user only contacts w/ phone numbers
        startActivityForResult(intent, CONTACT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == IMAGE_CODE) {
            val uri = data.data
            val cr = this.contentResolver
            try {
                val bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri!!))
                val imageView = findViewById<View>(R.id.imageView) as ImageView
                /* 将Bitmap设定到ImageView */
                imageView.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                LogTool.e(javaClass.name, e.message.toString())
            }

        } else if (requestCode == CONTACT_CODE) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                // Get the URI that points to the selected contact
                val contactUri = data.data
                // We only need the NUMBER column, because there will be only one row in the result
                val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                val cursor = contentResolver.query(contactUri!!, projection, null, null, null)
                cursor!!.moveToFirst()
                // Retrieve the phone number from the NUMBER column
                val column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val name = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHONETIC_NAME)
                val number = cursor.getString(column)
                val selectName = ""
                Functions.toast("$selectName:$number")

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}