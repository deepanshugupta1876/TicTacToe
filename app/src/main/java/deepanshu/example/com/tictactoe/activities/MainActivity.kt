package deepanshu.example.com.tictactoe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.GridLayoutManager
import deepanshu.example.com.tictactoe.adapters.GameBoardAdapter
import deepanshu.example.com.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), GameBoardAdapter.OnItemClickCallback {
    private lateinit var mBinding: ActivityMainBinding
    private var currentPlayer = PLAYER
    private lateinit var gameState: IntArray
    private var gameActive = true
    private lateinit var emptyPositionList:ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    private fun initializeGame() {
        currentPlayer = PLAYER
        gameState = IntArray(9) {-1} //Initialize the game state
        emptyPositionList = arrayListOf(0,1,2,3,4,5,6,7,8)
        gameActive = true
    }

    private fun setUp() {
        initBinding()
        initRecyclerview()
        initializeGame()
        addListener()
    }

    private fun addListener() {
        mBinding.btNewGame.setOnClickListener { resetGame() }
    }

    private fun initRecyclerview() {
        val adapter = GameBoardAdapter(this)
        val layoutManager = GridLayoutManager(this, 3)
        mBinding.gameBoard.layoutManager = layoutManager
        mBinding.gameBoard.adapter = adapter
    }

    private fun initBinding() {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    private fun resetGame() {
        initializeGame()
        resetGameBoard()
    }

    private fun resetGameBoard() {
        val adapter = GameBoardAdapter(this)
        mBinding.gameBoard.adapter = adapter
    }

    companion object {
        private const val PLAYER = 0
        private const val COMPUTER = 1
        private val WIN_POSITIONS = arrayOf(
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8),
            intArrayOf(2, 4, 6)
        )
        private const val TILE_STATE_EMPTY = ""
        private const val TILE_STATE_X = "X"
        private const val TILE_STATE_O = "O"
    }

    override fun onPlayerTap(view: AppCompatTextView, position: Int) {
        if (!gameActive) {
            return
        }

        if (emptyPositionList.isNotEmpty()) {
            view.setOnClickListener(null)
            emptyPositionList.remove(position)
            gameState[position] = currentPlayer

            if (isGameWon()) {
                gameActive = false
                mBinding.gameStatus.text =
                    when (currentPlayer == PLAYER) {
                        true -> {
                            view.text = TILE_STATE_O
                            "You Win!!"
                        }
                        false -> {
                            view.text = TILE_STATE_X
                            "Computer Win!!"
                        }
                    }
            } else {
                if (currentPlayer == PLAYER) {
                    view.text = TILE_STATE_O
                    mBinding.gameStatus.text = "Computer's Turn"
                    switchPlayer()
                    makeComputerMove()
                } else {
                    mBinding.gameStatus.text = "Your Turn"
                    view.text = TILE_STATE_X
                    switchPlayer()
                }
            }
        } else {
            mBinding.gameStatus.text = "Match Draw"
            gameActive = false
        }
    }

    private fun makeComputerMove() {
        val position:Int = emptyPositionList.random()
        (mBinding.gameBoard.findViewHolderForAdapterPosition(position) as GameBoardAdapter.GameBoardViewHolder).mBinding.tileText.performClick()
    }

    private fun isGameWon(): Boolean {
        for (winPosition in WIN_POSITIONS) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]]
                && gameState[winPosition[1]] == gameState[winPosition[2]]
                && gameState[winPosition[0]] != -1
            ) {
                return true
            }
        }
        return false
    }

    private fun switchPlayer() {
        currentPlayer = if (currentPlayer == PLAYER) COMPUTER else PLAYER
    }
}