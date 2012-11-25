package com.bazbatlabs.paint

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color}
import javax.imageio.ImageIO
import java.io.File
import swing._
import swing.event._

class DrawingPanel extends Panel {
  val imageWidth = 64
  val imageHeight = 64
  var scale = 1.0
  val image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB)

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
    val (screenWidth, screenHeight) = (size.width, size.height)
    g.clearRect(0, 0, screenWidth, screenHeight)

    val scaledImageWidth = (imageWidth * scale).toInt
    val scaledImageHeight = (imageWidth * scale).toInt

    g.drawImage(image, (screenWidth - scaledImageWidth) / 2, (screenHeight - scaledImageHeight) / 2,
                scaledImageWidth, scaledImageHeight, null)
  }

  def setPixel(x: Int, y: Int) {
    image.setRGB(x, y, Color.RED.getRGB())
    repaint
  }

  def saveImage() {
    ImageIO.write(image, "png", new File("foo.png"))
  }
}
