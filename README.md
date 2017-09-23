# PRS-SimulacijaHDD

Ovo je repozitorijum za projekat u vezi simulacije magnetnog diska.
Predmet: Performanse Računarskih Sistema


## Opis:

- Klasa RequestGenerator generiše zahteve koji se stavljaju u red diska.
- Klasa Disc je aktivna klasa i ona u svojoj run() metodi simulira rad diska i kada uzme neki zahtev iz svog reda prolazi kroz 3 faze:
1. Seek phase - fazu pronalaska staze
2. Rotational delay phase - fazu koja pronalazi sektor na kome se nalazi zahtev koji treba pročitati
3. Transfer time phase - fazu koja računa koliko je vremena potrebno da se učita zahtev koji je tražen
- Klasa Scheduler u sebi sadrži sve Event-ove
- Klasa Event u sebi ima polja koja opisuju za koju fazu je vezan i koliko je trajao
- Klasa Request u sebi sadrži broj sektora i broj staze
- Klasa Simulation je klasa u kojoj se nalazi main metoda i gde se radi ispis rezultata simulacije

*Ovo je kratak opis klasa i njihovih funkcionalnosti, celokupna dokumentacija za njih je napisana u blokovima komentara.*


## Rezultati simulacija:
Rezultati se nalaze u folderu Results sa posebnim README fajlovima.
