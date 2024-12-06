package ru.dayone.samsung3.features.proflie.presentation

import android.R
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.dayone.samsung3.features.databinding.ActivityProfileBinding
import ru.dayone.samsung3.features.proflie.domain.data.models.Achievement
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private val viewModel by viewModels<ProfileViewModel>()

    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initUi()
        observeViewModel()
        setUpRecyclerView()
        setUpSpinner()

        viewModel.requestSavedAvatarUri()
    }

    private fun setUpSpinner() {
        val statuses = arrayListOf(
            "Sleep", "Eat", "Code"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statuses)
        binding.spinner.adapter = adapter

    }

    private fun setUpRecyclerView() {
        val achievements = arrayListOf(
            Achievement(
                "First", "This is first achievement"
            ),
            Achievement(
                "Second", "This is second achievement"
            ),
            Achievement(
                "Third", "This is third achievement"
            ),
            Achievement(
                "Fourth", "This is fourth achievement"
            )
        )

        binding.rvAchievments.adapter = AchievementsAdapter(achievements)
        binding.rvAchievments.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        viewModel.data.observe(this) {
            binding.imageView.setImageBitmap(loadImageFromStorage(it))
        }
    }

    private fun initUi() {
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                Log.d(this.toString(), "Selected URI: $it")
                binding.imageView.setImageURI(it)
                viewModel.saveAvatarUri(saveToInternalStorage((binding.imageView.drawable as BitmapDrawable).bitmap))
            } else {
                Log.d(this.toString(), "No media chosed")
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.imageView.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.tvPhone.setOnClickListener {
            val uri = "tel:" + binding.tvPhone.text.toString()
            val i = Intent(Intent.ACTION_DIAL)
            i.setData(Uri.parse(uri))
            startActivity(i)
        }

        binding.tvEmail.setOnClickListener {
            val i = Intent(Intent.ACTION_SENDTO)
            i.setData(Uri.parse("mailto:"))
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.tvEmail.text.toString()))
            startActivity(i)
        }
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap): String {
        val cw = ContextWrapper(applicationContext)
        val directory = cw.getDir("imageDir", MODE_PRIVATE)
        val file = File(directory, "profile.jpg")

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }

    private fun loadImageFromStorage(path: String): Bitmap? {
        try {
            val f = File(path, "profile.jpg")
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            return b
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null
        }
    }
}