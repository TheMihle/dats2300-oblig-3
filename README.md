[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/teLsEufN)

Er ingen template her som i forrige, men oblig arket sier at det skal skrives Readme:

# Oppgave 1
Metoden leggInn() legger inn en verdi i det binære søketreet ved en iterativ loop. For hver gang sammenligner den verdi med den 
noden den er på. Hvis verdi er mindre, velges venstre barn som element for neste loop. Ellers velger høyre barn. 
Når neste barn er null, oprettes en ny node der med verdien, og loopen brytes. Hvis rot er null lages det en ny node med 
verdien som rot før loopen i det hele tatt starter.

# Oppgave 2
Metoden antall() leter gjennom binære søketreet iterativt med loop på samme måte som leggInn() bare at hver gang den 
kommer til det tallet den leter etter, øker den en tallet. En verdi til høyre barn kan være den samme verdien. Og når 
den når null stopper loopen. Antall ganger den fant tallet returneres til slutt.
Hvis verdi er null eller rot er null, returnerer den 0 før loopen i det hele tatt starter da det da ikke kan finnes.

# Oppgave 3
Metoden førstePostorden() Går gjennom treet rekursivt ved å følge postorden ved å prioritere å gå til venste barn, og 
ellers høyre barn intill den ikke er noen barn, da er det første post orden og den returnerer seg selv oppover.

Metoden nestePostorden() finner neste postorden ved å følge disse tingene:
Hvis forelder er null, er det ingen neste postorden
Hvis noden er forelder høyre barn, eller forelder ikke har noen høyre barn, er forelder neste.
Hvis noden er venstre barn og forelder har et høyre barn, er neste node den lengst ned til venstre (så høyre hvis ingen 
venstrebarn) fra høyrebarnet til forrelder. Derfor kan man bare kalle på nestePostorden på forelder.høyre.

# Oppgave 4
Metoden postOrden() looper gjennom nodene i treet i postOrden og gjør oppgave på node verdien via å kalle 
førstePostorden() en gang og nestePostorden() i en loop.

Metoden postOrdenRekursiv(Node, Oppgave) looper gjennom postorden rekursivt å gjør oppgave på nodene via at den er 
etter metoden er kalt igjen på barna. 

# Oppgave 5
Metoden fjern() sletter første element med en verdi i treet. Jeg delte opp funskjonen i findNode() og removeNode() da de 
er mere generelle. fjern() finner noden via findNode() for så og slette via removeNode() hvis node med verdien eksisterer.

Metoden findNode() looper gjennom treet med loop på samme måte som leggInn og antall, bare at den returnerer noden den 
finner.

Metode removeNode() fjerner den noden som er sent in som parameter.
Hvis begge barna er null, gjør den bare om på pekere. Hvis rot, sette rot til null.
Hvis det er ett barn, flytter det barnet opp til posisjonen til den slette noden via å sette pekere rundt den.
Hvis noden har to barn, finner den noden som er en barn høyre og så så langt den kommer til venstre barn. Kopierer 
verdien dens inn i den som skulle fjernes og sletter den noden i steden med å kalle metoden på nytt. Den vill maks a ett 
barn.
Kan forråsake at treet blir ubalansert, kunne kanskje implementert metode som noen ganger går til venstre men måtte da ha 
sjekk for like verdier, og Nikolai sa at vi ikke trenger å kunne kode det.

Metode fjernAlle() kaller finnNode() for så å legge dem til en stabel. Hver gang starter FinnNode(node.høre) på den 
neste noden istedenfor å starte på nytt hver gang. Til slutt slettes nodene fra treet fra bunnen og opp via stabelen.

Det skal være raskere enn å kjøre fjern() intill den returerer false, nåre jeg prøvde å teste dette, stemte ikke dette, 
men det kan hende fordi test treet mitt ikke var stort nok/eller for kunstig lagd.

Metode nullstill() går igjennom treet og setter alle pekere til null i postorden som vil si noder med ingen barn. 
Trenger ikke bry seg om å koble om pekerne til nye lovlige tre som mellomsteg. Testet at det er omtrent 50% raskere enn 
å slette rotnoden med lovlig mellomsteg intill alt er null.

Lagde et alternativt som istedenfor rekusjon la til alle nodene til stack for så å gi alle pekerne, men det er tregere.