# COMMANDES
JAVA = java
JAVAC = javac
JAR = jar cvfe
JAR_OPTIONS = -jar
NOM_JAR = JSonInspector.jar
JAVADOC = javadoc
OPTIONSDOCS = -d docs -noqualifier all -Xdoclint:none
JAVAC_OPTIONS = -d build -Xlint:unchecked
EXT = .java

# CHEMINS
SRC = src/fr/sae/JSonInspector
BUILD = build/fr/sae/JSonInspector
PACKAGE = fr.sae.JSonInspector
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
	${JAR} ${NOM_JAR} ${PACKAGE}.Main -C build fr
	make docs

clean:
	rm -rf build && rm -rf docs

docs:
	rm -rf ${DOCS}/*
	${JAVADOC} ${OPTIONSDOCS} ${SETTINGS} ${GRAPHICS} ${STORAGE} ${EXCEPTION} ${MAIN}