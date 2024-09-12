package com.jakewharton.diffuse.report.html

import com.jakewharton.diffuse.format.Apk
import com.jakewharton.diffuse.format.ArchiveFile
import com.jakewharton.diffuse.info.toSummaryTable
import com.jakewharton.diffuse.report.Report
import com.jakewharton.diffuse.report.toSummaryString
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.p
import kotlinx.html.stream.appendHTML

internal class ApkInfoHtmlReport(
  private val apk: Apk,
) : Report {
  override fun write(appendable: Appendable) {
    appendable.appendHTML().html {
      head { applyStyles() }

      body {
        p { +"${apk.filename} (signature: ${apk.signatures.toSummaryString()})" }

        toSummaryTable(
          "APK",
          apk.files,
          ArchiveFile.Type.APK_TYPES,
          skipIfEmptyTypes = setOf(ArchiveFile.Type.Native),
        )

        br()
        toSummaryTable(apk.dexes)
        br()
        toSummaryTable(apk.arsc)
      }
    }
  }

  override fun toString() = buildString { write(this) }
}