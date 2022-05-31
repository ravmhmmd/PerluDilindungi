package com.example.perludilindungi.list


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.perludilindungi.R
import com.example.perludilindungi.data.ResultsFaskes
import com.example.perludilindungi.database.Bookmark
import kotlin.random.Random

class CustomAdapter internal constructor(
    internal var context: Context,
    private val mList: List<ResultsFaskes>,
    private val bookmarkList: List<Bookmark>,
    private val isBookmark: Boolean
    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(context)
            .inflate(R.layout.card_view_design_facility, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (isBookmark) {
            val ItemsViewModel = bookmarkList[position]

            holder.textViewName.text = ItemsViewModel.name
            if (ItemsViewModel.facilityType != "") {
                holder.textViewFacilityType.text = ItemsViewModel.facilityType
            } else {
                holder.textViewFacilityType.text = "LAINNYA"
            }
            if (ItemsViewModel.facilityType == "RUMAH SAKIT") {
                holder.textViewFacilityType.setBackgroundColor(ContextCompat.getColor(context, R.color.purple))
            } else {
                holder.textViewFacilityType.setBackgroundColor(ContextCompat.getColor(context, R.color.pink_color))
            }
            holder.textViewAddress.text = ItemsViewModel.address
            holder.textViewPhone.text = ItemsViewModel.phone
            holder.textViewCode.text = "KODE: " + ItemsViewModel.code

            val action = BookmarkListFragmentDirections.openBookmarkDetailAction(
                ItemsViewModel.address,
                ItemsViewModel.facilityId,
                ItemsViewModel.facilityType,
                ItemsViewModel.hospitalClass,
                ItemsViewModel.code,
                ItemsViewModel.city,
                ItemsViewModel.latitude,
                ItemsViewModel.longitude,
                ItemsViewModel.name,
                ItemsViewModel.province,
                ItemsViewModel.sourceData,
                ItemsViewModel.status,
                ItemsViewModel.phone
            )

            holder.itemView.setOnClickListener(
                Navigation.createNavigateOnClickListener(action)
            )
        }
        else {
            val ItemsViewModel = mList[position]
            holder.textViewName.text = ItemsViewModel.nama
            if (ItemsViewModel.jenisFaskes != "") {
                holder.textViewFacilityType.text = ItemsViewModel.jenisFaskes
            } else {
                holder.textViewFacilityType.text = "LAINNYA"
            }
            if (ItemsViewModel.jenisFaskes == "RUMAH SAKIT") {
                holder.textViewFacilityType.setBackgroundColor(ContextCompat.getColor(context, R.color.purple))
            } else {
                holder.textViewFacilityType.setBackgroundColor(ContextCompat.getColor(context, R.color.pink_color))
            }
            holder.textViewAddress.text = ItemsViewModel.alamat
            holder.textViewPhone.text = ItemsViewModel.telp
            holder.textViewCode.text = "KODE: " + ItemsViewModel.kode

            val action = FacilityListFragmentDirections.openDetailAction(
                if (ItemsViewModel.alamat != null) ItemsViewModel.alamat!! else "",
                if (ItemsViewModel.id != null) ItemsViewModel.id!!.toLong() else Random.nextLong(10000000),
                if (ItemsViewModel.jenisFaskes != null) ItemsViewModel.jenisFaskes!! else "",
                if (ItemsViewModel.kelasRs != null) ItemsViewModel.kelasRs!! else "",
                if (ItemsViewModel.kode != null) ItemsViewModel.kode!! else "",
                if (ItemsViewModel.kota != null) ItemsViewModel.kota!! else "",
                if (ItemsViewModel.latitude != null) ItemsViewModel.latitude!! else "",
                if (ItemsViewModel.longitude != null) ItemsViewModel.longitude!! else "",
                if (ItemsViewModel.nama != null) ItemsViewModel.nama!! else "",
                if (ItemsViewModel.provinsi != null) ItemsViewModel.provinsi!! else "",
                if (ItemsViewModel.sourceData != null) ItemsViewModel.sourceData!! else "",
                if (ItemsViewModel.status != null) ItemsViewModel.status!! else "",
                if (ItemsViewModel.telp != null) ItemsViewModel.telp!! else ""
            )
            holder.itemView.setOnClickListener(
                Navigation.createNavigateOnClickListener(action)
            )
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        if (isBookmark) {
            return bookmarkList.size
        }
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewNama)
        val textViewFacilityType: TextView = itemView.findViewById(R.id.textViewFacilityType)
        val textViewAddress: TextView = itemView.findViewById(R.id.textViewAddress)
        val textViewPhone: TextView = itemView.findViewById(R.id.textViewPhone)
        val textViewCode: TextView = itemView.findViewById(R.id.textViewFacilityCode)
    }
}