package com.bazbatlabs.paint

import java.awt.Dimension
import swing._
import swing.FileChooser._
import swing.event._
import javax.swing.BorderFactory
import java.awt.{Graphics2D, Color}

object PaintApplication extends SimpleSwingApplication {
  val drawingPanel = new DrawingPanel

  val fileChooser = new FileChooser {
    multiSelectionEnabled = false
    fileSelectionMode = SelectionMode.FilesOnly
  }

  def top = new MainFrame {
    title = "paint"
    minimumSize = new Dimension(300, 300)

    menuBar = new MenuBar {
      contents += new Menu("File") {
        contents += new MenuItem("Open") {
          reactions += {
            case ButtonClicked(_) => {
              fileChooser.title = "Open File"

              val result = fileChooser.showOpenDialog(drawingPanel)
              if (result == Result.Approve) {
                drawingPanel.openImage(fileChooser.selectedFile)
              }
            }
          }
        }

        contents += new MenuItem("Save") {
          reactions += {
            case ButtonClicked(_) => {
              fileChooser.title = "Save File"

              val result = fileChooser.showSaveDialog(drawingPanel)
              if (result == Result.Approve) {
                drawingPanel.saveImage(fileChooser.selectedFile)
              }
            }
          }
        }
      }
    }

    contents = new BoxPanel(Orientation.Vertical) {
      border = BorderFactory.createLineBorder(Color.RED, 20)
      contents.append(drawingPanel)
    }
  }
}
