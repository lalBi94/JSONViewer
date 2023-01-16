# COMMANDES
JAVA = java
JAVAC = javac
JAR = java -jar
JAVADOC = javadoc
OPTIONSDOCS = -d docs -noqualifier all
JAVAC_OPTIONS = -d build -Xlint:unchecked
EXT = .java

# CHEMINS
SRC = src/fr/sae/JSonInspector
BUILD = build/fr/sae/JSonInspector
DOCS = docs
CORE = Main

# CHEMINS RELATIF AU PROJET
EXCEPTION = ${SRC}/Exception/*.java
STORAGE = ${SRC}/Storage/*.java
GRAPHICS = ${SRC}/Graphics/*.java
SETTINGS = ${SRC}/Settings/*.java
MAIN = ${SRC}/*.java

.PHONY: clean docs run

run:
	make clean
	mkdir build/ && mkdir docs/
	${JAVAC} ${JAVAC_OPTIONS} ${SETTINGS} ${GRAPHICS} ${STORAGE} ${EXCEPTION} ${MAIN}
	make docs
	${JAR} cvfe JSonInspector.jar fr.sae.JSonInspector.Main -C build fr
	cd build && java fr.sae.JSonInspector.Main && cd ..

clean:
	rm -rf build && rm -rf docs

docs:
	rm -rf ${DOCS}/*
	${JAVADOC} ${OPTIONSDOCS} ${SETTINGS} ${GRAPHICS} ${STORAGE} ${EXCEPTION} ${MAIN}