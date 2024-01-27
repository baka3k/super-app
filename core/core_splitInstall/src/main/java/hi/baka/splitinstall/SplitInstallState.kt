package hi.baka.splitinstall


/**
 * Represents the States in [hi.baka.splitinstall.SplitInstallCompose].
 */
enum class SplitInstallState {
    /**
     * The dynamic feature is not installed and needs to be download. This state represents that the
     * user needs to be prompted to grant the permission to download.
     */
    RequestDownload,

    /**
     * The dynamic feature is downloading.
     */
    Downloading,

    /**
     * The dynamic feature is ready to be opened.
     */
    FeatureReady,
}
