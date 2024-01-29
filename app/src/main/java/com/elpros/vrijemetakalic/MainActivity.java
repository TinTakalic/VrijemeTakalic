package com.elpros.vrijemetakalic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwabenaberko.openweathermaplib.constant.Languages;
import com.kwabenaberko.openweathermaplib.constant.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView city;
    private TextView temp;
    private TextView wind;
    private TextView desc;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = findViewById(R.id.cityName);
        temp = findViewById(R.id.temperature);
        desc = findViewById(R.id.weatherDesc);
        wind = findViewById(R.id.windSpeed);
        img = findViewById(R.id.weatherImage);

        OpenWeatherMapHelper helper = new OpenWeatherMapHelper("d3a5fc6422a215b8cb6946de4080673b");
        helper.setUnits(Units.METRIC);
        helper.setLanguage(Languages.CROATIAN);
        helper.getCurrentWeatherByCityName("Osijek", new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                city.setText(currentWeather.getName()+", "+currentWeather.getSys().getCountry());
                temp.setText(currentWeather.getMain().getTemp()+"°C");
                desc.setText(currentWeather.getWeather().get(0).getDescription()+", osjećaj: "+Math.round(currentWeather.getMain().getFeelsLike())+"°");
                wind.setText("vjetar: "+currentWeather.getWind().getSpeed()+" m/s\n tlak: "+Math.round(currentWeather.getMain().getPressure())+" hPa\n vlaga: "+Math.round(currentWeather.getMain().getHumidity())+"%");
                switch(currentWeather.getWeather().get(0).getId()+""){
                    case "800":
                        img.setImageResource(R.drawable.clear);
                        break;
                    case "801":
                    case "802":
                        img.setImageResource(R.drawable.clouds);
                        break;
                    case "803":
                    case "804":
                    case "741":
                    case "701":
                    case "721":
                    case "711":
                        img.setImageResource(R.drawable.overcast);
                        break;
                    case "300":
                    case "301":
                    case "302":
                    case "310":
                    case "311":
                    case "312":
                    case "313":
                    case "314":
                    case "321":
                    case "500":
                    case "501":
                    case "502":
                    case "503":
                    case "504":
                    case "511":
                    case "520":
                    case "521":
                    case "522":
                    case "531":
                        img.setImageResource(R.drawable.rain);
                        break;
                    case "210":
                    case "211":
                    case "212":
                    case "221":
                        img.setImageResource(R.drawable.thunder);
                        break;
                    case "200":
                    case "201":
                    case "202":
                    case "230":
                    case "231":
                    case "232":
                        img.setImageResource(R.drawable.thunderstorm_rain);
                        break;
                    case "600":
                    case "601":
                    case "602":
                    case "611":
                    case "612":
                    case "613":
                    case "615":
                    case "616":
                    case "620":
                    case "621":
                    case "622":
                        img.setImageResource(R.drawable.snow);
                        break;
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.v("CANTGETDATA", throwable.getMessage());
            }
        });
    }
}