package demo.cipher.tf.com.datacipherdemo

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import demo.cipher.tf.com.datacipherdemo.algorithm.Interpreter
import demo.cipher.tf.com.datacipherdemo.utils.Logger

class ClientFragment : Fragment(), View.OnClickListener {

    private var mSelector: TextView? = null
    private var mData: TextView? = null
    private var mInter: Interpreter? = null;

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Logger.i("ClientFragment")
        val v = inflater!!.inflate(R.layout.fragment_client, container, false)
        val selector = v.findViewById<TextView>(R.id.client_selecter)
        v.findViewById<Button>(R.id.client_send).setOnClickListener(this)
        selector.setOnClickListener(this)
        mSelector = selector
        mData = v.findViewById(R.id.client_data)
        v.findViewById<EditText>(R.id.client_edit).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                processMessage(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
        })
        return v;
    }

    private fun processMessage(s: CharSequence?) {
        val value = mSelector?.text.toString();
        if (TextUtils.isEmpty(value)) {
            return
        }
        mData?.text = mInter?.translateToSecret(s.toString())


        Logger.i(mInter?.algorithm?.getFormatEncode() ?: "EMPTY")
    }

    private fun process(dialog: DialogInterface, which: Int) {
        dialog.dismiss()
        val al = Config.CIPHER_LIST[which]
        mSelector!!.text = al
        val it = Interpreter.Factory.loadRefInterpreter(al)
        it!!.genAlgorithm()
        mInter = it
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.client_selecter -> {
                val builder = AlertDialog.Builder(context);
                builder.setItems(Config.CIPHER_LIST, DialogInterface.OnClickListener { dialog, which -> process(dialog, which) })
                builder.create().show();
            }
            R.id.client_send -> {
                CommunicationMgr.send(TalkObject(mData?.text.toString(), mInter?.algorithm?.getFormatDecode(), mSelector?.text.toString()))
                Toast.makeText(context, "Already Sent", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
