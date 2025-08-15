# Caminhos principais
SRC_DIR = src
BIN_DIR = bin
RES_DIR = res

# Encontra todos os arquivos .java
SOURCES := $(shell find $(SRC_DIR) -name "*.java")
# Gera a lista de .class correspondente
CLASSES := $(SOURCES:$(SRC_DIR)/%.java=$(BIN_DIR)/%.class)

# Nome do pacote/classe principal
MAIN_CLASS = main.Game

# Compilar tudo
$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	@mkdir -p $(dir $@)
	javac -d $(BIN_DIR) -sourcepath $(SRC_DIR) $<

compile: $(CLASSES)

# Rodar o jogo
run: compile
	java -cp "$(BIN_DIR):$(RES_DIR)" $(MAIN_CLASS)

# Limpar build
clean:
	rm -rf $(BIN_DIR)/*

.PHONY: compile run clean
