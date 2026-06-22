package ai.offside.mobile.android.component.ui.transition.fade

import android.animation.TimeInterpolator
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionSet


/**
 * Elements that begin and end at rest use standard easing. They speed up quickly and slow down
 * gradually, in order to emphasize the end of the transition.
 */
internal val FAST_OUT_SLOW_IN: TimeInterpolator by lazy(LazyThreadSafetyMode.NONE) {
    PathInterpolatorCompat.create(
        SequentialTransitionSet.INTERPOLATOR_X1_COORDINATE,
        SequentialTransitionSet.INTERPOLATOR_Y1_COORDINATE,
        SequentialTransitionSet.INTERPOLATOR_X2_COORDINATE,
        SequentialTransitionSet.INTERPOLATOR_Y2_COORDINATE
    )
}

/**
 * Creates a transition like [androidx.transition.AutoTransition], but customized to be more
 * true to Material Design.
 *
 * Fade through involves one element fading out completely before a new one fades in. These
 * transitions can be applied to text, icons, and other elements that don't perfectly overlap.
 * This technique lets the background show through during a transition, and it can provide
 * continuity between screens when paired with a shared transformation.
 *
 * See
 * [Expressing continuity](https://github.com/android/animation-samples/tree/main/Motion/app/src/main/java/com/example/android/motion/demo)
 * for the detail of fade through.
 */
fun fadeThrough(): Transition {
    return transitionTogether {
        interpolator = FAST_OUT_SLOW_IN
        this += ChangeBounds()
        this += transitionSequential {
            addTransition(Fade(Fade.OUT))
            addTransition(Fade(Fade.IN))
        }
    }
}

/**
 * @param body The transition the fade out and in start together
 */
inline fun transitionTogether(crossinline body: TransitionSet.() -> Unit): TransitionSet {
    return TransitionSet().apply {
        ordering = TransitionSet.ORDERING_TOGETHER
        body()
    }
}

/**
 * The transition take Sequential, Fade out and then Fade in the views
 * @param body : Transition body
 */
inline fun transitionSequential(
    crossinline body: SequentialTransitionSet.() -> Unit
): SequentialTransitionSet {
    return SequentialTransitionSet().apply(body)
}

/**
 * @param action Transition set
 */
inline fun TransitionSet.forEach(action: (transition: Transition) -> Unit) {
    for (i in 0 until transitionCount) {
        action(getTransitionAt(i) ?: throw IndexOutOfBoundsException())
    }
}

/**
 * @param action Transition set looped through the index
 */
inline fun TransitionSet.forEachIndexed(action: (index: Int, transition: Transition) -> Unit) {
    for (i in 0 until transitionCount) {
        action(i, getTransitionAt(i) ?: throw IndexOutOfBoundsException())
    }
}

/**
 * @param transition : Transition to be concatenated in TransitionSet
 */
operator fun TransitionSet.plusAssign(transition: Transition) {
    addTransition(transition)
}

/**
 * @param index : index of the transition to get
 */
operator fun TransitionSet.get(index: Int): Transition {
    return getTransitionAt(index) ?: throw IndexOutOfBoundsException()
}

