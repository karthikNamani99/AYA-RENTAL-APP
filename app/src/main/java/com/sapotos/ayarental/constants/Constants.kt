@file:Suppress("unused", "unused", "unused", "unused")

package com.sapotos.ayarental.constants

import ImageInterface
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Base64.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.sapotos.ayarental.R
import java.io.*
import java.security.InvalidKeyException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import javax.crypto.*
import javax.crypto.spec.DESKeySpec


class Constants {

    companion object {
        var cipher: Cipher? = null
        var myDesKey: SecretKey? = null


        const val OPTION_TAKE_PHOTO = "Take Photo"
        const val TAKE_FROM = ""
        const val OPTION_TAKE_PHOTO_BENEFICIARY = "Take Photo"
        const val OPTION_CHOOSE_FROM_GALLERY = "Choose photo from Gallery"
        const val OPTION_CHOOSE_FROM_GALLERY_MIME_TYPE = "image/*"

        //        const val TEMP_FILE_PREFIX = "temp_file"
        const val TEMP_FILE_PREFIX = "tempfile"
        const val TEMP_FILE_SUFFIX = ".png"
        const val PROFILE_PIC_CONTENT = "PROFILE_PIC_CONTENT"
        const val CAMERA_PERMISSION_CODE = 101
        const val CAMERA_REQUEST_CODE = 102
        const val GALLERY_REQUEST_CODE = 103


        const val APP_PREFERENCES = "app_preferences"
        const val CREDENTIALS = "CREDENTIALS"
        const val USERNAME = "USERNAME"
        const val PASSWORD = "PASSWORD"
        const val USER_NAME_ENCRYPTED = "USER_NAME_ENCRYPTED"
        const val PASSWORD_ENCRYPTED = "PASSWORD_ENCRYPTED"
        const val LAST_UPDATED_TIME = "LAST_UPDATED_TIME"

        const val LOGIN_USER_TYPE = "LOGIN_USER_TYPE"
        const val LOGIN_USER_NAME = "LOGIN_USER_NAME"
        const val LOGIN_COUNT = "0"

        const val IS_LOGIN_FIRST_TIME = "IS_LOGIN_FIRST_TIME"
        const val DBNAME = "ABHA_DATABASE"
        const val GIF_LOOP_COUNT = 1000
        const val Empty = ""
        const val PREF_RATING_GIVEN = "pref_rating_given"   // ✅ string key
        const val PREF_RATING_VALUE = "pref_rating_value"
        @RequiresApi(Build.VERSION_CODES.O)
        @Throws(
            IllegalBlockSizeException::class,
            BadPaddingException::class,
            InvalidKeyException::class
        )
        fun encode(jsonStrin: String, key: String): String {
            try {
                val dks = DESKeySpec(key.toByteArray())
                val skf: SecretKeyFactory = SecretKeyFactory.getInstance("DES")
                myDesKey = skf.generateSecret(dks)
                cipher = Cipher.getInstance("DES/ECB/PKCS5Padding")
            } catch (e: Exception) {
                println("Exception occured in encodeBeanToString : " + e.message)
            }
            val text = jsonStrin.toByteArray()
            cipher!!.init(Cipher.ENCRYPT_MODE, myDesKey)
            val textEncrypted = cipher!!.doFinal(text)
            val s1 = String(Base64.getEncoder().encode(textEncrypted))
            val sb = StringBuffer()
            val ch = s1.toCharArray()
            for (i in ch.indices) {
                val hexString = Integer.toHexString(ch[i].toInt())
                sb.append(hexString)
            }
            return sb.toString()
        }

        fun showToast(context: Context, msge: String) {
            Toast.makeText(context, msge, Toast.LENGTH_LONG).show()
        }


        fun addProfilePicture(context: Context, imageInterface: ImageInterface) {
            val options =
                arrayOf<CharSequence>(
                    OPTION_TAKE_PHOTO,
                    OPTION_TAKE_PHOTO_BENEFICIARY,
                    OPTION_CHOOSE_FROM_GALLERY
                )
            val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
            builder.setItems(options) { _, item ->
                when {
                    options[item] == OPTION_TAKE_PHOTO -> imageInterface.getImageFromCamera()
                    options[item] == OPTION_TAKE_PHOTO_BENEFICIARY -> imageInterface.getImageFromCameraBeneficiary()
                    options[item] == OPTION_CHOOSE_FROM_GALLERY -> imageInterface.getImageFromGallery()
                }
            }
            builder.show()
        }

        const val REG = "^(\\+91[\\-\\s]?)?[0]?(91)?[6789]\\d{9}\$"
        var PATTERN: Pattern = Pattern.compile(REG)
        fun CharSequence.isPhoneNumber(): Boolean = PATTERN.matcher(this).find()


        fun encodeImage(context: Context, uri: Uri?): String? {
            val imageStream: InputStream? = uri?.let { context.contentResolver.openInputStream(it) }
            val selectedImageBitmap = BitmapFactory.decodeStream(imageStream)
            val baos = ByteArrayOutputStream()
            selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos)
            val b: ByteArray = baos.toByteArray()
//            val imageBytes = decode(b, DEFAULT)
//            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length)
//            imageView.setImageBitmap(decodedImage)
//            val size=decodedImage.
            return encodeToString(b, DEFAULT)
        }

        fun encodeImage(path: String): String? {
            val imageFile = File(path)
            var fis: FileInputStream? = null
            try {
                fis = FileInputStream(imageFile)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            val bm = BitmapFactory.decodeStream(fis)
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val b = baos.toByteArray()
            return encodeToString(b, DEFAULT)
        }


        fun setErrorAlertDialog(context: Context, title: String, msge: String) {
            val alertDialog: AlertDialog.Builder =
                AlertDialog.Builder(context, R.style.MyDialogTheme)
            alertDialog.setTitle(title)
            alertDialog.setMessage(msge)

            alertDialog.setPositiveButton(
                "Ok"
            ) { dialog: DialogInterface?, _: Int ->
                dialog?.dismiss()
            }

            val alert: AlertDialog = alertDialog.create()
            alert.setCanceledOnTouchOutside(false)
            alert.show()
        }

        fun getDate(milliSeconds: Long, dateFormat: String?): String? {
            // Create a DateFormatter object for displaying date in specified format.
            val formatter = SimpleDateFormat(dateFormat)

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = milliSeconds
            return formatter.format(calendar.time)
        }

        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PRICE = "extra_price"            // can be "30" or "$30/day"
        const val EXTRA_HERO_RES = "extra_hero_res"      // Int drawable res id

        const val EXTRA_PICKUP_LOC = "extra_pickup_loc"
        const val EXTRA_DROP_LOC = "extra_drop_loc"

        const val EXTRA_PICKUP_DATE = "extra_pickup_date" // e.g., "7 December 2021"
        const val EXTRA_DROP_DATE = "extra_drop_date"

        // Optional millis if you want
        const val EXTRA_PICKUP_MILLIS = "extra_pickup_millis"
        const val EXTRA_DROP_MILLIS = "extra_drop_millis"

        const val EXTRA_BOOKING = "extra_booking"
    }
}
