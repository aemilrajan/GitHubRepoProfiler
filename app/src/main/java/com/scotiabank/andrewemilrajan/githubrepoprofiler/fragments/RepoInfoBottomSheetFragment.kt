package com.scotiabank.andrewemilrajan.githubrepoprofiler.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.scotiabank.andrewemilrajan.githubrepoprofiler.R


/**
 * Created by andrewemilrajan on 2019-09-22.
 */

class RepoInfoBottomSheetFragment : BottomSheetDialogFragment() {

    private val screenWidthScaleFactor = 0.95

    private var listener: Listener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as Listener?
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.repo_info_bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repoLastUpdatedDate = view.findViewById<TextView>(R.id.repo_last_updated_date)
        val repoStarCount = view.findViewById<TextView>(R.id.repo_star_count)
        val repoForkCount = view.findViewById<TextView>(R.id.repo_fork_count)

        listener?.let {
            repoLastUpdatedDate.text = it.repoLastUpdatedDate
            repoStarCount.text = it.repoStarCount
            repoForkCount.text = it.repoForkCount
        }
    }

    override fun onResume() {
        super.onResume()

        context?.let { context ->
            val configuration = context.resources?.configuration
            configuration?.let {
                val screenWidth = getPxFromDp(context, it.screenWidthDp.toFloat())
                val adjustedScreenWidth = screenWidth * screenWidthScaleFactor
                dialog.window?.setLayout(adjustedScreenWidth.toInt(), -1)
            }
        }
    }

    private fun getPxFromDp(context: Context, dp: Float): Int {
        return (dp * context.resources.displayMetrics.density + 0.5f).toInt()
    }


    interface Listener {
        /**
         * Get the repo last updated date
         */
        val repoLastUpdatedDate: String

        /**
         * Get the repo star count
         */
        val repoStarCount: String

        /**
         * Get the repo fork count
         */
        val repoForkCount: String
    }
}
