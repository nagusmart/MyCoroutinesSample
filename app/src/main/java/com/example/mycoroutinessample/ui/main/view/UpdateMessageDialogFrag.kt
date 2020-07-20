package com.example.mycoroutinessample.ui.main.view

import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.mycoroutinessample.R
import com.example.mycoroutinessample.data.model.User

class UpdateMessageDialogFrag : DialogFragment() {

    var mListener:ItemClickListener?=null

    companion object{
        const val ARG_NAME="name"

        fun newInstance(user: User): UpdateMessageDialogFrag{

            val fragment=UpdateMessageDialogFrag()

            val bundle=Bundle()
            bundle.putParcelable(ARG_NAME,user)

            fragment.arguments=bundle

//            val bundle = Bundle().apply {
//                putString(ARG_NAME, name)
//            }

            return fragment
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v=inflater.inflate(R.layout.update_message_layout,container,false)

        var user=arguments?.getParcelable<User>(ARG_NAME)


        val name=v.findViewById<EditText>(R.id.name)
        val title=v.findViewById<EditText>(R.id.title)
        val message=v.findViewById<EditText>(R.id.message)
        val cancel=v.findViewById<TextView>(R.id.cancel)
        val submit=v.findViewById<TextView>(R.id.submit)


        if(user!=null){
            name.setText(user.name)
            title.setText(user.title)
            message.setText(user.message)
        }

        cancel.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                dismiss()
            }
        })

        submit.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {

                val names=name.text.toString()
                val titles=title.text.toString()
                val messages=message.text.toString()
                val id=user?.id

                if(!names.isEmpty()&&!titles.isEmpty()&&!messages.isEmpty()){

                    if (id != null) {
                        mListener!!.updateMessage(id, names, titles, messages)

                        dismiss()
                    }

                }


            }
        })

        return v
    }

    fun setListener(listener:ItemClickListener){
        mListener=listener
    }

    override fun onResume() {
        super.onResume()

        val display: Display
        val window = dialog!!.window
        if (window != null) {
            val size = Point()
            display = window.windowManager.defaultDisplay
            display.getSize(size)
            val width = size.x
            window.setLayout((width * 0.85).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
            window.setGravity(Gravity.CENTER)
        }
    }

    interface ItemClickListener{
        fun updateMessage(id:String, name:String, title:String, message:String);
    }


}