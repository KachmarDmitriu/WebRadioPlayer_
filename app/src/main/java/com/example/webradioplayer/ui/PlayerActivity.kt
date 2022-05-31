package com.example.webradioplayer.ui

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.webradioplayer.PlayerService
import com.example.webradioplayer.R
import com.example.webradioplayer.databinding.PlayerActivityBinding
import com.example.webradioplayer.model.ListRadiostationViewModel
import com.google.android.exoplayer2.MediaItem

import androidx.activity.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.webradioplayer.dao.database.ListRadiostation


class PlayerActivity : AppCompatActivity()
{

    private val newWordActivityRequestCode = 1
    private val wordViewModel: ListRadiostationViewModel.WordViewModel by viewModels {
        ListRadiostationViewModel.WordViewModelFactory((application as ListRadoistationApplication).repository)
    }

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        PlayerActivityBinding.inflate(layoutInflater)
    }

    private lateinit var playerService: PlayerService
    private lateinit var binding: PlayerActivityBinding
    private var mBound: Boolean = false

    /** Defines callbacks for service binding, passed to bindService()*/
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {

            val binder = service as PlayerService.LocalBinder
            playerService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PlayerActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setContentView(binding.root)

        setupListeners()
        setupRecycler()
/*
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = CustomAdaper
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

         wordViewModel.allWords.observe(owner = this) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter}//submitList(it) }
 */
    }


   //    val database = WebPlayerDatabase.getDatabase(this)
/*

        //TODO видалити, лише для тестування БД
        GlobalScope.async {
            database.radiostationDao().insert(
                ListRadiostation(
                    nameRadiostation = "test name",
                    genre = "test genre",
                    urlRadiostation = ""
                )
            )
        }


        }Lj,fdkty dtcm rjl
/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let { reply ->
                val word = ListRadiostation(reply)
                wordViewModel.insert(word)  // uid, name ,url, genre
            }
        } else {


            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }

    }
    */
 */





    private fun setupListeners() {
        binding.buttonPlay.setOnClickListener { onPlay() }
        binding.buttonStop.setOnClickListener { onStopPlaying() }
    }


    private fun setupRecycler() {

        binding.recyclerView.adapter = CustomAdaper(listOf())

        binding.recyclerView.layoutManager =  LinearLayoutManager(this) //???? что делает????

    }


    public override fun onStart() {
            super.onStart()

            // Bind to LocalService
            Intent(this, PlayerService::class.java).also { intent ->
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
        }


        public override fun onStop() {
            super.onStop()

        unbindService(connection)
        mBound = false

        }


    private fun onPlay() {
        if (mBound) {
            val mediaItem = MediaItem.fromUri(getString(R.string.url_currentRadioStation))

            playerService.play(mediaItem)

            Toast.makeText(
                this,
                "playing: ${playerService.currentMediaItem}",// временно заменено для проверки, но это не подтягивает имя стануии ,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onStopPlaying() {
        if (mBound) {
            playerService.stop()
            Toast.makeText(this, "stopping", Toast.LENGTH_SHORT).show()
        }


    }

}