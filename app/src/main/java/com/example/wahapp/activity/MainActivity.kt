package com.example.wahapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.wahapp.Llamadas
import com.example.wahapp.Chats
import com.example.wahapp.R
import com.example.wahapp.Estados
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager2      : ViewPager2
    private lateinit var tabLayout       : TabLayout
    private lateinit var toolbar         : androidx.appcompat.widget.Toolbar
    private lateinit var appPagerAdapter : AppPagerAdapter
    private lateinit var auth            : FirebaseAuth
    private val titles = arrayListOf("Chats","Estados","Llamadas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        toolbar       = findViewById(R.id.toolbarMain)
        tabLayout     = findViewById(R.id.tabLayoutMain)
        viewPager2    = findViewById(R.id.viewPager2Main)
        auth          = FirebaseAuth.getInstance()
        toolbar.title = "Wahapp"
        setSupportActionBar(toolbar)
        appPagerAdapter = AppPagerAdapter(this)
        viewPager2.adapter = appPagerAdapter
        TabLayoutMediator(tabLayout,viewPager2){
            tab,position->
            tab.text = titles[position]
        }.attach()

    }
    class AppPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
          return when(position)
          {
              0-> Chats()
              1-> Estados()
              2-> Llamadas()
              else -> Chats()
          }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.profile -> {
                val intent = Intent(this,MenuActivity::class.java)
                intent.putExtra("OptionName","profile")
                startActivity(intent)
            }
            R.id.ajustes -> {
                val intent = Intent(this,MenuActivity::class.java)
                intent.putExtra("OptionName","ajustes")
                startActivity(intent)
            }
            R.id.closeSession -> {
                auth.signOut()
                val intent = Intent(this,AuthenticationActivity::class.java)
                startActivity(intent)
               // finish()
            }
            R.id.about -> {
                val intent = Intent(this,MenuActivity::class.java)
                intent.putExtra("OptionName","about")
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}