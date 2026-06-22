package ai.offside.mobile.android.component.ui.transition.fade

import android.animation.TimeInterpolator
import androidx.transition.Transition
import androidx.transition.TransitionSet

/**
 * Runs multiple [Transition]s sequentially.
 *
 * Setting a duration to this set will distribute the duration to each child transition based on its
 * weight. Interpolator is also segmented and applied to the transitions.
 *
 * (Background) A normal [TransitionSet] simply sets its properties to child transitions as they
 * are. This can be problematic for sequential transition sets. For example, setting a duration of
 * 300ms means that the entire duration will be the multiple of 300ms and the child count.
 */
class SequentialTransitionSet : TransitionSet() {

    init {
        ordering = ORDERING_SEQUENTIAL
    }

    private var _duration: Long = -1
    private var _interpolator: TimeInterpolator? = null

    private val weights = mutableListOf<Float>()

    /**
     * This adds to the transition set, like fade out and in, then the transition animation takes place in sequential order
     * @param transition : The transition like Fade-IN or Fade-OUT
     */
    override fun addTransition(transition: Transition): TransitionSet {
        super.addTransition(transition)
        weights += 1f
        distributeDuration()
        distributeInterpolator()
        return this
    }

    /**
     * @param duration : Duration of the transition
     */
    override fun setDuration(duration: Long): TransitionSet {
        // Don't call super.
        _duration = duration
        distributeDuration()
        return this
    }

    /**
     * @return duration required for the transition
     */
    override fun getDuration(): Long {
        return _duration
    }

    /**
     * Distribute the duration to each child transition based on its weight.
     */
    private fun distributeDuration() {
        if (_duration < 0) {
            forEach { transition ->
                transition.duration = -1
            }
            return
        }
        val totalWeight = weights.sum()
        forEachIndexed { i, transition ->
            transition.duration = (_duration * weights[i] / totalWeight).toLong()
        }
    }

    /**
     * Interpolator for the transition, for fade animation "FAST_OUT_SLOW_IN_INTERPOLATOR"
     */
    override fun setInterpolator(interpolator: TimeInterpolator?): TransitionSet {
        // Don't call super.
        _interpolator = interpolator
        distributeInterpolator()
        return this
    }

    /**
     * @return TimeInterpolator added for the transition
     */
    override fun getInterpolator(): TimeInterpolator? {
        return _interpolator
    }

    /**
     * Interpolator is also segmented and applied to the transitions based on each child transition weight
     */
    private fun distributeInterpolator() {
        val interpolator = _interpolator
        if (interpolator == null) {
            forEach { transition ->
                transition.interpolator = null
            }
            return
        }
        val totalWeight = weights.sum()
        var start = 0f
        forEachIndexed { i, transition ->
            val range = weights[i] / totalWeight
            transition.interpolator = SegmentInterpolator(
                interpolator,
                start,
                start + range
            )
            start += range
        }
    }

    companion object {
        const val INTERPOLATOR_X1_COORDINATE = 0.4f
        const val INTERPOLATOR_X2_COORDINATE = 0.2f
        const val INTERPOLATOR_Y1_COORDINATE = 0f
        const val INTERPOLATOR_Y2_COORDINATE = 1f
    }
}