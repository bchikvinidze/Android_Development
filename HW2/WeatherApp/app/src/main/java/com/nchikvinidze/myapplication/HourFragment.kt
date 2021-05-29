package com.nchikvinidze.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HourFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HourFragment : Fragment() {
    lateinit var rv: RecyclerView
    lateinit var detailAdapter: DetailAdapter
    var hoursList =  arrayListOf<HourDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_hour, container, false)
        rv = view.findViewById<RecyclerView>(R.id.rv_hours)
        detailAdapter = DetailAdapter(hoursList)
        rv.adapter = detailAdapter
        rv.layoutManager = LinearLayoutManager(container?.context, LinearLayoutManager.VERTICAL, false)
        setupDivider(view.context)
        updateData((activity as MainActivity).hourData)
        return view
    }

    fun setupDivider(viewContext: Context){
        var div = DividerItemDecoration(rv.context, DividerItemDecoration.VERTICAL)
        var mDivider = ContextCompat.getDrawable(viewContext, R.drawable.recycler_view_divider)
        if (mDivider != null) {
            div.setDrawable(mDivider)
        }
        rv.addItemDecoration(div)
    }

    var monthString = arrayOf(
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "May",
        "Jun",
        "Jul",
        "Aug",
        "Sep",
        "Oct",
        "Nov",
        "Dec"
    )

    fun updateData(data: HourCommentModel?){
        val itemsCnt = data?.list?.size
        hoursList.clear()
        for(i in 0..(itemsCnt!!-1)) {//itemsCnt!!
            var listitem = data?.list?.get(i)
            var iconName = listitem?.weather?.get(0)?.icon
            var temperature = listitem?.main?.temp.roundToInt()
            var weatherDesc = listitem?.weather?.get(0)?.description

            var hour= listitem?.dt_txt.subSequence(11,13).toString()
            var ampm = " AM"
            if(hour.toInt() > 12){
                ampm = " PM"
                hour = (hour.toInt() - 12).toString()
                if(hour.toInt() < 10) hour = "0"+hour
            }
            var day = " "+listitem?.dt_txt.subSequence(8,10).toString()
            var month = " "+monthString[listitem?.dt_txt.subSequence(5,7).toString().toInt()-1]

            hoursList.add(HourDetails(hour+ampm+day+month, iconName, temperature, weatherDesc))
            detailAdapter.notifyDataSetChanged() //.notifyItemInserted(hoursList.size - 1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}