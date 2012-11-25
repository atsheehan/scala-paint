package com.bazbatlabs.paint

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color}
import javax.imageio.ImageIO

import java.io.File
import swing._
import swing.event._

class NewImageDialog(val drawingPanel: DrawingPanel, owner: Window) extends Dialog(owner) {
  val dialog = this

  title = "New Image"
  modal = true
  visible = false


  contents = new GridPanel(3, 2) {
    val widthField = new TextField
    val heightField = new TextField


    minimumSize = new Dimension(240, 80)
    preferredSize = new Dimension(240, 80)
    maximumSize = new Dimension(240, 80)

    hGap = 6
    vGap = 4

    contents += new Label("Width")
    contents += widthField
    contents += new Label("Height")
    contents += heightField
    contents += new Button("Cancel") {
      reactions += {
        case ButtonClicked(_) => dialog.visible = false
      }
    }

    contents += new Button("OK") {
      reactions += {
        case ButtonClicked(_) => {
          drawingPanel.newImage(widthField.text.toInt, heightField.text.toInt)
          dialog.visible = false
        }
      }
    }
  }

  def show {
    setLocationRelativeTo(owner)
    visible = true
  }
}
