

# Tema Gestore Voli
Tema d'esame svolto come esercizio.

Traccia: https://elearning15.unibg.it/pluginfile.php/393476/mod_resource/content/1/SimulazioneTemaEsame.pdf

## ⚙️Funzionalità di FlightManager

✈️ ***1. lettura dei dati di voli e prenotazioni da un file (di testo) esterno.***

Chiamare il metodo pubblico di FlightManager  

    public void readFlightsFromFile(String path)

a cui si passa il percorso del file .csv (per esempio "./resources/flights.csv").  
Nel file .csv si possono commentare le righe con un '#'.  
Su ogni riga ci deve essere un volo (Flight) nel formato  

    id,date,departue,destination,maxSeats

Esempio: '10,01/12/2020,BGY,MLN,120'.  

Per leggere le prenotazioni da file è disponibile il metodo  

    public void readBookingsFromFile(String path)
al percorso "./resources/bookings.csv" è presente un file da leggere come esempio.

✈️ ***2. inserimento di un volo (da/a per una certa data e ora)***  

Si può aggiungere direttamente un volo con il metodo  

    public void addFlight(Flight f)
oppure si può aggiungere un volo data una stringa che rispetti il formato  

    "departure,destination,gg/mm/yyyy,hh:mm,maxSeats"
per esempio:

    FlightManager fm = new FlightManager();
    fm.addFlight("BGY,FCO,01/10/2000,09:30,120");
    
✈️ ***3. inserimento di una prenotazione: data prenotazione e volo (tramite ID), inserire la
prenotazione. Se il volo è pieno sollevare eccezione.***  

Per aggiungere una prenotazione ad un volo con un dato id si usa il metodo  

    public void addBookingToFlight(Booking  b, int  flightId)
Qualora il volo non abbia posti disponibili, il metodo lancia l'eccezione controllata *FlightCapacityExceededException*  

✈️ ***4. Spostamento di una prenotazione: da un volo ad un altro. Se però il volo nuovo è
pieno, viene lanciata una eccezione***  

Tale operazione può essere eseguita in due modi: con un riferimento alla prenotazione o con l'id della prenotazione da spostare.  

    public  void  moveBooking(Booking  b, int  fromFlightId, int  toFlightId)
    public  void  moveBooking(String  bookingId, int  fromFlightId, int  toFlightId)
Ciascuno dei due metodi lancia l'eccezione *FlightCapacityExceededException* se il volo in cui si vuole spostare la prenotazione è pieno.  

✈️ ***5. aggiunta di una persona in una prenotazione collettiva (passata tramite ID).***  

Per aggiungere una persona ad un GroupBooking si usa il metodo  

    public  void  addPersonToGroupBooking(String  ssn, String  bookingId)
il quale lancia l'eccezione *FlightCapacityExceededException* se nel volo associato non ci sono posti disponibili.  

✈️ ***6. stampa dei voli con relative prenotazioni e con anche i relativi costi. Fai due metodi di
stampa che prima di stampare, ordinano le prenotazioni o in ordine di costo o in ordine
alfabetico di ID.***  

Per stampare i voli gestiti da un FlightManager si ricorre al metodo  

    public  void  deepPrintFlights()
Nel caso in cui si vogliano visualizzare i voli ordinati secondo un dato criterio si può chiamare il metodo  

    public  void  deepPrintFlightsSorted(Comparator<Booking> comparator)
Il gestore mette a disposizione due Comparator di prenotazioni:  
*BookingPriceComparator* e *BookingAlphabetComparator*.  
Se si volessero stampare i voli ordinati secondo il prezzo, nel main si scriverebbe  

    FlightManager fm = new FlightManager();
    fm.deepPrintFlightsSorted(new  BookingPriceComparator()); 

✈️ ***7. ricerca di un volo tramite sigle di origine o destinazione che non sia ancora pieno***  

Per trovare un volo in in base alla partenza o alla destinazione si ricorre ai metodi  

    public  Flight  getFlightFromDeparture(String departure)
    public  Flight  getFlightFromDestination(String destination)
in cui departure/destination è una stringa di tre lettere che rappresenta il codice dell'areoporto. Esempio "BYG" o "byg".  
