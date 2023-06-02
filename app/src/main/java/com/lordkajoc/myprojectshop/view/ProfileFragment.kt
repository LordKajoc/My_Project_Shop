package com.lordkajoc.myprojectshop.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.lordkajoc.myprojectshop.R
import com.lordkajoc.myprojectshop.databinding.FragmentProfileBinding
import com.lordkajoc.myprojectshop.model.DataUserPostItem
import com.lordkajoc.myprojectshop.model.DataUsersResponseItem
import com.lordkajoc.myprojectshop.viewmodel.ProfileViewModel
import com.lordkajoc.myprojectshop.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var model: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(this).get(ProfileViewModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        if (sharedPreferences.getString("id", "")!!.isEmpty()) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Anda Yakin?")
                .setCancelable(false)
                .setNegativeButton("Cancel") { dialog, which ->
                    // Respond to negative button press
                    dialog.cancel()
                    findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
                }
                .setPositiveButton("Login") { dialog, which ->
                    // Respond to positive button press
                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                }
                .show()
        } else if (sharedPreferences.getString("id", "")!!.isNotEmpty()) {
            getDataProfile()
        }

        binding.btnupdateprofile.setOnClickListener {
            updateUserProfile()
            activity?.onBackPressed()
        }

        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    fun getDataProfile() {
        sharedPreferences = requireContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        val id = sharedPreferences.getString("id", "").toString()
        model.getProfileById(id)
        val imageUserProfile = binding.imageProfile
        model.dataUserProfile.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.etUsernameprofile.setText(it.name)
                binding.etImagesource.setText(it.image)
                context?.let { it1 ->
                    Glide.with(it1)
                        .load(it.image)
                        .into(imageUserProfile)
                }
            }
        }
    }

    fun updateUserProfile() {
        sharedPreferences = requireContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        val createat = sharedPreferences.getString("createat", "").toString()
        val id = sharedPreferences.getString("id", "").toString()
        val email = sharedPreferences.getString("email", "").toString()
        val pass = sharedPreferences.getString("password", "").toString()
        val nameUser = binding.etUsernameprofile.text.toString()
        val imageUser = binding.etImagesource.text.toString()
        val dataUser = DataUsersResponseItem(
            createat,
            email,
            id,
            imageUser,
            nameUser,
            pass
        )
        model.updateUser(id, dataUser)
        navigationBundlingSf(dataUser)
        model.dataUpdateUser.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(context, "Update Berhasil", Toast.LENGTH_SHORT)
            }
        }


    }

    private fun navigationBundlingSf(currentUser: DataUsersResponseItem) {
        sharedPreferences =
            requireActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        //shared pref to save log in history
        val sharedPref = sharedPreferences.edit()
        sharedPref.putString("createat", currentUser.createdAt)
        sharedPref.putString("email", currentUser.email)
        sharedPref.putString("password", currentUser.password)
        sharedPref.putString("username", currentUser.name)
        sharedPref.putString("image", currentUser.image)
        sharedPref.putString("id", currentUser.idUsers)
        sharedPref.apply()
    }


    private fun logout() {
        sharedPreferences =
            requireActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        //shared pref to save log in history
        val sharedPref = sharedPreferences.edit()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Logout")
            .setMessage("Anda Yakin?")
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialog, which ->
                // Respond to negative button press
                dialog.cancel()
            }
            .setPositiveButton("Logout") { dialog, which ->
                // Respond to positive button press
                sharedPref.clear()
                sharedPref.apply()
                activity?.onBackPressed()
            }
            .show()
    }
}
