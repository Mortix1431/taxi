
package com.example.taxi.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.taxi.R
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.taxi.databinding.FragmentUserProfileBinding
import com.example.taxi.models.UserProfile
import com.example.taxi.utils.SupaBaseObject
import com.example.taxi.utils.UserMethods
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import java.io.ByteArrayOutputStream

class UserProfileFragment : Fragment() {
    // Переменная для хранения привязки макета фрагмента
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    private val PICK_IMAGE = 1 // Код запроса для выбора изображения
    private var selectedImageUri: Uri? = null // Uri для выбранного изображения
    private lateinit var user: UserProfile // ID пользователя

    // Метод создания представления фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Метод вызывается после создания представления фрагмента
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Загрузка профиля пользователя
        loadUserProfile()

        // Установка слушателя для выбора изображения
        binding.profileImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE)
        }

        // Установка слушателя для кнопки сохранения
        binding.buttonSave.setOnClickListener {
            saveUserProfile() // Сохранение профиля пользователя
        }
    }

    // Обработка результата выбора изображения
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            binding.profileImage.setImageURI(selectedImageUri)
            if (null != selectedImageUri) {
                lifecycleScope.launch {
                    val inputStream = contentResolver.openInputStream(selectedImageUri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
                    try {
                        SupaBaseObject.getClient().storage["Название бакита с аватарами"].upload(user.user_id+".jpg", baos.toByteArray(), true)
                        Toast.makeText(requireContext(), "Аватар успешно загружен", Toast.LENGTH_SHORT).show()
                    } catch(_: Exception) {
                        Toast.makeText(requireContext(), "Произошла ошибка при загрузке аватара", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    // Метод загрузки профиля пользователя
    private fun loadUserProfile() {
        lifecycleScope.launch {
            try {
                val user_id = UserMethods().getUserSession().id
                user = supabaseClient.postgrest["users"]
                    .select() { eq("user_id", user_id) }
                    .decodeSingle<UserProfile>
                if(user != null){
                    // Обработка полученного профиля
                    // Заполнение полей формы данными профиля пользователя
                    binding.editFirstName.setText(user.first_name)
                    binding.editLastName.setText(user.last_name)
                    binding.editBirthDate.setText(user.birth_date)
                    binding.editEmail.setText(user.email)
                    binding.editCity.setText(user.city)
                    when (userProfile.gender) {
                        "Male" -> binding.radioGroupGender.check(R.id.radio_male)
                        "Female" -> binding.radioGroupGender.check(R.id.radio_female)
                    }
                    if(user.profileImageUri != null){
                        var byteAvatar = SupaBaseObject.getClient1().storage["Название бакита с аватарами"].downloadPublic("${user.user_id}.jpg")
                        val image: Drawable = BitmapDrawable(BitmapFactory.decodeByteArray(byteAvatar, 0, byteAvatar.size))
                        binding.profileImage.setImageDrawable(image)
                    }
                } else {
                    Toast.makeText(requireContext(), "Ошибка загрузки профиля", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("UserProfileFragment", "Ошибка загрузки профиля", e)
                Toast.makeText(requireContext(), "Ошибка загрузки профиля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Метод сохранения профиля пользователя
    private fun saveUserProfile() {
        val firstName = binding.editFirstName.text.toString()
        val lastName = binding.editLastName.text.toString()
        val birthDate = binding.editBirthDate.text.toString()
        val gender = when (binding.radioGroupGender.checkedRadioButtonId) {
            R.id.radio_male -> "Male"
            R.id.radio_female -> "Female"
            else -> ""
        }
        val email = binding.editEmail.text.toString()
        val city = binding.editCity.text.toString()

        // Проверка заполненности всех полей
        if (firstName.isEmpty() || lastName.isEmpty() || birthDate.isEmpty() || gender.isEmpty() || email.isEmpty() || city.isEmpty()) {
            Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        // Форматирование даты рождения
        val inputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate: Date?
        val formattedBirthDate: String
        try {
            parsedDate = inputDateFormat.parse(birthDate)
            formattedBirthDate = outputDateFormat.format(parsedDate!!)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Неверный формат даты", Toast.LENGTH_SHORT).show()
            return
        }

        // Подготовка данных для отправки на сервер
        

        lifecycleScope.launch {
            try {
                SupaBaseObject.getClient1().postgrest["users"]
                    .update(
                        set("first_name", firstName)
                        set("last_name", lastName)
                        set("birth_date", formattedBirthDate)
                        set("gender", gender)
                        set("email", email)
                        set("city", city)
                        ) { eq("user_id", user.user_id) }
                Toast.makeText(requireContext(), "Профиль успешно сохранен", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("UserProfileFragment", "Ошибка сохранения профиля", e)
                Toast.makeText(requireContext(), "Ошибка сохранения профиля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Освобождение ресурсов при уничтожении представления
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}











