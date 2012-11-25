package com.bazbatlabs.paint

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color}
import javax.imageio.ImageIO
import java.io.File
import swing._
import swing.event._

class DrawingPanel extends Panel {
  val width = 64
  val height = 64
  var scale = 1.0
  val image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

  listenTo(mouse.clicks, mouse.wheel)

  reactions += {
    case e: MouseClicked => {
      this.setPixel(e.point.x, e.point.y)
    }
    case e: MouseWheelMoved => {
      e.rotation match {
        case 1 => scale *= 0.8
        case -1 => scale *= 1.2
      }
      repaint
    }
  }

  override def paint(g: Graphics2D) {
    g.clearRect(0, 0, size.width, size.height)
    g.drawImage(image, 0, 0, (width * scale).toInt, (height * scale).toInt, null)
  }

  def setPixel(x: Int, y: Int) {
    image.setRGB(x, y, Color.RED.getRGB())
    repaint
  }

  def saveImage() {
    ImageIO.write(image, "png", new File("foo.png"))
  }
}
