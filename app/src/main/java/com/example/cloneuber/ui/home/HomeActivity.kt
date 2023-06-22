package com.example.cloneuber.ui.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cloneuber.R
import com.example.cloneuber.data.util.ImageUtil
import com.example.cloneuber.databinding.ActivityHomeBinding
import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.ui.dialog.LogoutDialogFragment
import com.example.cloneuber.ui.util.showToast
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() ,LogoutDialogFragment.LogoutDialogListener{

    private lateinit var binding:ActivityHomeBinding

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var  dialogFragment:LogoutDialogFragment

    private val viewModel: HomeViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialogFragment = LogoutDialogFragment()

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                dialogFragment.listener = this@HomeActivity
                dialogFragment.show(supportFragmentManager, "LogoutDialog")
            }
        })

        setupDrawer()
        viewModel.getUserData()
        setupObserver()

        val buttonLogout = binding.navigationView.findViewById<AppCompatButton>(R.id.buttonLogout)
        buttonLogout.setOnClickListener { setupLogout()}
    }

    private fun setupLogout() {
        dialogFragment.listener = this
        dialogFragment.show(supportFragmentManager, "LogoutDialog")
    }

    private fun setupObserver() {
        viewModel.user.observe(this) { result ->
            val view = binding.navigationView.getHeaderView(0)
            val nameHeader = view.findViewById<TextView>(R.id.textViewName)
            val emailHeader = view.findViewById<TextView>(R.id.textViewEmail)
            val imageHeader = view.findViewById<ShapeableImageView>(R.id.profileImage)

            when(result){
                is Resource.Loading ->{
                    nameHeader.text = getString(R.string.loading)
                    emailHeader.text = getString(R.string.loading)
                }
                is Resource.Success ->{
                    nameHeader.text = result.data.name
                    emailHeader.text = result.data.email
                    imageHeader.setImageBitmap(ImageUtil.base64ToBitmap(result.data.profileImage!!))
                }
                is Resource.Failure ->{ showToast(result.message) }

                else -> {
                    showToast(result.toString())
                }
            }

            viewModel.resultLogout.observe(this){
                if(it == getString(R.string.textViewSuccessLogout)) {
                    showToast(it)
                    finish()
                }
            }


        }
    }

    private fun setupDrawer() {
        setSupportActionBar(binding.toolbar)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        val navController = findNavController(R.id.navHostFragment)

        // Conectar NavigationView con NavController
        navigationView.setupWithNavController(navController)

        // Configurar el ActionBarDrawerToggle para mostrar el ícono del Navigation Drawer
        val actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout, binding.toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    override fun onLogoutConfirmed() {
        viewModel.logout()
    }

}