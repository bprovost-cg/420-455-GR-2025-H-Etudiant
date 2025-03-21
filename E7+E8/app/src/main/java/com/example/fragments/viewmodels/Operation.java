package com.example.fragments.viewmodels;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Operation {
    public final OperationType type;
    public final int valeurResultat;
    public final String heure;

    public Operation(OperationType type, int valeurResultat) {
        this.type = type;
        this.valeurResultat = valeurResultat;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        this.heure = sdf.format(new Date());
    }
}
