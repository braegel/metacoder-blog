package de.metacoder.blog.sandbox

/**
 * Created by IntelliJ IDEA.
 * Author: fbe und SuperTux88
 * Date: 11.06.11
 * Time: 23:12
 */

object Sandbox extends App {
  println("oh, hai")

  def getCounter(start : Int) : (Int) => Unit = {
    var x : Int = start
    println("getCounter")

    {
      (i : Int) => println("oh hai: " + x)
      x += 1
      println("i = " + i)
    }
  }

  val counter = getCounter(1)
  val counter2 = getCounter(7)
  counter(1)
  counter2(2)
  counter(3)
  counter2(4)
  counter(5)
  counter2(6)
}