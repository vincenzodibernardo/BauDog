package com.example.baudog


var uriImage    : String? = ""
var Nome        : String? = ""
var Cognome     : String? = ""
var Sesso       : String? = ""
var Eta         : String? = ""
var Email       : String? = ""
var Username    : String? = ""
var Cellulare   : String? = ""
var Id          : String? = ""

var logged      : String? = ""





fun Logged (AZIONE          : String,
            takeNome        : String?,
            takeCognome     : String?,
            takeSesso       : String?,
            takeEta         : String?,
            takeEmail       : String?,
            takeUsername    : String?,
            takeCellulare   : String?,
            takeId          : String?,
            takeUriImage    : String?
                                        ) : String?
{
    if(AZIONE=="LOGIN")
    {


        Nome        = takeNome
        Cognome     = takeCognome
        Sesso       = takeSesso
        Eta         = takeEta
        Email       = takeEmail
        Username    = takeUsername
        Cellulare   = takeCellulare
        Id          = takeId
        uriImage    = takeUriImage

        logged      = "Y"


    }

    if(AZIONE=="LOGOUT")
    {


        Nome        = ""
        Cognome     = ""
        Sesso       = ""
        Eta         = ""
        Email       = ""
        Username    = ""
        Cellulare   = ""
        Id          = ""
        uriImage    = ""

        logged      = "N"


    }


    if(AZIONE=="NOME")
        return Nome

    if(AZIONE=="COGNOME")
        return Cognome

    if(AZIONE=="SESSO")
        return Sesso

    if(AZIONE=="ETA")
        return Eta

    if(AZIONE=="EMAIL")
        return Email

    if(AZIONE=="USERNAME")
        return Username

    if(AZIONE=="CELLULARE")
        return Cellulare

    if(AZIONE=="ID")
        return Id

    if(AZIONE=="URIIMAGE")
        return uriImage

    if (AZIONE=="LOGGED")
        return logged








    return null

}



