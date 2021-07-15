package com.example.testassignmentapp

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager
    lateinit var sliderDotspanel: LinearLayout

    var imageId =
        arrayOf<Int>(R.drawable.sample_one, R.drawable.sample_two, R.drawable.sample_three, R.drawable.sample_four, R.drawable.sample_five)
    var imagesName =
        arrayOf("image1", "image2", "image3", "image4", "image5")

    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 500 //delay in milliseconds before task is to be executed
    val PERIOD_MS: Long = 3000 // time in milliseconds between successive task executions.

    private var dotscount = 0
    lateinit var dots: Array<ImageView?>

    lateinit var recyclerView: RecyclerView
    lateinit var dataAdapter: RecyclerViewAdapters
    var dataList:ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sliderDotspanel = findViewById(R.id.SliderDots)
        viewPager = findViewById(R.id.viewpager)
        val inputSearch = findViewById<View>(R.id.search_edit) as AppCompatEditText
        val btnClear = findViewById<View>(R.id.btn_clear) as AppCompatImageView

        val adapter: PagerAdapter = CustomAdapter(this@MainActivity, imageId, imagesName)
        viewPager.setAdapter(adapter)

        dotscount = adapter.getCount()
        dots = arrayOfNulls(dotscount)

        for (i in 0 until dotscount) {
            dots[i] = ImageView(this)
            dots[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.non_active_dot
                )
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            sliderDotspanel.addView(dots[i], params)
        }

        dots[0]!!.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.active_dot
            )
        )
        addAnimals(0)
        //Initializing RecyclerView
        recyclerView=findViewById(R.id.recycler_one)
        recyclerView.layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        dataAdapter = RecyclerViewAdapters(dataList, this)
        recyclerView.adapter = dataAdapter

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }


            override fun onPageSelected(position: Int) {
                for (i in 0 until dotscount) {
                    dots[i]!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            applicationContext,
                            R.drawable.non_active_dot
                        )
                    )
                }
                dots[position]!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.active_dot
                    )
                )
                addAnimals(position)
                dataAdapter.notifyDataSetChanged()
            }
        })

        /*After setting the adapter use the timer */

        /*After setting the adapter use the timer */
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == 4) {
                currentPage = 0
            }
            viewPager.setCurrentItem(currentPage++, true)
        }

        timer = Timer() // This will create a new Thread

        timer!!.schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(Update)
            }
        }, DELAY_MS, PERIOD_MS)

       /* val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)*/
        //Adding animal names
        //addAnimals();

        //Set up recyclerview with Vertical LayoutManager and the adapter


        inputSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // filter your list from your input
                filter(s.toString())
                dataAdapter.notifyDataSetChanged()
                //getRecycler()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!inputSearch.getText().toString().equals("")) { //if edittext include text
                    recyclerView.layoutManager= LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
                    dataAdapter = RecyclerViewAdapters(dataList, this@MainActivity)
                    recyclerView.adapter = dataAdapter
                    btnClear.setVisibility(View.VISIBLE)
                } else { //not include text
                    btnClear.setVisibility(View.GONE)
                }
            }

        })

        btnClear.setOnClickListener {
            inputSearch.setText("")
        }
    }

    private fun addAnimals(position: Int) {
        dataList.clear()
        dataList.add("Dog")
        dataList.add("Cat")
        dataList.add("Monkey")
        dataList.add("lion")
        dataList.add("Elephent")
        dataList.add("Cheetah")
        dataList.add("Snake")
        dataList.add("Cow")
        dataList.add("Ant")
        dataList.add("Tiger")
        dataList.add("Lizard")

        when (position) {
            1 -> {
                dataList.set(0, "Cat")
                dataList.set(1, "Dog")
            }
            2 -> {
                dataList.set(0, "Monkey")
                dataList.set(2, "Dog")
            }
            3 -> {
                dataList.set(0, "lion")
                dataList.set(3, "Dog")
            }
            4 -> {
                dataList.set(0, "Elephent")
                dataList.set(4, "Dog")
            }
        }

    }

    fun filter(text: String?) {
        val temp: MutableList<String> = ArrayList()
        for (d in this!!.dataList!!) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (text?.let { d.contains(it, true) }!!) {
                temp.add(d)
            }
        }
        //update recyclerview
        dataAdapter?.updateList(temp)
    }
}