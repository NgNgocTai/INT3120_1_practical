/*
 * Juice Tracker - Compose item migration
 */

package com.example.juicetracker.ui

import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.juicetracker.R
import com.example.juicetracker.data.Juice
import com.example.juicetracker.data.JuiceColor
import com.google.accompanist.themeadapter.material3.Mdc3Theme

// -------------------- Adapter --------------------
class JuiceListAdapter(
    private var onEdit: (Juice) -> Unit,
    private var onDelete: (Juice) -> Unit
) : ListAdapter<Juice, JuiceListAdapter.JuiceListViewHolder>(JuiceDiffCallback()) {

    // ViewHolder chứa ComposeView thay vì layout XML
    class JuiceListViewHolder(
        private val composeView: ComposeView,
        private val onEdit: (Juice) -> Unit,
        private val onDelete: (Juice) -> Unit
    ) : RecyclerView.ViewHolder(composeView) {

        // Gán nội dung Compose vào ComposeView
        fun bind(input: Juice) {
            composeView.setContent {
                ListItem(
                    input = input,
                    onEdit = onEdit,
                    onDelete = onDelete,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onEdit(input) }
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }
        }
    }

    // Tạo ViewHolder dùng ComposeView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JuiceListViewHolder {
        val composeView = ComposeView(parent.context).apply {
            // ⚠️ thêm dòng này để tránh lỗi "must supply a layout_width"
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return JuiceListViewHolder(
            composeView,
            onEdit,
            onDelete
        )
    }


    override fun onBindViewHolder(holder: JuiceListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

// So sánh item trong danh sách
class JuiceDiffCallback : DiffUtil.ItemCallback<Juice>() {
    override fun areItemsTheSame(oldItem: Juice, newItem: Juice) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Juice, newItem: Juice) = oldItem == newItem
}

// -------------------- Composables --------------------

// Mục danh sách chính
@Composable
fun ListItem(
    input: Juice,
    onEdit: (Juice) -> Unit,
    onDelete: (Juice) -> Unit,
    modifier: Modifier = Modifier
) {
    Mdc3Theme {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            JuiceIcon(input.color)
            Spacer(Modifier.width(8.dp))
            JuiceDetails(juice = input, modifier = Modifier.weight(1f))
            DeleteButton(
                onDelete = { onDelete(input) },
                modifier = Modifier.align(Alignment.Top)
            )
        }
    }
}

@Preview
@Composable
fun PreviewListItem() {
    ListItem(Juice(1, "Sweet Beet", "Apple, carrot, beet, and lemon", "Red", 4), {}, {})
}

// Biểu tượng màu nước ép
@Composable
fun JuiceIcon(color: String, modifier: Modifier = Modifier) {
    val colorLabelMap = JuiceColor.values().associateBy { stringResource(it.label) }
    val selectedColor = colorLabelMap[color]?.let { Color(it.color) }
    val juiceIconContentDescription = stringResource(R.string.juice_color, color)

    Box(modifier = modifier.semantics { contentDescription = juiceIconContentDescription }) {
        Icon(
            painter = painterResource(R.drawable.ic_juice_color),
            contentDescription = null,
            tint = selectedColor ?: Color.Red,
            modifier = Modifier.align(Alignment.Center)
        )
        Icon(painter = painterResource(R.drawable.ic_juice_clear), contentDescription = null)
    }
}

@Preview @Composable fun PreviewJuiceIcon() { JuiceIcon("Yellow") }

// Thông tin chi tiết: tên, mô tả, xếp hạng
@Composable
fun JuiceDetails(juice: Juice, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.Top) {
        Text(
            text = juice.name,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(juice.description)
        RatingDisplay(rating = juice.rating, modifier = Modifier.padding(top = 8.dp))
    }
}

@Preview @Composable
fun PreviewJuiceDetails() {
    JuiceDetails(Juice(1, "Sweet Beet", "Apple, carrot, beet, and lemon", "Red", 4))
}

// Hiển thị số sao theo rating
@Composable
fun RatingDisplay(rating: Int, modifier: Modifier = Modifier) {
    val displayDescription = pluralStringResource(R.plurals.number_of_stars, count = rating, rating)
    Row(modifier = modifier.semantics { contentDescription = displayDescription }) {
        repeat(rating) {
            Image(
                modifier = Modifier.size(32.dp),
                painter = painterResource(R.drawable.star),
                contentDescription = null
            )
        }
    }
}

// Nút xóa item
@Composable
fun DeleteButton(onDelete: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = { onDelete() }, modifier = modifier) {
        Icon(
            painter = painterResource(R.drawable.ic_delete),
            contentDescription = stringResource(R.string.delete)
        )
    }
}

@Preview @Composable fun PreviewDeleteIcon() { DeleteButton({}) }
