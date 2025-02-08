# Définir une variable contenant une liste de tous les fichiers .java
JAVA_FILES := $(wildcard *.java) $(wildcard */*.java) 

# La cible principale
all: Projet

# La règle pour construire myprogram
# Dépend de tous les fichiers .java trouvés
Projet: $(JAVA_FILES)
	@echo "Compilation des fichiers Java..."

	 javac *.java */*.java	

	@echo "Compilation terminée."
	@echo "\nCréation de l'archive JAR..."
	jar cfe Projet.jar Simulation *.class */*.class 
	
	@echo "Archive JAR créée."


clean:
	rm -f *.class */*.class Projet.jar
	@echo "Nettoyage terminé."