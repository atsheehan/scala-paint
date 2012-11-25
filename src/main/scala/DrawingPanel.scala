package com.bazbatlabs.paint

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color}
import javax.imageio.ImageIO

import java.io.File
import swing._
import swing.event._

class DrawingPanel extends Panel {
  var imageWidth = 64
  var imageHeight = 64
  var scale = 1.0
  var image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB)
  var dragging = false



  listenTo(mouse.clicks, mouse.wheel, mouse.moves)

  reactions += {
    case e: MousePressed => startDrag(e.point.x, e.point.y)
    case e: MouseDragged => drag(e.point.x, e.point.y)
    case _: MouseReleased => releaseDrag()
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
    val scaledImageWidth = (imageWidth * scale).toInt
    val scaledImageHeight = (imageWidth * scale).toInt
    val startX = (screenWidth - scaledImageWidth) / 2
    val startY = (screenHeight - scaledImageHeight) / 2

    g.clearRect(0, 0, screenWidth, screenHeight)
    g.drawImage(image, startX, startY, scaledImageWidth, scaledImageHeight, null)
  }

  def startDrag(screenX: Int, screenY: Int) {
    dragging = true
    setPixel(screenX, screenY)
  }

  def drag(screenX: Int, screenY: Int) {
    if (dragging) {
      setPixel(screenX, screenY)
    }
  }

  def releaseDrag() {
    dragging = false
  }

  def setPixel(screenX: Int, screenY: Int) {
    val (screenWidth, screenHeight) = (size.width, size.height)
    val scaledImageWidth = (imageWidth * scale).toInt
    val scaledImageHeight = (imageWidth * scale).toInt
    val startX = (screenWidth - scaledImageWidth) / 2
    val startY = (screenHeight - scaledImageHeight) / 2

    val actualX = ((screenX - startX) / scale).toInt
    val actualY = ((screenY - startY) / scale).toInt

    if (actualX >= 0 && actualX < imageWidth &&
        actualY >= 0 && actualY < imageHeight) {
      image.setRGB(actualX, actualY, Color.RED.getRGB())
    }

    repaint
  }

  def saveImage(file: File) {
    ImageIO.write(image, "png", file)
  }

  def openImage(file: File) {
    image = ImageIO.read(file)
    imageWidth = image.getWidth
    imageHeight = image.getHeight

    repaint
  }
}
