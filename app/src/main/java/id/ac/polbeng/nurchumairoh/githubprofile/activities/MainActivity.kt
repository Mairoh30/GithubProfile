package id.ac.polbeng.nurchumairoh.githubprofile.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.request.RequestOptions
import id.ac.polbeng.nurchumairoh.githubprofile.GlideApp
import id.ac.polbeng.nurchumairoh.githubprofile.R
import id.ac.polbeng.nurchumairoh.githubprofile.databinding.ActivityMainBinding
import id.ac.polbeng.nurchumairoh.githubprofile.helpers.Config
import id.ac.polbeng.nurchumairoh.githubprofile.models.GithubUser
import id.ac.polbeng.nurchumairoh.githubprofile.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menginisialisasi data binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menyembunyikan action bar
        supportActionBar?.hide()

        // Inisialisasi ViewModel
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)

        // Mengamati perubahan data ViewModel
        mainViewModel.githubUser.observe(this) { user ->
            user?.let { setUserData(it) } ?: showError("User not found")
        }

        mainViewModel.isLoading.observe(this) { showLoading(it) }

        // Menambahkan listener pada tombol pencarian
        binding.btnSearchUserLogin.setOnClickListener {
            val userLogin = binding.etSearchUserLogin.text.toString().trim()
            if (userLogin.isEmpty()) {
                showError("Please enter a valid username")
            } else {
                mainViewModel.searchUser(userLogin)
            }
        }
    }

    /**
     * Menampilkan data pengguna GitHub
     */
    private fun setUserData(githubUser: GithubUser) {
        binding.tvUser.text = """
            Username: ${githubUser.username}
            Name: ${githubUser.name ?: "N/A"}
            Repositories: ${githubUser.repoCount}
        """.trimIndent()

        GlideApp.with(this)
            .load(githubUser.avatarUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.baseline_image_24)
                    .error(R.drawable.baseline_broken_image_24)
            )
            .into(binding.imgUser)
    }

    /**
     * Menampilkan atau menyembunyikan loading indicator
     */
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    /**
     * Menampilkan pesan error
     */
    private fun showError(message: String) {
        binding.tvUser.text = message
    }
}
