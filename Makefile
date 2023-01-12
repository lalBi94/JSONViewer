# COMMANDES
JAVA = java
JAVAC = javac
JAVADOC = javadoc
OPTIONSDOCS = -d docs -private -public -protected -noqualifier all
OPTIONS = -d build -Xlint:unchecked -Xlint:deprecation
EXT = .java

# CHEMINS
SRC = src/
BUILD = build/
DOCS = docs/
CORE = Core
GraphicsPACKAGE = Graphics/
GraphicsPackageType = Graphics/Type/
ConsolePACKAGE = Console/

# POUR ALLER PLUS VITE
ALL = $(SRC)*$(EXT) $(SRC)$(ConsolePACKAGE)*$(EXT) $(SRC)$(GraphicsPACKAGE)*$(EXT) $(SRC)$(GraphicsPackageType)*$(EXT)

# LE FICHIER JSON (Pour mon test)
JSON = /home/bilal-linux/toFormat.json

.PHONY: console graphics clean docs

console:
	make clean
	mkdir build && mkdir docs
	$(JAVAC) $(OPTIONS) $(ALL)
	cd build && $(JAVA) $(CORE) $(JSON) && cd ..

graphics:
	make clean
	mkdir build && mkdir docs
	$(JAVAC) $(OPTIONS) $(ALL)
	cd build && $(JAVA) $(CORE) && cd ..

clean:
	rm -rf $(BUILD) && rm -rf $(DOCS)

docs:
	rm -rf $(BUILD)*
	$(JAVADOC) $(OPTIONSDOCS) $(ALL)