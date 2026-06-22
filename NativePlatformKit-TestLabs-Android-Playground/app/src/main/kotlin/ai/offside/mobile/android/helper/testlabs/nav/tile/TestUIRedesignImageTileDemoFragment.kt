package ai.offside.mobile.android.helper.testlabs.nav.tile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignImageTileDemoBinding
import ai.offside.mobile.android.component.ui.imagetile.ImageTileActionState
import ai.offside.mobile.android.component.ui.imagetile.ImageTileState

/**
 * A simple [Fragment] subclass.
 */
class TestUIRedesignImageTileDemoFragment : Fragment(R.layout.fragment_test_ui_redesign_image_tile_demo) {

    var _binding: FragmentTestUiRedesignImageTileDemoBinding? = null
    val binding: FragmentTestUiRedesignImageTileDemoBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTestUiRedesignImageTileDemoBinding.bind(view)

        binding.cardImageTileSimple.setImageTileData(
            ImageTileState.ImageTile(
                resources.getString(R.string.async_url),
                resources.getString(R.string.debug_ui_redesign_image_tile_title)
            )
        )

        binding.cardImageTileInfo.setImageTileData(
            ImageTileState.ImageTileWithInfo(
                resources.getString(R.string.async_url1),
                resources.getString(R.string.debug_ui_redesign_image_tile_title),
                resources.getString(R.string.debug_ui_redesign_image_tile_info)
            )
        )

        binding.cardImageTileButton.apply {
            setImageTileData(
                ImageTileState.ImageTileWithButton(
                    resources.getString(R.string.async_error_url),
                    resources.getString(R.string.debug_ui_redesign_image_tile_title),
                    resources.getString(R.string.debug_ui_redesign_image_tile_action)
                )
            )
            setActionListener(ImageTileActionState.IMAGE_TILE_BUTTON) {
                Toast.makeText(context, "Action Clicked", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cardImageTileIconButton.apply {
            setImageTileData(
                ImageTileState.ImageTileWithStatus(
                    resources.getString(R.string.async_url),
                    resources.getString(R.string.debug_ui_redesign_image_tile_title),
                    resources.getString(R.string.debug_ui_redesign_image_tile_icon_action)
                )
            )
            setActionListener(ImageTileActionState.IMAGE_TILE_STATUS) {
                Toast.makeText(context, "Action Clicked", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cardImageTileList.apply {
            setImageTileData(
                ImageTileState.ImageTileWithListAction(
                    resources.getString(R.string.async_url1),
                    resources.getString(R.string.debug_ui_redesign_image_tile_title),
                    ai.offside.mobile.android.component.ui.R.drawable.ic_decorative_card_solid,
                    resources.getString(R.string.debug_ui_redesign_image_tile_list_item)
                )
            )
            setActionListener(ImageTileActionState.IMAGE_TILE_LIST) {
                Toast.makeText(context, "Action Clicked", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cardImageTileOverlay.setImageTileData(
            ImageTileState.ImageTileWithOverlay(
                resources.getString(R.string.async_url),
                resources.getString(R.string.debug_ui_redesign_image_tile_title),
                ai.offside.mobile.android.component.ui.R.drawable.ic_decorative_lock_solid
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}