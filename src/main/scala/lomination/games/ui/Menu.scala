// package lomination.games.ui

// import org.jline.terminal.TerminalBuilder

// object Menu:
//   def create: Unit =
//     val window = Window(
//       Column("game", "Tic Tac Toe"),
//       Column("player 1", "User", "Minimax"),
//       Column("player 2", "User", "Minimax")
//     )

// object Window:
//   def height =
//     columns.map(_.height).max
//   def width =
//     columns.map(_.width).reduce((a, b) => a + b) + 2 * columns.length

//   def draw: Unit =
//     // Print titles
//     columns.foreach { case c =>
//       val spaces = c.width + 2 - c.title.length
//       print(" " * (spaces / 2) + c.title.toUpperCase + " " * (spaces - spaces / 2))
//     }
//     // Print columns
//     (0 until height - 1).foreach(i => columns.foreach(c =>
//       if i >= c.height then
//         print(" " * (c.width + 2))
//       else
//         print(" " + c.options(i) + " ")
//       ))

//     // val terminal = TerminalBuilder.builder().system(true).build()

//     // while (true) {
//     //
//     // }

//   def run(cursorCol: Int, cursorRow: Int, columns: Seq[Column]): Unit
//     draw
//     terminal.reader().read() match {
//       case 65 => println("Up")
//       case 66 => println("Down")
//       case 67 => println("Right")
//       case 68 => println("Left")
//       case _  => println(s"Other: $ch")
//     }

// case class Column(title: String, options: String*):
//   def height = 1 + options.length
//   def width = (options :+ title).map(_.length).max
