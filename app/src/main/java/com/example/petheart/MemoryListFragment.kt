package com.example.petheart

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.io.File
import java.util.*

private const val TAG = "MemoryListFragment"

class MemoryListFragment : Fragment() {

    interface Callbacks{
        fun onMemorySelected(memoryId: UUID)
    }

    private var callbacks:Callbacks? = null

    private lateinit var memoryRecyclerView: RecyclerView
    private var adapter: MemoryAdapter? = MemoryAdapter(emptyList())

    private val memoryListViewModel: MemoryListViewModel by lazy {
        ViewModelProviders.of(this).get(MemoryListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_memory_list,container,false)

        memoryRecyclerView = view.findViewById(R.id.memory_recycler_view) as RecyclerView
        memoryRecyclerView.layoutManager = LinearLayoutManager(context)
        memoryRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        memoryListViewModel.memoryListLiveData.observe(
            viewLifecycleOwner,
            Observer{ memories ->
                memories.let{
                    Log.i(TAG, "Got memories ${memories.size}")
                    updateUI(memories)
                }
            }
        )
    }

    override fun onDetach(){
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_memory_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.new_memory->{
                val memory = Memory()
                memoryListViewModel.addMemory(memory)
                callbacks?.onMemorySelected(memory.id)

                true
            }
            R.id.filter_favorite->{

                true
            }
            else-> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI(memories:List<Memory>){
        adapter = MemoryAdapter(memories)
        memoryRecyclerView.adapter = adapter
    }

    private inner class MemoryHolder(view:View)
        :RecyclerView.ViewHolder(view), View.OnClickListener{

        private lateinit var memory: Memory

        private val titleTextView: TextView = itemView.findViewById(R.id.memory_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.memory_date)
        private val favoriteImageView: ImageView = itemView.findViewById(R.id.favorite)
        private val photoView: ImageView = itemView.findViewById(R.id.memory_photo)

        init{
            itemView.setOnClickListener(this)
        }
        fun bind(memory: Memory){
            this.memory = memory
            titleTextView.text = this.memory.title
            dateTextView.text = this.memory.date.toString()
            favoriteImageView.visibility = if(memory.isFavorite){
                View.VISIBLE
            }else{
                View.GONE
            }
        }

        override fun onClick(v:View){
            callbacks?.onMemorySelected(memory.id)
        }

    }

    private inner class MemoryAdapter(var memories: List<Memory>)
        :RecyclerView.Adapter<MemoryHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryHolder {
            val view = layoutInflater.inflate(R.layout.list_item_memory,parent,false)
            return MemoryHolder(view)
        }

        override fun getItemCount() = memories.size

        override fun onBindViewHolder(holder: MemoryHolder, position: Int) {
            val memory = memories[position]
            holder.bind(memory)
        }
    }

    companion object{
        fun newInstance(): MemoryListFragment{
            return MemoryListFragment()
        }
    }
}

