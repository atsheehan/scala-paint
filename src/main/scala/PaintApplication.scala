package com.bazbatlabs.paint

import java.awt.Dimension
import swing._
import swing.event._

object PaintApplication extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "paint"
    minimumSize = new Dimension(300, 300)

    contents = new BoxPanel(Orientation.Vertical) {
      val drawingPanel = new DrawingPanel

      contents.append(drawingPanel)
      contents.append(new Button("Save") {
        reactions += {
          case ButtonClicked(_) => drawingPanel.saveImage()
        }
      })
    }
  }
}
