## Technisch ontwerp


### Analyse
Het functioneel ontwerp roept verschillende vragen op technisch vlak op. Dit document gaat hier duidelijkheid
over geven hoe het aan de technische kant moet verlopen. Er zijn verschillende dynamische objecten en ook 
statische objecten. Bepaalde objecten moeten op elkaar reageren als ze tegen elkaar aan komen.

#### Player
De speler loopt continue naar rechts in de game. Toch heeft de speler de meest speciale eigenschappen in
de game. De speler moet kunnen springen en power-ups gebruiken. Ook mag de speler niet door muren lopen
en is de speler af als hij tegen een muur aan komt. De snelheid van de speler moet tijdens de game aangepast
kunnen worden, gebaseerd op hoe ver de speler als is gekomen. Ook moet de speler van 'dimensie' kunnen 
veranderen. 

#### Power-ups
De power-ups in het spel moeten kunnen worden 'opgepakt' door de speler. Dit oppakken gebeurd als de speler
tegen de power-up aan loopt. Vervolgens wordt door het spel verwerkt welke power-up opgepakt is en wordt het
aan de speler beschikbaar gesteld. De power-ups kunnen variabelen van de speler aanpassen, zoals snelheid,
spring hoogte en wellicht het uitzetten van gravity. Hierdoor zijn de power-ups dicht verbonden aan de speler.

#### Level
Het level bestaat in essentie uit allemaal verschillende segmenten. Deze segmenten worden door ontwikkelaars
zelf in elkaar gezet. Om toch het gevoel van willekeurigheid te creeren, zorgt het spel ervoor dat er telkens
andere segmenten worden gekozen om herhaling te voorkomen. Deze segmenten moet een aantal verschillende tiles 
kunnen ondersteunen zoals `None`, `Red`, `Blue` en `Both`. Op deze manier hoeven we per bestand maar 1 segment
te maken, en niet 1 segment per 'dimensie'.

#### Collision
Het spel gaat veel gebruik maken van collision detection. Op dit moment lijkt er weinig nut om een 
`circle-to-circle` collision detection te schrijven, dus gaat alles met `rectangle-to-rectangle` collision
geregeld worden. Dit is accuraat genoeg en bovenal snel. 

---
### Class diagram

Dit class diagram laat de relaties tussen alle classes zien.
```plantuml
class Rectangle {
    width: int
    height: int
    
    intersect(rect: Rectangle): boolean
}

class Point {
    x: float
    y: float
}

class CollisionManager {
    entities: GameEntity[]

    detect(): void 
    resolve(): void

    add(entity: GameEntity): void
    remove(entity: GameEntity): void
}

class LevelSegment {
    tiles: Tile[][]

    load(filename: String): void

    tick(): void
    draw(): void
}

class Level {
    segments: List<LevelSegment>

    tick(): void
    draw(): void
}

class LevelManager {
    loadedSegments: List<LevelSegment>
    
    load(): void
    generate(): Level
}

abstract class GameEntity {
    collision: Rectangle
    position: Point

    tick(): void
    draw(): void

    isMovable(): boolean
}

enum TileType {
    Empty
    RedFloor
    BlueFloor
    RedWall
    BlueWall
}

class Tile extends GameEntity {
    type: TileType
}

class MovingEntity extends GameEntity {
    move(offset: Point): void
}

class Player extends MovingEntity {
    score: int
    powerup: Powerup

    handleInput(): void
}

class Powerup {
    onApply(): void
    onRemove(): void

    tick(): void
}

GameEntity --right- Rectangle
Point --- GameEntity
Point --right- CollisionManager

CollisionManager --- Rectangle
LevelManager --- Level

Tile --- TileType
Tile --left- Level

LevelManager --left- LevelSegment
LevelSegment --right- Level

Player --right- Powerup
```

---

### Class Reference

#### Rectangle, Point & CollisionManager
De `Rectangle` class is verantwoordelijk voor het bijhouden van de *collision* informatie voor de `CollisionManager`. 
Er kan gekeken worden of een `Rectangle` in een ander `Rectangle` zit door het aanroepen van de `intersect` functie.
Deze functie gaat veel gebruikt worden door de `CollisionManager` voor het vaststellen van een *collision*.

De `Point` class is een simpele class voor het bijhouden wat de positie van een object is.

De `CollisionManager` gaat elke *frame* in het spel na of objecten in elkaar komen en zonodig uit elkaar halen.

#### GameEntity & MovingEntity
`GameEntity` is eigenlijk een kleine upgrade van `Entity` zoals ie al in het *template* bestaat. We hebben een 
aantal eigenschappen altijd nodig in ons spel, dus zijn er de velden 'position' en 'collision' bij ingestopt.

`MovingEntity` is in het leven geroepen om alle functionaliteiten van `GameEntity` te behouden maar toch
onderscheidt te kunnen maken in bewegende of niet-bewegende objecten. Dit heeft effect op het afhandelen
van *collision*. Als een `GameEntity` tijdens een *collision detection* aangeeft niet te kunnen bewegen,
en het object waarmee het vergelijkt wordt ook niet, zal er geen *collision detection* plaatsvinden. Immers,
muren hoeven niet met andere muren te checken of er *collision* is. 
Als een `GameEntity` aangeeft wel te kunnen bewegen maar degene waar ie tegenaan komt niet kan bewegen, 
zal altijd de `GameEntity` die wel kan bewegen worden verplaatst.

#### Level, LevelSegment, LevelManager, Tile & TileType
De `LevelManager` is onstaan uit de wil om aan de start van het spel, alle verschillende segmenten
waar een level uit kan bestaan in te laden zodat dit later snel door de `Level` class gebruikt kan worden
om een level 'uit te breiden'. In werkelijkheid zal de `Level` class een bepaald aantal `LevelSegment`'s 
in het geheugen houden en als de speler er langs is gelopen en het `LevelSegment` uit beeld is, deze 
verwijderd zal worden.

De `Tile` class wordt het meest intens gebruikt omdat dit de bouwstenen zijn van een `Level`. De speler loopt
op deze tiles en er wordt *collision* mee gechecked. `Tile`'s hebben op dit moment weinig eigenschappen
maar dit zou uitgebreid kunnen worden in een later stadium. De eigenschap `TileType` geeft aan wat voor 
`Tile` dit is. Er zijn verschillende waardes hiervoor, verwerkt in het `TileType` enum.

#### Player & Powerup
De `Player` is de *controller* class van de speler. Het bevat de logica van het afhandelen van de *input*
en het maakt gebruik van de `Powerup` class. Uiteindelijk is dit een dun laagje over de `MovingEntity` class
die wat specifieke logica toevoegt over het automatisch naar rechts lopen.

Een `Powerup` heeft simpel *interface*. De `onApply()` functie wordt aangeroepen als een `Powerup` toegepast
wordt op de speler, denk aan een *use* actie. De `onRemove()` wordt aangeroepen als het effect van de
speler afgehaald moet worden. Specifieke `Powerup`'s gaan deze class uitbreiden door logica toe te voegen
die specifiek is aan het type `Powerup`.

### Activity Diagram
Er is nagedacht over een activity diagram in dit project. Het is bij games erg lastig om een fatsoenlijke
activity diagram te maken. Toch is er het volgende uitgekomen:

```plantuml
(*) --> "Game Launch" 

--> "Title screen"
--> "Main Menu" 

if "" as main then
    -left-> "Play Game" as play
else
    -right-> "View Highscores" as highscores
endif

highscores -right-> main

play -left-> "Game Loop" as game_loop

if "Player reaches level end"
    --> "Generate level segments"
    --> "Handle input" as input
else
    --> "Handle input" as input

-down-> "Update entities"
-down-> "Render"
if "Quit" then
    --> [Yes]"Close game"
    --> (*)
else 
    --> [No]game_loop
```