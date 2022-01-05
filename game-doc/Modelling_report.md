# Modelling Report

Romain HU 

Dakini MALLAM GARBA



# Diagramme de classes

![](Diagrams/classes.svg)

Pour la classe MoveBlock, le block à déplacer est dans le paramètre du constructeur





# Routes REST

**[REST_MAP]**


verbe : GET



URI : /map



 exemple de sortie Json:

{

"mapList" : ["map1","map2","map3"]

}



**[REST_GET_SCORE]**



verbe : GET



URI : /score/{map}/



exemple de sortie Json : 

{
	"mapName" : "map",

​	"scoreList" : [5,4,3,2,1]

}





**[REST_ADD_SCORE]**

verb : POST



URI : /score/{map}/{player}/{newScore}



exemple de sortie Json :

{ "mapName" : "map",

  "playerName" : "player",

  "Score" : newScore

}



# Diagrammes de séquences

### Ajouter un nouveau bloc sur la Map

![](Diagrams/putBlock.svg)

### Déplacer un bloc

![](Diagrams/moveBlock.svg)

# Diagrammes de séquence REST

### Ajout d'une nouvelle Map

![](Diagrams/REST1.svg)

### Retourner la liste des Maps du jeu

![](Diagrams/REST2.svg)





# Mock-up

### Page d'accueil

![](Mockup/Home_Page.png)

### Page du jeu

![](Mockup/Game_Page.png)

### Partie terminée

![](Mockup/Loser_Page.png)

### Page des scores

![](Mockup/Records_Page.png)

### Quitter le jeu

![](Mockup/Exit_Page.png)