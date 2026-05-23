package com.pulseradio.data

data class StationCategory(
    val id: String,
    val name: String,
    val emoji: String,
    val stations: List<RadioStation>
)

object StationDatabase {

    val allStations: List<RadioStation> by lazy {
        categories.flatMap { it.stations }
    }

    val categories: List<StationCategory> = listOf(

        // ELECTRONIC / EDM / DANCE - 80+ stations
        StationCategory(
            id = "electronic",
            name = "Electronic / EDM",
            emoji = "🎧",
            stations = listOf(
                // DI.FM family
                RadioStation("e001", "DI.FM Progressive", "Progressive House", "USA", "https://progressive.streams.di.fm/", "https://images.unsplash.com/photo-1571266028243-3716f02d2d2e?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e002", "DI.FM Trance", "Trance", "USA", "https://trance.streams.di.fm/", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e003", "DI.FM House", "House", "USA", "https://house.streams.di.fm/", "https://images.unsplash.com/photo-1558618666-fcd25c85f82e?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e004", "DI.FM Techno", "Techno", "Germany", "https://techno.streams.di.fm/", "https://images.unsplash.com/photo-1598387993441-a364f854c3e1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e005", "DI.FM Deep House", "Deep House", "USA", "https://deephouse.streams.di.fm/", "https://images.unsplash.com/photo-1574169208507-84376144848b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e006", "DI.FM Minimal", "Minimal", "Germany", "https://minimal.streams.di.fm/", "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e007", "DI.FM Drum and Bass", "DnB", "UK", "https://drumandbass.streams.di.fm/", "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e008", "DI.FM Dubstep", "Dubstep", "UK", "https://dubstep.streams.di.fm/", "https://images.unsplash.com/photo-1545128485-c400e7702796?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e009", "DI.FM Electro House", "Electro House", "USA", "https://electro.streams.di.fm/", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e010", "DI.FM Future Synthwave", "Synthwave", "Internet", "https://futuresynthpop.streams.di.fm/", "https://images.unsplash.com/photo-1557672172-298e090bd0f1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e011", "DI.FM Lounge", "Lounge", "USA", "https://lounge.streams.di.fm/", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e012", "DI.FM Chillout", "Chillout", "USA", "https://chillout.streams.di.fm/", "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e013", "DI.FM Ambient", "Ambient", "USA", "https://ambient.streams.di.fm/", "https://images.unsplash.com/photo-1470115636492-6d2b56f9146d?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e014", "DI.FM PsyChill", "PsyChill", "Goa", "https://psychill.streams.di.fm/", "https://images.unsplash.com/photo-1535131749006-b7f58c99034b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e015", "DI.FM Goa PsyTrance", "PsyTrance", "Goa", "https://goapsy.streams.di.fm/", "https://images.unsplash.com/photo-1496293455970-f8581aae0e3b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e016", "DI.FM Hard Dance", "Hard Dance", "Netherlands", "https://harddance.streams.di.fm/", "https://images.unsplash.com/photo-1526666923127-b2970f64b422?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e017", "DI.FM Hardcore", "Hardcore", "Netherlands", "https://hardcore.streams.di.fm/", "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e018", "DI.FM Trap", "Trap", "USA", "https://trap.streams.di.fm/", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e019", "DI.FM Future Bass", "Future Bass", "USA", "https://futurebass.streams.di.fm/", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e020", "DI.FM Breaks", "Breaks", "UK", "https://breaks.streams.di.fm/", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e021", "DI.FM Big Room House", "Big Room", "USA", "https://bigroom.streams.di.fm/", "https://images.unsplash.com/photo-1558618666-fcd25c85f82e?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e022", "DI.FM Electro Swing", "Electro Swing", "Paris", "https://electroswing.streams.di.fm/", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e023", "DI.FM Nu Disco", "Nu Disco", "France", "https://nudisco.streams.di.fm/", "https://images.unsplash.com/photo-1520523839897-bd0b52f945a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e024", "DI.FM Indie Dance", "Indie Dance", "Berlin", "https://indiedance.streams.di.fm/", "https://images.unsplash.com/photo-1483412033650-1015ddeb83d1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e025", "DI.FM Soulful House", "Soulful House", "Chicago", "https://soulfulhouse.streams.di.fm/", "https://images.unsplash.com/photo-1496293455970-f8581aae0e3b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e026", "DI.FM Latin House", "Latin House", "Miami", "https://latinhouse.streams.di.fm/", "https://images.unsplash.com/photo-1533109721025-d1ae7ee7c1e1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e027", "DI.FM Tech House", "Tech House", "UK", "https://techhouse.streams.di.fm/", "https://images.unsplash.com/photo-1598387993441-a364f854c3e1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e028", "DI.FM Glitch Hop", "Glitch Hop", "USA", "https://glitchhop.streams.di.fm/", "https://images.unsplash.com/photo-1545128485-c400e7702796?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e029", "DI.FM Jungle", "Jungle", "London", "https://jungle.streams.di.fm/", "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e030", "DI.FM UK Garage", "UK Garage", "London", "https://ukgarage.streams.di.fm/", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e031", "DI.FM Oldschool Acid", "Acid House", "Chicago", "https://oldschoolacid.streams.di.fm/", "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e032", "DI.FM Oldschool Techno", "Oldschool Techno", "Detroit", "https://oldschooltechno.streams.di.fm/", "https://images.unsplash.com/photo-1549834125-82f047a8bb6c?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e033", "DI.FM Oldschool Trance", "Classic Trance", "Germany", "https://oldschooltrance.streams.di.fm/", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e034", "DI.FM Oldschool House", "Classic House", "Chicago", "https://oldschoolhouse.streams.di.fm/", "https://images.unsplash.com/photo-1558618666-fcd25c85f82e?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e035", "DI.FM Space Dreams", "Space Ambient", "Internet", "https://spacemusic.streams.di.fm/", "https://images.unsplash.com/photo-1462331940025-496dfbfc7564?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e036", "DI.FM Dark DnB", "Dark DnB", "UK", "https://darkdnb.streams.di.fm/", "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e037", "DI.FM Liquid DnB", "Liquid DnB", "UK", "https://liquiddnb.streams.di.fm/", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e038", "DI.FM Classic EuroDance", "EuroDance", "Europe", "https://classiceurodance.streams.di.fm/", "https://images.unsplash.com/photo-1526666923127-b2970f64b422?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e039", "DI.FM Classic EuroDisco", "EuroDisco", "Italy", "https://classiceurodisco.streams.di.fm/", "https://images.unsplash.com/photo-1520523839897-bd0b52f945a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e040", "DI.FM Disco House", "Disco House", "NYC", "https://discohouse.streams.di.fm/", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e041", "DI.FM Funky House", "Funky House", "Chicago", "https://funkyhouse.streams.di.fm/", "https://images.unsplash.com/photo-1496293455970-f8581aae0e3b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e042", "DI.FM Tribal House", "Tribal House", "Brazil", "https://tribalhouse.streams.di.fm/", "https://images.unsplash.com/photo-1535525153412-5a42439a210d?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e043", "DI.FM Progressive Psy", "Progressive Psy", "Goa", "https://progressivepsy.streams.di.fm/", "https://images.unsplash.com/photo-1535131749006-b7f58c99034b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e044", "DI.FM Dark PsyTrance", "Dark Psy", "Goa", "https://darkpsy.streams.di.fm/", "https://images.unsplash.com/photo-1470115636492-6d2b56f9146d?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e045", "DI.FM Atmospheric Breaks", "Atmospheric Breaks", "Russia", "https://atmosphericbreaks.streams.di.fm/", "https://images.unsplash.com/photo-1462331940025-496dfbfc7564?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e046", "DI.FM Chillstep", "Chillstep", "UK", "https://chillstep.streams.di.fm/", "https://images.unsplash.com/photo-1519834785169-98be25ec3f84?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e047", "DI.FM Melodic Progressive", "Melodic Prog", "Berlin", "https://melodicprogressive.streams.di.fm/", "https://images.unsplash.com/photo-1571266028243-3716f02d2d2e?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e048", "DI.FM Vocal Chillout", "Vocal Chill", "UK", "https://vocalchillout.streams.di.fm/", "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e049", "DI.FM Vocal Trance", "Vocal Trance", "Netherlands", "https://vocaltrance.streams.di.fm/", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e050", "DI.FM Hands Up", "Hands Up", "Germany", "https://handsup.streams.di.fm/", "https://images.unsplash.com/photo-1526666923127-b2970f64b422?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e051", "DI.FM Club Dubstep", "Club Dubstep", "USA", "https://clubdubstep.streams.di.fm/", "https://images.unsplash.com/photo-1545128485-c400e7702796?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e052", "DI.FM Club Sounds", "Club Sounds", "Ibiza", "https://clubsounds.streams.di.fm/", "https://images.unsplash.com/photo-1558618666-fcd25c85f82e?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e053", "DI.FM Cosmic Downtempo", "Downtempo", "Berlin", "https://cosmicdowntempo.streams.di.fm/", "https://images.unsplash.com/photo-1470115636492-6d2b56f9146d?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e054", "DI.FM Deep Nu Disco", "Deep Nu Disco", "France", "https://deepnudisco.streams.di.fm/", "https://images.unsplash.com/photo-1520523839897-bd0b52f945a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e055", "DI.FM Deep Tech", "Deep Tech", "Berlin", "https://deeptech.streams.di.fm/", "https://images.unsplash.com/photo-1598387993441-a364f854c3e1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e056", "DI.FM Detroit House", "Detroit House", "Detroit", "https://detroithouse.streams.di.fm/", "https://images.unsplash.com/photo-1549834125-82f047a8bb6c?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e057", "DI.FM Disco", "Disco", "NYC", "https://disco.streams.di.fm/", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e058", "DI.FM DJ Mixes", "DJ Mixes", "Global", "https://djmixes.streams.di.fm/", "https://images.unsplash.com/photo-1574169208507-84376144848b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e059", "DI.FM Dub", "Dub", "Jamaica", "https://dub.streams.di.fm/", "https://images.unsplash.com/photo-1534531173927-aeb928d54385?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e060", "DI.FM Eclectronica", "Eclectronica", "Berlin", "https://eclectronica.streams.di.fm/", "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e061", "DI.FM Electro", "Electro", "Detroit", "https://electro.streams.di.fm/", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e062", "DI.FM Epic Trance", "Epic Trance", "Netherlands", "https://epictrance.streams.di.fm/", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e063", "DI.FM Ethnic Electronica", "Ethnic Electronic", "World", "https://ethnicelectronica.streams.di.fm/", "https://images.unsplash.com/photo-1535525153412-5a42439a210d?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e064", "DI.FM French Lounge", "French Lounge", "Paris", "https://frenchlounge.streams.di.fm/", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e065", "DI.FM Full On PsyTrance", "Full On Psy", "Goa", "https://fullonpsy.streams.di.fm/", "https://images.unsplash.com/photo-1535131749006-b7f58c99034b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e066", "DI.FM Future Garage", "Future Garage", "London", "https://futuregarage.streams.di.fm/", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e067", "DI.FM Gabber", "Gabber", "Netherlands", "https://gabber.streams.di.fm/", "https://images.unsplash.com/photo-1526666923127-b2970f64b422?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e068", "DI.FM German Dance", "German Dance", "Berlin", "https://germandance.streams.di.fm/", "https://images.unsplash.com/photo-1558618666-fcd25c85f82e?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e069", "DI.FM German Techno", "German Techno", "Berlin", "https://germantechno.streams.di.fm/", "https://images.unsplash.com/photo-1598387993441-a364f854c3e1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e070", "DI.FM Global Chillout", "Global Chill", "World", "https://globalchillout.streams.di.fm/", "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e071", "DI.FM Global Goa", "Global Goa", "Goa", "https://globalgoa.streams.di.fm/", "https://images.unsplash.com/photo-1496293455970-f8581aae0e3b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e072", "DI.FM Hardstyle", "Hardstyle", "Netherlands", "https://hardstyle.streams.di.fm/", "https://images.unsplash.com/photo-1526666923127-b2970f64b422?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e073", "DI.FM House Classics", "House Classics", "Chicago", "https://houseclassics.streams.di.fm/", "https://images.unsplash.com/photo-1558618666-fcd25c85f82e?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e074", "DI.FM Latin Dance", "Latin Dance", "Miami", "https://latindance.streams.di.fm/", "https://images.unsplash.com/photo-1533109721025-d1ae7ee7c1e1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e075", "DI.FM Latin Techno", "Latin Techno", "Mexico", "https://latintechno.streams.di.fm/", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e076", "DI.FM Liquid Trap", "Liquid Trap", "USA", "https://liquidtrap.streams.di.fm/", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e077", "DI.FM Mainstage", "Mainstage", "USA", "https://mainstage.streams.di.fm/", "https://images.unsplash.com/photo-1574169208507-84376144848b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e078", "DI.FM Neurofunk", "Neurofunk", "UK", "https://neurofunk.streams.di.fm/", "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e079", "DI.FM Nightcore", "Nightcore", "Japan", "https://nightcore.streams.di.fm/", "https://images.unsplash.com/photo-1534531173927-aeb928d54385?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e080", "DI.FM Oldschool Electro", "Oldschool Electro", "Detroit", "https://oldschoolelectro.streams.di.fm/", "https://images.unsplash.com/photo-1549834125-82f047a8bb6c?auto=format&fit=crop&q=80&w=400"),

                // Other electronic sources
                RadioStation("e081", "FNOOB Techno", "Underground Techno", "London", "https://stream.fnoobtechno.com/", "https://images.unsplash.com/photo-1545128485-c400e7702796?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e082", "Data Transmission", "Underground Dance", "UK", "https://datatransmission.co.uk/radio/", "https://images.unsplash.com/photo-1496293455970-f8581aae0e3b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e083", "Nightride FM", "Synthwave", "Internet", "https://stream.nightride.fm/nightride.m4a", "https://images.unsplash.com/photo-1535131749006-b7f58c99034b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e084", "Poolside FM", "Summer Chill", "Montreal", "https://poolside.fm/stream.mp3", "https://images.unsplash.com/photo-1534234828563-025217188d73?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e085", "Radio Record", "Russian Dance", "St Petersburg", "https://air.radiorecord.ru:805/rr_320", "https://images.unsplash.com/photo-1526666923127-b2970f64b422?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e086", "Record Deep", "Deep House Russia", "Russia", "https://air.radiorecord.ru:805/deep_320", "https://images.unsplash.com/photo-1574169208507-84376144848b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e087", "Record Techno", "Techno Russia", "Russia", "https://air.radiorecord.ru:805/techno_320", "https://images.unsplash.com/photo-1598387993441-a364f854c3e1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e088", "Record Trance", "Trance Russia", "Russia", "https://air.radiorecord.ru:805/trance_320", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e089", "Radio FG", "Dance France", "Paris", "https://radiofg.impek.com/fg", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e090", "Fun Radio", "Dance Pop France", "Paris", "https://streaming.radio.funradio.fr/fun-1-48-192", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e091", "Ibiza Global Radio", "Ibiza House", "Ibiza", "https://ibizaglobalradio.streaming-pro.com:8000/ibizaglobalradio.mp3", "https://images.unsplash.com/photo-1574169208507-84376144848b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e092", "Ibiza Sonica", "Deep Ibiza", "Ibiza", "https://s1.sonicabroadcast.com:7005/stream", "https://images.unsplash.com/photo-1558618666-fcd25c85f82e?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e093", "Deep House Ibiza", "Deep House", "Ibiza", "https://51.255.235.165/stream/1/", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e094", "Minimal Mix Radio", "Minimal", "Germany", "https://minimalmix.stream.laut.fm/minimalmix", "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e095", "Electro Swing", "Swing Electronic", "Paris", "https://electroswing.stream.laut.fm/electroswing", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e096", "Drum & Bass Arena", "DnB", "London", "https://stream.dabreakfastclub.com/", "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e097", "Synthwave Nightdrive", "Synthwave", "Tokyo", "https://stream.syntheticfm.com/1", "https://images.unsplash.com/photo-1557672172-298e090bd0f1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e098", "Radio Nova", "Electro France", "Paris", "https://stream.radio-nova.com/nova", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e099", "TSF Jazz", "Jazz Electronic", "Paris", "https://tsfjazz.ice.infomaniak.ch/tsfjazz-high.mp3", "https://images.unsplash.com/photo-1511192336575-5a79af67a629?auto=format&fit=crop&q=80&w=400"),
                RadioStation("e100", "Radio Meuh", "Eclectic Electronic", "France", "https://radiomeuh.ice.infomaniak.ch/radiomeuh-128.mp3", "https://images.unsplash.com/photo-1483412033650-1015ddeb83d1?auto=format&fit=crop&q=80&w=400"),
            )
        ),

        // LOFI / CHILL / AMBIENT - 30 stations
        StationCategory(
            id = "lofi",
            name = "Lofi & Chill",
            emoji = "☕",
            stations = listOf(
                RadioStation("l001", "Lofi Girl", "Lofi / Study", "Worldwide", "https://play.streamafrica.net/lofiradio", "https://images.unsplash.com/photo-1518609878373-06d740f60d8b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l002", "Chillhop Radio", "Chillhop", "Netherlands", "https://stream.zeno.fm/0r0xa7928zuvv", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l003", "Radio Relax", "Ambient", "USA", "https://stream.relaxfm.hu/live", "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l004", "Sleep Radio", "Sleep", "New Zealand", "https://sleepradio.stream.laut.fm/sleepradio", "https://images.unsplash.com/photo-1519834785169-98be25ec3f84?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l005", "Jazz Cafe", "Jazz Lounge", "Paris", "https://jazzradio.ice.infomaniak.ch/jazzradio-high.mp3", "https://images.unsplash.com/photo-1511192336575-5a79af67a629?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l006", "Coffee Shop Radio", "Coffee House", "Seattle", "https://stream.zeno.fm/f3wfn8gf4xquv", "https://images.unsplash.com/photo-1442512595367-42736f9e51f9?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l007", "Ambient Sleeping Pill", "Drone", "Canada", "https://radio.stereo90.6r7k-6y7k.spectrumradio.net/stream", "https://images.unsplash.com/photo-1470115636492-6d2b56f9146d?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l008", "Poolside FM", "Summer", "Montreal", "https://poolside.fm/stream.mp3", "https://images.unsplash.com/photo-1534234828563-025217188d73?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l009", "Lofi 24/7", "Lofi Beats", "Internet", "https://stream.zeno.fm/0r0xa7928zuvv", "https://images.unsplash.com/photo-1518609878373-06d740f60d8b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l010", "Chilled Cow", "Study Beats", "Internet", "https://stream.zeno.fm/0r0xa7928zuvv", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l011", "Rainy Jazz", "Rainy Mood", "Internet", "https://stream.zeno.fm/f3wfn8gf4xquv", "https://images.unsplash.com/photo-1519834785169-98be25ec3f84?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l012", "Night Jazz", "Late Night", "Paris", "https://jazzradio.ice.infomaniak.ch/jazzradio-high.mp3", "https://images.unsplash.com/photo-1511192336575-5a79af67a629?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l013", "Smooth Lounge", "Lounge", "Ibiza", "https://lounge.streams.di.fm/", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l014", "Zen Radio", "Meditation", "World", "https://zen.stream.laut.fm/zen", "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l015", "Nature Sounds", "Nature", "World", "https://nature.stream.laut.fm/nature", "https://images.unsplash.com/photo-1470115636492-6d2b56f9146d?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l016", "Piano Bar", "Solo Piano", "World", "https://piano.stream.laut.fm/piano", "https://images.unsplash.com/photo-1520523839897-bd0b52f945a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l017", "Cafe Del Mar", "Balearic", "Ibiza", "https://cafedelmar.stream.laut.fm/cafedelmar", "https://images.unsplash.com/photo-1534234828563-025217188d73?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l018", "St Germain", "Downtempo", "Paris", "https://stgermain.stream.laut.fm/stgermain", "https://images.unsplash.com/photo-1511192336575-5a79af67a629?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l019", "Chic Lounge", "Chic", "France", "https://chic.stream.laut.fm/chic", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l020", "Buddha Bar", "Ethnic Chill", "Paris", "https://buddhabar.stream.laut.fm/buddhabar", "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l021", "Deep Sleep", "Deep Sleep", "World", "https://deepsleep.stream.laut.fm/deepsleep", "https://images.unsplash.com/photo-1519834785169-98be25ec3f84?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l022", "Reiki Music", "Healing", "World", "https://reiki.stream.laut.fm/reiki", "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l023", "Yoga Music", "Yoga", "India", "https://yoga.stream.laut.fm/yoga", "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l024", "Spa Radio", "Spa", "World", "https://spa.stream.laut.fm/spa", "https://images.unsplash.com/photo-1519834785169-98be25ec3f84?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l025", "Healing Frequencies", "432Hz", "World", "https://432.stream.laut.fm/432", "https://images.unsplash.com/photo-1470115636492-6d2b56f9146d?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l026", "White Noise", "Noise", "World", "https://whitenoise.stream.laut.fm/whitenoise", "https://images.unsplash.com/photo-1519834785169-98be25ec3f84?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l027", "Ocean Waves", "Ocean", "World", "https://ocean.stream.laut.fm/ocean", "https://images.unsplash.com/photo-1505118380757-91f5f5632de0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l028", "Forest Sounds", "Forest", "World", "https://forest.stream.laut.fm/forest", "https://images.unsplash.com/photo-1448375240586-882707db888b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l029", "Rain Sounds", "Rain", "World", "https://rain.stream.laut.fm/rain", "https://images.unsplash.com/photo-1515694346937-94d85e41e6f0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("l030", "Thunderstorm", "Storm", "World", "https://thunder.stream.laut.fm/thunder", "https://images.unsplash.com/photo-1505118380757-91f5f5632de0?auto=format&fit=crop&q=80&w=400"),
            )
        ),

        // ROCK / METAL / ALTERNATIVE - 30 stations
        StationCategory(
            id = "rock",
            name = "Rock & Metal",
            emoji = "🎸",
            stations = listOf(
                RadioStation("r001", "Classic Rock HD", "Classic Rock", "New York", "https://server6.02host.xyz:8124/stream", "https://images.unsplash.com/photo-1498038432885-c6f3f1b912ee?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r002", "K-ROCK", "Alternative", "Los Angeles", "https://stream.revma.ihrhls.com/zc13", "https://images.unsplash.com/photo-1525201548942-d8732f6617a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r003", "Radio Paradise Rock", "Eclectic", "California", "https://stream.radioparadise.com/rock-192", "https://images.unsplash.com/photo-1459749411177-0473ef716175?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r004", "Metal Nation", "Heavy Metal", "Texas", "https://stream.laut.fm/metal-nation", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r005", "Indie 88", "Indie Rock", "Toronto", "https://cob-ais.leanstream.co/CIVCFM", "https://images.unsplash.com/photo-1483412033650-1015ddeb83d1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r006", "Grunge Radio", "Grunge", "Seattle", "https://grunge.stream.laut.fm/grunge", "https://images.unsplash.com/photo-1511735111819-9a3f77ebd235?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r007", "Punk Radio", "Punk", "London", "https://punk.stream.laut.fm/punk", "https://images.unsplash.com/photo-1549834125-82f047a8bb6c?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r008", "Blues Radio", "Blues", "Chicago", "https://blues.stream.laut.fm/blues", "https://images.unsplash.com/photo-1516280440614-6697288d5d38?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r009", "Hard Rock Hell", "Hard Rock", "UK", "https://hardrockhell.stream.laut.fm/hardrockhell", "https://images.unsplash.com/photo-1498038432885-c6f3f1b912ee?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r010", "Metal Hammer", "Metal", "Germany", "https://metalhammer.stream.laut.fm/metalhammer", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r011", "Prog Rock", "Progressive", "UK", "https://progrock.stream.laut.fm/progrock", "https://images.unsplash.com/photo-1459749411177-0473ef716175?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r012", "Classic Metal", "Classic Metal", "USA", "https://classicmetal.stream.laut.fm/classicmetal", "https://images.unsplash.com/photo-1498038432885-c6f3f1b912ee?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r013", "Doom Metal", "Doom", "UK", "https://doommetal.stream.laut.fm/doommetal", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r014", "Black Metal", "Black Metal", "Norway", "https://blackmetal.stream.laut.fm/blackmetal", "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r015", "Death Metal", "Death Metal", "Florida", "https://deathmetal.stream.laut.fm/deathmetal", "https://images.unsplash.com/photo-1549834125-82f047a8bb6c?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r016", "Thrash Metal", "Thrash", "California", "https://thrashmetal.stream.laut.fm/thrashmetal", "https://images.unsplash.com/photo-1498038432885-c6f3f1b912ee?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r017", "Power Metal", "Power Metal", "Germany", "https://powermetal.stream.laut.fm/powermetal", "https://images.unsplash.com/photo-1525201548942-d8732f6617a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r018", "Glam Rock", "Glam", "Los Angeles", "https://glamrock.stream.laut.fm/glamrock", "https://images.unsplash.com/photo-1525201548942-d8732f6617a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r019", "Psychedelic Rock", "Psychedelic", "San Francisco", "https://psychedelic.stream.laut.fm/psychedelic", "https://images.unsplash.com/photo-1459749411177-0473ef716175?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r020", "Stoner Rock", "Stoner", "Palm Desert", "https://stonerrock.stream.laut.fm/stonerrock", "https://images.unsplash.com/photo-1498038432885-c6f3f1b912ee?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r021", "Post Rock", "Post Rock", "Iceland", "https://postrock.stream.laut.fm/postrock", "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r022", "Shoegaze", "Shoegaze", "UK", "https://shoegaze.stream.laut.fm/shoegaze", "https://images.unsplash.com/photo-1483412033650-1015ddeb83d1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r023", "Britpop", "Britpop", "Manchester", "https://britpop.stream.laut.fm/britpop", "https://images.unsplash.com/photo-1525201548942-d8732f6617a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r024", "Garage Rock", "Garage", "Detroit", "https://garagerock.stream.laut.fm/garagerock", "https://images.unsplash.com/photo-1511735111819-9a3f77ebd235?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r025", "Surf Rock", "Surf", "California", "https://surfrock.stream.laut.fm/surfrock", "https://images.unsplash.com/photo-1500534314209-a25ddb2bd429?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r026", "Rockabilly", "Rockabilly", "Memphis", "https://rockabilly.stream.laut.fm/rockabilly", "https://images.unsplash.com/photo-1516280440614-6697288d5d38?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r027", "Ska Punk", "Ska Punk", "California", "https://skapunk.stream.laut.fm/skapunk", "https://images.unsplash.com/photo-1549834125-82f047a8bb6c?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r028", "Emo Radio", "Emo", "New Jersey", "https://emo.stream.laut.fm/emo", "https://images.unsplash.com/photo-1483412033650-1015ddeb83d1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r029", "Screamo", "Screamo", "USA", "https://screamo.stream.laut.fm/screamo", "https://images.unsplash.com/photo-1549834125-82f047a8bb6c?auto=format&fit=crop&q=80&w=400"),
                RadioStation("r030", "Math Rock", "Math Rock", "USA", "https://mathrock.stream.laut.fm/mathrock", "https://images.unsplash.com/photo-1459749411177-0473ef716175?auto=format&fit=crop&q=80&w=400"),
            )
        ),

        // LATIN - 30 stations
        StationCategory(
            id = "latin",
            name = "Latino",
            emoji = "🌎",
            stations = listOf(
                RadioStation("la01", "La Mega", "Reggaeton", "Miami", "https://stream.revma.ihrhls.com/zc71", "https://images.unsplash.com/photo-1533109721025-d1ae7ee7c1e1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la02", "Radio Activ", "Latin Pop", "Mexico City", "https://stream.zeno.fm/7qup0wmsrfeuv", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la03", "Salsa Radio", "Salsa", "Cali", "https://salsa.stream.laut.fm/salsa", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la04", "Bachata Radio", "Bachata", "Santo Domingo", "https://bachata.stream.laut.fm/bachata", "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la05", "Cumbia Radio", "Cumbia", "Buenos Aires", "https://cumbia.stream.laut.fm/cumbia", "https://images.unsplash.com/photo-1535525153412-5a42439a210d?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la06", "Radio Disney Latino", "Pop Latino", "Argentina", "https://stream.zeno.fm/6qup0wmsrfeuv", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la07", "Vallenato Radio", "Vallenato", "Valledupar", "https://vallenato.stream.laut.fm/vallenato", "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la08", "Merengue Radio", "Merengue", "Caracas", "https://merengue.stream.laut.fm/merengue", "https://images.unsplash.com/photo-1525201548942-d8732f6617a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la09", "Reggaeton 24/7", "Reggaeton", "Puerto Rico", "https://reggaeton.stream.laut.fm/reggaeton", "https://images.unsplash.com/photo-1533109721025-d1ae7ee7c1e1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la10", "Latin Jazz", "Latin Jazz", "Cuba", "https://latinjazz.stream.laut.fm/latinjazz", "https://images.unsplash.com/photo-1511192336575-5a79af67a629?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la11", "Tango Radio", "Tango", "Buenos Aires", "https://tango.stream.laut.fm/tango", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la12", "Flamenco Radio", "Flamenco", "Seville", "https://flamenco.stream.laut.fm/flamenco", "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la13", "Mariachi", "Mariachi", "Guadalajara", "https://mariachi.stream.laut.fm/mariachi", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la14", "Ranchera", "Ranchera", "Mexico", "https://ranchera.stream.laut.fm/ranchera", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la15", "Banda", "Banda", "Sinaloa", "https://banda.stream.laut.fm/banda", "https://images.unsplash.com/photo-1533109721025-d1ae7ee7c1e1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la16", "Norteño", "Norteño", "Monterrey", "https://norteno.stream.laut.fm/norteno", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la17", "Tropical", "Tropical", "Miami", "https://tropical.stream.laut.fm/tropical", "https://images.unsplash.com/photo-1533109721025-d1ae7ee7c1e1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la18", "Dembow", "Dembow", "Panama", "https://dembow.stream.laut.fm/dembow", "https://images.unsplash.com/photo-1533109721025-d1ae7ee7c1e1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la19", "Latin Trap", "Latin Trap", "USA", "https://latintrap.stream.laut.fm/latintrap", "https://images.unsplash.com/photo-1533109721025-d1ae7ee7c1e1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la20", "Salsa Brava", "Salsa Brava", "NYC", "https://salsabrava.stream.laut.fm/salsabrava", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la21", "Timba", "Timba", "Havana", "https://timba.stream.laut.fm/timba", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la22", "Son Cubano", "Son", "Cuba", "https://son.stream.laut.fm/son", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la23", "Bolero", "Bolero", "Mexico", "https://bolero.stream.laut.fm/bolero", "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la24", "Trova", "Trova", "Cuba", "https://trova.stream.laut.fm/trova", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la25", "Fado", "Fado", "Lisbon", "https://fado.stream.laut.fm/fado", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la26", "Bossa Nova", "Bossa Nova", "Rio", "https://bossanova.stream.laut.fm/bossanova", "https://images.unsplash.com/photo-1485579149621-3123dd979885?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la27", "Samba", "Samba", "Rio", "https://samba.stream.laut.fm/samba", "https://images.unsplash.com/photo-1520523839897-bd0b52f945a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la28", "MPB", "MPB", "Brazil", "https://mpb.stream.laut.fm/mpb", "https://images.unsplash.com/photo-1520523839897-bd0b52f945a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la29", "Axé", "Axé", "Salvador", "https://axe.stream.laut.fm/axe", "https://images.unsplash.com/photo-1520523839897-bd0b52f945a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("la30", "Forró", "Forró", "Brazil", "https://forro.stream.laut.fm/forro", "https://images.unsplash.com/photo-1520523839897-bd0b52f945a0?auto=format&fit=crop&q=80&w=400"),
            )
        ),

        // JAZZ / SOUL / FUNK - 20 stations
        StationCategory(
            id = "jazz",
            name = "Jazz & Soul",
            emoji = "🎷",
            stations = listOf(
                RadioStation("j001", "Jazz24", "Jazz", "USA", "https://live.wostreaming.net/manifest/jazz24-jazz24mp3-ibc1", "https://images.unsplash.com/photo-1415201364774-f6f0bb35f28f?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j002", "Smooth Jazz", "Smooth Jazz", "New York", "https://stream.revma.ihrhls.com/zc42", "https://images.unsplash.com/photo-1511192336575-5a79af67a629?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j003", "Funk Radio", "Funk", "Detroit", "https://funk.stream.laut.fm/funk", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j004", "Soul Radio", "Soul / R&B", "Memphis", "https://soul.stream.laut.fm/soul", "https://images.unsplash.com/photo-1496293455970-f8581aae0e3b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j005", "Bossa Nova", "Bossa Nova", "Rio", "https://bossanova.stream.laut.fm/bossanova", "https://images.unsplash.com/photo-1485579149621-3123dd979885?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j006", "Classic Jazz", "Classic Jazz", "New Orleans", "https://classicjazz.stream.laut.fm/classicjazz", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j007", "Bebop", "Bebop", "NYC", "https://bebop.stream.laut.fm/bebop", "https://images.unsplash.com/photo-1415201364774-f6f0bb35f28f?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j008", "Cool Jazz", "Cool Jazz", "California", "https://cooljazz.stream.laut.fm/cooljazz", "https://images.unsplash.com/photo-1511192336575-5a79af67a629?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j009", "Fusion", "Fusion", "Miles", "https://fusion.stream.laut.fm/fusion", "https://images.unsplash.com/photo-1415201364774-f6f0bb35f28f?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j010", "Free Jazz", "Free Jazz", "NYC", "https://freejazz.stream.laut.fm/freejazz", "https://images.unsplash.com/photo-1415201364774-f6f0bb35f28f?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j011", "Swing", "Swing", "Harlem", "https://swing.stream.laut.fm/swing", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j012", "Big Band", "Big Band", "USA", "https://bigband.stream.laut.fm/bigband", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j013", "Vocal Jazz", "Vocal Jazz", "USA", "https://vocaljazz.stream.laut.fm/vocaljazz", "https://images.unsplash.com/photo-1511192336575-5a79af67a629?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j014", "Piano Jazz", "Piano Jazz", "USA", "https://pianojazz.stream.laut.fm/pianojazz", "https://images.unsplash.com/photo-1520523839897-bd0b52f945a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j015", "Gypsy Jazz", "Gypsy Jazz", "Paris", "https://gypsyjazz.stream.laut.fm/gypsyjazz", "https://images.unsplash.com/photo-1485579149621-3123dd979885?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j016", "Acid Jazz", "Acid Jazz", "London", "https://acidjazz.stream.laut.fm/acidjazz", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j017", "Neo Soul", "Neo Soul", "USA", "https://neosoul.stream.laut.fm/neosoul", "https://images.unsplash.com/photo-1496293455970-f8581aae0e3b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j018", "Motown", "Motown", "Detroit", "https://motown.stream.laut.fm/motown", "https://images.unsplash.com/photo-1496293455970-f8581aae0e3b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j019", "Disco", "Disco", "NYC", "https://disco.stream.laut.fm/disco", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("j020", "Rare Groove", "Rare Groove", "UK", "https://raregroove.stream.laut.fm/raregroove", "https://images.unsplash.com/photo-1496293455970-f8581aae0e3b?auto=format&fit=crop&q=80&w=400"),
            )
        ),

        // POP / HITS - 20 stations
        StationCategory(
            id = "pop",
            name = "Pop & Hits",
            emoji = "🎤",
            stations = listOf(
                RadioStation("p001", "Top 40 Radio", "Top 40", "New York", "https://stream.revma.ihrhls.com/zc4", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p002", "Capital FM", "Pop Hits", "London", "https://media-ssl.musicradio.com/Capital", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p003", "NRJ France", "Pop / Dance", "Paris", "https://stream.nrjaudio.fm/fr/nrj", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p004", "Kiss FM", "Pop", "Los Angeles", "https://stream.revma.ihrhls.com/zc2", "https://images.unsplash.com/photo-1496293455970-f8581aae0e3b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p005", "Radio Disney", "Pop Teen", "USA", "https://stream.revma.ihrhls.com/zc6", "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p006", "Hits 1", "Top Hits", "Miami", "https://hits1.stream.laut.fm/hits1", "https://images.unsplash.com/photo-1520523839897-bd0b52f945a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p007", "J-Pop Radio", "J-Pop", "Tokyo", "https://jpop.stream.laut.fm/jpop", "https://images.unsplash.com/photo-1534531173927-aeb928d54385?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p008", "K-Pop Radio", "K-Pop", "Seoul", "https://kpop.stream.laut.fm/kpop", "https://images.unsplash.com/photo-1499364615650-ec38552f4f34?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p009", "C-Pop", "C-Pop", "China", "https://cpop.stream.laut.fm/cpop", "https://images.unsplash.com/photo-1499364615650-ec38552f4f34?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p010", "Bollywood Hits", "Bollywood Pop", "Mumbai", "https://bollywood.stream.laut.fm/bollywood", "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p011", "Arabic Pop", "Arabic Pop", "Cairo", "https://arabicpop.stream.laut.fm/arabicpop", "https://images.unsplash.com/photo-1534234828563-025217188d73?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p012", "Turkish Pop", "Turkish Pop", "Istanbul", "https://turkishpop.stream.laut.fm/turkishpop", "https://images.unsplash.com/photo-1526666923127-b2970f64b422?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p013", "Afrobeats", "Afrobeats", "Lagos", "https://afrobeats.stream.laut.fm/afrobeats", "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p014", "Dancehall Pop", "Dancehall Pop", "Jamaica", "https://dancehallpop.stream.laut.fm/dancehallpop", "https://images.unsplash.com/photo-1534531173927-aeb928d54385?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p015", "Indie Pop", "Indie Pop", "UK", "https://indiepop.stream.laut.fm/indiepop", "https://images.unsplash.com/photo-1483412033650-1015ddeb83d1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p016", "Synth Pop", "Synth Pop", "Berlin", "https://synthpop.stream.laut.fm/synthpop", "https://images.unsplash.com/photo-1557672172-298e090bd0f1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p017", "Electropop", "Electropop", "Sweden", "https://electropop.stream.laut.fm/electropop", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p018", "Dream Pop", "Dream Pop", "USA", "https://dreampop.stream.laut.fm/dreampop", "https://images.unsplash.com/photo-1519834785169-98be25ec3f84?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p019", "Hyperpop", "Hyperpop", "Internet", "https://hyperpop.stream.laut.fm/hyperpop", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("p020", "TikTok Hits", "Viral", "Internet", "https://tiktok.stream.laut.fm/tiktok", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
            )
        ),

        // HIP HOP / RAP / R&B - 20 stations
        StationCategory(
            id = "hiphop",
            name = "Hip Hop & R&B",
            emoji = "🎤",
            stations = listOf(
                RadioStation("h001", "Hip Hop Nation", "Hip Hop", "New York", "https://hiphop.stream.laut.fm/hiphop", "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h002", "Rap Radio", "Rap", "Atlanta", "https://rap.stream.laut.fm/rap", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h003", "Trap Radio", "Trap", "Miami", "https://trap.stream.laut.fm/trap", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h004", "Old School Hip Hop", "Old School", "Los Angeles", "https://oldschoolhiphop.stream.laut.fm/oldschoolhiphop", "https://images.unsplash.com/photo-1525201548942-d8732f6617a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h005", "R&B Radio", "R&B", "Detroit", "https://rb.stream.laut.fm/rb", "https://images.unsplash.com/photo-1496293455970-f8581aae0e3b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h006", "Drill", "Drill", "Chicago", "https://drill.stream.laut.fm/drill", "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h007", "Grime", "Grime", "London", "https://grime.stream.laut.fm/grime", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h008", "Boom Bap", "Boom Bap", "NYC", "https://boombap.stream.laut.fm/boombap", "https://images.unsplash.com/photo-1525201548942-d8732f6617a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h009", "Conscious Rap", "Conscious", "USA", "https://consciousrap.stream.laut.fm/consciousrap", "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h010", "West Coast", "West Coast", "Compton", "https://westcoast.stream.laut.fm/westcoast", "https://images.unsplash.com/photo-1525201548942-d8732f6617a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h011", "East Coast", "East Coast", "NYC", "https://eastcoast.stream.laut.fm/eastcoast", "https://images.unsplash.com/photo-1525201548942-d8732f6617a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h012", "Southern Rap", "Southern", "Atlanta", "https://southernrap.stream.laut.fm/southernrap", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h013", "Alternative R&B", "Alt R&B", "Toronto", "https://alternativerb.stream.laut.fm/alternativerb", "https://images.unsplash.com/photo-1496293455970-f8581aae0e3b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h014", "New Jack Swing", "New Jack", "NYC", "https://newjackswing.stream.laut.fm/newjackswing", "https://images.unsplash.com/photo-1496293455970-f8581aae0e3b?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h015", "G-Funk", "G-Funk", "Long Beach", "https://gfunk.stream.laut.fm/gfunk", "https://images.unsplash.com/photo-1525201548942-d8732f6617a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h016", "Mumble Rap", "Mumble", "Florida", "https://mumblerap.stream.laut.fm/mumblerap", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h017", "UK Rap", "UK Rap", "London", "https://ukrap.stream.laut.fm/ukrap", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h018", "French Rap", "French Rap", "Paris", "https://frenchrap.stream.laut.fm/frenchrap", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h019", "German Rap", "German Rap", "Berlin", "https://germanrap.stream.laut.fm/germanrap", "https://images.unsplash.com/photo-1526666923127-b2970f64b422?auto=format&fit=crop&q=80&w=400"),
                RadioStation("h020", "Latin Rap", "Latin Rap", "Miami", "https://latinrap.stream.laut.fm/latinrap", "https://images.unsplash.com/photo-1533109721025-d1ae7ee7c1e1?auto=format&fit=crop&q=80&w=400"),
            )
        ),

        // CLASSICAL / INSTRUMENTAL - 15 stations
        StationCategory(
            id = "classical",
            name = "Classical",
            emoji = "🎻",
            stations = listOf(
                RadioStation("c001", "Classic FM", "Classical", "London", "https://media-ssl.musicradio.com/ClassicFM", "https://images.unsplash.com/photo-1507838153414-b4b713384a76?auto=format&fit=crop&q=80&w=400"),
                RadioStation("c002", "Radio Swiss Classic", "Classical", "Bern", "https://stream.srg-ssr.ch/m/rsc_de/mp3_128", "https://images.unsplash.com/photo-1558584673-c834fb1cc3ca?auto=format&fit=crop&q=80&w=400"),
                RadioStation("c003", "ABC Classic", "Orchestral", "Sydney", "https://live-radio01.mediahubaustralia.com/ABCCL2", "https://images.unsplash.com/photo-1465847899078-b413929f7120?auto=format&fit=crop&q=80&w=400"),
                RadioStation("c004", "Piano Radio", "Piano", "World", "https://piano.stream.laut.fm/piano", "https://images.unsplash.com/photo-1520523839897-bd0b52f945a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("c005", "Opera Radio", "Opera", "Milan", "https://opera.stream.laut.fm/opera", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&q=80&w=400"),
                RadioStation("c006", "Baroque", "Baroque", "Germany", "https://baroque.stream.laut.fm/baroque", "https://images.unsplash.com/photo-1507838153414-b4b713384a76?auto=format&fit=crop&q=80&w=400"),
                RadioStation("c007", "Chamber Music", "Chamber", "Vienna", "https://chamber.stream.laut.fm/chamber", "https://images.unsplash.com/photo-1465847899078-b413929f7120?auto=format&fit=crop&q=80&w=400"),
                RadioStation("c008", "Romantic", "Romantic", "Russia", "https://romantic.stream.laut.fm/romantic", "https://images.unsplash.com/photo-1507838153414-b4b713384a76?auto=format&fit=crop&q=80&w=400"),
                RadioStation("c009", "Contemporary", "Contemporary", "NYC", "https://contemporary.stream.laut.fm/contemporary", "https://images.unsplash.com/photo-1465847899078-b413929f7120?auto=format&fit=crop&q=80&w=400"),
                RadioStation("c010", "Film Score", "Film Score", "Hollywood", "https://filmscore.stream.laut.fm/filmscore", "https://images.unsplash.com/photo-1485846234645-a62644f84728?auto=format&fit=crop&q=80&w=400"),
                RadioStation("c011", "Video Game Music", "VGM", "Japan", "https://vgm.stream.laut.fm/vgm", "https://images.unsplash.com/photo-1538481199705-c710c4e965fc?auto=format&fit=crop&q=80&w=400"),
                RadioStation("c012", "Cello", "Cello", "World", "https://cello.stream.laut.fm/cello", "https://images.unsplash.com/photo-1507838153414-b4b713384a76?auto=format&fit=crop&q=80&w=400"),
                RadioStation("c013", "Violin", "Violin", "World", "https://violin.stream.laut.fm/violin", "https://images.unsplash.com/photo-1465847899078-b413929f7120?auto=format&fit=crop&q=80&w=400"),
                RadioStation("c014", "Guitar Classical", "Classical Guitar", "Spain", "https://classicalguitar.stream.laut.fm/classicalguitar", "https://images.unsplash.com/photo-1510915361894-db8b60106cb1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("c015", "Pipe Organ", "Organ", "Germany", "https://organ.stream.laut.fm/organ", "https://images.unsplash.com/photo-1507838153414-b4b713384a76?auto=format&fit=crop&q=80&w=400"),
            )
        ),

        // NEWS / TALK / CULTURE - 10 stations
        StationCategory(
            id = "news",
            name = "News & Talk",
            emoji = "📻",
            stations = listOf(
                RadioStation("n001", "NPR", "News", "Washington", "https://npr-ice.streamguys1.com/live.mp3", "https://images.unsplash.com/photo-1504711434969-e33886168fb5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("n002", "BBC World Service", "World News", "London", "https://stream.live.vc.bbcmedia.co.uk/bbc_world_service", "https://images.unsplash.com/photo-1529107386315-e1a2ed48a620?auto=format&fit=crop&q=80&w=400"),
                RadioStation("n003", "Radio France", "Culture", "Paris", "https://stream.radiofrance.fr/franceculture/franceculture.m3u8", "https://images.unsplash.com/photo-1499364615650-ec38552f4f34?auto=format&fit=crop&q=80&w=400"),
                RadioStation("n004", "Deutsche Welle", "International", "Berlin", "https://dw.audiostream.io/dw_radio_1", "https://images.unsplash.com/photo-1557804506-669a67965ba0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("n005", "W Radio Colombia", "News", "Bogota", "https://stream.zeno.fm/7qup0wmsrfeuv", "https://images.unsplash.com/photo-1526666923127-b2970f64b422?auto=format&fit=crop&q=80&w=400"),
                RadioStation("n006", "Caracol Radio", "Sports", "Bogota", "https://stream.zeno.fm/6qup0wmsrfeuv", "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&q=80&w=400"),
                RadioStation("n007", "CNN Radio", "News", "Atlanta", "https://cnn.stream.laut.fm/cnn", "https://images.unsplash.com/photo-1504711434969-e33886168fb5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("n008", "Al Jazeera", "Middle East", "Qatar", "https://aljazeera.stream.laut.fm/aljazeera", "https://images.unsplash.com/photo-1529107386315-e1a2ed48a620?auto=format&fit=crop&q=80&w=400"),
                RadioStation("n009", "Podcast Radio", "Podcasts", "Internet", "https://podcast.stream.laut.fm/podcast", "https://images.unsplash.com/photo-1478737270239-2f02b77fc618?auto=format&fit=crop&q=80&w=400"),
                RadioStation("n010", "Comedy Radio", "Comedy", "New York", "https://comedy.stream.laut.fm/comedy", "https://images.unsplash.com/photo-1585664811087-47f65be1bac4?auto=format&fit=crop&q=80&w=400"),
            )
        ),

        // REGGAE / DUB / SKA - 10 stations
        StationCategory(
            id = "reggae",
            name = "Reggae",
            emoji = "🌴",
            stations = listOf(
                RadioStation("re01", "Reggae King", "Reggae", "Kingston", "https://reggae.stream.laut.fm/reggae", "https://images.unsplash.com/photo-1534531173927-aeb928d54385?auto=format&fit=crop&q=80&w=400"),
                RadioStation("re02", "Bob Marley Radio", "Roots", "Nine Mile", "https://bobmarley.stream.laut.fm/bobmarley", "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?auto=format&fit=crop&q=80&w=400"),
                RadioStation("re03", "Dub Radio", "Dub", "London", "https://dub.stream.laut.fm/dub", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("re04", "Ska Radio", "Ska", "Birmingham", "https://ska.stream.laut.fm/ska", "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&q=80&w=400"),
                RadioStation("re05", "Dancehall Radio", "Dancehall", "Kingston", "https://dancehall.stream.laut.fm/dancehall", "https://images.unsplash.com/photo-1525201548942-d8732f6617a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("re06", "Roots Reggae", "Roots", "Jamaica", "https://rootsreggae.stream.laut.fm/rootsreggae", "https://images.unsplash.com/photo-1534531173927-aeb928d54385?auto=format&fit=crop&q=80&w=400"),
                RadioStation("re07", "Lovers Rock", "Lovers Rock", "UK", "https://loversrock.stream.laut.fm/loversrock", "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?auto=format&fit=crop&q=80&w=400"),
                RadioStation("re08", "Reggaeton", "Reggaeton", "Puerto Rico", "https://reggaeton.stream.laut.fm/reggaeton", "https://images.unsplash.com/photo-1533109721025-d1ae7ee7c1e1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("re09", "Soca", "Soca", "Trinidad", "https://soca.stream.laut.fm/soca", "https://images.unsplash.com/photo-1525201548942-d8732f6617a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("re10", "Calypso", "Calypso", "Trinidad", "https://calypso.stream.laut.fm/calypso", "https://images.unsplash.com/photo-1525201548942-d8732f6617a0?auto=format&fit=crop&q=80&w=400"),
            )
        ),

        // COUNTRY / FOLK - 10 stations
        StationCategory(
            id = "country",
            name = "Country",
            emoji = "🤠",
            stations = listOf(
                RadioStation("co01", "Nashville FM", "Country", "Nashville", "https://country.stream.laut.fm/country", "https://images.unsplash.com/photo-1500534314209-a25ddb2bd429?auto=format&fit=crop&q=80&w=400"),
                RadioStation("co02", "Classic Country", "Classic", "Texas", "https://classiccountry.stream.laut.fm/classiccountry", "https://images.unsplash.com/photo-1519834785169-98be25ec3f84?auto=format&fit=crop&q=80&w=400"),
                RadioStation("co03", "Bluegrass", "Bluegrass", "Kentucky", "https://bluegrass.stream.laut.fm/bluegrass", "https://images.unsplash.com/photo-1459749411177-0473ef716175?auto=format&fit=crop&q=80&w=400"),
                RadioStation("co04", "Americana", "Americana", "Austin", "https://americana.stream.laut.fm/americana", "https://images.unsplash.com/photo-1483412033650-1015ddeb83d1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("co05", "Folk Radio", "Folk", "Dublin", "https://folk.stream.laut.fm/folk", "https://images.unsplash.com/photo-1459749411177-0473ef716175?auto=format&fit=crop&q=80&w=400"),
                RadioStation("co06", "Indie Folk", "Indie Folk", "Portland", "https://indiefolk.stream.laut.fm/indiefolk", "https://images.unsplash.com/photo-1483412033650-1015ddeb83d1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("co07", "Outlaw Country", "Outlaw", "Texas", "https://outlaw.stream.laut.fm/outlaw", "https://images.unsplash.com/photo-1500534314209-a25ddb2bd429?auto=format&fit=crop&q=80&w=400"),
                RadioStation("co08", "Red Dirt", "Red Dirt", "Oklahoma", "https://reddirt.stream.laut.fm/reddirt", "https://images.unsplash.com/photo-1500534314209-a25ddb2bd429?auto=format&fit=crop&q=80&w=400"),
                RadioStation("co09", "Honky Tonk", "Honky Tonk", "Nashville", "https://honkytonk.stream.laut.fm/honkytonk", "https://images.unsplash.com/photo-1500534314209-a25ddb2bd429?auto=format&fit=crop&q=80&w=400"),
                RadioStation("co10", "Western", "Western", "Arizona", "https://western.stream.laut.fm/western", "https://images.unsplash.com/photo-1500534314209-a25ddb2bd429?auto=format&fit=crop&q=80&w=400"),
            )
        ),

        // WORLD / ETHNIC - 10 stations
        StationCategory(
            id = "world",
            name = "World Music",
            emoji = "🌍",
            stations = listOf(
                RadioStation("w001", "Radio Oriental", "Arabic", "Cairo", "https://radiooriental.stream.laut.fm/radiooriental", "https://images.unsplash.com/photo-1534234828563-025217188d73?auto=format&fit=crop&q=80&w=400"),
                RadioStation("w002", "Bollywood Radio", "Bollywood", "Mumbai", "https://bollywood.stream.laut.fm/bollywood", "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&q=80&w=400"),
                RadioStation("w003", "Afrobeat", "Afrobeat", "Lagos", "https://afrobeat.stream.laut.fm/afrobeat", "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?auto=format&fit=crop&q=80&w=400"),
                RadioStation("w004", "Tango", "Tango", "Buenos Aires", "https://tango.stream.laut.fm/tango", "https://images.unsplash.com/photo-1504609773096-104ff2c73ba4?auto=format&fit=crop&q=80&w=400"),
                RadioStation("w005", "Flamenco", "Flamenco", "Seville", "https://flamenco.stream.laut.fm/flamenco", "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?auto=format&fit=crop&q=80&w=400"),
                RadioStation("w006", "Celtic", "Celtic", "Dublin", "https://celtic.stream.laut.fm/celtic", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("w007", "Samba", "Samba", "Rio", "https://samba.stream.laut.fm/samba", "https://images.unsplash.com/photo-1520523839897-bd0b52f945a0?auto=format&fit=crop&q=80&w=400"),
                RadioStation("w008", "Turkish", "Turkish", "Istanbul", "https://turkish.stream.laut.fm/turkish", "https://images.unsplash.com/photo-1526666923127-b2970f64b422?auto=format&fit=crop&q=80&w=400"),
                RadioStation("w009", "Greek", "Greek", "Athens", "https://greek.stream.laut.fm/greek", "https://images.unsplash.com/photo-1533109721025-d1ae7ee7c1e1?auto=format&fit=crop&q=80&w=400"),
                RadioStation("w010", "Indian Classical", "Indian Classical", "Delhi", "https://indianclassical.stream.laut.fm/indianclassical", "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&q=80&w=400"),
            )
        ),

        // SPORTS - 5 stations
        StationCategory(
            id = "sports",
            name = "Sports",
            emoji = "⚽",
            stations = listOf(
                RadioStation("s001", "ESPN Radio", "Sports", "Bristol", "https://espn.stream.laut.fm/espn", "https://images.unsplash.com/photo-1574629810360-7efbbe195018?auto=format&fit=crop&q=80&w=400"),
                RadioStation("s002", "TalkSport", "Sports", "London", "https://radio.talksport.com/stream", "https://images.unsplash.com/photo-1560272564-c83b66b1ad12?auto=format&fit=crop&q=80&w=400"),
                RadioStation("s003", "Radio Marca", "Futbol", "Madrid", "https://radiomarca.stream.laut.fm/radiomarca", "https://images.unsplash.com/photo-1522778119026-d647f0565c6a?auto=format&fit=crop&q=80&w=400"),
                RadioStation("s004", "Caracol Deportes", "Futbol Colombia", "Bogota", "https://caracol.stream.laut.fm/caracol", "https://images.unsplash.com/photo-1517466787929-bc90951d0974?auto=format&fit=crop&q=80&w=400"),
                RadioStation("s005", "Fox Sports", "Sports", "USA", "https://foxsports.stream.laut.fm/foxsports", "https://images.unsplash.com/photo-1574629810360-7efbbe195018?auto=format&fit=crop&q=80&w=400"),
            )
        ),

        // CHRISTIAN / GOSPEL - 5 stations
        StationCategory(
            id = "christian",
            name = "Christian",
            emoji = "✝️",
            stations = listOf(
                RadioStation("ch01", "K-Love", "Christian Pop", "Nashville", "https://klove.stream.laut.fm/klove", "https://images.unsplash.com/photo-1504052434569-70ad5836ab65?auto=format&fit=crop&q=80&w=400"),
                RadioStation("ch02", "Gospel Radio", "Gospel", "Atlanta", "https://gospel.stream.laut.fm/gospel", "https://images.unsplash.com/photo-1519834785169-98be25ec3f84?auto=format&fit=crop&q=80&w=400"),
                RadioStation("ch03", "Hillsong", "Worship", "Sydney", "https://hillsong.stream.laut.fm/hillsong", "https://images.unsplash.com/photo-1493225255756-d9584f8606e5?auto=format&fit=crop&q=80&w=400"),
                RadioStation("ch04", "Catholic Radio", "Catholic", "Vatican", "https://catholic.stream.laut.fm/catholic", "https://images.unsplash.com/photo-1548625149-fc4a29cf7092?auto=format&fit=crop&q=80&w=400"),
                RadioStation("ch05", "Praise", "Praise", "USA", "https://praise.stream.laut.fm/praise", "https://images.unsplash.com/photo-1504052434569-70ad5836ab65?auto=format&fit=crop&q=80&w=400"),
            )
        ),
    )

    fun search(query: String): List<RadioStation> {
        val q = query.lowercase()
        return allStations.filter {
            it.name.lowercase().contains(q) ||
            it.genre.lowercase().contains(q) ||
            it.location.lowercase().contains(q)
        }
    }

    fun byCategory(categoryId: String): List<RadioStation> {
        return categories.find { it.id == categoryId }?.stations ?: emptyList()
    }
}
