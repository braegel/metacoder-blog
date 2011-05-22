package de.metacoder.blog 

import xmlengine.Persister

object XMLBackend extends App {
	println("starting xml backend standalone app");		
	Persister start()
	val future = Persister !! "kaese"
	println(future.apply)

	Persister !! 'die
}
