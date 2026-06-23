/// Per-screen capture status.
enum CaptureStatus {
  /// Not yet captured.
  pending,

  /// Captured successfully.
  captured,

  /// Intentionally skipped.
  skipped,

  /// Capture attempted but failed.
  failed,
}

/// Parses a [CaptureStatus] from its name.
CaptureStatus captureStatusFromName(String? name) =>
    CaptureStatus.values.firstWhere((s) => s.name == name, orElse: () => CaptureStatus.pending);

/// The capture record for one screen within a run.
class ScreenRecord {
  /// Creates a screen record.
  ScreenRecord({
    required this.id,
    this.status = CaptureStatus.pending,
    this.notes,
    this.file,
    this.capturedAt,
  });

  /// Builds from JSON.
  factory ScreenRecord.fromJson(Map<String, dynamic> json) => ScreenRecord(
        id: json['id'] as String,
        status: captureStatusFromName(json['status'] as String?),
        notes: json['notes'] as String?,
        file: json['file'] as String?,
        capturedAt: json['capturedAt'] as String?,
      );

  /// Screen id (matches the catalog).
  final String id;

  /// Capture status.
  CaptureStatus status;

  /// Optional free-text note.
  String? notes;

  /// Relative screenshot path (within the run dir), if captured.
  String? file;

  /// ISO timestamp of capture.
  String? capturedAt;

  /// JSON form.
  Map<String, dynamic> toJson() => {
        'id': id,
        'status': status.name,
        if (notes != null) 'notes': notes,
        if (file != null) 'file': file,
        if (capturedAt != null) 'capturedAt': capturedAt,
      };
}

/// A capture run: device/app metadata + per-screen records. Serializable for resume + manifest.
class RunState {
  /// Creates a run state.
  RunState({
    required this.runId,
    required this.startedAt,
    required this.records,
    this.finishedAt,
    this.serial,
    this.avd,
    this.api,
    this.appVersionName,
    this.appGitSha,
  });

  /// Builds from JSON.
  factory RunState.fromJson(Map<String, dynamic> json) => RunState(
        runId: json['runId'] as String,
        startedAt: json['startedAt'] as String,
        finishedAt: json['finishedAt'] as String?,
        serial: json['serial'] as String?,
        avd: json['avd'] as String?,
        api: (json['api'] as num?)?.toInt(),
        appVersionName: json['appVersionName'] as String?,
        appGitSha: json['appGitSha'] as String?,
        records: {
          for (final r in (json['records'] as List<dynamic>))
            (r as Map<String, dynamic>)['id'] as String: ScreenRecord.fromJson(r),
        },
      );

  /// Run identifier (timestamp-based).
  final String runId;

  /// ISO start timestamp.
  final String startedAt;

  /// ISO finish timestamp (null while in progress).
  String? finishedAt;

  /// Target emulator serial.
  String? serial;

  /// AVD name.
  String? avd;

  /// Device API level.
  int? api;

  /// Playground versionName.
  String? appVersionName;

  /// Playground git short SHA.
  String? appGitSha;

  /// Records keyed by screen id.
  final Map<String, ScreenRecord> records;

  /// Whether the run is finished.
  bool get isFinished => finishedAt != null;

  /// Count of records in a given status.
  int countOf(CaptureStatus status) => records.values.where((r) => r.status == status).length;

  /// Total screens.
  int get total => records.length;

  /// Captured count.
  int get captured => countOf(CaptureStatus.captured);

  /// Skipped count.
  int get skipped => countOf(CaptureStatus.skipped);

  /// Failed count.
  int get failed => countOf(CaptureStatus.failed);

  /// Fraction handled (captured+skipped+failed) of total, 0..1.
  double get progress => total == 0 ? 0 : (captured + skipped + failed) / total;

  /// JSON form.
  Map<String, dynamic> toJson() => {
        'runId': runId,
        'startedAt': startedAt,
        if (finishedAt != null) 'finishedAt': finishedAt,
        if (serial != null) 'serial': serial,
        if (avd != null) 'avd': avd,
        if (api != null) 'api': api,
        if (appVersionName != null) 'appVersionName': appVersionName,
        if (appGitSha != null) 'appGitSha': appGitSha,
        'records': [for (final r in records.values) r.toJson()],
      };
}
