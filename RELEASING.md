# Releasing

Run the **Release** workflow (`.github/workflows/release.yml`) on the branch to release. Leave the
version empty to use the next one, or enter it, e.g. `2026.09.00`.

Versions follow `YYYY.MM.xx`, where `xx` starts from `00` and goes up with each release in the month.
When the version is empty, the workflow takes the current year and month (UTC), and adds one to the
highest `xx` among the existing tags of that month, or uses `00` if there is none. It publishes the Nordic BOM and the Nordic version catalog in stages:

1. **Check** picks or validates the version, and that the tag does not exist yet.
2. **Tag** creates the tag and pushes it.
3. **Publish** signs and uploads both artifacts to Maven Central, and releases the staging repository.

With **Dry run** ticked nothing is tagged or published: the artifacts are only built and signed into
the local Maven repository of the runner (`./gradlew publishToMavenLocal`), which checks the signing
key and the secrets.

The **Deploy Nordic BOM** and **Deploy Nordic Version Catalog** workflows publish just one artifact,
from the latest existing tag. They need the full git history.

## How the version is set

The version of both artifacts is taken from (in this order):

1. the `releaseVersion` Gradle property, e.g. `-PreleaseVersion=2026.09.00`,
2. the `RELEASE_VERSION` environment variable,
3. the latest git tag (anything after `%` is ignored),
4. `0.0.0` if none of the above is available, e.g. in a clone without tags.

The **Release** workflow sets `RELEASE_VERSION`, so it does not need git tags or history. A missing
git or tag is not an error, it is only logged with `--info`.
