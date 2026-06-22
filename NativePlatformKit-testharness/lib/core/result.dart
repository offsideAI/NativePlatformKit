/// A minimal `Result` type used by services to return either a success value
/// or an error, without throwing across the MVVM boundary.
///
/// View models call services and translate [Ok]/[Err] into UI state.
library;

/// The outcome of a service call: either [Ok] with a value or [Err] with an error.
sealed class Result<T> {
  const Result();

  /// Pattern-match helper.
  R when<R>({
    required R Function(T value) ok,
    required R Function(Object error, StackTrace? stackTrace) err,
  });

  /// `true` when this is an [Ok].
  bool get isOk => this is Ok<T>;
}

/// A successful [Result] carrying [value].
final class Ok<T> extends Result<T> {
  /// Creates a successful result.
  const Ok(this.value);

  /// The success value.
  final T value;

  @override
  R when<R>({
    required R Function(T value) ok,
    required R Function(Object error, StackTrace? stackTrace) err,
  }) =>
      ok(value);
}

/// A failed [Result] carrying an [error] and optional [stackTrace].
final class Err<T> extends Result<T> {
  /// Creates a failed result.
  const Err(this.error, [this.stackTrace]);

  /// The error that occurred.
  final Object error;

  /// The stack trace, if available.
  final StackTrace? stackTrace;

  @override
  R when<R>({
    required R Function(T value) ok,
    required R Function(Object error, StackTrace? stackTrace) err,
  }) =>
      err(error, stackTrace);
}
