// Sets the project group and the version, which is taken from (in this order):
// 1. the `releaseVersion` Gradle property, e.g. -PreleaseVersion=2026.09.00,
// 2. the `RELEASE_VERSION` environment variable,
// 3. the latest git tag (anything after `%` is ignored),
// 4. `0.0.0` if none of the above is available, e.g. in a clone without tags.
group = "no.nordicsemi.gradle"

fun git(vararg args: String): String? = try {
    val output = providers.exec {
        commandLine("git", *args)
        isIgnoreExitValue = true
    }
    if (output.result.get().exitValue != 0) {
        logger.info("`git ${args.joinToString(" ")}` failed: ${output.standardError.asText.get().trim()}")
        null
    } else {
        output.standardOutput.asText.get().trim().ifEmpty { null }
    }
} catch (e: Exception) {
    logger.info("Could not run `git ${args.joinToString(" ")}`: ${e.message}")
    null
}

val explicitVersion = providers.gradleProperty("releaseVersion").orNull
    ?: providers.environmentVariable("RELEASE_VERSION").orNull

version = if (!explicitVersion.isNullOrBlank()) {
    explicitVersion.trim()
} else {
    // Version may have % to add additional information
    git("describe", "--tags", "--abbrev=0")?.split("%")?.first() ?: run {
        logger.info("No git tag found, using 0.0.0 as the version.")
        "0.0.0"
    }
}
