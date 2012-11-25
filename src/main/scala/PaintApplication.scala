package com.bazbatlabs.paint

import java.awt.Dimension
import swing._
import swing.event._

object PaintApplication extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "paint"
    minimumSize = new Dimension(300, 300)

    contents = new BoxPanel(Orientation.Vertical) {
      lazy val drawingPanel = new DrawingPanel {
        listenTo(mouse.clicks)
        reactions += {
          case e: MouseClicked => {
            this.setPixel(e.point.x, e.point.y)
          }
        }
      }

      contents.append(drawingPanel)
      contents.append(new Button("Save") {
        reactions += {
          case ButtonClicked(_) => drawingPanel.saveImage()
        }
      })
    }
  }
}
