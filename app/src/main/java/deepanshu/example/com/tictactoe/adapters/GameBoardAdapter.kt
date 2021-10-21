package deepanshu.example.com.tictactoe.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import deepanshu.example.com.tictactoe.databinding.ItemGameBoardTileBinding

class GameBoardAdapter(private val mListener: OnItemClickCallback) :
    RecyclerView.Adapter<GameBoardAdapter.GameBoardViewHolder>() {

    inner class GameBoardViewHolder(binding: ItemGameBoardTileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val mBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameBoardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val mItemBinding = ItemGameBoardTileBinding.inflate(inflater, parent, false)
        return GameBoardViewHolder(mItemBinding)
    }

    override fun onBindViewHolder(holder: GameBoardViewHolder, position: Int) {

        holder.mBinding.tileText.setOnClickListener {
            mListener.onPlayerTap(holder.mBinding.tileText, position)
        }
    }


    override fun getItemCount() = TILE_COUNT

    companion object {
        private const val TILE_COUNT = 9

    }

    public interface OnItemClickCallback {
        fun onPlayerTap(view: AppCompatTextView, position: Int)
    }
}