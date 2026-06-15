/*
 * Copyright (c) 2025, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list
 * of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be
 * used to endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

plugins {
    `java-platform`
    `maven-publish`
    signing
}
apply(from = "../gradle/git-tag-version.gradle")

val getVersionNameFromTags: groovy.lang.Closure<String> by ext

group = "no.nordicsemi.android"
version = getVersionNameFromTags()

dependencies {
    constraints {
        api(libs.compat.scanner)
        api(libs.ble)
        api(libs.ble.ktx)
        api(libs.ble.common)
        api(libs.blek.client.core)
        api(libs.blek.client.core.android)
        api(libs.blek.client.android)
        api(libs.blek.client.android.mock)
        api(libs.blek.advertiser.core)
        api(libs.blek.advertiser.core.android)
        api(libs.blek.advertiser.android)
        api(libs.blek.advertiser.android.mock)
        api(libs.blek.environment.android)
        api(libs.blek.environment.android.mock)
        api(libs.blek.environment.android.compose)
        api(libs.dfu)
        api(libs.mcumgr.core)
        api(libs.mcumgr.ble)
        api(libs.log)
        api(libs.log.timber)
        api(libs.kotlin.id)
        api(libs.kotlin.log)
        api(libs.kotlin.data)
        api(libs.core)
        api(libs.ui)
        api(libs.theme)
        api(libs.scanner.ble)
        api(libs.analytics)
        api(libs.navigation)
        api(libs.logger)
        api(libs.permissions.nfc)
        api(libs.permissions.ble)
        api(libs.permissions.internet)
        api(libs.permissions.wifi)
        api(libs.permissions.notification)
        api(libs.wifi.ble)
        api(libs.wifi.softap)
        api(libs.wifi.nfc)
        api(libs.memfault)
    }
}

publishing {
    publications {
        create<MavenPublication>("bom") {
            from(components["javaPlatform"])

            pom {
                name.set("Nordic BOM for Android")
                description.set("The platform BOM with Android libraries by Nordic.")
                url.set("https://github.com/NordicSemiconductor/Android-Version-Catalog")

                // https://maven.apache.org/pom.html#licenses
                licenses {
                    license {
                        name.set("BSD-3-Clause")
                        url.set("http://opensource.org/licenses/BSD-3-Clause")
                        distribution.set("repo")
                    }
                }

                // https://maven.apache.org/pom.html#scm
                scm {
                    url.set("https://github.com/NordicSemiconductor/Android-Version-Catalog")
                    connection.set("scm:git@github.com:NordicSemiconductor/Android-Version-Catalog.git")
                    developerConnection.set("scm:git@github.com:NordicSemiconductor/Android-Version-Catalog.git")
                }

                // https://maven.apache.org/pom.html#organization
                organization {
                    name.set("Nordic Semiconductor ASA")
                    url.set("https://www.nordicsemi.com")
                }

                // https://maven.apache.org/pom.html#developers
                developers {
                    developer {
                        id.set("mag")
                        name.set("Mobile Applications Group")
                        email.set("mag@nordicsemi.no")
                        organization.set("Nordic Semiconductor ASA")
                        organizationUrl.set("https://www.nordicsemi.com")
                    }
                }
            }
        }
    }
}

ext["signing.keyId"] = System.getenv("GPG_SIGNING_KEY")
ext["signing.password"] = System.getenv("GPG_PASSWORD")
ext["signing.secretKeyRingFile"] = "../sec.gpg"

signing {
    sign(publishing.publications)
}