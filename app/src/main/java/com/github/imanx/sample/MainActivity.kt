package com.github.imanx.sample

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.github.imanx.State
import com.github.imanx.StateLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        state_view.postDelayed({
            val random = Random().nextInt(3) + 1;
            when (2) {
                1 -> {
                    state_view.setState(State.Normal);
                    list.adapter = Adapter(this);
                }

                2 -> {
                    state_view.setState(State.Empty);

                }
                3 -> state_view.setState(State.Failure);
            }

        }, 2 * 1000);


        val view = state_view.getStateView(State.Empty)
        view.findViewById<TextView>(R.id.txt_view).text = "No Content for display :( "


        state_view.setOnChangeStateListener { view, state, d ->
            Log.i("TAG" ,"state $state $d");
        }


    }

    class Adapter(context: Context) : ArrayAdapter<Int>(context, R.layout.item) {

        var items = ArrayList<Int>();

        init {
            for (i in 0..1000) {
                items.add(i);
            }
        }


        override fun getCount(): Int {
            return items.size;
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView;
            if (view == null) {
                view = View.inflate(context, R.layout.item, null);

            }

            val txt = view!!.findViewById<TextView>(R.id.txt_view);
            txt.text = "Item ${items.get(position)}"

            return view;

        }
    }
}


