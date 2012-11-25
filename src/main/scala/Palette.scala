package com.bazbatlabs.paint

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color}
import javax.imageio.ImageIO

import java.io.File
import swing._
import swing.event._

class Palette extends Panel {

  var currentColor = Color.black

  preferredSize = new Dimension(100, 50)
  minimumSize = new Dimension(100, 50)
  maximumSize = new Dimension(100, 50)

  listenTo(mouse.clicks)
  reactions += {
    case e: MouseClicked => chooseColor(e.point.x, e.point.y)
  }

  def chooseColor(x: Int, y: Int) {
    val width = size.width
    val height = size.height

    if (x < width / 2) {
      currentColor = Color.red
    } else {
      currentColor = Color.green
    }
  }

  override def paint(g: Graphics2D) {
    val width = size.width
    val height = size.height

    g.clearRect(0, 0, width, height)

    g.setColor(Color.red)
    g.fillRect(0, 0, width / 2, height)

    g.setColor(Color.green)
    g.fillRect(width / 2, 0, width / 2, height)
  }
}
