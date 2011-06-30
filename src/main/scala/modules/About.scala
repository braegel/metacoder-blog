package de.metacoder.blog.modules

class About extends Renderable {

  val matchingRule = IdMatchingRule("mainContentModule")

  onRender {
    case _ => {
      <div>
        <h2>&Uuml;ber die Autoren</h2>
        <h3>Benjamin Neff</h3>
        <p>for the lulz</p>
        <h3>Felix Becker</h3>
        <p>schinken</p>
      </div>
    }
  }
}