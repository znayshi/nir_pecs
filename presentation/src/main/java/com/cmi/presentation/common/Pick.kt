package com.cmi.presentation.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks

abstract class Pick : Fragment(), PickiTCallbacks {

    private lateinit var pickiT: PickiT

    protected abstract fun onStartLoadingImage()
    protected abstract fun onProgressUpdate(progress: Int)
    protected abstract fun onImageLoaded(wasSuccessful: Boolean, path: String?)

    protected fun getPickiT(): PickiT = pickiT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            pickiT = PickiT(requireContext(), this, it)
        }
    }

    override fun PickiTonUriReturned() {
        onStartLoadingImage()
    }

    override fun PickiTonStartListener() {
        // Implement if needed
    }

    override fun PickiTonProgressUpdate(progress: Int) {
        onProgressUpdate(progress)
    }

    override fun PickiTonCompleteListener(
        path: String?,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        onImageLoaded(wasSuccessful, path)
    }
}
