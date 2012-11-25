package com.bazbatlabs.paint

import java.awt.Dimension
import swing._
import swing.FileChooser._
import swing.event._
import javax.swing.BorderFactory
import java.awt.{Graphics2D, Color}

object PaintApplication extends SimpleSwingApplication {
  val fileChooser = new FileChooser

  def top = new MainFrame {
    title = "paint"
    minimumSize = new Dimension(300, 300)

    contents = new BoxPanel(Orientation.Vertical) {
      val drawingPanel = new DrawingPanel

      border = BorderFactory.createLineBorder(Color.RED, 20)

      contents.append(drawingPanel)
      contents.append(new Button("Save") {
        reactions += {
          case ButtonClicked(_) => {
            fileChooser.title = "Save File"
            fileChooser.multiSelectionEnabled = false
            fileChooser.fileSelectionMode = SelectionMode.FilesOnly

            val result = fileChooser.showSaveDialog(drawingPanel)
            if (result == Result.Approve) {
              drawingPanel.saveImage(fileChooser.selectedFile)
            }
          }
        }
      })
    }
  }
}
