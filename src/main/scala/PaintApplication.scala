package com.bazbatlabs.paint

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel
import javax.swing.{UIManager}

import java.awt.Dimension
import swing._
import swing.FileChooser._
import swing.event._
import javax.swing.BorderFactory
import java.awt.{Graphics2D, Color}

object PaintApplication extends SimpleSwingApplication {
  for (style: UIManager.LookAndFeelInfo <- UIManager.getInstalledLookAndFeels()) {
    if ("Nimbus".equals(style.getName())) {
      UIManager.setLookAndFeel(style.getClassName())
    }
  }

  val palette = new Palette
  val drawingPanel = new DrawingPanel(palette)

  val fileChooser = new FileChooser {
    multiSelectionEnabled = false
    fileSelectionMode = SelectionMode.FilesOnly
  }

  def top = new MainFrame {
    val window = this
    minimumSize = new Dimension(300, 300)

    menuBar = new MenuBar {
      contents += new Menu("File") {
        contents += new MenuItem("New") {
          reactions += {
            case ButtonClicked(_) => {
              new NewImageDialog(drawingPanel, window).show
            }
          }
        }

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
      contents.append(drawingPanel)
      contents.append(palette)
    }
  }
}
