package com.bazbatlabs.paint

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color}
import swing._

class DrawingPanel extends Panel {
  val image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB)

  override def paint(g: Graphics2D) {
    g.drawImage(image, 0, 0, null)
  }

  def setPixel(x: Int, y: Int) {
    image.setRGB(x, y, Color.RED.getRGB())
    repaint
  }
}
