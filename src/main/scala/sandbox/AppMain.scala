package de.metacoder.blog.sandbox

import de.metacoder.blog.util.Logging
import de.metacoder.blog.xmlengine.Persister

object AppMain extends App with Logging {
	logger info "Starting XML Backend standalone app"
	Persister start()
	val future = Persister !! 'load
	logger trace future.apply
	Persister !! 'persist
	Persister !! 'die
}
