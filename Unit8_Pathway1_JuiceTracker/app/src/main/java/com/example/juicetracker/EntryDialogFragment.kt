package com.example.juicetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.juicetracker.data.JuiceColor
import com.example.juicetracker.databinding.FragmentEntryDialogBinding
import com.example.juicetracker.ui.AppViewModelProvider
import com.example.juicetracker.ui.EntryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EntryDialogFragment : BottomSheetDialogFragment() {

    private val entryViewModel by viewModels<EntryViewModel> { AppViewModelProvider.Factory }
    // Dùng navArgs để nhận argument từ navigation graph
    private val args: EntryDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEntryDialogBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentEntryDialogBinding.bind(view)
        // Lấy ID từ argument
        val juiceId = args.itemId

        // Logic cho nút Lưu
        binding.saveButton.setOnClickListener {
            entryViewModel.saveJuice(
                juiceId,
                binding.name.text.toString(),
                binding.description.text.toString(),
                // Tạm thời hard-code màu, bạn có thể cải tiến sau
                JuiceColor.Red.name,
                binding.ratingBar.rating.toInt()
            )
            // Đóng dialog sau khi lưu
            findNavController().navigateUp()
        }

        // Logic cho nút Hủy
        binding.cancelButton.setOnClickListener {
            // Đóng dialog
            findNavController().navigateUp()
        }
    }
}