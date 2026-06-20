<!--
  Copyright 2026 The NativePlatformKit Authors
  Licensed under the Apache License, Version 2.0. See the LICENSE file.
-->
# Publishing

NativePlatformKit publishes to **Maven Central via the Central Portal** using the
[`com.vanniktech.maven.publish`](https://vanniktech.github.io/gradle-maven-publish-plugin/) plugin
(configured by the `npk.publish` convention plugin).

> The old OSSRH flow (`oss.sonatype.org` / `s01` / Nexus staging) reached end-of-life on
> **2025-06-30** and is **not** used. See [ADR-0010](../DECISIONS.md).

Coordinates: **`das.arunabh.nativeplatformkit:nativeplatformkit:<version>`**.

## What gets published

`./gradlew :nativeplatformkit:publish` (or the Central Portal task) uploads, for the `release`
variant:

- the AAR,
- a **sources** jar,
- a **Dokka-generated Javadoc** jar,
- a complete POM (name, description, URL, license, SCM, developer) — populated from
  `gradle.properties` (`GROUP`, `VERSION_NAME`, `POM_*`),
- GPG signatures for all of the above (release builds only).

## Versions: SNAPSHOT vs release

Version is centralized in [`gradle.properties`](../gradle.properties) as `VERSION_NAME`.

- Ends with `-SNAPSHOT` → published to the snapshots repository; **signing is skipped**.
- Otherwise → a release; **signing is required** and the deployment is created in the Central
  Portal (it is **not** auto-released — a human promotes it).

`publishToMavenLocal` always works with no credentials (SNAPSHOTs/local skip signing):

```bash
./gradlew publishToMavenLocal
ls ~/.m2/repository/das/arunabh/nativeplatformkit/nativeplatformkit/
```

## One-time setup

### 1. Central Portal account + namespace

1. Create an account at <https://central.sonatype.com>.
2. Register and verify the namespace **`das.arunabh`** (DNS TXT record or the GitHub-org method).
3. Under **Account → Generate User Token**, generate a token. You get a **username** and
   **password** pair — these are *not* your portal login.

### 2. GPG signing key

```bash
# Generate a key (RSA 4096):
gpg --full-generate-key

# Find the key id and publish the public key so Central can verify signatures:
gpg --list-secret-keys --keyid-format=long
gpg --keyserver keyserver.ubuntu.com --send-keys <KEY_ID>

# Export the secret key in the in-memory ASCII-armored format the plugin expects:
gpg --armor --export-secret-keys <KEY_ID>
```

### 3. Credentials — environment variables only (never commit)

The vanniktech plugin reads these Gradle properties; provide them as **environment variables** with
the `ORG_GRADLE_PROJECT_` prefix (so nothing lands in a file):

| Gradle property | Environment variable | Value |
|---|---|---|
| `mavenCentralUsername` | `ORG_GRADLE_PROJECT_mavenCentralUsername` | Central Portal **token** username |
| `mavenCentralPassword` | `ORG_GRADLE_PROJECT_mavenCentralPassword` | Central Portal **token** password |
| `signingInMemoryKey` | `ORG_GRADLE_PROJECT_signingInMemoryKey` | ASCII-armored secret key (from `gpg --armor --export-secret-keys`) |
| `signingInMemoryKeyPassword` | `ORG_GRADLE_PROJECT_signingInMemoryKeyPassword` | Passphrase for that key |

> `local.properties`, keystores, and `*.gpg` are git-ignored as defense in depth. **Never** commit
> credentials or keys.

Local release dry run:

```bash
export ORG_GRADLE_PROJECT_mavenCentralUsername=...
export ORG_GRADLE_PROJECT_mavenCentralPassword=...
export ORG_GRADLE_PROJECT_signingInMemoryKey="$(gpg --armor --export-secret-keys <KEY_ID>)"
export ORG_GRADLE_PROJECT_signingInMemoryKeyPassword=...

./gradlew publishToMavenCentral --no-configuration-cache
```

Then visit <https://central.sonatype.com/publishing/deployments> to review and **Publish** the
deployment.

## Releasing via CI (recommended)

A push of a `v*` git tag triggers `.github/workflows/publish.yml`, which builds, runs all checks,
and publishes — gated on success. Store the four values above as GitHub **Actions secrets**:

`MAVEN_CENTRAL_USERNAME`, `MAVEN_CENTRAL_PASSWORD`, `SIGNING_IN_MEMORY_KEY`,
`SIGNING_IN_MEMORY_KEY_PASSWORD` (the workflow maps them to the `ORG_GRADLE_PROJECT_*` vars).

### Cutting a release

1. Set `VERSION_NAME` in `gradle.properties` to the release version (e.g. `1.0.0`), update
   [CHANGELOG.md](../CHANGELOG.md), and merge.
2. Tag and push:
   ```bash
   git tag v1.0.0 && git push origin v1.0.0
   ```
3. CI publishes the deployment to the Central Portal.
4. Promote the deployment in the portal (auto-release is intentionally off).
5. Bump `VERSION_NAME` to the next `-SNAPSHOT` (e.g. `1.1.0-SNAPSHOT`) on `main`.
