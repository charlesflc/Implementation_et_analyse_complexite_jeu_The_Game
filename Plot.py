import pandas as pd 
import numpy as np
import matplotlib.pyplot as plt
import sys

#Recuperer les arguments de la ligne de commande
if(len(sys.argv) != 3):
    print("Erreur: il faut 2 arguments : le nom du fichier et le titre du graphique")
    sys.exit(1)

filename = sys.argv[1]
plottitle = sys.argv[2]


#Ouvrir le fichier
#print("Ouverture du fichier : " + filename)
data = pd.read_csv(filename,sep=";",header=None)

#Afficher les 5 premières lignes
#Definir les noms des colonnes
data.columns = ["tour", "points"]

#print("Affichage des 5 premières lignes")
#print(data.head())


nombre_de_barres = len(data["points"].value_counts())
largeur_par_barre = 0.3  # Ajuster selon la largeur désirée pour chaque barre
largeur_fig = max(min(nombre_de_barres * largeur_par_barre, 40), 10)  # Minimum de 10 et maximum de 40 pouces
plt.figure(figsize=(largeur_fig, 6))  # Hauteur fixe de 6 pouces


data["points"].value_counts(normalize=True).sort_index().plot(kind="bar",color="darkorange")

plt.title(plottitle)

# Calculer la moyenne des points
texte_moyenne = "Moyenne des points : " + str(data["points"].mean().round(2))
texte_mediane = "Médiane des points : " + str(data["points"].median().round(2))
texte_pourcentage_reussite = ""


if 98 in data["points"].value_counts().index:
    texte_pourcentage_reussite = "Pourcentage de parties avec score de 98 : " + str((data["points"].value_counts(normalize=True).loc[98]*100).round(2)) + "%"
else:
    texte_pourcentage_reussite = "Pas de parties avec score de 98"


plt.figtext(0.5, -0.00, texte_mediane, ha="center", fontsize=10)
plt.figtext(0.5, -0.05, texte_moyenne, ha="center", fontsize=10)
plt.figtext(0.5, -0.10, texte_pourcentage_reussite, ha="center", fontsize=10)
plt.subplots_adjust(bottom=0.15) 
plt.savefig(plottitle+'.png', dpi=300, bbox_inches='tight')
