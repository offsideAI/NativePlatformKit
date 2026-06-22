package ai.offside.mobile.android.helper.testlabs.nav.tile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import ai.offside.mobile.android.helper.testlabs.R
import ai.offside.mobile.android.helper.testlabs.databinding.FragmentTestUiRedesignFeatureTileDemoBinding

/**
 * A simple [Fragment] subclass.
 */
class TestUIRedesignFeatureTileDemoFragment : Fragment(R.layout.fragment_test_ui_redesign_feature_tile_demo) {

    var _binding: FragmentTestUiRedesignFeatureTileDemoBinding? = null
    val binding: FragmentTestUiRedesignFeatureTileDemoBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTestUiRedesignFeatureTileDemoBinding.bind(view)

        binding.simpleFeatureTile.setMoreMenuActionListener {
            Toast.makeText(context, "More Action Clicked", Toast.LENGTH_SHORT).show()
        }

        binding.simpleFeatureTile.setTileActionListener {
            Toast.makeText(context, "Feature Tile Action Clicked", Toast.LENGTH_SHORT).show()
        }

        binding.notificationFeatureTile.setMoreMenuActionListener {
            Toast.makeText(context, "More Action Clicked", Toast.LENGTH_SHORT).show()
        }

        binding.notificationFeatureTile.setTileActionListener {
            Toast.makeText(context, "Feature Tile Action Clicked", Toast.LENGTH_SHORT).show()
        }

        binding.notificationLargeFeatureTile.setMoreMenuActionListener {
            Toast.makeText(context, "More Action Clicked", Toast.LENGTH_SHORT).show()
        }

        binding.notificationLargeFeatureTile.setTileActionListener {
            Toast.makeText(context, "Feature Tile Action Clicked", Toast.LENGTH_SHORT).show()
        }

        binding.notificationFeatureTileAction.setTileActionListener {
            Toast.makeText(context, "Feature Tile Action Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}