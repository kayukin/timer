package com.kayukin.timer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayukin.timer.service.ClockifyClient;
import com.kayukin.timer.service.ClockifyService;
import com.kayukin.timer.service.DBusHandler;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.cinnamon.ScreenSaver;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusSigHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class AppConfig {
    private static final String X_API_KEY = "X-Api-Key";
    private static final String API_CLOCKIFY = "https://api.clockify.me/";

    @Bean
    public DBusConnection dBusConnection(DBusSigHandler<ScreenSaver.ActiveChanged> handler) throws DBusException {
        DBusConnection connection = DBusConnection.getConnection(DBusConnection.DBusBusType.SESSION);
        connection.addSigHandler(ScreenSaver.ActiveChanged.class, handler);
        return connection;
    }

    @Bean
    public DBusSigHandler<ScreenSaver.ActiveChanged> handler(ClockifyService clockifyService) {
        return new DBusHandler(clockifyService);
    }

    @Bean
    public ClockifyService clockifyService(ClockifyClient clockifyClient) {
        return new ClockifyService(clockifyClient);
    }

    @Bean
    public ClockifyClient clockifyClient(ObjectMapper objectMapper,
                                         @Value("${clockify.api-key}") String apiKey) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader(X_API_KEY, apiKey)
                            .build();
                    return chain.proceed(request);
                })
                //.addInterceptor(interceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(API_CLOCKIFY)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build()
                .create(ClockifyClient.class);
    }
}
