package com.example.appclubtenis.Helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import java.util.Locale;

public class LanguageLocale {
//Permitimos cambiar el idioma en tiempo de ejecución
    public static Context setLocale(Context context, String language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }
        return updateResources(context, language);
    }


    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);

        LocaleList localeList = new LocaleList(locale);
        LocaleList.setDefault(localeList);
        config.setLocales(localeList);

        return context.createConfigurationContext(config);
    }

}
