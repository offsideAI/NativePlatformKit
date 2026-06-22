import 'package:flutter/cupertino.dart';
import 'package:macos_ui/macos_ui.dart';

/// A polished placeholder shown inside a section's [ContentArea] until that
/// section's milestone is implemented. Establishes the visual language and the
/// feature-first structure without claiming functionality that isn't built yet.
class PlaceholderContent extends StatelessWidget {
  /// Creates a placeholder for a section.
  const PlaceholderContent({
    required this.icon,
    required this.title,
    required this.milestone,
    required this.summary,
    super.key,
  });

  /// Large glyph representing the section.
  final IconData icon;

  /// Section title.
  final String title;

  /// The milestone/epic that will implement this section (e.g. `M2 · Epic E2`).
  final String milestone;

  /// One-line description of what the section will do.
  final String summary;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    return Center(
      child: ConstrainedBox(
        constraints: const BoxConstraints(maxWidth: 460),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            MacosIcon(icon, size: 56, color: theme.primaryColor),
            const SizedBox(height: 16),
            Text(title, style: theme.typography.largeTitle),
            const SizedBox(height: 8),
            Text(
              summary,
              textAlign: TextAlign.center,
              style: theme.typography.body.copyWith(
                color: theme.typography.body.color?.withValues(alpha: 0.7),
              ),
            ),
            const SizedBox(height: 20),
            _MilestoneBadge(label: milestone),
          ],
        ),
      ),
    );
  }
}

class _MilestoneBadge extends StatelessWidget {
  const _MilestoneBadge({required this.label});

  final String label;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
      decoration: BoxDecoration(
        color: theme.primaryColor.withValues(alpha: 0.12),
        borderRadius: BorderRadius.circular(8),
      ),
      child: Text(
        label,
        style: theme.typography.caption1.copyWith(
          color: theme.primaryColor,
          fontWeight: FontWeight.w600,
        ),
      ),
    );
  }
}
