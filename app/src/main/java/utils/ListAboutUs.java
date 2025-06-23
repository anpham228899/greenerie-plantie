package utils;

import java.util.ArrayList;
import models.AboutUs;

public class ListAboutUs {
    public static ArrayList<AboutUs> getAboutUsList() {
        ArrayList<AboutUs> aboutUsList = new ArrayList<>();

        aboutUsList.add(new AboutUs("Greenerie is your all-in-one plant care assistant. From watering reminders to expert tips, we help you keep your plants thriving and your spaces blooming. Whether you’re a beginner or a botanical pro, we’re here to support your green journey."));
        aboutUsList.add(new AboutUs("We imagine a future where every home and city corner is alive with nature. At Greenerie, we believe plant care is self-care—and a step toward a healthier, more sustainable planet."));
        aboutUsList.add(new AboutUs("We’re here to help you grow:\n" +
                "• Nurture – Smart tools to care for your plants effortlessly\n" +
                "• Educate – Clear, expert-backed guidance for every plant lover\n" +
                "• Inspire – Eco-friendly habits that benefit you and the Earth\n" +
                "• Connect – Join a global movement of nature-minded growers"));
        return aboutUsList;
    }
}
