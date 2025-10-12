package com.example.juicetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.juicetracker.databinding.FragmentTrackerBinding
import com.example.juicetracker.ui.AppViewModelProvider
import com.example.juicetracker.ui.JuiceListAdapter
import com.example.juicetracker.ui.TrackerViewModel
import kotlinx.coroutines.launch

// Fragment hiển thị danh sách nước ép và cho phép thêm, sửa, xóa
class TrackerFragment : Fragment() {

    private val viewModel by viewModels<TrackerViewModel> { AppViewModelProvider.Factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTrackerBinding.inflate(inflater, container, false).root
    }

    private val adapter = JuiceListAdapter(
        onEdit = { drink ->
            findNavController().navigate(
                TrackerFragmentDirections.actionTrackerFragmentToEntryDialogFragment(drink.id)
            )
        },
        onDelete = { drink ->
            viewModel.deleteJuice(drink)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentTrackerBinding.bind(view)
        binding.recyclerView.adapter = adapter

        // Khi nhấn nút thêm (FAB), mở dialog nhập thông tin nước ép
        binding.fab.setOnClickListener { fabView ->
            fabView.findNavController().navigate(
                TrackerFragmentDirections.actionTrackerFragmentToEntryDialogFragment()
            )
        }

        // Lắng nghe luồng dữ liệu từ ViewModel và cập nhật giao diện
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.juicesStream.collect {
                    adapter.submitList(it)
                }
            }
        }
    }
}
